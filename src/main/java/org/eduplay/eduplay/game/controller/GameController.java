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
            @RequestParam int classLevel,
            @RequestParam Subject subject,
            @RequestParam Difficulty difficulty) {

        List<Question> questions = questionGeneratorService
                .generateQuestions(classLevel, subject, difficulty);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitSession(
            @RequestBody Map<String, Object> payload,
            Authentication auth) {

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        int correct = (int) payload.get("correctAnswers");
        int total   = (int) payload.get("totalQuestions");
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
}