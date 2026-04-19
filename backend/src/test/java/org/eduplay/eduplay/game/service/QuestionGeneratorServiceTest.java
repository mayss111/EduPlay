package org.eduplay.eduplay.game.service;

import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "ollama.base-url=http://localhost:11434",
        "ollama.model=qwen2.5:7b-instruct",
        "ollama.temperature=0.2"
})
@DisplayName("Question Generator Service Tests")
class QuestionGeneratorServiceTest {

    @Autowired
    private QuestionGeneratorService questionGeneratorService;

    @Test
    @DisplayName("Should generate 10 questions for MATH subject")
    void testMathGeneration() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                3, Subject.MATH, Difficulty.MOYEN, AppLanguage.FRENCH
        );
        
        assertEquals(10, questions.size(), "Should generate exactly 10 questions");
        assertTrue(questions.stream().allMatch(q -> q.getSubject() == Subject.MATH),
                "All questions should be MATH subject");
    }

    @Test
    @DisplayName("All questions should have 4 distinct choices")
    void testChoiceUniqueness() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                2, Subject.FRENCH, Difficulty.SIMPLE, AppLanguage.FRENCH
        );

        for (Question q : questions) {
            Set<String> choices = Set.of(
                    q.getChoiceA().toLowerCase().trim(),
                    q.getChoiceB().toLowerCase().trim(),
                    q.getChoiceC().toLowerCase().trim(),
                    q.getChoiceD().toLowerCase().trim()
            );
            assertEquals(4, choices.size(), 
                    "Question must have 4 distinct choices: " + q.getQuestionText());
        }
    }

    @Test
    @DisplayName("All questions should have valid correctChoice")
    void testCorrectChoiceValidity() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                4, Subject.SCIENCE, Difficulty.DIFFICILE, AppLanguage.FRENCH
        );

        for (Question q : questions) {
            String correct = q.getCorrectChoice();
            assertTrue(correct != null && (correct.equals("A") || correct.equals("B") 
                    || correct.equals("C") || correct.equals("D")),
                    "correctChoice must be A, B, C or D, got: " + correct);
        }
    }

    @Test
    @DisplayName("Questions should not have null or empty fields")
    void testQuestionCompleteness() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                1, Subject.HISTORY, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        for (Question q : questions) {
            assertNotNull(q.getQuestionText(), "questionText must not be null");
            assertFalse(q.getQuestionText().isBlank(), "questionText must not be blank");
            
            assertNotNull(q.getChoiceA(), "choiceA must not be null");
            assertFalse(q.getChoiceA().isBlank(), "choiceA must not be blank");
            
            assertNotNull(q.getChoiceB(), "choiceB must not be null");
            assertFalse(q.getChoiceB().isBlank(), "choiceB must not be blank");
            
            assertNotNull(q.getChoiceC(), "choiceC must not be null");
            assertFalse(q.getChoiceC().isBlank(), "choiceC must not be blank");
            
            assertNotNull(q.getChoiceD(), "choiceD must not be null");
            assertFalse(q.getChoiceD().isBlank(), "choiceD must not be blank");
            
            assertNotNull(q.getExplanation(), "explanation must not be null");
            assertFalse(q.getExplanation().isBlank(), "explanation must not be blank");
        }
    }

    @Test
    @DisplayName("Should respect subject consistency")
    void testSubjectConsistency() {
        List<Question> mathQuestions = questionGeneratorService.generateQuestions(
                3, Subject.GEOGRAPHY, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        for (Question q : mathQuestions) {
            assertEquals(Subject.GEOGRAPHY, q.getSubject(),
                    "Question subject must match requested subject");
        }
    }

    @Test
    @DisplayName("Arabic questions should be in Arabic language")
    void testArabicGeneration() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                2, Subject.ARABIC, Difficulty.SIMPLE, AppLanguage.ARABIC
        );

        assertEquals(10, questions.size(), "Should generate exactly 10 questions");
        assertTrue(questions.stream().allMatch(q -> q.getSubject() == Subject.ARABIC),
                "All questions should be ARABIC subject");
    }

    @Test
    @DisplayName("Question text should end with question mark")
    void testQuestionMark() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                4, Subject.MATH, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        for (Question q : questions) {
            assertTrue(q.getQuestionText().endsWith("?") || q.getQuestionText().endsWith("؟"),
                    "Question must end with question mark: " + q.getQuestionText());
        }
    }

    @Test
    @DisplayName("Should handle all subjects")
    void testAllSubjects() {
        for (Subject subject : Subject.values()) {
            List<Question> questions = questionGeneratorService.generateQuestions(
                    2, subject, Difficulty.MOYEN, AppLanguage.FRENCH
            );
            assertEquals(10, questions.size(), 
                    "Should generate 10 questions for subject: " + subject);
        }
    }

    @Test
    @DisplayName("Should handle all difficulty levels")
    void testAllDifficulties() {
        for (Difficulty difficulty : Difficulty.values()) {
            List<Question> questions = questionGeneratorService.generateQuestions(
                    3, Subject.SCIENCE, difficulty, AppLanguage.FRENCH
            );
            assertEquals(10, questions.size(), 
                    "Should generate 10 questions for difficulty: " + difficulty);
        }
    }
}
