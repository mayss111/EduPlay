package org.eduplay.eduplay.game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Locale;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionGeneratorService {

    private static final int TARGET_QUESTION_COUNT = 10;
    private static final int MAX_GENERATION_ATTEMPTS = 2;
    private static final long QUESTION_CACHE_TTL_MS = 30_000;
    private static final long VARIATION_BUCKET_MS = 4_000;
    private static final int MIN_QUALITY_SCORE = 70;
    private static final double DUPLICATE_SIMILARITY_THRESHOLD = 0.74;

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${ollama.model:qwen2.5:7b-instruct}")
    private String ollamaModel;

    @Value("${ollama.temperature:0.2}")
    private double ollamaTemperature;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = createRestTemplate();
    private final Map<String, CachedQuestions> questionCache = new ConcurrentHashMap<>();

    private record CachedQuestions(long createdAtEpochMs, List<Question> questions) {}

    private RestTemplate createRestTemplate() {
        org.springframework.http.client.SimpleClientHttpRequestFactory factory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(8000);
        factory.setReadTimeout(15000);
        return new RestTemplate(factory);
    }

    public List<Question> generateQuestions(int classLevel,
                                            Subject subject,
                                            Difficulty difficulty,
                                            AppLanguage language) {
        log.info("Generation IA (Ollama): classe={} matiere={} niveau={} langue={}", classLevel, subject, difficulty, language);

        String cacheKey = cacheKey(classLevel, subject, difficulty, language);
        List<Question> cached = getCachedQuestions(cacheKey);
        if (!cached.isEmpty()) {
            log.info("Questions servies depuis le cache: {}", cacheKey);
            return rotateForVariety(cached);
        }

        if (ollamaBaseUrl == null || ollamaBaseUrl.isBlank()) {
            throw new IllegalStateException("OLLAMA_BASE_URL non defini. Generation Ollama impossible.");
        }

        Exception lastException = null;
        List<Question> candidatePool = new ArrayList<>();

        for (int attempt = 1; attempt <= MAX_GENERATION_ATTEMPTS; attempt++) {
            try {
                String generationPrompt = buildPrompt(classLevel, subject, difficulty, language);
                String generationBody = callOllama(generationPrompt);
                List<Question> generated = parseOllamaResponse(generationBody, subject, classLevel, difficulty);

                List<Question> normalized = normalizeAndDiversify(generated, language);
                candidatePool.addAll(normalized);

                List<Question> bestSoFar = selectBestQuestions(candidatePool, difficulty, TARGET_QUESTION_COUNT);
                if (bestSoFar.size() >= TARGET_QUESTION_COUNT) {
                    List<Question> tailored = tailorQuestionsForClassAndDifficulty(bestSoFar, classLevel, difficulty, language);
                    List<Question> finalized = finalizeQuestionSet(tailored, classLevel, subject, difficulty, language);
                    putInCache(cacheKey, finalized);
                    return rotateForVariety(finalized);
                }

                log.warn("Generation insuffisante (tentative {}): {} questions valides.", attempt, bestSoFar.size());
            } catch (HttpStatusCodeException e) {
                log.error("Erreur Ollama [{}]: {}", e.getStatusCode(), e.getResponseBodyAsString());
                throw new IllegalStateException("Erreur API Ollama: " + e.getStatusCode());
            } catch (Exception e) {
                lastException = e;
                log.warn("Tentative {} echouee: {}", attempt, e.getMessage());
            }
        }

        if (lastException != null) {
            log.warn("Passage en fallback local apres erreur Ollama: {}", lastException.getMessage());
        } else {
            log.warn("Passage en fallback local: generation Ollama insuffisante.");
        }

        List<Question> fallback = buildFallbackQuestions(classLevel, subject, difficulty, language);
        candidatePool.addAll(fallback);

        List<Question> best = selectBestQuestions(candidatePool, difficulty, TARGET_QUESTION_COUNT);
        if (best.size() >= TARGET_QUESTION_COUNT) {
            List<Question> tailored = tailorQuestionsForClassAndDifficulty(best, classLevel, difficulty, language);
            List<Question> finalized = finalizeQuestionSet(tailored, classLevel, subject, difficulty, language);
            return rotateForVariety(finalized);
        }

        // Last-resort path: return a fully playable set from fallback content
        // even when diversity/quality selection was too restrictive.
        List<Question> emergencyBase = new ArrayList<>(fallback);
        List<Question> emergencyTailored = tailorQuestionsForClassAndDifficulty(emergencyBase, classLevel, difficulty, language);
        List<Question> emergencyFinal = finalizeQuestionSet(emergencyTailored, classLevel, subject, difficulty, language);

        if (!emergencyFinal.isEmpty()) {
            List<Question> safe = ensureMinimumPlayableSet(emergencyFinal, fallback, subject, classLevel, difficulty);
            return rotateForVariety(safe);
        }

        throw new IllegalStateException("Generation indisponible temporairement.");
    }

        private String buildPrompt(int classLevel, Subject subject, Difficulty difficulty, AppLanguage language) {
                String outputLanguage = language == AppLanguage.ARABIC ? "arabe" : "francais";
                String pedagogicalFocus = pedagogicalFocus(classLevel, subject, difficulty, language);
                String questionMix = questionMixGuidance(language);
                String styleGuidance = styleGuidance(classLevel, subject, difficulty, language);
                return """
                                Genere exactement 10 questions QCM en %s pour classe %deme, matiere %s, niveau %s.
                                Objectifs pedagogiques:
                                %s

                                Repartition pedagogique attendue:
                                %s

                                Consignes de style:
                                %s

                                Retourne UNIQUEMENT un JSON valide (sans markdown):
                                [
                                    {
                                        "questionText": "... ?",
                                        "choiceA": "...",
                                        "choiceB": "...",
                                        "choiceC": "...",
                                        "choiceD": "...",
                                        "correctChoice": "A/B/C/D",
                                        "explanation": "..."
                                    }
                                ]

                                Regles:
                                - Toute la sortie doit etre en %s (question, choix, explication).
                                - Faits vrais, niveau primaire tunisien.
                                - Les questions doivent etre differentes par formulation et par contexte.
                                - Chaque question doit utiliser un sous-theme ou une situation differente.
                                - Ne reutilise pas la meme structure d'une question a l'autre.
                                - 4 choix differents, plausibles, sans pieges absurdes.
                                - correctChoice doit etre A, B, C ou D.
                                - Explication courte mais utile (justification, regle ou raisonnement simple).
                                - Eviter les repetitions de structure ou d'idee entre questions.
                                - Ne rien inventer.
                                - Chaque question doit avoir une reponse unique et defendable.
                                """.formatted(outputLanguage, classLevel, subjectLabel(subject), difficultyLabel(difficulty), pedagogicalFocus, questionMix, styleGuidance, outputLanguage);
        }

    private String pedagogicalFocus(int classLevel, Subject subject, Difficulty difficulty, AppLanguage language) {
        if (language == AppLanguage.ARABIC) {
            return "- مستوى الصف: " + classLevel + "\n"
                    + "- درجة الصعوبة: " + difficultyLabel(difficulty) + "\n"
                    + "- المادة: " + subjectLabel(subject) + "\n"
                    + "- اجعل الاسئلة متدرجة ومناسبة لعمر التلميذ.";
        }

        return "- Classe cible: " + classLevel + "\n"
                + "- Niveau cible: " + difficultyLabel(difficulty) + "\n"
                + "- Matiere cible: " + subjectLabel(subject) + "\n"
                + "- Questions progressives et adaptees au niveau scolaire.";
    }

    private String questionMixGuidance(AppLanguage language) {
        if (language == AppLanguage.ARABIC) {
            return "- 3 اسئلة تذكير بسيط\n"
                    + "- 4 اسئلة تطبيق مباشر\n"
                    + "- 2 اسئلة تفكير او تفسير\n"
                    + "- 1 سؤال انتقال او توسيع للمفهوم";
        }

        return "- 3 questions de rappel\n"
                + "- 4 questions d'application directe\n"
                + "- 2 questions de raisonnement ou d'explication\n"
                + "- 1 question de transfert pour varier le contexte";
    }

    private String styleGuidance(int classLevel, Subject subject, Difficulty difficulty, AppLanguage language) {
        String band = classBand(classLevel);
        String topic = subjectTheme(subject, classLevel, difficulty, classLevel + difficultyWeight(difficulty), language);
        String diversityHint = language == AppLanguage.ARABIC
                ? "- غيّر نوع الوضعية في كل سؤال: تعريف، اختيار، مقارنة، تطبيق، تفسير."
                : "- Alterne les situations: definition, choix, comparaison, application, interpretation.";

        if (language == AppLanguage.ARABIC) {
            return "- مناسب للـ " + band + "\n"
                    + "- استعمل سياقات من محور: " + topic + "\n"
                    + diversityHint + "\n"
                    + "- غير بداية الجمل وتجنب التكرار الحرفي.";
        }

        return "- Niveau de classe: " + band + "\n"
                + "- Utilise des contextes relies au theme: " + topic + "\n"
                + diversityHint + "\n"
                + "- Varie les debuts de phrases et evite la repetition litterale.";
    }

    private List<Question> tailorQuestionsForClassAndDifficulty(List<Question> questions,
                                                                int classLevel,
                                                                Difficulty difficulty,
                                                                AppLanguage language) {
        List<Question> adapted = new ArrayList<>();
        int seed = variationSalt();
        int idx = 0;
        for (Question q : questions) {
            if (q == null) {
                continue;
            }
            idx++;
            int variantIndex = idx + seed;
            q.setTopicTag(subjectTheme(q.getSubject(), classLevel, difficulty, variantIndex, language));
            q.setGenerationProfile(questionProfile(classLevel, difficulty, variantIndex, language));
            injectContextVariant(q, classLevel, difficulty, language, variantIndex);
            if (q.getSubject() == Subject.MATH) {
                adaptMathQuestion(q, classLevel, difficulty, variantIndex);
            } else {
                adaptTextQuestion(q, classLevel, difficulty, language, variantIndex, q.getSubject());
            }
            enforceCorrectChoiceFromExplanation(q);
            q.setQualityScore(qualityScore(q, difficulty));
            adapted.add(q);
        }
        return adapted;
    }

    private int variationSalt() {
        long bucket = System.currentTimeMillis() / VARIATION_BUCKET_MS;
        return (int) Math.floorMod(bucket, 37);
    }

    private void injectContextVariant(Question question,
                                      int classLevel,
                                      Difficulty difficulty,
                                      AppLanguage language,
                                      int index) {
        if (question == null || question.getSubject() == Subject.MATH) {
            return;
        }

        question.setQuestionText(stripQuestionContextDecorations(question.getQuestionText()));
    }

    private void adaptMathQuestion(Question question, int classLevel, Difficulty difficulty, int index) {
        int diffWeight = switch (difficulty) {
            case SIMPLE -> 0;
            case MOYEN -> 1;
            case DIFFICILE -> 2;
            case EXCELLENT -> 3;
        };
        int shift = Math.max(0, classLevel - 1) + diffWeight + (index % 2);

        if (isArithmeticMathQuestion(question.getQuestionText())) {
            question.setQuestionText(shiftNumbers(question.getQuestionText(), shift));
            question.setChoiceA(shiftNumbers(question.getChoiceA(), shift));
            question.setChoiceB(shiftNumbers(question.getChoiceB(), shift));
            question.setChoiceC(shiftNumbers(question.getChoiceC(), shift));
            question.setChoiceD(shiftNumbers(question.getChoiceD(), shift));
            question.setExplanation(shiftNumbers(question.getExplanation(), shift));
        }

        enforceMathAnswerConsistency(question);
    }

    private boolean isArithmeticMathQuestion(String questionText) {
        if (questionText == null) {
            return false;
        }
        String t = questionText.toLowerCase();
        if (containsAny(t, "forme", "cote", "cotes", "triangle", "carre", "cercle", "rectangle", "شكل", "أضلاع", "مثلث", "مربع", "دائرة")) {
            return false;
        }
        return t.contains("+")
                || t.contains("-")
                || t.contains("x")
                || t.contains("×")
                || containsAny(t, "font", "double", "vient apres", "يأتي بعد", "ضعف", "كم يساوي");
    }

    private void enforceMathAnswerConsistency(Question question) {
        String q = Optional.ofNullable(question.getQuestionText()).orElse("");
        Integer expected = extractExpectedMathResult(q);
        if (expected == null) {
            return;
        }

        Map<String, String> choices = Map.of(
                "A", Optional.ofNullable(question.getChoiceA()).orElse("").trim(),
                "B", Optional.ofNullable(question.getChoiceB()).orElse("").trim(),
                "C", Optional.ofNullable(question.getChoiceC()).orElse("").trim(),
                "D", Optional.ofNullable(question.getChoiceD()).orElse("").trim()
        );

        String expectedText = String.valueOf(expected);
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            if (expectedText.equals(entry.getValue())) {
                question.setCorrectChoice(entry.getKey());
                return;
            }
        }
    }

    private Integer extractExpectedMathResult(String questionText) {
        if (questionText == null || questionText.isBlank()) {
            return null;
        }

        Matcher op = Pattern.compile("(\\d{1,3})\\s*([+\\-x×])\\s*(\\d{1,3})").matcher(questionText);
        if (op.find()) {
            int a = Integer.parseInt(op.group(1));
            int b = Integer.parseInt(op.group(3));
            return switch (op.group(2)) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "x", "×" -> a * b;
                default -> null;
            };
        }

        Matcher after = Pattern.compile("(?:apres|après|بعد)\\s*(\\d{1,3})", Pattern.CASE_INSENSITIVE).matcher(questionText);
        if (after.find()) {
            return Integer.parseInt(after.group(1)) + 1;
        }

        Matcher doub = Pattern.compile("(?:double|ضعف)\\D*(\\d{1,3})", Pattern.CASE_INSENSITIVE).matcher(questionText);
        if (doub.find()) {
            return Integer.parseInt(doub.group(1)) * 2;
        }

        return null;
    }

    private void enforceCorrectChoiceFromExplanation(Question question) {
        String explanation = Optional.ofNullable(question.getExplanation()).orElse("").trim();
        if (explanation.isBlank()) {
            return;
        }

        Map<String, String> choices = new LinkedHashMap<>();
        choices.put("A", Optional.ofNullable(question.getChoiceA()).orElse("").trim());
        choices.put("B", Optional.ofNullable(question.getChoiceB()).orElse("").trim());
        choices.put("C", Optional.ofNullable(question.getChoiceC()).orElse("").trim());
        choices.put("D", Optional.ofNullable(question.getChoiceD()).orElse("").trim());

        String normalizedExplanation = normalizeForContains(explanation);
        String bestLetter = null;
        int bestLen = -1;
        boolean tie = false;

        for (Map.Entry<String, String> entry : choices.entrySet()) {
            String choiceText = normalizeForContains(entry.getValue());
            if (choiceText.length() < 3) {
                continue;
            }
            if (normalizedExplanation.contains(choiceText)) {
                if (choiceText.length() > bestLen) {
                    bestLetter = entry.getKey();
                    bestLen = choiceText.length();
                    tie = false;
                } else if (choiceText.length() == bestLen) {
                    tie = true;
                }
            }
        }

        if (bestLetter != null && !tie) {
            question.setCorrectChoice(bestLetter);
        }
    }

    private String normalizeForContains(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    private List<Question> finalizeQuestionSet(List<Question> questions,
                                               int classLevel,
                                               Subject subject,
                                               Difficulty difficulty,
                                               AppLanguage language) {
        List<Question> finalized = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        int index = addValidatedQuestions(
                questions,
                classLevel,
                subject,
                difficulty,
                language,
                finalized,
                seen,
                0
        );

        if (finalized.size() < TARGET_QUESTION_COUNT) {
            List<Question> fallbackCandidates = buildFallbackQuestions(classLevel, subject, difficulty, language);
            int nextIndex = addValidatedQuestions(
                    fallbackCandidates,
                    classLevel,
                    subject,
                    difficulty,
                    language,
                    finalized,
                    seen,
                    index
            );

            // If strict validation is too restrictive for some profiles, relax only the
            // explanation-based uniqueness constraint so users still get playable sets.
            if (finalized.size() < TARGET_QUESTION_COUNT) {
                addRelaxedQuestions(
                        fallbackCandidates,
                        classLevel,
                        subject,
                        difficulty,
                        language,
                        finalized,
                        seen,
                        nextIndex
                );
            }
        }

        return finalized.size() > TARGET_QUESTION_COUNT
                ? new ArrayList<>(finalized.subList(0, TARGET_QUESTION_COUNT))
                : finalized;
    }

    private int addValidatedQuestions(List<Question> source,
                                      int classLevel,
                                      Subject subject,
                                      Difficulty difficulty,
                                      AppLanguage language,
                                      List<Question> target,
                                      Set<String> seen,
                                      int startIndex) {
        int index = startIndex;

        for (Question q : source) {
            if (q == null || target.size() >= TARGET_QUESTION_COUNT) {
                continue;
            }

            index++;
            q.setSubject(subject);
            q.setClassLevel(classLevel);
            q.setDifficulty(difficulty);
            sanitize(q);
            ensureDistinctChoices(q, subject, language, classLevel, difficulty, index);

            if (subject == Subject.MATH) {
                enforceMathAnswerConsistency(q);
            }

            enforceCorrectChoiceFromExplanation(q);
            ensureCorrectChoiceIsValid(q);

            if (!isPedagogicallyValid(q) || !isSingleAnswerWellJustified(q)) {
                continue;
            }

            String key = normalizeForContains(q.getQuestionText());
            if (key.isBlank() || !seen.add(key)) {
                continue;
            }

            target.add(q);
        }

        return index;
    }

    private int addRelaxedQuestions(List<Question> source,
                                    int classLevel,
                                    Subject subject,
                                    Difficulty difficulty,
                                    AppLanguage language,
                                    List<Question> target,
                                    Set<String> seen,
                                    int startIndex) {
        int index = startIndex;

        for (Question q : source) {
            if (q == null || target.size() >= TARGET_QUESTION_COUNT) {
                continue;
            }

            index++;
            q.setSubject(subject);
            q.setClassLevel(classLevel);
            q.setDifficulty(difficulty);
            sanitize(q);
            ensureDistinctChoices(q, subject, language, classLevel, difficulty, index);

            if (subject == Subject.MATH) {
                enforceMathAnswerConsistency(q);
            }

            enforceCorrectChoiceFromExplanation(q);
            ensureCorrectChoiceIsValid(q);

            if (!isPedagogicallyValid(q)) {
                continue;
            }

            String key = normalizeForContains(q.getQuestionText());
            if (key.isBlank() || !seen.add(key)) {
                continue;
            }

            target.add(q);
        }

        return index;
    }

    private void ensureDistinctChoices(Question question,
                                       Subject subject,
                                       AppLanguage language,
                                       int classLevel,
                                       Difficulty difficulty,
                                       int index) {
        Map<String, String> byLetter = new LinkedHashMap<>();
        byLetter.put("A", Optional.ofNullable(question.getChoiceA()).orElse("").trim());
        byLetter.put("B", Optional.ofNullable(question.getChoiceB()).orElse("").trim());
        byLetter.put("C", Optional.ofNullable(question.getChoiceC()).orElse("").trim());
        byLetter.put("D", Optional.ofNullable(question.getChoiceD()).orElse("").trim());

        Set<String> used = new HashSet<>();
        for (Map.Entry<String, String> entry : byLetter.entrySet()) {
            String normalized = normalizeForContains(entry.getValue());
            if (normalized.isBlank() || used.contains(normalized)) {
                String replacement = buildReplacementChoice(question, subject, language, classLevel, difficulty, index, used);
                byLetter.put(entry.getKey(), replacement);
                used.add(normalizeForContains(replacement));
            } else {
                used.add(normalized);
            }
        }

        question.setChoiceA(byLetter.get("A"));
        question.setChoiceB(byLetter.get("B"));
        question.setChoiceC(byLetter.get("C"));
        question.setChoiceD(byLetter.get("D"));
    }

    private String buildReplacementChoice(Question question,
                                          Subject subject,
                                          AppLanguage language,
                                          int classLevel,
                                          Difficulty difficulty,
                                          int index,
                                          Set<String> used) {
        if (subject == Subject.MATH) {
            Integer expected = extractExpectedMathResult(question.getQuestionText());
            if (expected != null) {
                int[] candidates = new int[] {
                        expected + 1,
                        Math.max(0, expected - 1),
                        expected + Math.max(2, classLevel),
                        Math.max(0, expected - Math.max(2, difficultyWeight(difficulty) + 1))
                };
                for (int c : candidates) {
                    String candidate = String.valueOf(c);
                    if (!used.contains(normalizeForContains(candidate))) {
                        return candidate;
                    }
                }
            }
        }

        String[] pool = replacementPool(subject, language);
        for (String candidate : pool) {
            if (!used.contains(normalizeForContains(candidate))) {
                return candidate;
            }
        }

        String fallback = language == AppLanguage.ARABIC ? "خيار " + index : "Choix " + index;
        if (!used.contains(normalizeForContains(fallback))) {
            return fallback;
        }
        return fallback + " " + difficultyWeight(difficulty);
    }

    private String[] replacementPool(Subject subject, AppLanguage language) {
        if (language == AppLanguage.ARABIC) {
            return switch (subject) {
                case MATH -> new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                case SCIENCE -> new String[]{"الماء", "الهواء", "النبات", "الحيوان", "الشمس", "الأرض"};
                case HISTORY -> new String[]{"الماضي", "الحاضر", "الخط الزمني", "حدث", "شخصية"};
                case GEOGRAPHY -> new String[]{"الشمال", "الجنوب", "الشرق", "الغرب", "خريطة", "عاصمة"};
                case FRENCH -> new String[]{"اسم", "فعل", "جمع", "مفرد", "جملة"};
                case ARABIC -> new String[]{"كلمة", "جملة", "حرف", "مرادف", "معنى"};
            };
        }

        return switch (subject) {
            case MATH -> new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            case SCIENCE -> new String[]{"eau", "air", "plante", "animal", "soleil", "terre"};
            case HISTORY -> new String[]{"passe", "present", "frise chronologique", "evenement", "personnage"};
            case GEOGRAPHY -> new String[]{"nord", "sud", "est", "ouest", "carte", "capitale"};
            case FRENCH -> new String[]{"nom", "verbe", "adjectif", "phrase", "syllabe"};
            case ARABIC -> new String[]{"mot arabe", "lettre", "phrase", "sens", "vocabulaire"};
        };
    }

    private void ensureCorrectChoiceIsValid(Question question) {
        String correct = Optional.ofNullable(question.getCorrectChoice()).orElse("").trim().toUpperCase();
        Map<String, String> choices = new LinkedHashMap<>();
        choices.put("A", Optional.ofNullable(question.getChoiceA()).orElse("").trim());
        choices.put("B", Optional.ofNullable(question.getChoiceB()).orElse("").trim());
        choices.put("C", Optional.ofNullable(question.getChoiceC()).orElse("").trim());
        choices.put("D", Optional.ofNullable(question.getChoiceD()).orElse("").trim());

        if (choices.containsKey(correct) && !choices.get(correct).isBlank()) {
            return;
        }

        String explanation = normalizeForContains(question.getExplanation());
        String bestLetter = "A";
        int bestScore = -1;

        for (Map.Entry<String, String> entry : choices.entrySet()) {
            String candidate = normalizeForContains(entry.getValue());
            if (candidate.isBlank()) {
                continue;
            }

            int score = overlapScore(explanation, candidate);
            if (score > bestScore) {
                bestScore = score;
                bestLetter = entry.getKey();
            }
        }

        question.setCorrectChoice(bestLetter);
    }

    private int overlapScore(String base, String candidate) {
        if (base == null || candidate == null || base.isBlank() || candidate.isBlank()) {
            return 0;
        }
        int score = 0;
        for (String token : candidate.split("\\s+")) {
            if (token.length() < 3) {
                continue;
            }
            if (base.contains(token)) {
                score += token.length();
            }
        }
        return score;
    }

    private boolean isSingleAnswerWellJustified(Question question) {
        String correct = Optional.ofNullable(question.getCorrectChoice()).orElse("").trim().toUpperCase();
        Map<String, String> choices = new LinkedHashMap<>();
        choices.put("A", Optional.ofNullable(question.getChoiceA()).orElse("").trim());
        choices.put("B", Optional.ofNullable(question.getChoiceB()).orElse("").trim());
        choices.put("C", Optional.ofNullable(question.getChoiceC()).orElse("").trim());
        choices.put("D", Optional.ofNullable(question.getChoiceD()).orElse("").trim());

        if (!choices.containsKey(correct) || choices.get(correct).isBlank()) {
            return false;
        }

        if (question.getSubject() == Subject.MATH) {
            Integer expected = extractExpectedMathResult(question.getQuestionText());
            if (expected == null) {
                return true;
            }
            String expectedText = String.valueOf(expected);
            long matches = choices.values().stream().filter(expectedText::equals).count();
            return matches == 1 && expectedText.equals(choices.get(correct));
        }

        String explanation = normalizeForContains(question.getExplanation());
        if (explanation.isBlank()) {
            return false;
        }

        String correctText = normalizeForContains(choices.get(correct));
        int correctScore = overlapScore(explanation, correctText);
        if (correctScore <= 0) {
            return false;
        }

        int competitorBest = 0;
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            if (entry.getKey().equals(correct)) {
                continue;
            }
            competitorBest = Math.max(competitorBest, overlapScore(explanation, normalizeForContains(entry.getValue())));
        }

        return correctScore > competitorBest;
    }

    private String cacheKey(int classLevel, Subject subject, Difficulty difficulty, AppLanguage language) {
        return classLevel + "|" + subject + "|" + difficulty + "|" + language;
    }

    private List<Question> getCachedQuestions(String cacheKey) {
        CachedQuestions cached = questionCache.get(cacheKey);
        if (cached == null) {
            return List.of();
        }

        long age = System.currentTimeMillis() - cached.createdAtEpochMs();
        if (age > QUESTION_CACHE_TTL_MS) {
            questionCache.remove(cacheKey);
            return List.of();
        }
        return copyQuestions(cached.questions());
    }

    private void putInCache(String cacheKey, List<Question> questions) {
        questionCache.put(cacheKey, new CachedQuestions(System.currentTimeMillis(), copyQuestions(questions)));
    }

    private List<Question> rotateForVariety(List<Question> questions) {
        List<Question> copies = copyQuestions(questions);
        if (copies.size() <= 1) {
            return copies;
        }

        int shift = (int) ((System.currentTimeMillis() / 2000L) % copies.size());
        if (shift != 0) {
            Collections.rotate(copies, shift);
        }
        return copies;
    }

    private List<Question> copyQuestions(List<Question> questions) {
        List<Question> copies = new ArrayList<>();
        for (Question q : questions) {
            if (q == null) {
                continue;
            }
            copies.add(Question.builder()
                    .questionText(q.getQuestionText())
                    .choiceA(q.getChoiceA())
                    .choiceB(q.getChoiceB())
                    .choiceC(q.getChoiceC())
                    .choiceD(q.getChoiceD())
                    .correctChoice(q.getCorrectChoice())
                    .explanation(q.getExplanation())
                    .subject(q.getSubject())
                    .classLevel(q.getClassLevel())
                    .difficulty(q.getDifficulty())
                    .qualityScore(q.getQualityScore())
                    .topicTag(q.getTopicTag())
                    .generationProfile(q.getGenerationProfile())
                    .build());
        }
        return copies;
    }

    private String shiftNumbers(String text, int shift) {
        if (text == null || text.isBlank() || shift <= 0) {
            return text;
        }

        Pattern p = Pattern.compile("\\b\\d{1,3}\\b");
        Matcher m = p.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            int n = Integer.parseInt(m.group());
            int updated = n <= 0 ? n : Math.min(999, n + shift);
            m.appendReplacement(sb, String.valueOf(updated));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private void adaptTextQuestion(Question question,
                                   int classLevel,
                                   Difficulty difficulty,
                                   AppLanguage language,
                                   int index,
                                   Subject subject) {
        String qt = stripQuestionContextDecorations(question.getQuestionText());
        if (!qt.isBlank()) {
            question.setQuestionText(qt);
        }

        String explanation = Optional.ofNullable(question.getExplanation()).orElse("").trim();
        if (language == AppLanguage.ARABIC) {
            String objective = "هدف التعلم: " + learningObjective(subject, difficulty, language) + ".";
            if (!explanation.contains("هدف التعلم")) {
                question.setExplanation((explanation.isBlank() ? "" : explanation + " ") + objective);
            }
        } else {
            String objective = "Objectif d'apprentissage: " + learningObjective(subject, difficulty, language) + ".";
            if (!explanation.contains("Objectif d'apprentissage")) {
                question.setExplanation((explanation.isBlank() ? "" : explanation + " ") + objective);
            }
        }

        String levelHint = language == AppLanguage.ARABIC
                ? "مراعاة مستوى الصف " + classLevel + "."
                : "Adapte au niveau de la classe " + classLevel + ".";
        String updatedExp = Optional.ofNullable(question.getExplanation()).orElse("").trim();
        if (!updatedExp.contains(levelHint)) {
            question.setExplanation((updatedExp.isBlank() ? "" : updatedExp + " ") + levelHint);
        }
    }

    private String stripQuestionContextDecorations(String questionText) {
        if (questionText == null) {
            return "";
        }

        String cleaned = questionText.trim();

        cleaned = cleaned.replaceAll("^(?:Contexte[^-]*|Dans un contexte[^-]*|Dans un exercice adapte au niveau[^-]*|Dans une situation de classe[^-]*|Dans une activite d'apprentissage[^-]*|في نشاط القسم[^-]*|في تمرين مناسب للمستوى[^-]*|في وضعية تعلم يومية[^-]*|في مثال مرتبط بالحياة المدرسية[^-]*)\\s*[-,:]\\s*", "");
        cleaned = cleaned.replaceAll("\\s*\\((?:classe|الصف)[^\\)]*\\)\\s*$", "");
        cleaned = cleaned.replaceAll("\\s*(?:-\\s*)?(?:classe\\s*\\d+\\s*-\\s*niveau\\s*[^\\|\\)]*|الصف\\s*\\d+\\s*-\\s*[^\\|\\)]*)(?:\\s*\\|\\s*(?:theme|محور)[:：]?\\s*[^\\)]*)?\\s*$", "");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

        if (!cleaned.isEmpty() && !(cleaned.endsWith("?") || cleaned.endsWith("؟"))) {
            cleaned = cleaned + "?";
        }

        return cleaned;
    }

    private String classProfileContext(int classLevel, Difficulty difficulty, AppLanguage language) {
        String band = classLevel <= 2 ? "debutant"
                : classLevel <= 4 ? "intermediaire"
                : "avance";

        if (language == AppLanguage.ARABIC) {
            return switch (band) {
                case "debutant" -> "سياق حياتي بسيط";
                case "intermediaire" -> "سياق مدرسي تطبيقي";
                default -> "سياق تحليلي مناسب";
            };
        }

        return switch (band) {
            case "debutant" -> "Contexte quotidien simple";
            case "intermediaire" -> "Contexte scolaire applique";
            default -> "Contexte d'analyse guidee";
        };
    }

    private String subjectTheme(Subject subject,
                                int classLevel,
                                Difficulty difficulty,
                                int index,
                                AppLanguage language) {
        int offset = classLevel + difficultyWeight(difficulty) + index;
        String band = classBand(classLevel);

        if (language == AppLanguage.ARABIC) {
            String[] pool = switch (subject) {
                case FRENCH -> switch (band) {
                    case "debutant" -> new String[]{"الحروف", "المقاطع", "الكلمات اليومية", "الجمل القصيرة", "المفرد", "الجمع", "القراءة", "الكتابة", "التهجئة", "المعجم"};
                    case "intermediaire" -> new String[]{"القواعد", "الفعل", "الاسم", "الصفة", "الضمائر", "التركيب", "الفهم", "التمييز", "الترقيم", "المعنى"};
                    default -> new String[]{"الاستنتاج", "إعادة الصياغة", "تركيب الجمل", "فهم النص", "السياق", "المقارنة", "التحليل", "الاستعمال", "اللغات", "المعجم المتقدم"};
                };
                case ARABIC -> switch (band) {
                    case "debutant" -> new String[]{"الحروف", "المقاطع", "الكلمات اليومية", "الجمل القصيرة", "المفرد", "الجمع", "القراءة", "الكتابة", "الإملاء", "المعجم"};
                    case "intermediaire" -> new String[]{"النحو", "الفعل", "الاسم", "الصفة", "الضمائر", "التركيب", "الفهم", "التمييز", "الترقيم", "المعنى"};
                    default -> new String[]{"الاستنتاج", "إعادة الصياغة", "تحليل النص", "الفهم القرائي", "السياق", "المقارنة", "التحليل", "الاستعمال", "الأساليب", "المعجم المتقدم"};
                };
                case SCIENCE -> switch (band) {
                    case "debutant" -> new String[]{"الجسم", "الحواس", "الماء", "الهواء", "النبات", "الحيوان", "الضوء", "الحرارة", "النظافة", "البيئة"};
                    case "intermediaire" -> new String[]{"أعضاء الجسم", "الطاقة", "المادة", "التوازن", "النباتات", "الحيوانات", "الطقس", "الصحة", "التحول", "الملاحظة"};
                    default -> new String[]{"السبب والنتيجة", "الظواهر", "التجريب", "المقارنة", "البيئة", "الدورات", "التحليل", "التفسير", "العلوم اليومية", "الاستنتاج"};
                };
                case HISTORY -> switch (band) {
                    case "debutant" -> new String[]{"الماضي", "الحاضر", "الشخصيات", "الأحداث", "الترتيب", "الزمن", "الصور القديمة", "المدرسة", "البلاد", "الذاكرة"};
                    case "intermediaire" -> new String[]{"التسلسل الزمني", "الوثائق", "التراث", "الأحداث", "الشخصيات", "الحقبة", "المقارنة", "الأسئلة", "السبب", "النتيجة"};
                    default -> new String[]{"التحليل التاريخي", "الاستنتاج", "المصادر", "السياق", "التحول", "الترتيب الزمني", "التراث التونسي", "الوقائع", "العلاقة", "الدلالة"};
                };
                case GEOGRAPHY -> switch (band) {
                    case "debutant" -> new String[]{"الاتجاهات", "الخريطة", "المدينة", "القرية", "البحر", "الجبال", "الصحراء", "الطقس", "البلد", "الحدود"};
                    case "intermediaire" -> new String[]{"المناخ", "الخرائط", "المنظر الطبيعي", "الاتجاهات", "تونس", "الجوار", "الرموز", "الموقع", "المياه", "السكان"};
                    default -> new String[]{"تحليل الخريطة", "العلاقة بين المكان والمناخ", "المجالات", "الحدود", "الأنشطة", "المدن", "القراءة", "المقارنة", "الاستنتاج", "التوزيع"};
                };
                case MATH -> switch (band) {
                    case "debutant" -> new String[]{"الحساب", "العد", "المقارنة", "الجمع", "الطرح", "الأشكال", "الترتيب", "الوقت", "الأعداد", "القياس"};
                    case "intermediaire" -> new String[]{"المسائل", "الكسور", "الضرب", "القسمة", "المنطق", "الهندسة", "القياس", "الأنماط", "العدد", "الاستراتيجية"};
                    default -> new String[]{"الاستدلال", "حل المسائل", "التحقق", "الأنماط", "التمثيل", "الهندسة", "التركيب", "التفسير", "الاستراتيجية", "القياس"};
                };
            };
            return pool[Math.floorMod(offset, pool.length)];
        }

        String[] pool = switch (subject) {
            case FRENCH -> switch (band) {
                case "debutant" -> new String[]{"letters", "syllabes", "mots", "phrases courtes", "singulier", "pluriel", "lecture", "ecriture", "orthographe", "vocabulaire"};
                case "intermediaire" -> new String[]{"grammaire", "conjugaison", "accord", "types de phrases", "synonymes", "antonymes", "ponctuation", "lecture de texte", "vocabulaire", "reformulation"};
                default -> new String[]{"comprehension", "analyse", "inference", "structure", "coherence", "lexique", "texte court", "reformulation", "production", "interpretation"};
            };
            case ARABIC -> switch (band) {
                case "debutant" -> new String[]{"الحروف", "المقاطع", "الكلمات", "الجمل القصيرة", "المفرد", "الجمع", "القراءة", "الكتابة", "الإملاء", "المعجم"};
                case "intermediaire" -> new String[]{"النحو", "التصريف", "التركيب", "التمييز", "الفهم", "الترقيم", "المعنى", "الأساليب", "القراءة", "الكتابة"};
                default -> new String[]{"الفهم القرائي", "الاستنتاج", "التحليل", "إعادة الصياغة", "السياق", "الأسلوب", "الاستعمال", "المعجم", "التركيب", "التفسير"};
            };
            case SCIENCE -> switch (band) {
                case "debutant" -> new String[]{"corps humain", "sens", "eau", "air", "plante", "animal", "lumiere", "chaleur", "proprete", "milieu"};
                case "intermediaire" -> new String[]{"organes", "energie", "matiere", "equilibre", "plantes", "animaux", "meteo", "sante", "transformation", "observation"};
                default -> new String[]{"cause", "consequence", "phenomenes", "experience", "comparaison", "cycle", "analyse", "interpretation", "sciences du quotidien", "conclusion"};
            };
            case HISTORY -> switch (band) {
                case "debutant" -> new String[]{"passe", "present", "personnages", "evenements", "ordre", "temps", "photos anciennes", "ecole", "pays", "memoire"};
                case "intermediaire" -> new String[]{"chronologie", "documents", "patrimoine", "faits", "personnages", "epoque", "comparaison", "questions", "cause", "consequence"};
                default -> new String[]{"analyse historique", "sources", "contexte", "transition", "ordre chronologique", "patrimoine tunisien", "faits", "lien", "sens", "interpretation"};
            };
            case GEOGRAPHY -> switch (band) {
                case "debutant" -> new String[]{"orientation", "carte", "ville", "campagne", "mer", "montagne", "desert", "meteo", "pays", "frontiere"};
                case "intermediaire" -> new String[]{"climat", "lecture de carte", "paysage", "points cardinaux", "Tunisie", "voisins", "symboles", "localisation", "eaux", "population"};
                default -> new String[]{"analyse de carte", "lien climat-territoire", "milieux", "frontieres", "activites", "villes", "lecture", "comparaison", "conclusion", "repartition"};
            };
            case MATH -> switch (band) {
                case "debutant" -> new String[]{"calcul", "comptage", "comparaison", "addition", "soustraction", "formes", "ordre", "temps", "nombres", "mesure"};
                case "intermediaire" -> new String[]{"problemes", "fractions", "multiplication", "division", "raisonnement", "geometrie", "mesure", "motifs", "nombre", "strategie"};
                default -> new String[]{"raisonnement", "problemes", "verification", "motifs", "representation", "geometrie", "construction", "interpretation", "strategie", "mesure"};
            };
        };
        return pool[Math.floorMod(offset, pool.length)];
    }

    private String learningObjective(Subject subject, Difficulty difficulty, AppLanguage language) {
        if (language == AppLanguage.ARABIC) {
            return switch (subject) {
                case FRENCH, ARABIC -> switch (difficulty) {
                    case SIMPLE -> "تمييز الكلمات الأساسية";
                    case MOYEN -> "تطبيق قاعدة لغوية بسيطة";
                    case DIFFICILE -> "تحليل بنية الجملة";
                    case EXCELLENT -> "استنتاج المعنى من السياق";
                };
                case SCIENCE -> switch (difficulty) {
                    case SIMPLE -> "التعرف على المفاهيم العلمية الأساسية";
                    case MOYEN -> "الربط بين السبب والنتيجة";
                    case DIFFICILE -> "تفسير ظاهرة علمية قصيرة";
                    case EXCELLENT -> "المقارنة بين مفاهيم علمية";
                };
                case HISTORY -> switch (difficulty) {
                    case SIMPLE -> "تحديد حدث أو شخصية";
                    case MOYEN -> "ترتيب الأحداث زمنيا";
                    case DIFFICILE -> "فهم علاقة حدثين";
                    case EXCELLENT -> "استخلاص دلالة تاريخية";
                };
                case GEOGRAPHY -> switch (difficulty) {
                    case SIMPLE -> "تمييز الاتجاهات أو الأماكن";
                    case MOYEN -> "قراءة معلومة جغرافية بسيطة";
                    case DIFFICILE -> "تفسير معطى من خريطة";
                    case EXCELLENT -> "تحليل علاقة بين المناخ والمكان";
                };
                case MATH -> switch (difficulty) {
                    case SIMPLE -> "إجراء حساب مباشر";
                    case MOYEN -> "حل مسألة قصيرة";
                    case DIFFICILE -> "اختيار استراتيجية حساب";
                    case EXCELLENT -> "تفسير الحل والتحقق منه";
                };
            };
        }

        return switch (subject) {
            case FRENCH, ARABIC -> switch (difficulty) {
                case SIMPLE -> "reconnaitre les mots essentiels";
                case MOYEN -> "appliquer une regle de langue simple";
                case DIFFICILE -> "analyser la structure d'une phrase";
                case EXCELLENT -> "deduire le sens a partir du contexte";
            };
            case SCIENCE -> switch (difficulty) {
                case SIMPLE -> "identifier un concept scientifique de base";
                case MOYEN -> "faire un lien cause-consequence";
                case DIFFICILE -> "expliquer une petite situation scientifique";
                case EXCELLENT -> "comparer deux notions scientifiques";
            };
            case HISTORY -> switch (difficulty) {
                case SIMPLE -> "identifier un evenement ou un personnage";
                case MOYEN -> "ordonner des faits dans le temps";
                case DIFFICILE -> "comprendre le lien entre deux evenements";
                case EXCELLENT -> "interpreter la portee d'un fait historique";
            };
            case GEOGRAPHY -> switch (difficulty) {
                case SIMPLE -> "reconnaitre un lieu ou une direction";
                case MOYEN -> "lire une information geographique simple";
                case DIFFICILE -> "interpretrer une donnee de carte";
                case EXCELLENT -> "analyser le lien entre climat et territoire";
            };
            case MATH -> switch (difficulty) {
                case SIMPLE -> "effectuer un calcul direct";
                case MOYEN -> "resoudre un probleme court";
                case DIFFICILE -> "choisir une strategie de calcul";
                case EXCELLENT -> "justifier et verifier la solution";
            };
        };
    }

    private int difficultyWeight(Difficulty difficulty) {
        return switch (difficulty) {
            case SIMPLE -> 0;
            case MOYEN -> 1;
            case DIFFICILE -> 2;
            case EXCELLENT -> 3;
        };
    }

    private String callOllama(String prompt) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", ollamaModel);
        body.put("prompt", prompt);
        body.put("stream", false);
        body.put("options", Map.of("temperature", ollamaTemperature));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.postForEntity(
                ollamaBaseUrl + "/api/generate",
                new HttpEntity<>(body, headers),
                String.class
        );

        return response.getBody();
    }

    @SuppressWarnings("unchecked")
    private List<Question> parseOllamaResponse(String body,
                                               Subject subject,
                                               int classLevel,
                                               Difficulty difficulty) {
        try {
            if (body == null || body.isBlank()) {
                log.error("Reponse vide de l'API Ollama");
                return List.of();
            }

            JsonNode root = objectMapper.readTree(body);
            String text = root.path("response").asText("");
            if (text.isBlank()) {
                log.info("Champ 'response' vide dans la reponse Ollama");
                return List.of();
            }

            String json = cleanJsonText(text);
            if (!json.isBlank()) {
                List<?> questionsList = objectMapper.readValue(json, List.class);
                return buildQuestionsFromRaw(questionsList, subject, classLevel, difficulty);
            }

            int idx = text.indexOf('[');
            int last = text.lastIndexOf(']');
            if (idx >= 0 && last > idx) {
                String jsonArray = text.substring(idx, last + 1);
                List<?> questionsList = objectMapper.readValue(jsonArray, List.class);
                return buildQuestionsFromRaw(questionsList, subject, classLevel, difficulty);
            }

            log.info("Aucun tableau JSON trouve dans la reponse de l'API Ollama");
            return List.of();
        } catch (Exception e) {
            log.error("Erreur parsing reponse Ollama : {}", e.getMessage(), e);
            return List.of();
        }
    }

    private String cleanJsonText(String text) {
        if (text == null) {
            return "";
        }
        String cleaned = text.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7).trim();
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3).trim();
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3).trim();
        }
        return cleaned;
    }

    @SuppressWarnings("unchecked")
    private List<Question> buildQuestionsFromRaw(List<?> rawList,
                                                 Subject subject,
                                                 int classLevel,
                                                 Difficulty difficulty) {
        List<Question> result = new ArrayList<>();
        for (Object item : rawList) {
            if (!(item instanceof Map)) {
                continue;
            }
            Map<?, ?> q = (Map<?, ?>) item;

            Object tmp;
            tmp = q.get("questionText");
            String questionText = tmp == null ? "" : tmp.toString();
            tmp = q.get("choiceA");
            String choiceA = tmp == null ? "" : tmp.toString();
            tmp = q.get("choiceB");
            String choiceB = tmp == null ? "" : tmp.toString();
            tmp = q.get("choiceC");
            String choiceC = tmp == null ? "" : tmp.toString();
            tmp = q.get("choiceD");
            String choiceD = tmp == null ? "" : tmp.toString();
            tmp = q.get("correctChoice");
            String correctChoice = tmp == null ? "" : tmp.toString();
            tmp = q.get("explanation");
            String explanation = tmp == null ? "" : tmp.toString();

            Question question = Question.builder()
                    .questionText(questionText)
                    .choiceA(choiceA)
                    .choiceB(choiceB)
                    .choiceC(choiceC)
                    .choiceD(choiceD)
                    .correctChoice(correctChoice)
                    .explanation(explanation)
                    .subject(subject)
                    .classLevel(classLevel)
                    .difficulty(difficulty)
                    .build();
            result.add(question);
        }
        return result;
    }

    private List<Question> normalizeAndDiversify(List<Question> questions, AppLanguage language) {
        List<Question> normalized = new ArrayList<>();
        Set<String> seenQuestionTexts = new HashSet<>();
        List<String> seenSignatures = new ArrayList<>();

        for (Question question : questions) {
            if (question == null) {
                continue;
            }

            sanitize(question);

            if (isBlank(question.getQuestionText())
                    || isBlank(question.getChoiceA())
                    || isBlank(question.getChoiceB())
                    || isBlank(question.getChoiceC())
                    || isBlank(question.getChoiceD())) {
                continue;
            }

            if (!isPedagogicallyValid(question)) {
                continue;
            }

            if (!isSubjectConsistent(question, language)) {
                continue;
            }

            if (!isDifficultyConsistent(question)) {
                continue;
            }

            int classLevel = Optional.ofNullable(question.getClassLevel()).orElse(1);
            Difficulty questionDifficulty = Optional.ofNullable(question.getDifficulty()).orElse(Difficulty.MOYEN);
            AppLanguage effectiveLanguage = language;
            question.setTopicTag(subjectTheme(question.getSubject(), classLevel, questionDifficulty, normalized.size() + 1, effectiveLanguage));
            question.setGenerationProfile(questionProfile(classLevel, questionDifficulty, normalized.size() + 1, effectiveLanguage));

            String dedupeKey = question.getQuestionText().trim().toLowerCase();
            if (!seenQuestionTexts.add(dedupeKey)) {
                continue;
            }

            String signature = questionSignature(question);
            if (isNearDuplicate(signature, seenSignatures)) {
                continue;
            }

            shuffleChoicesAndFixCorrect(question);
            question.setQualityScore(qualityScore(question, question.getDifficulty()));
            seenSignatures.add(signature);
            normalized.add(question);
        }
        return normalized;
    }

    private boolean isSubjectConsistent(Question question, AppLanguage language) {
        Subject subject = question.getSubject();
        String text = (question.getQuestionText() + " " + question.getExplanation()).toLowerCase();

        if (subject == null) {
            return false;
        }

        if (language == AppLanguage.ARABIC) {
            return switch (subject) {
                case MATH -> containsAny(text, "كم", "يساوي", "عدد", "جمع", "طرح", "ضرب", "قيمة", "شكل");
                case SCIENCE -> containsAny(text, "جسم", "نبات", "حيوان", "ماء", "هواء", "عين", "أذن", "صحة");
                case GEOGRAPHY -> containsAny(text, "عاصمة", "قارة", "بحر", "خريطة", "شمال", "جنوب", "شرق", "غرب");
                case HISTORY -> containsAny(text, "تاريخ", "الماضي", "قديم", "حقبة", "زمني", "حدث");
                case FRENCH -> containsAny(text, "كلمة", "جملة", "حرف", "فعل", "اسم", "جمع", "مفرد");
                case ARABIC -> containsAny(text, "كلمة", "جملة", "حرف", "لغة", "مرادف", "معنى", "العربية");
            };
        }

        return switch (subject) {
            case MATH -> containsAny(text,
                    "combien", "calcule", "addition", "soustraction", "multiplication", "division",
                    "nombre", "chiffre", "triangle", "carre", "cercle", "rectangle", "semaine", "jours")
                    && !containsAny(text, "capitale", "fleuve", "continent", "tunisie", "pays voisin", "planet");
            case SCIENCE -> containsAny(text,
                    "corps", "sens", "animal", "plante", "eau", "air", "soleil", "lune", "saison",
                    "pluie", "meteo", "chaud", "froid", "jour", "nuit")
                    && !containsAny(text, "capitale", "fleuve", "continent", "point cardinal", "histoire politique");
            case GEOGRAPHY -> containsAny(text,
                    "pays", "capitale", "continent", "mer", "ocean", "fleuve", "montagne", "carte", "nord", "sud", "est", "ouest");
            case HISTORY -> containsAny(text,
                    "histoire", "avant", "apres", "ancien", "epoque", "personnage", "date", "civilisation");
            case FRENCH -> containsAny(text,
                    "mot", "phrase", "verbe", "nom", "adjectif", "alphabet", "lettre", "syllabe", "pluriel", "singulier");
            case ARABIC -> containsAny(text,
                    "lettre", "mot", "phrase", "arabe", "alphabet", "vocabulaire");
        };
    }

    private boolean containsAny(String text, String... tokens) {
        if (text == null || text.isBlank()) {
            return false;
        }
        for (String token : tokens) {
            if (token != null && !token.isBlank() && text.contains(token.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void sanitize(Question question) {
        question.setQuestionText(cleanSentence(question.getQuestionText()));
        question.setChoiceA(cleanSentence(question.getChoiceA()));
        question.setChoiceB(cleanSentence(question.getChoiceB()));
        question.setChoiceC(cleanSentence(question.getChoiceC()));
        question.setChoiceD(cleanSentence(question.getChoiceD()));
        question.setExplanation(cleanSentence(question.getExplanation()));
        question.setCorrectChoice(Optional.ofNullable(question.getCorrectChoice())
                .map(String::trim)
                .map(String::toUpperCase)
                .orElse("A"));

        String q = question.getQuestionText();
        if (!q.isBlank() && !(q.endsWith("?") || q.endsWith("؟"))) {
            question.setQuestionText(q + "?");
        }
    }

    private String cleanSentence(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("\\s+", " ").trim();
    }

    private boolean isPedagogicallyValid(Question question) {
        // Question text length check
        String q = question.getQuestionText().trim();
        if (q.length() < 8 || q.length() > 220) {
            return false;
        }

        // Explanation length check
        String exp = question.getExplanation().trim();
        if (exp.length() < 6 || exp.length() > 260) {
            return false;
        }

        // Question must end with question mark
        if (!(q.endsWith("?") || q.endsWith("؟"))) {
            return false;
        }

        // All 4 choices must exist and be different
        String a = question.getChoiceA().trim();
        String b = question.getChoiceB().trim();
        String c = question.getChoiceC().trim();
        String d = question.getChoiceD().trim();

        if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            return false;
        }

        Set<String> uniqueChoices = Set.of(
                a.toLowerCase(),
                b.toLowerCase(),
                c.toLowerCase(),
                d.toLowerCase()
        );
        if (uniqueChoices.size() < 4) {
            return false;
        }

        // All choices must be reasonably short
        if (a.length() > 180 || b.length() > 180 || c.length() > 180 || d.length() > 180) {
            return false;
        }

        // Correct choice must be valid (A/B/C/D)
        String correct = Optional.ofNullable(question.getCorrectChoice())
                .map(String::trim)
                .map(String::toUpperCase)
                .orElse("");
        
        if (!("A".equals(correct) || "B".equals(correct) || "C".equals(correct) || "D".equals(correct))) {
            return false;
        }

        // Reject questions that look like placeholders or examples
        String qLower = q.toLowerCase();
        if (qLower.contains("..." ) || qLower.contains("placeholder") || 
            qLower.contains("example") || qLower.contains("replace this") ||
            qLower.startsWith("qu ") || qLower.startsWith("write") ||
            q.contains("null") || q.contains("undefined")) {
            return false;
        }

        // Reject obvious nonsense/hallucination patterns observed in prior outputs
        if (qLower.contains("pouvoir special")
                || qLower.contains("cherubin")
                || qLower.contains("favorit")
                || qLower.contains("trop fatigue")
                || qLower.contains("etrangers")) {
            return false;
        }

        // Question should contain multiple words (not single word question)
        if (q.split("\\s+").length < 3) {
            return false;
        }

        return true;
    }

    private boolean isDifficultyConsistent(Question question) {
        String text = (question.getQuestionText() + " " + question.getExplanation()).toLowerCase();
        int words = text.split("\\s+").length;
        Difficulty difficulty = question.getDifficulty();

        if (difficulty == null) {
            return true;
        }

        return switch (difficulty) {
            case SIMPLE -> words <= 35;
            case MOYEN -> words >= 12 && words <= 55;
            case DIFFICILE -> words >= 14;
            case EXCELLENT -> words >= 18;
        };
    }

    private int qualityScore(Question question, Difficulty expectedDifficulty) {
        int score = 0;
        String q = question.getQuestionText().trim();
        String exp = question.getExplanation().trim();
        score += Math.min(q.length(), 90);
        score += Math.min(exp.length(), 80);
        score += Math.min(tokenCount(q) * 3, 24);
        score += Math.min(tokenCount(exp) * 2, 24);

        if (exp.split("\\s+").length >= 8) {
            score += 18;
        }

        List<String> choices = List.of(question.getChoiceA(), question.getChoiceB(), question.getChoiceC(), question.getChoiceD());
        boolean conciseChoices = choices.stream().allMatch(c -> c != null && c.trim().length() >= 1 && c.trim().length() <= 40);
        if (conciseChoices) {
            score += 15;
        }

        if (question.getTopicTag() != null && !question.getTopicTag().isBlank()) {
            score += 10;
        }

        if (question.getGenerationProfile() != null && !question.getGenerationProfile().isBlank()) {
            score += 5;
        }

        if (question.getDifficulty() == expectedDifficulty) {
            score += 30;
        }
        if (isDifficultyConsistent(question)) {
            score += 20;
        }
        if (isSingleAnswerWellJustified(question)) {
            score += 20;
        }
        return score;
    }

    private List<Question> selectBestQuestions(List<Question> source, Difficulty difficulty, int limit) {
        List<Question> unique = takeUniqueByQuestionText(source, 1000);
        unique.forEach(q -> q.setQualityScore(Optional.ofNullable(q.getQualityScore()).orElse(qualityScore(q, difficulty))));
        unique.sort(Comparator
                .comparingInt((Question q) -> Optional.ofNullable(q.getQualityScore()).orElse(0))
                .reversed()
                .thenComparing(q -> Optional.ofNullable(q.getTopicTag()).orElse(""))
                .thenComparing(Question::getQuestionText));

        List<Question> selected = new ArrayList<>();
        List<String> seenSignatures = new ArrayList<>();
        Map<String, Integer> topicCounts = new HashMap<>();

        for (Question question : unique) {
            if (selected.size() >= limit) {
                break;
            }

            String signature = questionSignature(question);
            if (isNearDuplicate(signature, seenSignatures)) {
                continue;
            }

            String topic = Optional.ofNullable(question.getTopicTag()).orElse("global");
            int maxPerTopic = selected.size() < limit - 2 ? 1 : 2;
            if (topicCounts.getOrDefault(topic, 0) >= maxPerTopic && selected.size() < limit - 2) {
                continue;
            }

            selected.add(question);
            seenSignatures.add(signature);
            topicCounts.merge(topic, 1, Integer::sum);
        }

        if (selected.size() < limit) {
            for (Question question : unique) {
                if (selected.size() >= limit) {
                    break;
                }

                String signature = questionSignature(question);
                if (isNearDuplicate(signature, seenSignatures)) {
                    continue;
                }

                selected.add(question);
                seenSignatures.add(signature);
            }
        }

        if (selected.size() < limit) {
            Set<String> selectedTexts = new HashSet<>();
            for (Question selectedQuestion : selected) {
                selectedTexts.add(normalizeForContains(selectedQuestion.getQuestionText()));
            }

            for (Question question : unique) {
                if (selected.size() >= limit) {
                    break;
                }

                String key = normalizeForContains(question.getQuestionText());
                if (key.isBlank() || !selectedTexts.add(key)) {
                    continue;
                }

                selected.add(question);
            }
        }

        return selected.size() <= limit
                ? selected
                : new ArrayList<>(selected.subList(0, limit));
    }

    private void shuffleChoicesAndFixCorrect(Question question) {
        Map<String, String> originalChoices = Map.of(
                "A", question.getChoiceA().trim(),
                "B", question.getChoiceB().trim(),
                "C", question.getChoiceC().trim(),
                "D", question.getChoiceD().trim()
        );

        String originalCorrect = Optional.ofNullable(question.getCorrectChoice())
                .map(String::trim)
                .map(String::toUpperCase)
                .orElse("A");

        if (!originalChoices.containsKey(originalCorrect)) {
            originalCorrect = "A";
        }

        String correctText = originalChoices.get(originalCorrect);

        List<Map.Entry<String, String>> shuffled = new ArrayList<>(originalChoices.entrySet());
        Collections.shuffle(shuffled);

        question.setChoiceA(shuffled.get(0).getValue());
        question.setChoiceB(shuffled.get(1).getValue());
        question.setChoiceC(shuffled.get(2).getValue());
        question.setChoiceD(shuffled.get(3).getValue());

        for (int i = 0; i < shuffled.size(); i++) {
            if (Objects.equals(shuffled.get(i).getValue(), correctText)) {
                question.setCorrectChoice(switch (i) {
                    case 0 -> "A";
                    case 1 -> "B";
                    case 2 -> "C";
                    default -> "D";
                });
                return;
            }
        }

        question.setCorrectChoice("A");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private List<Question> takeUniqueByQuestionText(List<Question> source, int limit) {
        List<Question> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (Question question : source) {
            if (question == null || isBlank(question.getQuestionText())) {
                continue;
            }
            String key = question.getQuestionText().trim().toLowerCase();
            if (!seen.add(key)) {
                continue;
            }
            result.add(question);
            if (result.size() >= limit) {
                break;
            }
        }

        return result;
    }

    private List<Question> ensureMinimumPlayableSet(List<Question> selected,
                                                    List<Question> fallbackSource,
                                                    Subject subject,
                                                    int classLevel,
                                                    Difficulty difficulty) {
        List<Question> result = new ArrayList<>(selected);
        Set<String> seen = new HashSet<>();

        for (Question q : result) {
            seen.add(normalizeForContains(q.getQuestionText()));
        }

        for (Question q : fallbackSource) {
            if (result.size() >= TARGET_QUESTION_COUNT) {
                break;
            }
            String key = normalizeForContains(q.getQuestionText());
            if (key.isBlank() || !seen.add(key)) {
                continue;
            }
            q.setSubject(subject);
            q.setClassLevel(classLevel);
            q.setDifficulty(difficulty);
            sanitize(q);
            ensureCorrectChoiceIsValid(q);
            if (isPedagogicallyValid(q)) {
                result.add(q);
            }
        }

        return result.size() > TARGET_QUESTION_COUNT
                ? new ArrayList<>(result.subList(0, TARGET_QUESTION_COUNT))
                : result;
    }

    private String questionSignature(Question question) {
        String combined = Optional.ofNullable(question.getQuestionText()).orElse("") + " "
                + Optional.ofNullable(question.getExplanation()).orElse("");
        return normalizeSignature(combined);
    }

    private boolean isNearDuplicate(String signature, List<String> seenSignatures) {
        if (signature == null || signature.isBlank()) {
            return true;
        }

        for (String seen : seenSignatures) {
            if (semanticOverlap(signature, seen) >= DUPLICATE_SIMILARITY_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    private double semanticOverlap(String a, String b) {
        Set<String> tokensA = signatureTokens(a);
        Set<String> tokensB = signatureTokens(b);
        if (tokensA.isEmpty() || tokensB.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(tokensA);
        intersection.retainAll(tokensB);

        Set<String> union = new HashSet<>(tokensA);
        union.addAll(tokensB);

        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }

    private Set<String> signatureTokens(String text) {
        String normalized = normalizeSignature(text);
        if (normalized.isBlank()) {
            return Set.of();
        }

        Set<String> tokens = new HashSet<>();
        for (String token : normalized.split("\\s+")) {
            if (token.length() >= 3) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private String normalizeSignature(String text) {
        if (text == null) {
            return "";
        }

        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N} ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private int tokenCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        return (int) Pattern.compile("\\S+").matcher(text).results().count();
    }

    private String classBand(int classLevel) {
        if (classLevel <= 2) {
            return "debutant";
        }
        if (classLevel <= 4) {
            return "intermediaire";
        }
        return "avance";
    }

    private String questionProfile(int classLevel, Difficulty difficulty, int index, AppLanguage language) {
        int slot = Math.floorMod(classLevel + difficultyWeight(difficulty) + index, 8);
        if (language == AppLanguage.ARABIC) {
            return switch (slot) {
                case 0 -> "استرجاع مباشر";
                case 1 -> "تطبيق عملي";
                case 2 -> "تفكير وتفسير";
                case 3 -> "مقارنة";
                case 4 -> "تحليل";
                case 5 -> "نقل المفهوم";
                case 6 -> "تثبيت مكتسبات";
                default -> "استنتاج";
            };
        }

        return switch (slot) {
            case 0 -> "rappel direct";
            case 1 -> "application";
            case 2 -> "raisonnement";
            case 3 -> "comparaison";
            case 4 -> "analyse";
            case 5 -> "transfert";
            case 6 -> "consolidation";
            default -> "inference";
        };
    }

        private List<Question> buildFallbackQuestions(int classLevel, Subject subject, Difficulty difficulty, AppLanguage language) {
            if (language == AppLanguage.ARABIC) {
                return buildArabicFallbackQuestions(classLevel, subject, difficulty);
            }

            List<Question> questions = switch (subject) {
                case MATH -> List.of(
                        createQuestion("Combien font 3 + 2?", "4", "5", "6", "7", "B", "En mathematiques, 3 + 2 = 5.", subject, classLevel, difficulty),
                        createQuestion("Quel nombre vient apres 9?", "10", "8", "11", "7", "A", "Dans la suite des nombres, 10 vient apres 9.", subject, classLevel, difficulty),
                        createQuestion("Combien font 6 - 1?", "7", "4", "5", "3", "C", "En soustraction, 6 - 1 = 5.", subject, classLevel, difficulty),
                        createQuestion("Quelle forme a trois cotes?", "Cercle", "Carre", "Rectangle", "Triangle", "D", "Un triangle a trois cotes.", subject, classLevel, difficulty),
                        createQuestion("Combien font 2 x 3?", "5", "6", "4", "3", "B", "En multiplication, 2 x 3 = 6.", subject, classLevel, difficulty),
                        createQuestion("Quel est le double de 4?", "6", "8", "10", "12", "B", "Le double de 4 est 8.", subject, classLevel, difficulty),
                        createQuestion("Combien font 5 + 4?", "8", "9", "7", "6", "B", "5 + 4 = 9.", subject, classLevel, difficulty),
                        createQuestion("Combien font 9 - 3?", "5", "7", "6", "8", "C", "9 - 3 = 6.", subject, classLevel, difficulty),
                        createQuestion("Quelle forme a quatre cotes egales?", "Triangle", "Carre", "Cercle", "Ligne", "B", "Un carre a quatre cotes egales.", subject, classLevel, difficulty),
                        createQuestion("Combien font 1 + 7?", "6", "7", "8", "9", "C", "1 + 7 = 8.", subject, classLevel, difficulty)
                );
                case SCIENCE -> List.of(
                        createQuestion("Quel organe nous permet de voir?", "Oreille", "Nez", "Oeil", "Main", "C", "En science, l'oeil est l'organe de la vue.", subject, classLevel, difficulty),
                        createQuestion("Quelle saison est souvent tres chaude?", "Hiver", "Ete", "Automne", "Printemps", "B", "En meteo, l'ete est generalement la saison la plus chaude.", subject, classLevel, difficulty),
                        createQuestion("De quoi les plantes ont-elles besoin pour pousser?", "Soleil et eau", "Papier", "Piles", "Metal", "A", "En science, les plantes ont besoin d'eau et de soleil.", subject, classLevel, difficulty),
                        createQuestion("Lequel est un animal domestique?", "Lion", "Chat", "Tigre", "Loup", "B", "Un chat est un animal domestique.", subject, classLevel, difficulty),
                        createQuestion("Quelle partie du corps aide a entendre?", "Oreille", "Coude", "Genou", "Doigt", "A", "L'oreille sert a entendre les sons.", subject, classLevel, difficulty),
                        createQuestion("Quel sens utilise le nez?", "La vue", "L'ouie", "L'odorat", "Le gout", "C", "Le nez sert a sentir les odeurs.", subject, classLevel, difficulty),
                        createQuestion("Que boit-on pour rester en bonne sante?", "Du sable", "De l'eau", "Du carton", "De la craie", "B", "L'eau est importante pour le corps.", subject, classLevel, difficulty),
                        createQuestion("Quel est le moment entre le jour et la nuit?", "Matin", "Midi", "Soir", "Minuit", "C", "Le soir vient avant la nuit.", subject, classLevel, difficulty),
                        createQuestion("Quel animal pond des oeufs?", "Chat", "Vache", "Poule", "Cheval", "C", "La poule pond des oeufs.", subject, classLevel, difficulty),
                        createQuestion("Quel objet emet de la lumiere?", "Lampe", "Cahier", "Chaise", "Stylo", "A", "Une lampe peut emettre de la lumiere.", subject, classLevel, difficulty)
                );
                case GEOGRAPHY -> List.of(
                        createQuestion("Quelle est la capitale de la Tunisie?", "Sfax", "Tunis", "Sousse", "Bizerte", "B", "La capitale de la Tunisie est Tunis.", subject, classLevel, difficulty),
                        createQuestion("Sur quelle mer se trouve la Tunisie?", "Mer Rouge", "Mer Noire", "Mer Mediterranee", "Mer Caspienne", "C", "La Tunisie se trouve sur la mer Mediterranee.", subject, classLevel, difficulty),
                        createQuestion("Quel point cardinal indique le haut d'une carte scolaire?", "Nord", "Sud", "Est", "Ouest", "A", "En geographie scolaire, le nord est en haut.", subject, classLevel, difficulty),
                        createQuestion("Quel continent contient la Tunisie?", "Europe", "Afrique", "Asie", "Amerique", "B", "La Tunisie est en Afrique.", subject, classLevel, difficulty),
                        createQuestion("Quel est un pays voisin de la Tunisie?", "Japon", "Bresil", "Algerie", "Canada", "C", "L'Algerie est un pays voisin de la Tunisie.", subject, classLevel, difficulty),
                        createQuestion("Quel instrument aide a trouver le nord?", "Boussole", "Crayon", "Gomme", "Cahier", "A", "La boussole indique le nord.", subject, classLevel, difficulty),
                        createQuestion("Comment appelle-t-on l'endroit ou l'eau et la terre se rencontrent?", "Montagne", "Plage", "Foret", "Desert", "B", "La plage est au bord de la mer.", subject, classLevel, difficulty),
                        createQuestion("Quel mot designe une grande etendue d'eau salee?", "Lac", "Riviere", "Mer", "Source", "C", "La mer est une grande etendue d'eau salee.", subject, classLevel, difficulty),
                        createQuestion("Dans quelle direction le soleil se leve-t-il?", "Nord", "Sud", "Est", "Ouest", "C", "Le soleil se leve a l'est.", subject, classLevel, difficulty),
                        createQuestion("Quel est un element d'une carte?", "Legende", "Fourchette", "Tasse", "Brosse", "A", "La legende aide a lire une carte.", subject, classLevel, difficulty)
                );
                case FRENCH -> List.of(
                        createQuestion("Quel mot est au pluriel?", "chat", "chats", "maison", "arbre", "B", "En francais, chats est au pluriel.", subject, classLevel, difficulty),
                        createQuestion("Combien de lettres dans le mot 'soleil'?", "5", "6", "7", "4", "B", "Le mot soleil contient 6 lettres.", subject, classLevel, difficulty),
                        createQuestion("Quel est un verbe?", "courir", "bleu", "table", "ecole", "A", "En francais, courir est un verbe.", subject, classLevel, difficulty),
                        createQuestion("Quelle phrase est correcte?", "Le enfants joue.", "Les enfants jouent.", "Les enfant joue.", "Le enfant jouent.", "B", "La forme correcte est: Les enfants jouent.", subject, classLevel, difficulty),
                        createQuestion("Quel mot commence par la lettre A?", "ballon", "arbre", "stylo", "livre", "B", "Arbre commence par la lettre A.", subject, classLevel, difficulty),
                        createQuestion("Quel est le contraire de petit?", "Grand", "Bleu", "Joli", "Vite", "A", "Le contraire de petit est grand.", subject, classLevel, difficulty),
                        createQuestion("Quel mot est un nom?", "manger", "joli", "maison", "vite", "C", "Maison est un nom.", subject, classLevel, difficulty),
                        createQuestion("Combien de mots dans 'Le chat dort'?", "1", "2", "3", "4", "C", "La phrase contient trois mots.", subject, classLevel, difficulty),
                        createQuestion("Quel mot rime avec 'pain'?", "main", "table", "chien", "livre", "A", "Pain et main riment.", subject, classLevel, difficulty),
                        createQuestion("Quelle lettre vient apres B?", "A", "C", "D", "E", "B", "Dans l'alphabet, C suit B.", subject, classLevel, difficulty)
                );
                case ARABIC -> List.of(
                        createQuestion("Quel mot est un mot arabe courant de l'ecole?", "kitab", "computer", "football", "internet", "A", "Kitab est un mot arabe courant.", subject, classLevel, difficulty),
                        createQuestion("Quelle lettre arabe vient au debut de l'alphabet?", "alif", "ya", "mim", "nun", "A", "Dans l'alphabet arabe, alif vient au debut.", subject, classLevel, difficulty),
                        createQuestion("Quel mot signifie 'ecole' en arabe?", "madrasa", "bahr", "qalam", "shams", "A", "Madrasa signifie ecole en arabe.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'soleil'?", "qamar", "shams", "ma", "bint", "B", "Shams signifie soleil.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'livre'?", "bayt", "kitab", "bab", "nahar", "B", "Kitab signifie livre.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'maison'?", "bayt", "shams", "qalam", "madrasa", "A", "Bayt signifie maison.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'stylo'?", "qalam", "kitab", "bab", "qamar", "A", "Qalam signifie stylo.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'lune'?", "shams", "qamar", "ma", "bahr", "B", "Qamar signifie lune.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'eau'?", "ma", "nour", "walad", "bint", "A", "Ma signifie eau.", subject, classLevel, difficulty),
                        createQuestion("Quel mot arabe signifie 'garcon'?", "bint", "walad", "qalam", "kitab", "B", "Walad signifie garcon.", subject, classLevel, difficulty)
                );
                case HISTORY -> List.of(
                        createQuestion("L'histoire parle surtout de quoi?", "Du passe", "Du futur", "Des jeux video", "Des recettes", "A", "L'histoire etudie le passe.", subject, classLevel, difficulty),
                        createQuestion("Que signifie 'ancien' en histoire?", "Tres recent", "Qui date du passe", "Imaginaire", "Rapide", "B", "Ancien signifie qui vient du passe.", subject, classLevel, difficulty),
                        createQuestion("Quel outil aide a ranger les evenements du passe?", "Ligne du temps", "Boussole", "Calculatrice", "Thermometre", "A", "En histoire, la ligne du temps organise les evenements.", subject, classLevel, difficulty),
                        createQuestion("En histoire, on parle souvent de quelle epoque?", "Epoque ancienne", "Epoque des robots", "Epoque du futur", "Epoque imaginaire", "A", "L'histoire etudie des epoques reelles du passe.", subject, classLevel, difficulty),
                        createQuestion("Que fait-on en histoire a l'ecole?", "On etudie des faits passes", "On change la meteo", "On construit des fusees", "On dessine des cartes au hasard", "A", "En histoire, on apprend des faits passes verifies.", subject, classLevel, difficulty),
                        createQuestion("Qui etudie le passe a l'ecole?", "L'historien", "Le jardinier", "Le cuisinier", "Le peintre", "A", "L'historien etudie le passe.", subject, classLevel, difficulty),
                        createQuestion("Une ligne du temps sert a quoi?", "Ranger les evenements", "Mesurer la pluie", "Compter les billes", "Tracer des cercles", "A", "La ligne du temps range les evenements dans l'ordre.", subject, classLevel, difficulty),
                        createQuestion("Le passe est avant quoi?", "Le present", "Le futur", "La nuit", "L'ete", "A", "Le passe vient avant le present.", subject, classLevel, difficulty),
                        createQuestion("Quand apprend-on l'histoire?", "A l'ecole", "Dans la mer", "Dans la cuisine", "Au jardin seulement", "A", "On apprend l'histoire a l'ecole.", subject, classLevel, difficulty),
                        createQuestion("Une photo ancienne montre quoi?", "Le passe", "Le futur", "Un calcul", "Une recette", "A", "Une photo ancienne montre une periode passee.", subject, classLevel, difficulty)
                );
            };

            List<Question> expanded = expandFallbackPool(questions, classLevel, subject, difficulty, language);
            List<Question> normalized = normalizeAndDiversify(expanded, language);
            if (normalized.isEmpty()) {
                normalized = copyQuestions(expanded);
            }
            if (normalized.size() >= TARGET_QUESTION_COUNT) {
                return selectDiverseSlice(normalized, TARGET_QUESTION_COUNT);
            }

            List<Question> completed = new ArrayList<>(normalized);
            Set<String> seen = new HashSet<>();
            for (Question question : completed) {
                seen.add(question.getQuestionText().trim().toLowerCase());
            }

            for (Question question : expanded) {
                if (completed.size() >= TARGET_QUESTION_COUNT) {
                    break;
                }
                String key = question.getQuestionText().trim().toLowerCase();
                if (seen.add(key)) {
                    completed.add(question);
                }
            }

                return completed.size() > TARGET_QUESTION_COUNT
                    ? selectDiverseSlice(completed, TARGET_QUESTION_COUNT)
                    : completed;
        }

            private List<Question> buildArabicFallbackQuestions(int classLevel, Subject subject, Difficulty difficulty) {
            List<Question> questions = switch (subject) {
                case MATH -> List.of(
                    createQuestion("كم يساوي 3 + 2؟", "4", "5", "6", "7", "B", "في الرياضيات 3 + 2 يساوي 5.", subject, classLevel, difficulty),
                    createQuestion("ما هو العدد الذي يأتي بعد 9؟", "10", "8", "11", "7", "A", "العدد 10 يأتي بعد 9.", subject, classLevel, difficulty),
                    createQuestion("كم يساوي 6 - 1؟", "7", "4", "5", "3", "C", "في الطرح 6 - 1 يساوي 5.", subject, classLevel, difficulty),
                    createQuestion("أي شكل له ثلاثة أضلاع؟", "دائرة", "مربع", "مستطيل", "مثلث", "D", "المثلث له ثلاثة أضلاع.", subject, classLevel, difficulty),
                    createQuestion("كم يساوي 2 × 3؟", "5", "6", "4", "3", "B", "في الضرب 2 × 3 يساوي 6.", subject, classLevel, difficulty),
                    createQuestion("ما هو ضعف العدد 4؟", "6", "8", "10", "12", "B", "ضعف العدد 4 هو 8.", subject, classLevel, difficulty),
                    createQuestion("كم يساوي 5 + 4؟", "8", "9", "7", "6", "B", "5 + 4 يساوي 9.", subject, classLevel, difficulty),
                    createQuestion("كم يساوي 9 - 3؟", "5", "7", "6", "8", "C", "9 - 3 يساوي 6.", subject, classLevel, difficulty),
                    createQuestion("أي شكل له أربعة أضلاع متساوية؟", "مثلث", "مربع", "دائرة", "خط", "B", "المربع له أربعة أضلاع متساوية.", subject, classLevel, difficulty),
                    createQuestion("كم يساوي 1 + 7؟", "6", "7", "8", "9", "C", "1 + 7 يساوي 8.", subject, classLevel, difficulty)
                );
                case SCIENCE -> List.of(
                    createQuestion("أي عضو يساعدنا على الرؤية؟", "الأذن", "الأنف", "العين", "اليد", "C", "العين هي عضو الرؤية.", subject, classLevel, difficulty),
                    createQuestion("أي فصل يكون غالبا حارا؟", "الشتاء", "الصيف", "الخريف", "الربيع", "B", "فصل الصيف غالبا هو الأكثر حرارة.", subject, classLevel, difficulty),
                    createQuestion("ماذا تحتاج النباتات لكي تنمو؟", "الشمس والماء", "الورق", "البطاريات", "المعدن", "A", "النبات يحتاج الماء والشمس للنمو.", subject, classLevel, difficulty),
                    createQuestion("أي حيوان يمكن أن يعيش في البيت؟", "أسد", "قط", "نمر", "ذئب", "B", "القط حيوان أليف.", subject, classLevel, difficulty),
                    createQuestion("أي جزء من الجسم يساعد على السمع؟", "الأذن", "المرفق", "الركبة", "الإصبع", "A", "الأذن تساعدنا على السمع.", subject, classLevel, difficulty),
                    createQuestion("أي حاسة نستعمل بالأنف؟", "البصر", "السمع", "الشم", "الذوق", "C", "الأنف نستعمله لحاسة الشم.", subject, classLevel, difficulty),
                    createQuestion("ماذا نشرب لنحافظ على صحتنا؟", "الرمل", "الماء", "الورق", "الطباشير", "B", "الماء مهم لصحة الجسم.", subject, classLevel, difficulty),
                    createQuestion("ما الوقت الذي يأتي قبل الليل؟", "الصباح", "الظهر", "المساء", "منتصف الليل", "C", "المساء يأتي قبل الليل.", subject, classLevel, difficulty),
                    createQuestion("أي حيوان يبيض؟", "قط", "بقرة", "دجاجة", "حصان", "C", "الدجاجة تبيض.", subject, classLevel, difficulty),
                    createQuestion("أي شيء يصدر الضوء؟", "مصباح", "دفتر", "كرسي", "قلم", "A", "المصباح يصدر الضوء.", subject, classLevel, difficulty)
                );
                case GEOGRAPHY -> List.of(
                    createQuestion("ما هي عاصمة تونس؟", "صفاقس", "تونس", "سوسة", "بنزرت", "B", "عاصمة تونس هي مدينة تونس.", subject, classLevel, difficulty),
                    createQuestion("على أي بحر تقع تونس؟", "البحر الأحمر", "البحر الأسود", "البحر الأبيض المتوسط", "بحر قزوين", "C", "تقع تونس على البحر الأبيض المتوسط.", subject, classLevel, difficulty),
                    createQuestion("أي جهة تكون أعلى الخريطة عادة؟", "الشمال", "الجنوب", "الشرق", "الغرب", "A", "عادة يكون الشمال أعلى الخريطة.", subject, classLevel, difficulty),
                    createQuestion("في أي قارة تقع تونس؟", "أوروبا", "إفريقيا", "آسيا", "أمريكا", "B", "تونس تقع في قارة إفريقيا.", subject, classLevel, difficulty),
                    createQuestion("أي دولة مجاورة لتونس؟", "اليابان", "البرازيل", "الجزائر", "كندا", "C", "الجزائر دولة مجاورة لتونس.", subject, classLevel, difficulty),
                    createQuestion("أي أداة تساعد على معرفة الشمال؟", "البوصلة", "قلم", "ممحاة", "دفتر", "A", "البوصلة تحدد الاتجاهات ومنها الشمال.", subject, classLevel, difficulty),
                    createQuestion("ماذا نسمي المكان بين البحر واليابسة؟", "جبل", "شاطئ", "غابة", "صحراء", "B", "الشاطئ هو مكان التقاء البحر باليابسة.", subject, classLevel, difficulty),
                    createQuestion("ما اسم المساحة الكبيرة من الماء المالح؟", "بحيرة", "نهر", "بحر", "عين ماء", "C", "البحر مساحة كبيرة من الماء المالح.", subject, classLevel, difficulty),
                    createQuestion("من أي جهة تشرق الشمس؟", "الشمال", "الجنوب", "الشرق", "الغرب", "C", "تشرق الشمس من جهة الشرق.", subject, classLevel, difficulty),
                    createQuestion("ما العنصر الذي يساعد على قراءة الخريطة؟", "المفتاح", "شوكة", "كوب", "فرشاة", "A", "مفتاح الخريطة يساعد على فهم الرموز.", subject, classLevel, difficulty)
                );
                case FRENCH -> List.of(
                    createQuestion("أي كلمة في صيغة الجمع؟", "chat", "chats", "maison", "arbre", "B", "الكلمة chats في صيغة الجمع.", subject, classLevel, difficulty),
                    createQuestion("كم حرفا في كلمة soleil؟", "5", "6", "7", "4", "B", "كلمة soleil تحتوي على 6 أحرف.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة هي فعل؟", "courir", "bleu", "table", "ecole", "A", "كلمة courir هي فعل.", subject, classLevel, difficulty),
                    createQuestion("أي جملة صحيحة؟", "Le enfants joue.", "Les enfants jouent.", "Les enfant joue.", "Le enfant jouent.", "B", "الجملة الصحيحة هي Les enfants jouent.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة تبدأ بالحرف A؟", "ballon", "arbre", "stylo", "livre", "B", "كلمة arbre تبدأ بالحرف A.", subject, classLevel, difficulty),
                    createQuestion("ما عكس كلمة petit؟", "Grand", "Bleu", "Joli", "Vite", "A", "عكس petit هو Grand.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة هي اسم؟", "manger", "joli", "maison", "vite", "C", "كلمة maison اسم.", subject, classLevel, difficulty),
                    createQuestion("كم كلمة في Le chat dort؟", "1", "2", "3", "4", "C", "الجملة تحتوي على 3 كلمات.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة تُقافي pain؟", "main", "table", "chien", "livre", "A", "pain و main لهما نفس القافية.", subject, classLevel, difficulty),
                    createQuestion("أي حرف يأتي بعد B؟", "A", "C", "D", "E", "B", "الحرف C يأتي بعد B.", subject, classLevel, difficulty)
                );
                case ARABIC -> List.of(
                    createQuestion("أي كلمة معناها كتاب؟", "كتاب", "قلم", "باب", "بيت", "A", "كلمة كتاب تعني book.", subject, classLevel, difficulty),
                    createQuestion("ما هو أول حرف في العربية؟", "أ", "ي", "م", "ن", "A", "الحرف الأول في العربية هو الألف.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها مدرسة؟", "مدرسة", "بحر", "شمس", "قمر", "A", "كلمة مدرسة معناها school.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها شمس؟", "قمر", "شمس", "ماء", "بيت", "B", "كلمة شمس معناها sun.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها بيت؟", "بيت", "قلم", "كتاب", "باب", "A", "كلمة بيت معناها house.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها قلم؟", "قلم", "قمر", "ولد", "بنت", "A", "كلمة قلم معناها pen.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها قمر؟", "شمس", "قمر", "ماء", "نهر", "B", "كلمة قمر معناها moon.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها ماء؟", "ماء", "نور", "ولد", "بنت", "A", "كلمة ماء معناها water.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها ولد؟", "بنت", "ولد", "قلم", "كتاب", "B", "كلمة ولد معناها boy.", subject, classLevel, difficulty),
                    createQuestion("أي كلمة معناها بنت؟", "ولد", "بنت", "باب", "بيت", "B", "كلمة بنت معناها girl.", subject, classLevel, difficulty)
                );
                case HISTORY -> List.of(
                    createQuestion("ماذا ندرس في مادة التاريخ؟", "الماضي", "المستقبل", "الألعاب", "الطبخ", "A", "مادة التاريخ تدرس أحداث الماضي.", subject, classLevel, difficulty),
                    createQuestion("ماذا تعني كلمة قديم؟", "حديث جدا", "من الماضي", "خيالي", "سريع", "B", "القديم يعني شيئا من الماضي.", subject, classLevel, difficulty),
                    createQuestion("ما الأداة التي ترتب أحداث الماضي؟", "الخط الزمني", "البوصلة", "الآلة الحاسبة", "ميزان الحرارة", "A", "الخط الزمني ينظم الأحداث حسب الزمن.", subject, classLevel, difficulty),
                    createQuestion("التاريخ يهتم غالبا بأي زمن؟", "الزمن القديم", "زمن الروبوت", "الزمن القادم", "زمن خيالي", "A", "التاريخ يركز على الأزمنة الماضية.", subject, classLevel, difficulty),
                    createQuestion("ماذا نفعل في درس التاريخ؟", "نتعلم أحداثا ماضية", "نغيّر الطقس", "نبني صواريخ", "نرسم عشوائيا", "A", "في التاريخ نتعلم أحداثا حقيقية من الماضي.", subject, classLevel, difficulty),
                    createQuestion("من يدرس الماضي؟", "المؤرخ", "البستاني", "الطباخ", "الرسام", "A", "المؤرخ يدرس الماضي.", subject, classLevel, difficulty),
                    createQuestion("الخط الزمني يساعدنا على ماذا؟", "ترتيب الأحداث", "قياس المطر", "عد الكرات", "رسم الدوائر", "A", "الخط الزمني يساعد على ترتيب الأحداث.", subject, classLevel, difficulty),
                    createQuestion("الماضي يأتي قبل ماذا؟", "الحاضر", "المستقبل", "الليل", "الصيف", "A", "الماضي يسبق الحاضر.", subject, classLevel, difficulty),
                    createQuestion("أين نتعلم التاريخ؟", "في المدرسة", "في البحر", "في المطبخ", "في الحديقة", "A", "نتعلم التاريخ في المدرسة.", subject, classLevel, difficulty),
                    createQuestion("ماذا تُظهر الصورة القديمة؟", "الماضي", "المستقبل", "حساب", "وصفة", "A", "الصورة القديمة تمثل زمنا ماضيا.", subject, classLevel, difficulty)
                );
            };

            List<Question> expanded = expandFallbackPool(questions, classLevel, subject, difficulty, AppLanguage.ARABIC);
            List<Question> normalized = normalizeAndDiversify(expanded, AppLanguage.ARABIC);
            if (normalized.isEmpty()) {
                normalized = copyQuestions(expanded);
            }

            if (normalized.size() >= TARGET_QUESTION_COUNT) {
                return selectDiverseSlice(normalized, TARGET_QUESTION_COUNT);
            }

            List<Question> completed = new ArrayList<>(normalized);
            Set<String> seen = new HashSet<>();
            for (Question question : completed) {
                seen.add(question.getQuestionText().trim().toLowerCase());
            }

            for (Question question : expanded) {
                if (completed.size() >= TARGET_QUESTION_COUNT) {
                    break;
                }
                String key = question.getQuestionText().trim().toLowerCase();
                if (seen.add(key)) {
                    completed.add(question);
                }
            }

                return completed.size() > TARGET_QUESTION_COUNT
                    ? selectDiverseSlice(completed, TARGET_QUESTION_COUNT)
                    : completed;
            }

    private List<Question> expandFallbackPool(List<Question> base,
                                              int classLevel,
                                              Subject subject,
                                              Difficulty difficulty,
                                              AppLanguage language) {
        List<Question> expanded = new ArrayList<>();
        if (base == null || base.isEmpty()) {
            return expanded;
        }

        int seed = variationSalt();
        int index = 0;
        for (Question original : base) {
            if (original == null) {
                continue;
            }

            index++;
            int[] offsets = new int[] {0, 11, 23, 37};
            for (int offset : offsets) {
                Question variant = copyQuestion(original);
                applyFallbackVariant(variant, classLevel, subject, difficulty, language, index + seed + offset);
                expanded.add(variant);
            }
        }

        return expanded;
    }

    private Question copyQuestion(Question q) {
        return Question.builder()
                .questionText(q.getQuestionText())
                .choiceA(q.getChoiceA())
                .choiceB(q.getChoiceB())
                .choiceC(q.getChoiceC())
                .choiceD(q.getChoiceD())
                .correctChoice(q.getCorrectChoice())
                .explanation(q.getExplanation())
                .subject(q.getSubject())
                .classLevel(q.getClassLevel())
                .difficulty(q.getDifficulty())
                .build();
    }

    private void applyFallbackVariant(Question question,
                                      int classLevel,
                                      Subject subject,
                                      Difficulty difficulty,
                                      AppLanguage language,
                                      int variantIndex) {
        if (question == null) {
            return;
        }

        if (subject == Subject.MATH) {
            adaptMathQuestion(question, classLevel, difficulty, variantIndex);
            return;
        }

        injectContextVariant(question, classLevel, difficulty, language, variantIndex);

        question.setQuestionText(rephraseFallbackQuestion(question.getQuestionText(), language, variantIndex));

        String theme = subjectTheme(subject, classLevel, difficulty, variantIndex, language);
        String objective = learningObjective(subject, difficulty, language);
        String explanation = Optional.ofNullable(question.getExplanation()).orElse("").trim();

        String add = language == AppLanguage.ARABIC
                ? "محور: " + theme + " | هدف: " + objective + "."
                : "Theme: " + theme + " | Objectif: " + objective + ".";

        if (!explanation.contains(add)) {
            question.setExplanation((explanation.isBlank() ? "" : explanation + " ") + add);
        }
    }

    private List<Question> selectDiverseSlice(List<Question> source, int limit) {
        if (source == null || source.isEmpty()) {
            return List.of();
        }
        if (source.size() <= limit) {
            return new ArrayList<>(source);
        }

        List<Question> pool = copyQuestions(source);
        int rotate = Math.floorMod(variationSalt() * 3, pool.size());
        Collections.rotate(pool, rotate);

        List<Question> selected = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Question q : pool) {
            if (selected.size() >= limit) {
                break;
            }
            String key = normalizeForContains(q.getQuestionText());
            if (key.isBlank() || !seen.add(key)) {
                continue;
            }
            selected.add(q);
        }

        return selected.size() >= limit
                ? selected
                : new ArrayList<>(pool.subList(0, limit));
    }

    private String rephraseFallbackQuestion(String questionText, AppLanguage language, int variantIndex) {
        if (questionText == null || questionText.isBlank()) {
            return questionText;
        }

        String text = questionText.trim();
        int slot = Math.floorMod(variantIndex, 4);

        if (language == AppLanguage.ARABIC) {
            if (slot == 1) {
                text = text.replaceFirst("^ما هو", "حدد");
                text = text.replaceFirst("^ما هي", "حدد");
            } else if (slot == 2) {
                text = text.replaceFirst("^أي", "اختر");
            } else if (slot == 3) {
                text = text.replaceFirst("^كم يساوي", "احسب");
            }
        } else {
            if (slot == 1) {
                text = text.replaceFirst("^Quel", "Lequel");
                text = text.replaceFirst("^Quelle", "Laquelle");
            } else if (slot == 2) {
                text = text.replaceFirst("^Combien font", "Calcule");
            } else if (slot == 3) {
                text = text.replaceFirst("^Quel est", "Donne");
                text = text.replaceFirst("^Quelle est", "Donne");
            }
        }

        if (!(text.endsWith("?") || text.endsWith("؟"))) {
            text = text + (language == AppLanguage.ARABIC ? "؟" : "?");
        }
        return text;
    }

    private Question createQuestion(String questionText,
                                    String choiceA,
                                    String choiceB,
                                    String choiceC,
                                    String choiceD,
                                    String correctChoice,
                                    String explanation,
                                    Subject subject,
                                    int classLevel,
                                    Difficulty difficulty) {
        return Question.builder()
                .questionText(questionText)
                .choiceA(choiceA)
                .choiceB(choiceB)
                .choiceC(choiceC)
                .choiceD(choiceD)
                .correctChoice(correctChoice)
                .explanation(explanation)
                .subject(subject)
                .classLevel(classLevel)
                .difficulty(difficulty)
                .build();
    }

    private String subjectLabel(Subject subject) {
        return switch (subject) {
            case MATH -> "Mathematiques";
            case FRENCH -> "Francais";
            case ARABIC -> "Arabe";
            case SCIENCE -> "Sciences";
            case HISTORY -> "Histoire";
            case GEOGRAPHY -> "Geographie";
        };
    }

    private String difficultyLabel(Difficulty difficulty) {
        return switch (difficulty) {
            case SIMPLE -> "Simple";
            case MOYEN -> "Moyen";
            case DIFFICILE -> "Difficile";
            case EXCELLENT -> "Excellent";
        };
    }
}
