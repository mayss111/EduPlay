package org.eduplay.eduplay.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.entity.QuestionBank;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.eduplay.eduplay.repository.QuestionBankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Service de génération de questions - Utilise uniquement la base de données
 * Plus de dépendance à Ollama pour une solution 100% gratuite et fiable
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionGeneratorService {

    private static final int TARGET_QUESTION_COUNT = 10;
    private final QuestionBankRepository questionBankRepository;

    /**
     * Génère des questions pour un profil donné en les récupérant de la base de données
     */
    @Transactional(readOnly = true)
    public List<Question> generateQuestions(int classLevel,
                                            Subject subject,
                                            Difficulty difficulty,
                                            AppLanguage language) {
        log.info("Génération questions: classe={} matiere={} niveau={} langue={}", 
                 classLevel, subject, difficulty, language);

        List<Question> questions = new ArrayList<>();

        // 1. Essayer de trouver des questions exactes pour ce profil
        List<QuestionBank> exactMatches = questionBankRepository.findLeastUsed(
            subject, classLevel, difficulty, language
        );
        
        for (QuestionBank qb : exactMatches) {
            if (questions.size() >= TARGET_QUESTION_COUNT) break;
            questions.add(convertToQuestion(qb));
        }

        // 2. Si pas assez, élargir à d'autres difficultés
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Pas assez de questions exactes, élargissement aux autres difficultés");
            List<Difficulty> difficulties = getSimilarDifficulties(difficulty);
            
            for (Difficulty d : difficulties) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                
                List<QuestionBank> similar = questionBankRepository.findLeastUsed(
                    subject, classLevel, d, language
                );
                
                for (QuestionBank qb : similar) {
                    if (questions.size() >= TARGET_QUESTION_COUNT) break;
                    if (!alreadyAdded(questions, qb)) {
                        questions.add(convertToQuestion(qb));
                    }
                }
            }
        }

        // 3. Si toujours pas assez, prendre n'importe quelles questions de la matière
        if (questions.size() < TARGET_QUESTION_COUNT) {
            log.info("Élargissement à toute la matière");
            List<QuestionBank> anySubject = questionBankRepository.findBySubjectAndClassLevel(
                subject, classLevel
            );
            
            for (QuestionBank qb : anySubject) {
                if (questions.size() >= TARGET_QUESTION_COUNT) break;
                if (!alreadyAdded(questions, qb)) {
                    questions.add(convertToQuestion(qb));
                }
            }
        }

        // 4. Mélanger pour la variété
        Collections.shuffle(questions, new Random());

        // 5. Mélanger les choix dans chaque question
        for (Question q : questions) {
            shuffleChoices(q);
        }

        log.info("Questions générées: {}/{}", questions.size(), TARGET_QUESTION_COUNT);
        
        // Si on n'a toujours pas assez, on complète avec des questions par défaut
        while (questions.size() < TARGET_QUESTION_COUNT) {
            questions.add(createEmergencyQuestion(subject, classLevel, language));
        }

        return questions;
    }

    /**
     * Convertit une QuestionBank en Question
     */
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
            .build();
    }

    /**
     * Vérifie si une question est déjà dans la liste
     */
    private boolean alreadyAdded(List<Question> questions, QuestionBank qb) {
        return questions.stream()
            .anyMatch(q -> q.getQuestionText().equals(qb.getQuestionText()));
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
            if (choice.equals(correctText)) {
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
            .build();
    }
}