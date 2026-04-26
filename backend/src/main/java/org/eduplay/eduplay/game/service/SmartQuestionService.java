package org.eduplay.eduplay.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.entity.QuestionBank;
import org.eduplay.eduplay.entity.UserQuestionHistory;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.eduplay.eduplay.repository.QuestionBankRepository;
import org.eduplay.eduplay.repository.UserQuestionHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartQuestionService {

    private final QuestionBankRepository questionBankRepository;
    private final UserQuestionHistoryRepository historyRepository;

    private static final int TARGET_QUESTION_COUNT = 10;
    private static final int DAYS_TO_AVOID_REPEAT = 7; // Éviter les questions vues dans les 7 derniers jours

    /**
     * Sélectionne intelligemment des questions pour un utilisateur
     * En évitant les répétitions et en adaptant la difficulté
     */
    @Transactional(readOnly = true)
    public List<Question> selectQuestionsForUser(Long userId, int classLevel, Subject subject, 
                                                  Difficulty difficulty, AppLanguage language) {
        log.info("Sélection intelligente: user={}, class={}, subject={}, difficulty={}, lang={}", 
                 userId, classLevel, subject, difficulty, language);

        // 0. ADAPTATIVE DIFFICULTY
        Difficulty effectiveDifficulty = adjustDifficultyBasedOnPerformance(userId, subject, difficulty);
        if (effectiveDifficulty != difficulty) {
            log.info("Difficulté adaptée par performance: {} -> {}", difficulty, effectiveDifficulty);
        }

        // On remonte très loin pour exclure les questions déjà répondues
        LocalDateTime daysAgo = LocalDateTime.now().minusYears(10);
        
        // 1. Essayer de trouver des questions jamais vues
        List<QuestionBank> rawQuestions = questionBankRepository.findLeastUsedNotRecentlySeen(
            userId, subject, classLevel, effectiveDifficulty, language, daysAgo
        );
        log.info("Phase 1: {} questions trouvées pour {}-{}-{}", rawQuestions.size(), subject, effectiveDifficulty, language);
        
        Set<String> currentTexts = new HashSet<>();
        Set<Long> currentIds = new HashSet<>();
        List<QuestionBank> questions = new ArrayList<>();
        
        for (QuestionBank qb : rawQuestions) {
            if (questions.size() >= TARGET_QUESTION_COUNT) break;
            String normalizedText = qb.getQuestionText().trim().toLowerCase();
            if (!currentTexts.contains(normalizedText)) {
                questions.add(qb);
                currentTexts.add(normalizedText);
                currentIds.add(qb.getId());
            }
        }
        
        // 2. Si pas assez, élargir aux difficultés similaires
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Phase 2: Élargissement aux difficultés similaires (Actuel: {})", questions.size());
            List<Difficulty> similarDiffs = getSimilarDifficulties(effectiveDifficulty);
            for (Difficulty d : similarDiffs) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                if (d == effectiveDifficulty) continue;

                List<QuestionBank> similar = questionBankRepository.findLeastUsed(subject, classLevel, d, language);
                log.info("  Essai difficulté {}: {} questions trouvées", d, similar.size());
                
                Set<Long> seenIds = historyRepository.findByUserId(userId).stream()
                    .map(UserQuestionHistory::getQuestionId)
                    .collect(Collectors.toSet());

                for (QuestionBank qb : similar) {
                    if (questions.size() >= TARGET_QUESTION_COUNT) break;
                    String normalizedText = qb.getQuestionText().trim().toLowerCase();
                    if (!seenIds.contains(qb.getId()) && !currentIds.contains(qb.getId()) && !currentTexts.contains(normalizedText)) {
                        questions.add(qb);
                        currentIds.add(qb.getId());
                        currentTexts.add(normalizedText);
                    }
                }
            }
        }

        // 3. Si toujours pas assez, élargir aux autres langues (en dernier recours avant répétition)
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Phase 3: Élargissement aux autres langues (Actuel: {})", questions.size());
            for (AppLanguage l : AppLanguage.values()) {
                if (l == language) continue;
                if (questions.size() >= TARGET_QUESTION_COUNT) break;

                List<QuestionBank> otherLang = questionBankRepository.findLeastUsed(subject, classLevel, Difficulty.SIMPLE, l);
                log.info("  Essai langue {}: {} questions trouvées", l, otherLang.size());
                
                Set<Long> seenIds = historyRepository.findByUserId(userId).stream()
                    .map(UserQuestionHistory::getQuestionId)
                    .collect(Collectors.toSet());

                for (QuestionBank qb : otherLang) {
                    if (questions.size() >= TARGET_QUESTION_COUNT) break;
                    String normalizedText = qb.getQuestionText().trim().toLowerCase();
                    if (!seenIds.contains(qb.getId()) && !currentIds.contains(qb.getId()) && !currentTexts.contains(normalizedText)) {
                        questions.add(qb);
                        currentIds.add(qb.getId());
                        currentTexts.add(normalizedText);
                    }
                }
            }
        }

        // 4. Si toujours pas assez, on accepte de répéter les MOINS vues
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Phase 4: Stock épuisé, inclusion des questions déjà vues (Actuel: {})", questions.size());
            // On essaie d'abord la difficulté demandée, puis TOUTES les autres
            List<Difficulty> allDiffs = Arrays.asList(Difficulty.values());
            for (Difficulty d : allDiffs) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                
                List<QuestionBank> fallback = questionBankRepository.findLeastUsed(
                    subject, classLevel, d, language
                );
                log.info("  Répétition diff {}: {} questions disponibles", d, fallback.size());
                Collections.shuffle(fallback);
                
                for (QuestionBank qb : fallback) {
                    if (questions.size() >= TARGET_QUESTION_COUNT) break;
                    String normalizedText = qb.getQuestionText().trim().toLowerCase();
                    if (!currentIds.contains(qb.getId()) && !currentTexts.contains(normalizedText)) {
                        questions.add(qb);
                        currentIds.add(qb.getId());
                        currentTexts.add(normalizedText);
                    }
                }
            }
        }

        // 5. Mélanger pour la variété
        Collections.shuffle(questions);

        // 6. Convertir en entité Question et mélanger les choix
        List<Question> result = questions.stream()
            .limit(TARGET_QUESTION_COUNT)
            .map(this::convertToQuestion)
            .collect(Collectors.toList());

        for (Question q : result) {
            shuffleChoices(q);
        }

        // 7. Si on n'a toujours pas assez (base vide ?), on complète avec des questions par défaut
        if (result.size() < TARGET_QUESTION_COUNT) {
            log.warn("BASE VIDE ou FILTRES TROP STRICTS: {}/{} questions trouvées. Utilisation questions d'urgence.", 
                     result.size(), TARGET_QUESTION_COUNT);
            while (result.size() < TARGET_QUESTION_COUNT) {
                result.add(createEmergencyQuestion(subject, classLevel, language));
            }
        }

        log.info("Résultat final: {} questions prêtes", result.size());
        return result;
    }

    /**
     * Ajuste la difficulté en fonction du taux de réussite de l'utilisateur sur la matière
     */
    private Difficulty adjustDifficultyBasedOnPerformance(Long userId, Subject subject, Difficulty requested) {
        Double successRate = historyRepository.getSuccessRateBySubject(userId, subject);
        if (successRate == null) return requested;

        if (successRate > 0.85) { // Plus de 85% de réussite -> On monte d'un cran
            return switch (requested) {
                case SIMPLE -> Difficulty.MOYEN;
                case MOYEN -> Difficulty.DIFFICILE;
                case DIFFICILE, EXCELLENT -> Difficulty.EXCELLENT;
            };
        } else if (successRate < 0.40) { // Moins de 40% de réussite -> On descend d'un cran
            return switch (requested) {
                case EXCELLENT -> Difficulty.DIFFICILE;
                case DIFFICILE -> Difficulty.MOYEN;
                case MOYEN, SIMPLE -> Difficulty.SIMPLE;
            };
        }
        return requested;
    }

    /**
     * Retourne les difficultés similaires pour élargir la recherche
     */
    private List<Difficulty> getSimilarDifficulties(Difficulty current) {
        return switch (current) {
            case SIMPLE -> List.of(Difficulty.MOYEN, Difficulty.SIMPLE);
            case MOYEN -> List.of(Difficulty.SIMPLE, Difficulty.DIFFICILE, Difficulty.MOYEN);
            case DIFFICILE -> List.of(Difficulty.MOYEN, Difficulty.EXCELLENT, Difficulty.DIFFICILE);
            case EXCELLENT -> List.of(Difficulty.DIFFICILE, Difficulty.EXCELLENT);
        };
    }

    /**
     * Mélange les choix d'une question et ajuste la bonne réponse
     */
    private void shuffleChoices(Question q) {
        List<String> choices = new ArrayList<>();
        choices.add(q.getChoiceA());
        choices.add(q.getChoiceB());
        choices.add(q.getChoiceC());
        choices.add(q.getChoiceD());

        String correctText = switch (q.getCorrectChoice().toUpperCase()) {
            case "A" -> q.getChoiceA();
            case "B" -> q.getChoiceB();
            case "C" -> q.getChoiceC();
            case "D" -> q.getChoiceD();
            default -> q.getChoiceA();
        };

        Collections.shuffle(choices);

        q.setChoiceA(choices.get(0));
        q.setChoiceB(choices.get(1));
        q.setChoiceC(choices.get(2));
        q.setChoiceD(choices.get(3));

        // Retrouver la nouvelle position de la bonne réponse
        for (int i = 0; i < 4; i++) {
            String choice = switch (i) {
                case 0 -> q.getChoiceA();
                case 1 -> q.getChoiceB();
                case 2 -> q.getChoiceC();
                case 3 -> q.getChoiceD();
                default -> "";
            };
            if (choice != null && choice.equals(correctText)) {
                q.setCorrectChoice(switch (i) {
                    case 0 -> "A";
                    case 1 -> "B";
                    case 2 -> "C";
                    case 3 -> "D";
                    default -> "A";
                });
                break;
            }
        }
    }

    /**
     * Crée une question d'urgence si la base est vide
     */
    private Question createEmergencyQuestion(Subject subject, int classLevel, AppLanguage language) {
        String questionText = language == AppLanguage.ARABIC 
            ? "سؤال طوارئ: ما هو 1 + 1؟" 
            : "Question d'urgence : Combien font 1 + 1 ?";
        
        return Question.builder()
            .questionText(questionText)
            .choiceA("1")
            .choiceB("2")
            .choiceC("3")
            .choiceD("4")
            .correctChoice("B")
            .explanation(language == AppLanguage.ARABIC 
                ? "1 + 1 = 2" 
                : "1 + 1 = 2")
            .subject(subject)
            .classLevel(classLevel)
            .difficulty(Difficulty.SIMPLE)
            .qualityScore(50)
            .appLanguage(language)
            .build();
    }


    /**
     * Sélectionne des questions sans filtre utilisateur (pour diagnostic ou premier usage)
     */
    @Transactional(readOnly = true)
    public List<Question> selectQuestions(int classLevel, Subject subject, 
                                          Difficulty difficulty, AppLanguage language) {
        List<QuestionBank> questions = questionBankRepository.findLeastUsed(
            subject, classLevel, difficulty, language
        );

        if (questions.size() < TARGET_QUESTION_COUNT) {
            List<QuestionBank> random = questionBankRepository.findRandom(
                subject.name(), 
                classLevel, 
                difficulty.name(), 
                language.name(), 
                TARGET_QUESTION_COUNT
            );
            
            Set<Long> existingIds = questions.stream()
                .map(QuestionBank::getId)
                .collect(Collectors.toSet());
            
            for (QuestionBank qb : random) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                if (!existingIds.contains(qb.getId())) {
                    questions.add(qb);
                    existingIds.add(qb.getId());
                }
            }
        }

        return questions.stream()
            .limit(TARGET_QUESTION_COUNT)
            .map(this::convertToQuestion)
            .collect(Collectors.toList());
    }

    /**
     * Enregistre les résultats d'une session et incrémente les compteurs d'usage
     */
    @Transactional
    public void recordQuestionResults(Long userId, List<Map<String, Object>> results) {
        for (Map<String, Object> result : results) {
            try {
                Long questionId = Long.valueOf(result.get("questionId").toString());
                boolean isCorrect = (boolean) result.get("isCorrect");

                // 1. Incrémenter l'usage global
                questionBankRepository.findById(questionId).ifPresent(qb -> {
                    qb.incrementUsage();
                    questionBankRepository.save(qb);
                });

                // 2. Enregistrer l'historique personnel
                UserQuestionHistory history = UserQuestionHistory.builder()
                        .userId(userId)
                        .questionId(questionId)
                        .isCorrect(isCorrect)
                        .answeredAt(LocalDateTime.now())
                        .build();
                historyRepository.save(history);
            } catch (Exception e) {
                log.error("Erreur lors de l'enregistrement du résultat: {}", e.getMessage());
            }
        }
    }

    /**
     * Enregistre l'historique des réponses d'un utilisateur
     */
    @Transactional
    public void recordUserAnswers(Long userId, List<Question> questions, 
                                  List<String> userAnswers) {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String userAnswer = i < userAnswers.size() ? userAnswers.get(i) : "";
            
            // Trouver la question bank correspondante
            Optional<QuestionBank> qbOpt = findQuestionBankByText(q.getQuestionText());
            if (qbOpt.isPresent()) {
                QuestionBank qb = qbOpt.get();
                qb.incrementUsage();
                questionBankRepository.save(qb);

                // Enregistrer l'historique
                UserQuestionHistory history = UserQuestionHistory.builder()
                    .userId(userId)
                    .questionId(qb.getId())
                    .isCorrect(q.getCorrectChoice().equalsIgnoreCase(userAnswer))
                    .build();
                historyRepository.save(history);
            }
        }
    }

    /**
     * Calcule le taux de réussite par matière
     */
    @Transactional(readOnly = true)
    public Map<Subject, Double> getSuccessRatesBySubject(Long userId) {
        Map<Subject, Double> rates = new HashMap<>();
        for (Subject subject : Subject.values()) {
            Double rate = historyRepository.getSuccessRateBySubject(userId, subject.name());
            if (rate != null) {
                rates.put(subject, rate);
            }
        }
        return rates;
    }

    private Question convertToQuestion(QuestionBank qb) {
        return Question.builder()
            .id(qb.getId())
            .questionText(qb.getQuestionText())
            .choiceA(qb.getChoiceA())
            .choiceB(qb.getChoiceB())
            .choiceC(qb.getChoiceC())
            .choiceD(qb.getChoiceD())
            .correctChoice(qb.getCorrectChoice())
            .explanation(qb.getExplanation())
            .subject(qb.getSubject())
            .classLevel(qb.getClassLevel())
            .difficulty(qb.getDifficulty())
            .qualityScore(qb.getQualityScore())
            .topicTag(qb.getTopicTag())
            .appLanguage(qb.getAppLanguage())
            .build();
    }

    private Optional<QuestionBank> findQuestionBankByText(String questionText) {
        return questionBankRepository.findByQuestionText(questionText);
    }
}