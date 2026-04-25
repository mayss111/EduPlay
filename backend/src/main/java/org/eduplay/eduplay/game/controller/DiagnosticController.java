package org.eduplay.eduplay.game.controller;

import lombok.RequiredArgsConstructor;
import org.eduplay.eduplay.enums.Subject;
import org.eduplay.eduplay.repository.QuestionBankRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class DiagnosticController {

    private final QuestionBankRepository questionBankRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalQuestions", questionBankRepository.count());
        
        Map<String, Long> perSubject = new HashMap<>();
        for (Subject s : Subject.values()) {
            perSubject.put(s.name(), (long) questionBankRepository.findBySubjectAndClassLevel(s, 1).size());
        }
        stats.put("questionsLevel1", perSubject);
        
        return stats;
    }
}
