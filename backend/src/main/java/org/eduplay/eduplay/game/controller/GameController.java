package org.eduplay.eduplay.game.controller;


import org.eduplay.eduplay.entity.*;
import org.eduplay.eduplay.enums.*;
import org.eduplay.eduplay.game.service.QuestionGeneratorService;
import org.eduplay.eduplay.game.service.SmartQuestionService;
import org.eduplay.eduplay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final QuestionGeneratorService questionGeneratorService;
    private final SmartQuestionService smartQuestionService;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final UserQuestionHistoryRepository historyRepository;

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions(
            @RequestParam Subject subject,
            @RequestParam Difficulty difficulty,
            @RequestParam(required = false) AppLanguage language,
            Authentication auth) {

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        int classLevel = user.getClassLevel() == null ? 1 : user.getClassLevel();
        AppLanguage effectiveLanguage = language != null
                ? language
                : (user.getLanguage() == null ? AppLanguage.FRENCH : user.getLanguage());

        if (user.getLanguage() != effectiveLanguage) {
            user.setLanguage(effectiveLanguage);
            userRepository.save(user);
        }

        // Utiliser SmartQuestionService pour une sélection intelligente
        List<Question> questions = smartQuestionService.selectQuestionsForUser(
            user.getId(), classLevel, subject, difficulty, effectiveLanguage
        );

        // Si pas assez de questions, fallback sur QuestionGeneratorService (Ollama)
        if (questions.size() < 10) {
            try {
                List<Question> generated = questionGeneratorService
                    .generateQuestions(classLevel, subject, difficulty, effectiveLanguage);
                if (generated != null && !generated.isEmpty()) {
                    questions = generated;
                }
            } catch (Exception e) {
                // Garder les questions existantes même si moins de 10
            }
        }

        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitSession(
            @RequestBody Map<String, Object> payload,
            Authentication auth) {

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        int correct = toInt(payload.get("correctAnswers"), "correctAnswers");
        int total   = toInt(payload.get("totalQuestions"), "totalQuestions");
        int xp      = correct * 10;

        // Bonus streak
        user.setStreak(user.getStreak() + 1);
        if (user.getStreak() % 5 == 0) xp += 50;

        user.setTotalXp(user.getTotalXp() + xp);
        userRepository.save(user);

        Subject subject = Subject.valueOf((String) payload.get("subject"));
        Score score = Score.builder()
                .user(user)
                .subject(subject)
                .points(xp)
                .build();
        scoreRepository.save(score);

        // Enregistrer l'historique des réponses si fourni
        @SuppressWarnings("unchecked")
        List<String> answers = payload.get("answers") != null 
            ? (List<String>) payload.get("answers") 
            : new ArrayList<>();
        
        if (!answers.isEmpty()) {
            // Récupérer les questions de la session (devrait être passé dans le payload)
            // Pour l'instant, on enregistre juste le score global
        }

        return ResponseEntity.ok(Map.of(
                "xpEarned", xp,
                "totalXp", user.getTotalXp(),
                "streak", user.getStreak(),
                "correctAnswers", correct,
                "totalQuestions", total
        ));
    }

    @GetMapping("/diagnostic")
    public ResponseEntity<Map<String, Object>> qualityDiagnostic(
            @RequestParam Subject subject,
            @RequestParam Difficulty difficulty,
            @RequestParam(defaultValue = "FRENCH") AppLanguage language) {
        
        List<Question> questions = smartQuestionService.selectQuestions(
            1, subject, difficulty, language
        );

        int totalQuestions = questions.size();
        int withFourChoices = 0;
        int validCorrectChoice = 0;
        int endsWithQuestionMark = 0;
        int hasExplanation = 0;
        int allFieldsFilled = 0;
        int qualityScoreTotal = 0;
        int lowConfidence = 0;
        Set<String> topics = new HashSet<>();

        for (Question q : questions) {
            Set<String> choices = new HashSet<>();
            if (q.getChoiceA() != null && !q.getChoiceA().isBlank()) choices.add(q.getChoiceA());
            if (q.getChoiceB() != null && !q.getChoiceB().isBlank()) choices.add(q.getChoiceB());
            if (q.getChoiceC() != null && !q.getChoiceC().isBlank()) choices.add(q.getChoiceC());
            if (q.getChoiceD() != null && !q.getChoiceD().isBlank()) choices.add(q.getChoiceD());

            if (choices.size() == 4) withFourChoices++;

            String correct = q.getCorrectChoice();
            if (correct != null && (correct.equals("A") || correct.equals("B") || correct.equals("C") || correct.equals("D"))) {
                validCorrectChoice++;
            }

            if (q.getQuestionText() != null && (q.getQuestionText().endsWith("?") || q.getQuestionText().endsWith("؟"))) {
                endsWithQuestionMark++;
            }

            if (q.getExplanation() != null && !q.getExplanation().isBlank()) {
                hasExplanation++;
            }

                        Integer qualityScore = q.getQualityScore();
                        if (qualityScore != null) {
                                qualityScoreTotal += qualityScore;
                                if (qualityScore < 70) {
                                        lowConfidence++;
                                }
                        }

                        if (q.getTopicTag() != null && !q.getTopicTag().isBlank()) {
                                topics.add(q.getTopicTag());
                        }

            if (q.getQuestionText() != null && !q.getQuestionText().isBlank()
                    && q.getChoiceA() != null && !q.getChoiceA().isBlank()
                    && q.getChoiceB() != null && !q.getChoiceB().isBlank()
                    && q.getChoiceC() != null && !q.getChoiceC().isBlank()
                    && q.getChoiceD() != null && !q.getChoiceD().isBlank()
                    && q.getCorrectChoice() != null && !q.getCorrectChoice().isBlank()
                    && q.getExplanation() != null && !q.getExplanation().isBlank()) {
                allFieldsFilled++;
            }
        }

        int qualityScore = (int) (100.0 * allFieldsFilled / Math.max(1, totalQuestions));
        int averageQualityScore = (int) Math.round(qualityScoreTotal / Math.max(1.0, totalQuestions));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("subject", subject);
        response.put("difficulty", difficulty);
        response.put("language", language);
        response.put("totalQuestions", totalQuestions);
        response.put("withFourDistinctChoices", withFourChoices);
        response.put("withValidCorrectChoice", validCorrectChoice);
        response.put("endsWithQuestionMark", endsWithQuestionMark);
        response.put("hasExplanation", hasExplanation);
        response.put("allFieldsFilled", allFieldsFilled);
        response.put("qualityScore%", qualityScore);
        response.put("averageQualityScore", averageQualityScore);
        response.put("lowConfidenceQuestions", lowConfidence);
        response.put("distinctTopics", topics.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserStats(Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        Map<Subject, Double> successRates = smartQuestionService.getSuccessRatesBySubject(user.getId());
        
        // Convert Map<Subject, Double> to Map<String, Double> for JSON serialization
        Map<String, Double> successRatesString = new HashMap<>();
        for (Map.Entry<Subject, Double> entry : successRates.entrySet()) {
            successRatesString.put(entry.getKey().name(), entry.getValue());
        }
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalQuestionsAnswered", historyRepository.countByUserId(user.getId()));
        response.put("totalCorrectAnswers", historyRepository.countByUserIdAndIsCorrectTrue(user.getId()));
        response.put("successRatesBySubject", successRatesString);

        return ResponseEntity.ok(response);
    }

        private int toInt(Object value, String fieldName) {
                if (value instanceof Number number) {
                        return number.intValue();
                }
                if (value instanceof String text && !text.isBlank()) {
                        return Integer.parseInt(text);
                }
                throw new IllegalArgumentException("Champ invalide: " + fieldName);
        }
}