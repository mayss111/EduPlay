package org.eduplay.eduplay.game.controller;


import org.eduplay.eduplay.entity.*;
import org.eduplay.eduplay.enums.*;
import org.eduplay.eduplay.game.service.QuestionGeneratorService;
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
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions(
            @RequestParam Subject subject,
            @RequestParam Difficulty difficulty,
            Authentication auth) {

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        int classLevel = user.getClassLevel() == null ? 1 : user.getClassLevel();
        AppLanguage language = user.getLanguage() == null ? AppLanguage.FRENCH : user.getLanguage();

        List<Question> questions = questionGeneratorService
                .generateQuestions(classLevel, subject, difficulty, language);
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
        
        List<Question> questions = questionGeneratorService
                .generateQuestions(1, subject, difficulty, language);

        int totalQuestions = questions.size();
        int withFourChoices = 0;
        int validCorrectChoice = 0;
        int endsWithQuestionMark = 0;
        int hasExplanation = 0;
        int allFieldsFilled = 0;

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

        return ResponseEntity.ok(Map.of(
                "subject", subject,
                "difficulty", difficulty,
                "language", language,
                "totalQuestions", totalQuestions,
                "withFourDistinctChoices", withFourChoices,
                "withValidCorrectChoice", validCorrectChoice,
                "endsWithQuestionMark", endsWithQuestionMark,
                "hasExplanation", hasExplanation,
                "allFieldsFilled", allFieldsFilled,
                "qualityScore%", qualityScore
        ));
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