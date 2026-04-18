package org.eduplay.eduplay.game.service;



import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionGeneratorService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "questions", key = "#classLevel+'-'+#subject+'-'+#difficulty")
    public List<Question> generateQuestions(int classLevel,
                                            Subject subject,
                                            Difficulty difficulty) {
        log.info("Génération IA : classe={} matière={} niveau={}", classLevel, subject, difficulty);

        String prompt = buildPrompt(classLevel, subject, difficulty);

        Map<String, Object> body = Map.of(
                "model", "claude-sonnet-4-20250514",
                "max_tokens", 2000,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.anthropic.com/v1/messages",
                new HttpEntity<>(body, headers),
                Map.class
        );

        return parseResponse(response.getBody(), subject, classLevel, difficulty);
    }

    private String buildPrompt(int classLevel, Subject subject, Difficulty difficulty) {
        return """
            Tu es un professeur expert pour l'école primaire en Tunisie.
            Génère exactement 10 questions QCM adaptées en français pour :
            - Classe : %dème année primaire
            - Matière : %s
            - Niveau de difficulté : %s

            Réponds UNIQUEMENT avec un tableau JSON valide, sans texte avant ou après :
            [
              {
                "questionText": "La question ici ?",
                "choiceA": "Première réponse",
                "choiceB": "Deuxième réponse",
                "choiceC": "Troisième réponse",
                "choiceD": "Quatrième réponse",
                "correctChoice": "A",
                "explanation": "Explication courte et simple"
              }
            ]
            """.formatted(classLevel, subject.name(), difficulty.name());
    }

    @SuppressWarnings("unchecked")
    private List<Question> parseResponse(Map<String, Object> body,
                                         Subject subject,
                                         int classLevel,
                                         Difficulty difficulty) {
        try {
            List<Map<String, Object>> content = (List<Map<String, Object>>) body.get("content");
            String json = (String) content.get(0).get("text");

            List<Map<String, String>> raw = objectMapper.readValue(
                    json, new TypeReference<>() {});

            return raw.stream().map(q -> Question.builder()
                    .questionText(q.get("questionText"))
                    .choiceA(q.get("choiceA"))
                    .choiceB(q.get("choiceB"))
                    .choiceC(q.get("choiceC"))
                    .choiceD(q.get("choiceD"))
                    .correctChoice(q.get("correctChoice"))
                    .explanation(q.get("explanation"))
                    .subject(subject)
                    .classLevel(classLevel)
                    .difficulty(difficulty)
                    .build()
            ).toList();

        } catch (Exception e) {
            log.error("Erreur parsing réponse Claude : {}", e.getMessage());
            return List.of();
        }
    }
}