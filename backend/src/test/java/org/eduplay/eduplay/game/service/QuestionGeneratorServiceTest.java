package org.eduplay.eduplay.game.service;

import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "ollama.base-url=http://localhost:11434",
        "ollama.model=qwen2.5:7b-instruct",
        "ollama.temperature=0.2",
        "spring.datasource.url=jdbc:h2:mem:eduplay_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.cache.type=none",
        "spring.session.store-type=none",
        "app.jwt.secret=TestJwtSecretKey_ChangeMe_2026_Min32Chars",
        "app.jwt.expiration=86400000",
        "app.cors.allowed-origins=http://localhost:4200",
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

    @Test
    @DisplayName("Different class levels should produce different question sets")
    void testClassLevelDifferentiation() {
        List<Question> level1 = questionGeneratorService.generateQuestions(
                1, Subject.FRENCH, Difficulty.MOYEN, AppLanguage.FRENCH
        );
        List<Question> level6 = questionGeneratorService.generateQuestions(
                6, Subject.FRENCH, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        Set<String> level1Texts = level1.stream()
                .map(Question::getQuestionText)
                .map(String::trim)
                .collect(Collectors.toSet());
        Set<String> level6Texts = level6.stream()
                .map(Question::getQuestionText)
                .map(String::trim)
                .collect(Collectors.toSet());

        assertNotEquals(level1Texts, level6Texts,
                "Questions should differ between class levels 1 and 6");
    }

    @Test
    @DisplayName("Generated questions should expose quality metadata and topic diversity")
    void testQualityMetadataAndTopicDiversity() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                3, Subject.SCIENCE, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        assertEquals(10, questions.size(), "Should generate exactly 10 questions");
        assertTrue(questions.stream().allMatch(q -> q.getQualityScore() != null && q.getQualityScore() >= 0),
                "Each question should expose a quality score");

        long distinctTopics = questions.stream()
                .map(Question::getTopicTag)
                .filter(topic -> topic != null && !topic.isBlank())
                .distinct()
                .count();

        assertTrue(distinctTopics >= 3,
                "Questions should span at least 3 distinct topic tags");
    }

    @Test
    @DisplayName("correctChoice should always point to an existing choice")
    void testCorrectChoicePointsToExistingChoice() {
        for (Subject subject : Subject.values()) {
            List<Question> questions = questionGeneratorService.generateQuestions(
                    4, subject, Difficulty.DIFFICILE, AppLanguage.FRENCH
            );

            for (Question q : questions) {
                Map<String, String> byLetter = Map.of(
                        "A", q.getChoiceA(),
                        "B", q.getChoiceB(),
                        "C", q.getChoiceC(),
                        "D", q.getChoiceD()
                );

                String correct = q.getCorrectChoice();
                assertTrue(byLetter.containsKey(correct),
                        "correctChoice must be one of A/B/C/D");
                assertNotNull(byLetter.get(correct),
                        "correctChoice must point to a non-null choice");
                assertFalse(byLetter.get(correct).isBlank(),
                        "correctChoice must point to a non-empty choice");
            }
        }
    }

    @Test
    @DisplayName("Math questions with parsable operation should have matching answer")
    void testMathOperationConsistency() {
        List<Question> questions = questionGeneratorService.generateQuestions(
                5, Subject.MATH, Difficulty.MOYEN, AppLanguage.FRENCH
        );

        Pattern operation = Pattern.compile("(\\d{1,3})\\s*([+\\-x×])\\s*(\\d{1,3})");
        for (Question q : questions) {
            Matcher m = operation.matcher(q.getQuestionText());
            if (!m.find()) {
                continue;
            }

            int a = Integer.parseInt(m.group(1));
            int b = Integer.parseInt(m.group(3));
            int expected = switch (m.group(2)) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "x", "×" -> a * b;
                default -> Integer.MIN_VALUE;
            };

            Map<String, String> byLetter = Map.of(
                    "A", q.getChoiceA().trim(),
                    "B", q.getChoiceB().trim(),
                    "C", q.getChoiceC().trim(),
                    "D", q.getChoiceD().trim()
            );

            String expectedText = String.valueOf(expected);
            if (byLetter.containsValue(expectedText)) {
                assertEquals(expectedText, byLetter.get(q.getCorrectChoice()),
                        "For parsable math question, correctChoice should match expected result");
            }
        }
    }
}
