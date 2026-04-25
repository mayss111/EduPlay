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
        log.info("Sélection intelligente: user={}, class={}, subject={}, difficulty={}", 
                 userId, classLevel, subject, difficulty);

        // On remonte très loin (10 ans) pour exclure TOUTES les questions déjà répondues
        LocalDateTime daysAgo = LocalDateTime.now().minusYears(10);
        
        // 1. Essayer de trouver des questions jamais vues ou pas récemment
        List<QuestionBank> questions = questionBankRepository.findLeastUsedNotRecentlySeen(
            userId, subject, classLevel, difficulty, language, daysAgo
        );
        Collections.shuffle(questions);

        // 2. Si pas assez, prendre les moins utilisées globalement (MAIS TOUJOURS PAS VUES)
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Pas assez de questions uniques, recherche approfondie dans la base...");
            List<QuestionBank> leastUsed = questionBankRepository.findLeastUsed(
                subject, classLevel, difficulty, language
            );
            
            Set<Long> seenIds = historyRepository.findByUserId(userId).stream()
                .map(UserQuestionHistory::getQuestionId)
                .collect(Collectors.toSet());
            
            Set<Long> currentIds = questions.stream()
                .map(QuestionBank::getId)
                .collect(Collectors.toSet());
            
            for (QuestionBank qb : leastUsed) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                if (!seenIds.contains(qb.getId()) && !currentIds.contains(qb.getId())) {
                    questions.add(qb);
                    currentIds.add(qb.getId());
                }
            }
        }

        // 3. Si toujours pas assez, on accepte de répéter les MOINS vues (en dernier recours)
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Stock épuisé, inclusion des questions les moins récemment vues");
            List<QuestionBank> fallback = questionBankRepository.findLeastUsed(
                subject, classLevel, difficulty, language
            );
            Collections.shuffle(fallback);
            
            Set<Long> currentIds = questions.stream()
                .map(QuestionBank::getId)
                .collect(Collectors.toSet());
                
            for (QuestionBank qb : fallback) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                if (!currentIds.contains(qb.getId())) {
                    questions.add(qb);
                    currentIds.add(qb.getId());
                }
            }
        }

        // 3. Si toujours pas assez, prendre aléatoirement
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Fallback sur sélection aléatoire");
            List<QuestionBank> random = questionBankRepository.findRandom(
                subject.name(), 
                classLevel, 
                difficulty.name(), 
                language.name(), 
                TARGET_QUESTION_COUNT - questions.size()
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
            Collections.shuffle(random);
        }

        // 4. Convertir en entité Question
        List<Question> result = questions.stream()
            .limit(TARGET_QUESTION_COUNT)
            .map(this::convertToQuestion)
            .collect(Collectors.toList());

        log.info("Questions sélectionnées: {}/{}", result.size(), TARGET_QUESTION_COUNT);
        return result;
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

    /**
     * Adapte la difficulté selon les performances
     */
    public Difficulty adjustDifficulty(Long userId, Subject subject, Difficulty currentDifficulty) {
        Double successRate = historyRepository.getSuccessRateBySubject(userId, subject.name());
        
        if (successRate == null) return currentDifficulty;

        if (successRate > 0.8) {
            return nextDifficulty(currentDifficulty);
        } else if (successRate < 0.4) {
            return prevDifficulty(currentDifficulty);
        }
        
        return currentDifficulty;
    }

    private Difficulty nextDifficulty(Difficulty d) {
        return switch (d) {
            case SIMPLE -> Difficulty.MOYEN;
            case MOYEN -> Difficulty.DIFFICILE;
            case DIFFICILE -> Difficulty.EXCELLENT;
            case EXCELLENT -> Difficulty.EXCELLENT;
        };
    }

    private Difficulty prevDifficulty(Difficulty d) {
        return switch (d) {
            case SIMPLE -> Difficulty.SIMPLE;
            case MOYEN -> Difficulty.SIMPLE;
            case DIFFICILE -> Difficulty.MOYEN;
            case EXCELLENT -> Difficulty.DIFFICILE;
        };
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