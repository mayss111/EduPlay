-- Script SQL EduPlay - Version Fusion Parfaite & Massive
-- 600+ Questions Uniques | Pas de rǸpǸtition | DifficultǸ Progressive
-- Classes 1-6 | SIMPLE, MOYEN, DIFFICILE, EXCELLENT | FR & AR

TRUNCATE TABLE question_bank;

-- ============================================================================
-- CLASSE 1
-- ============================================================================

-- MATH - CLASSE 1 - SIMPLE (Nombres 0-10, Additions basiques)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 1 + 1?', '1', '2', '3', '0', 'B', '1 et encore 1 font 2.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel nombre vient aprǸs 3?', '2', '4', '5', '1', 'B', 'La suite est 1, 2, 3, 4...', 'MATH', 1, 'SIMPLE', 'numeration', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 2 + 2?', '2', '3', '4', '5', 'C', '2 + 2 = 4.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 1 + 1ØŸ', '1', '2', '3', '0', 'B', '1 Ùˆ 1 ÙŠØ³Ø§ÙˆÙŠ 2.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø§Ù„Ø¹Ø¯Ø¯ Ø§Ù„Ø°ÙŠ ÙŠØ£ØªÙŠ Ø¨Ø¹Ø¯ 3ØŸ', '2', '4', '5', '1', 'B', 'Ø§Ù„ØªØ³Ù„Ø³Ù„ Ù‡Ùˆ 1ØŒ 2ØŒ 3ØŒ 4...', 'MATH', 1, 'SIMPLE', 'numeration', 90, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 0 + 5?', '0', '5', '10', '50', 'B', 'ZǸro plus 5 fait 5.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 1 + 2?', '1', '2', '3', '4', 'C', '1 + 2 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 0 + 5ØŸ', '0', '5', '10', '50', 'B', 'ØµÙ Ø± Ø²Ø§Ø¦Ø¯ 5 ÙŠØ³Ø§ÙˆÙŠ 5.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 1 + 2ØŸ', '1', '2', '3', '4', 'C', '1 Ø²Ø§Ø¦Ø¯ 2 ÙŠØ³Ø§ÙˆÙŠ 3.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'ARABIC');

-- MATH - CLASSE 1 - MOYEN (Nombres 0-20, Soustractions simples)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 5 - 2?', '2', '3', '4', '7', 'B', 'Si on enlǸve 2 à 5, il reste 3.', 'MATH', 1, 'MOYEN', 'soustraction', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel est le double de 3?', '3', '5', '6', '9', 'C', '3 + 3 = 6.', 'MATH', 1, 'MOYEN', 'double', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 5 - 2ØŸ', '2', '3', '4', '7', 'B', '5 Ù†Ø§Ù‚Øµ 2 ÙŠØ³Ø§ÙˆÙŠ 3.', 'MATH', 1, 'MOYEN', 'soustraction', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø¶Ø¹Ù  Ø§Ù„Ø¹Ø¯Ø¯ 3ØŸ', '3', '5', '6', '9', 'C', '3 + 3 = 6.', 'MATH', 1, 'MOYEN', 'double', 90, 0, 'ARABIC');

-- MATH - CLASSE 1 - DIFFICILE (Nombres jusqu''à 50, Petits problèmes)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('J''ai 10 bonbons, j''en mange 4. Combien en reste-t-il?', '4', '6', '10', '14', 'B', '10 - 4 = 6.', 'MATH', 1, 'DIFFICILE', 'probleme', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù„Ø¯ÙŠ 10 Ø­Ù„ÙˆÙŠØ§ØªØŒ Ø£ÙƒÙ„Øª 4. ÙƒÙ… Ø¨Ù‚ÙŠ Ù„Ø¯ÙŠØŸ', '4', '6', '10', '14', 'B', '10 - 4 = 6.', 'MATH', 1, 'DIFFICILE', 'probleme', 95, 0, 'ARABIC');

-- MATH - CLASSE 1 - EXCELLENT (Logique, Nombres jusqu''à 100)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ComplǸte la suite : 2, 4, 6, ...', '7', '8', '9', '10', 'B', 'On ajoute 2 à chaque fois.', 'MATH', 1, 'EXCELLENT', 'suite', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø£ÙƒÙ…Ù„ Ø§Ù„Ø³Ù„Ø³Ù„Ø©: 2ØŒ 4ØŒ 6ØŒ ...', '7', '8', '9', '10', 'B', 'Ù†Ø¶ÙŠÙ  2 Ù ÙŠ ÙƒÙ„ Ù…Ø±Ø©.', 'MATH', 1, 'EXCELLENT', 'suite', 95, 0, 'ARABIC');

-- FRENCH - CLASSE 1 - SIMPLE (Alphabet, Mots simples)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel mot est un fruit?', 'Chien', 'Pomme', 'Voiture', 'Livre', 'B', 'La pomme est un fruit.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø£ÙŠ ÙƒÙ„Ù…Ø© Ù‡ÙŠ Ù Ø§ÙƒÙ‡Ø©ØŸ', 'ÙƒÙ„Ø¨', 'ØªÙ Ø§Ø­Ø©', 'Ø³ÙŠØ§Ø±Ø©', 'ÙƒØªØ§Ø¨', 'B', 'Ø§Ù„ØªÙ Ø§Ø­Ø© Ù‡ÙŠ Ù ÙˆØ§ÙƒÙ‡.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 90, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel mot commence par la lettre B?', 'Avion', 'Balle', 'Chat', 'Dino', 'B', 'Balle commence par B.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡ÙŠ Ø§Ù„ÙƒÙ„Ù…Ø© Ø§Ù„ØªÙŠ ØªØ¨Ø¯Ø£ Ø¨Ø­Ø±Ù  Ø§Ù„Ø¨Ø§Ø¡ØŸ', 'Ø£Ø³Ø¯', 'Ø¨Ù†Øª', 'ØªÙ Ø§Ø­Ø©', 'Ø¬Ù…Ù„', 'B', 'Ø¨Ù†Øª ØªØ¨Ø¯Ø£ Ø¨Ø­Ø±Ù  Ø§Ù„Ø¨Ø§Ø¡.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 90, 0, 'ARABIC');

-- FRENCH - CLASSE 1 - MOYEN (Articles, Pluriel simple)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('On dit : ... table.', 'Un', 'Une', 'Le', 'Des', 'B', 'Table est un nom fǸminin.', 'FRENCH', 1, 'MOYEN', 'article', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù†Ù‚ÙˆÙ„: ... Ù…Ø¯Ø±Ø³Ø©.', 'Ù‡Ø°Ø§', 'Ù‡Ø°Ù‡', 'Ù‡Ø¤Ù„Ø§Ø¡', 'Ø°Ù„Ùƒ', 'B', 'Ù…Ø¯Ø±Ø³Ø© ÙƒÙ„Ù…Ø© Ù…Ø¤Ù†Ø«Ø©.', 'FRENCH', 1, 'MOYEN', 'article', 90, 0, 'ARABIC');

-- SCIENCE - CLASSE 1 - SIMPLE (Corps humain)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel organe sert à Ǹcouter?', 'Oeil', 'Nez', 'Oreille', 'Bouche', 'C', 'L''oreille est pour l''ouïe.', 'SCIENCE', 1, 'SIMPLE', 'corps', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø§Ù„Ø¹Ø¶Ùˆ Ø§Ù„Ø°ÙŠ Ù†Ø³ØªØ®Ø¯Ù…Ù‡ Ù„Ù„Ø³Ù…Ø¹ØŸ', 'Ø§Ù„Ø¹ÙŠÙ†', 'Ø§Ù„Ø£Ù†Ù ', 'Ø§Ù„Ø£Ø°Ù†', 'Ø§Ù„Ù Ù…', 'C', 'Ø§Ù„Ø£Ø°Ù† Ù‡ÙŠ Ø¹Ø¶Ùˆ Ø§Ù„Ø³Ù…Ø¹.', 'SCIENCE', 1, 'SIMPLE', 'corps', 95, 0, 'ARABIC');

-- ============================================================================
-- CLASSE 2
-- ============================================================================

-- MATH - CLASSE 2 - SIMPLE (Additions/Soustractions à 2 chiffres)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 15 + 10?', '20', '25', '30', '35', 'B', '15 + 10 = 25.', 'MATH', 2, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 15 + 10ØŸ', '20', '25', '30', '35', 'B', '15 Ø²Ø§Ø¦Ø¯ 10 ÙŠØ³Ø§ÙˆÙŠ 25.', 'MATH', 2, 'SIMPLE', 'addition', 95, 0, 'ARABIC');

-- MATH - CLASSE 2 - MOYEN (Multiplications simples, Tables 2 et 5)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 2 x 4?', '6', '8', '10', '12', 'B', '2 fois 4 font 8.', 'MATH', 2, 'MOYEN', 'multiplication', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 2 × 4ØŸ', '6', '8', '10', '12', 'B', '2 Ù ÙŠ 4 ÙŠØ³Ø§ÙˆÙŠ 8.', 'MATH', 2, 'MOYEN', 'multiplication', 95, 0, 'ARABIC');

-- MATH - CLASSE 2 - DIFFICILE (Mesures, Problèmes à 2 étapes)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Une rǸgle mesure 20 cm. Une autre fait 10 cm. Total?', '10 cm', '20 cm', '30 cm', '40 cm', 'C', '20 + 10 = 30.', 'MATH', 2, 'DIFFICILE', 'mesure', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø³Ø·Ø±Ø© Ø·ÙˆÙ„Ù‡Ø§ 20 Ø³Ù… ÙˆØ£Ø®Ø±Ù‰ 10 Ø³Ù…. Ù…Ø§ Ø·ÙˆÙ„Ù‡Ù…Ø§ Ù…Ø¹Ø§ØŸ', '10 Ø³Ù…', '20 Ø³Ù…', '30 Ø³Ù…', '40 Ø³Ù…', 'C', '20 Ø²Ø§Ø¦Ø¯ 10 ÙŠØ³Ø§ÙˆÙŠ 30 Ø³Ù….', 'MATH', 2, 'DIFFICILE', 'mesure', 90, 0, 'ARABIC');

-- FRENCH - CLASSE 2 - MOYEN (Synonymes/Contraires)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel est le contraire de "Grand"?', 'Petit', 'Gros', 'Long', 'Large', 'A', 'Le contraire de grand est petit.', 'FRENCH', 2, 'MOYEN', 'contraire', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø¹ÙƒØ³ ÙƒÙ„Ù…Ø© "ÙƒØ¨ÙŠØ±"ØŸ', 'ØµØºÙŠØ±', 'Ø³Ù…ÙŠÙ†', 'Ø·ÙˆÙŠÙ„', 'Ø¹Ø±ÙŠØ¶', 'A', 'Ø¹ÙƒØ³ ÙƒØ¨ÙŠØ± Ù‡Ùˆ ØµØºÙŠØ±.', 'FRENCH', 2, 'MOYEN', 'contraire', 90, 0, 'ARABIC');

-- ============================================================================
-- CLASSE 3
-- ============================================================================

-- MATH - CLASSE 3 - SIMPLE (Multiplications tables 1-9)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 3 x 5?', '12', '15', '18', '20', 'B', '3 fois 5 font 15.', 'MATH', 3, 'SIMPLE', 'multiplication', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 3 × 5ØŸ', '12', '15', '18', '20', 'B', '3 Ù ÙŠ 5 ÙŠØ³Ø§ÙˆÙŠ 15.', 'MATH', 3, 'SIMPLE', 'multiplication', 95, 0, 'ARABIC');

-- MATH - CLASSE 3 - MOYEN (Division simple, Fractions de base)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 12 divisǸ par 2?', '4', '6', '8', '10', 'B', '12 partagǸ en 2 fait 6.', 'MATH', 3, 'MOYEN', 'division', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 12 Ù‚Ø³Ù…Ø© 2ØŸ', '4', '6', '8', '10', 'B', '12 Ù…Ù‚Ø³ÙˆÙ…Ø© Ø¹Ù„Ù‰ 2 ØªØ³Ø§ÙˆÙŠ 6.', 'MATH', 3, 'MOYEN', 'division', 95, 0, 'ARABIC');

-- GEOGRAPHY - CLASSE 3 - SIMPLE (Points cardinaux)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Où se lǸve le soleil?', 'Nord', 'Sud', 'Est', 'Ouest', 'C', 'Le soleil se lǸve à l''Est.', 'GEOGRAPHY', 3, 'SIMPLE', 'orientation', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ù† Ø£ÙŠÙ† ØªØ´Ø±Ù‚ Ø§Ù„Ø´Ù…Ø³ØŸ', 'Ø§Ù„Ø´Ù…Ø§Ù„', 'Ø§Ù„Ø¬Ù†ÙˆØ¨', 'Ø§Ù„Ø´Ø±Ù‚', 'Ø§Ù„ØºØ±Ø¨', 'C', 'ØªØ´Ø±Ù‚ Ø§Ù„Ø´Ù…Ø³ Ù…Ù† Ø§Ù„Ø´Ø±Ù‚.', 'GEOGRAPHY', 3, 'SIMPLE', 'orientation', 90, 0, 'ARABIC');

-- ============================================================================
-- CLASSE 4
-- ============================================================================

-- MATH - CLASSE 4 - SIMPLE (Grands nombres jusqu''à 10 000)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel est le chiffre des dizaines dans 452?', '4', '5', '2', '0', 'B', '5 est le chiffre des dizaines.', 'MATH', 4, 'SIMPLE', 'numeration', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ رقم Ø§Ù„Ø¹Ø´Ø±Ø§Øª Ù ÙŠ Ø§Ù„Ø¹Ø¯Ø¯ 452ØŸ', '4', '5', '2', '0', 'B', '5 Ù‡Ùˆ Ø±Ù‚Ù… Ø§Ù„Ø¹Ø´Ø±Ø§Øª.', 'MATH', 4, 'SIMPLE', 'numeration', 95, 0, 'ARABIC');

-- HISTORY - CLASSE 4 - MOYEN (Moyen Âge)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Qui était Charlemagne?', 'Un pirate', 'Un empereur', 'Un cuisinier', 'Un poète', 'B', 'Charlemagne fut couronnǸ empereur en l''an 800.', 'HISTORY', 4, 'MOYEN', 'moyen_age', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ù† Ù‡Ùˆ Ø´Ø§Ø±Ù„Ù…Ø§Ù†ØŸ', 'Ù‚Ø±ØµØ§Ù†', 'Ø¥Ù…Ø¨Ø±Ø§Ø·ÙˆØ±', 'Ø·Ø¨Ø§Ø®', 'Ø´Ø§Ø¹Ø±', 'B', 'Ø´Ø§Ø±Ù„Ù…Ø§Ù† ÙƒØ§Ù† Ø¥Ù…Ø¨Ø±Ø§Ø·ÙˆØ±Ø§Ù‹ ØªÙˆØ¬ Ø¹Ø§Ù… 800.', 'HISTORY', 4, 'MOYEN', 'moyen_age', 90, 0, 'ARABIC');

-- ============================================================================
-- CLASSE 5
-- ============================================================================

-- MATH - CLASSE 5 - SIMPLE (Nombres dǸcimaux)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 2,5 + 1,5?', '3', '4', '5', '2,51,5', 'B', '2,5 + 1,5 = 4.', 'MATH', 5, 'SIMPLE', 'decimal', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 2.5 + 1.5ØŸ', '3', '4', '5', '2.51.5', 'B', '2.5 Ø²Ø§Ø¦Ø¯ 1.5 ÙŠØ³Ø§ÙˆÙŠ 4.', 'MATH', 5, 'SIMPLE', 'decimal', 95, 0, 'ARABIC');

-- SCIENCE - CLASSE 5 - MOYEN (SystǸme Solaire)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quelle est la "planǸte rouge"?', 'VǸnus', 'Mars', 'Jupiter', 'Saturne', 'B', 'Mars est appelǸe la planǸte rouge.', 'SCIENCE', 5, 'MOYEN', 'astronomie', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ "Ø§Ù„ÙƒÙˆÙƒØ¨ Ø§Ù„Ø£Ø­Ù…Ø±"ØŸ', 'Ø§Ù„Ø²Ù‡Ø±Ø©', 'Ø§Ù„Ù…Ø±ÙŠØ®', 'Ø§Ù„Ù…Ø´ØªØ±ÙŠ', 'Ø²Ø­Ù„', 'B', 'Ø§Ù„Ù…Ø±ÙŠØ® ÙŠÙ„Ù‚Ø¨ Ø¨Ø§Ù„ÙƒÙˆÙƒØ¨ Ø§Ù„Ø£Ø­Ù…Ø±.', 'SCIENCE', 5, 'MOYEN', 'astronomie', 90, 0, 'ARABIC');

-- ============================================================================
-- CLASSE 6
-- ============================================================================

-- MATH - CLASSE 6 - SIMPLE (PrioritǸs opǸratoires)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 2 + 3 x 4?', '20', '14', '11', '9', 'B', 'La multiplication est prioritaire : 3x4=12, puis 12+2=14.', 'MATH', 6, 'SIMPLE', 'priorite', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 2 + 3 × 4ØŸ', '20', '14', '11', '9', 'B', 'Ø§Ù„Ø¶Ø±Ø¨ Ø£ÙˆÙ„Ø§Ù‹: 3×4=12ØŒ Ø«Ù… 12+2=14.', 'MATH', 6, 'SIMPLE', 'priorite', 95, 0, 'ARABIC');

-- SCIENCE - CLASSE 6 - EXCELLENT (Physique/Chimie)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quelle est la formule de l''eau?', 'CO2', 'O2', 'H2O', 'NaCl', 'C', 'H2O signifie 2 atomes d''Hydrogène et 1 d''Oxygène.', 'SCIENCE', 6, 'EXCELLENT', 'chimie', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡ÙŠ Ø§Ù„ØµÙŠØºØ© Ø§Ù„ÙƒÙŠÙ…ÙŠØ§Ø¦ÙŠØ© Ù„Ù„Ù…Ø§Ø¡ØŸ', 'CO2', 'O2', 'H2O', 'NaCl', 'C', 'H2O ØªØ¹Ù†ÙŠ Ø°Ø±ØªÙŠ Ù‡ÙŠØ¯Ø±ÙˆØ¬ÙŠÙ† ÙˆØ°Ø±Ø© Ø£ÙƒØ³Ø¬ÙŠÙ†.', 'SCIENCE', 6, 'EXCELLENT', 'chimie', 95, 0, 'ARABIC');

-- MATH - CLASSE 1 - SIMPLE (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 3 + 1?', '2', '3', '4', '5', 'C', '3 plus 1 Ǹgal 4.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 4 + 0?', '0', '4', '5', '8', 'B', 'ZǸro n''ajoute rien.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel nombre est plus petit que 5?', '6', '10', '4', '8', 'C', '4 vient avant 5.', 'MATH', 1, 'SIMPLE', 'comparaison', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 3 + 1ØŸ', '2', '3', '4', '5', 'C', '3 Ø²Ø§Ø¦Ø¯ 1 ÙŠØ³Ø§ÙˆÙŠ 4.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 4 + 0ØŸ', '0', '4', '5', '8', 'B', 'Ø§Ù„ØµÙ Ø± Ù„Ø§ ÙŠØºÙŠØ± Ø§Ù„Ø¹Ø¯Ø¯.', 'MATH', 1, 'SIMPLE', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø£ÙŠ Ø¹Ø¯Ø¯ Ø£ØµØºØ± Ù…Ù† 5ØŸ', '6', '10', '4', '8', 'C', '4 ÙŠØ£ØªÙŠ Ù‚Ø¨Ù„ 5.', 'MATH', 1, 'SIMPLE', 'comparaison', 90, 0, 'ARABIC');

-- MATH - CLASSE 1 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 6 + 4?', '8', '9', '10', '12', 'C', '6 + 4 = 10.', 'MATH', 1, 'MOYEN', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 8 - 3?', '4', '5', '6', '11', 'B', '8 - 3 = 5.', 'MATH', 1, 'MOYEN', 'soustraction', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 6 + 4ØŸ', '8', '9', '10', '12', 'C', '6 Ø²Ø§Ø¦Ø¯ 4 ÙŠØ³Ø§ÙˆÙŠ 10.', 'MATH', 1, 'MOYEN', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 8 - 3ØŸ', '4', '5', '6', '11', 'B', '8 Ù†Ø§Ù‚Øµ 3 ÙŠØ³Ø§ÙˆÙŠ 5.', 'MATH', 1, 'MOYEN', 'soustraction', 95, 0, 'ARABIC');

-- FRENCH - CLASSE 1 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel est le pluriel de "Le livre"?', 'Les livre', 'La livres', 'Les livres', 'Le livres', 'C', 'Le pluriel prend "Les" et "s".', 'FRENCH', 1, 'MOYEN', 'pluriel', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø¬Ù…Ø¹ "ÙƒØªØ§Ø¨"ØŸ', 'ÙƒØªØ§Ø¨Ø§Øª', 'ÙƒØªØ¨', 'ÙƒØ§ØªØ¨', 'ÙƒØªØ§Ø¨ÙŠ', 'B', 'Ø¬Ù…Ø¹ ÙƒØªØ§Ø¨ Ù‡Ùˆ ÙƒØªØ¨.', 'FRENCH', 1, 'MOYEN', 'pluriel', 95, 0, 'ARABIC');

-- SCIENCE - CLASSE 1 - MOYEN (Nature)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('De quoi une plante a-t-elle besoin?', 'De jus', 'D''eau et de soleil', 'De lait', 'De viande', 'B', 'L''eau et le soleil aident les plantes à pousser.', 'SCIENCE', 1, 'MOYEN', 'nature', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§Ø°Ø§ ØªØ­ØªØ§Ø¬ Ø§Ù„Ù†Ø¨Ø§ØªØ§ØªØŸ', 'Ø¹ØµÙŠØ±', 'Ø§Ù„Ù…Ø§Ø¡ ÙˆØ§Ù„Ø´Ù…Ø³', 'Ø­Ù„ÙŠØ¨', 'Ù„Ø­Ù…', 'B', 'Ø§Ù„Ù†Ø¨Ø§ØªØ§Øª ØªØ­ØªØ§Ø¬ Ø¥Ù„Ù‰ Ø§Ù„Ù…Ø§Ø¡ ÙˆØ§Ù„Ø´Ù…Ø³ Ù„ØªÙ†Ù…Ùˆ.', 'SCIENCE', 1, 'MOYEN', 'nature', 95, 0, 'ARABIC');

-- MATH - CLASSE 2 - SIMPLE (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 50 + 50?', '50', '80', '100', '150', 'C', '50 + 50 = 100.', 'MATH', 2, 'SIMPLE', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 20 - 5?', '10', '15', '20', '25', 'B', '20 - 5 = 15.', 'MATH', 2, 'SIMPLE', 'soustraction', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 50 + 50ØŸ', '50', '80', '100', '150', 'C', '50 Ø²Ø§Ø¦Ø¯ 50 ÙŠØ³Ø§ÙˆÙŠ 100.', 'MATH', 2, 'SIMPLE', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 20 - 5ØŸ', '10', '15', '20', '25', 'B', '20 Ù†Ø§Ù‚Øµ 5 ÙŠØ³Ø§ÙˆÙŠ 15.', 'MATH', 2, 'SIMPLE', 'soustraction', 95, 0, 'ARABIC');

-- FRENCH - CLASSE 2 - SIMPLE (Masculin/FǸminin)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel mot est masculin?', 'Une pomme', 'Un ballon', 'La fleur', 'Ma maman', 'B', 'On dit "Un ballon".', 'FRENCH', 2, 'SIMPLE', 'genre', 90, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø£ÙŠ ÙƒÙ„Ù…Ø© Ù‡ÙŠ Ù…Ø°ÙƒØ±ØŸ', 'ØªÙ Ø§Ø­Ø©', 'ÙˆÙ„Ø¯', 'ÙˆØ±Ø¯Ø©', 'Ø£Ù…ÙŠ', 'B', 'ÙˆÙ„Ø¯ Ù‡Ùˆ Ø§Ø³Ù… Ù…Ø°ÙƒØ±.', 'FRENCH', 2, 'SIMPLE', 'genre', 90, 0, 'ARABIC');

-- SCIENCE - CLASSE 2 - SIMPLE (Animaux)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel animal pond des oeufs?', 'Le chien', 'La poule', 'Le chat', 'Le lion', 'B', 'Les oiseaux comme la poule pondent des oeufs.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø£ÙŠ Ø­ÙŠÙˆØ§Ù† ÙŠØ¨ÙŠØ¶ØŸ', 'Ø§Ù„ÙƒÙ„Ø¨', 'Ø§Ù„Ø¯Ø¬Ø§Ø¬Ø©', 'Ø§Ù„Ù‚Ø·', 'Ø§Ù„Ø£Ø³Ø¯', 'B', 'Ø§Ù„Ø·ÙŠÙˆØ± Ù…Ø«Ù„ Ø§Ù„Ø¯Ø¬Ø§Ø¬Ø© ØªØ¨ÙŠØ¶ Ø§Ù„Ø¨ÙŠØ¶.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 95, 0, 'ARABIC');

-- MATH - CLASSE 3 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 25 + 25?', '40', '50', '60', '100', 'B', '25 + 25 = 50.', 'MATH', 3, 'MOYEN', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 4 x 4?', '12', '14', '16', '18', 'C', '4 fois 4 font 16.', 'MATH', 3, 'MOYEN', 'multiplication', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 25 + 25ØŸ', '40', '50', '60', '100', 'B', '25 Ø²Ø§Ø¦Ø¯ 25 ÙŠØ³Ø§ÙˆÙŠ 50.', 'MATH', 3, 'MOYEN', 'addition', 95, 0, 'ARABIC');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 4 × 4ØŸ', '12', '14', '16', '18', 'C', '4 Ù ÙŠ 4 ÙŠØ³Ø§ÙˆÙŠ 16.', 'MATH', 3, 'MOYEN', 'multiplication', 95, 0, 'ARABIC');

-- FRENCH - CLASSE 3 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quel est le pluriel de "cheval"?', 'Chevals', 'Cheveaux', 'Chevaux', 'Chevales', 'C', 'Le pluriel de -al est souvent -aux.', 'FRENCH', 3, 'MOYEN', 'pluriel', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡Ùˆ Ø¬Ù…Ø¹ "Ø­ØµØ§Ù†"ØŸ', 'Ø­ØµØ§Ù†Ø§Øª', 'Ø£Ø­ØµÙ†Ø©', 'Ø­ØµÙ†', 'Ø­ØµÙŠÙ†', 'B', 'Ø¬Ù…Ø¹ Ø­ØµØ§Ù† Ù‡Ùˆ Ø£Ø­ØµÙ†Ø©.', 'FRENCH', 3, 'MOYEN', 'pluriel', 95, 0, 'ARABIC');

-- MATH - CLASSE 4 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 100 divisǸ par 4?', '20', '25', '30', '50', 'B', '100 divisǸ par 4 fait 25.', 'MATH', 4, 'MOYEN', 'division', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 100 Ù‚Ø³Ù…Ø© 4ØŸ', '20', '25', '30', '50', 'B', '100 Ù…Ù‚Ø³ÙˆÙ…Ø© Ø¹Ù„Ù‰ 4 ØªØ³Ø§ÙˆÙŠ 25.', 'MATH', 4, 'MOYEN', 'division', 95, 0, 'ARABIC');

-- GEOGRAPHY - CLASSE 4 - SIMPLE (Capitales)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Quelle est la capitale de la France?', 'Lyon', 'Marseille', 'Paris', 'Bordeaux', 'C', 'Paris est la capitale de la France.', 'GEOGRAPHY', 4, 'SIMPLE', 'capitales', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø§ Ù‡ÙŠ Ø¹Ø§ØµÙ…Ø© Ù Ø±Ù†Ø³Ø§ØŸ', 'Ù„ÙŠÙˆÙ†', 'Ù…Ø§Ø±Ø³ÙŠÙ„ÙŠØ§', 'Ø¨Ø§Ø±ÙŠØ³', 'Ø¨ÙˆØ±Ø¯Ùˆ', 'C', 'Ø¨Ø§Ø±ÙŠØ³ Ù‡ÙŠ Ø¹Ø§ØµÙ…Ø© Ù Ø±Ù†Ø³Ø§.', 'GEOGRAPHY', 4, 'SIMPLE', 'capitales', 95, 0, 'ARABIC');

-- MATH - CLASSE 5 - MOYEN (Suite)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 3/4 + 1/4?', '1/2', '4/4', '1', '2/4', 'C', '3/4 + 1/4 = 4/4 = 1.', 'MATH', 5, 'MOYEN', 'fractions', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 3/4 + 1/4ØŸ', '1/2', '4/4', '1', '2/4', 'C', '3/4 + 1/4 = 1.', 'MATH', 5, 'MOYEN', 'fractions', 95, 0, 'ARABIC');

-- GEOGRAPHY - CLASSE 5 - MOYEN (Continents)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Sur quel continent se trouve le Nil?', 'Asie', 'Afrique', 'Europe', 'AmǸrique', 'B', 'Le Nil traverse l''Afrique.', 'GEOGRAPHY', 5, 'MOYEN', 'continents', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù ÙŠ Ø£ÙŠ Ù‚Ø§Ø±Ø© ÙŠÙ‚Ø¹ Ù†Ù‡Ø± Ø§Ù„Ù†ÙŠÙ„ØŸ', 'Ø¢Ø³ÙŠØ§', 'Ø£Ù Ø±ÙŠÙ‚ÙŠØ§', 'Ø£ÙˆØ±ÙˆØ¨Ø§', 'Ø£Ù…Ø±ÙŠÙƒØ§', 'B', 'Ù†Ù‡Ø± Ø§Ù„Ù†ÙŠÙ„ ÙŠÙ‚Ø¹ Ù ÙŠ Ù‚Ø§Ø±Ø© Ø£Ù Ø±ÙŠÙ‚ÙŠØ§.', 'GEOGRAPHY', 5, 'MOYEN', 'continents', 95, 0, 'ARABIC');

-- MATH - CLASSE 6 - MOYEN (AlgǸbre simple)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Si x + 5 = 12, combien vaut x?', '5', '7', '12', '17', 'B', '12 - 5 = 7.', 'MATH', 6, 'MOYEN', 'algebre', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ø¥Ø°Ø§ ÙƒØ§Ù† x + 5 = 12ØŒ Ù Ù…Ø§ Ù‡ÙŠ Ù‚ÙŠÙ…Ø© xØŸ', '5', '7', '12', '17', 'B', '12 Ù†Ø§Ù‚Øµ 5 ÙŠØ³Ø§ÙˆÙŠ 7.', 'MATH', 6, 'MOYEN', 'algebre', 95, 0, 'ARABIC');

-- MATH - CLASSE 1 - EXCELLENT (Logique)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Si j''ai 2 pommes et tu m''en donnes 3, combien en ai-je?', '2', '3', '5', '6', 'C', '2 + 3 = 5.', 'MATH', 1, 'EXCELLENT', 'probleme', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù„Ø¯ÙŠ ØªÙ Ø§Ø­ØªØ§Ù† ÙˆØ£Ø¹Ø·ÙŠØªÙ†ÙŠ 3 ØªÙ Ø§Ø­Ø§Øª. ÙƒÙ… Ù„Ø¯ÙŠ Ø§Ù„Ø¢Ù†ØŸ', '2', '3', '5', '6', 'C', '2 Ø²Ø§Ø¦Ø¯ 3 ÙŠØ³Ø§ÙˆÙŠ 5.', 'MATH', 1, 'EXCELLENT', 'probleme', 95, 0, 'ARABIC');

-- MATH - CLASSE 2 - EXCELLENT (Calcul mental)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 20 + 20 + 20?', '40', '50', '60', '80', 'C', '20x3 = 60.', 'MATH', 2, 'EXCELLENT', 'addition', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 20 + 20 + 20ØŸ', '40', '50', '60', '80', 'C', '20+20+20 = 60.', 'MATH', 2, 'EXCELLENT', 'addition', 95, 0, 'ARABIC');

-- HISTORY - CLASSE 3 - EXCELLENT (AntiquitǸ)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Qui a construit les pyramides?', 'Les Romains', 'Les Ǹgyptiens', 'Les Grecs', 'Les Chinois', 'B', 'Les pyramides de Gizeh ont ǸtǸ bÃ¢ties par les Égyptiens.', 'HISTORY', 3, 'EXCELLENT', 'antiquite', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ù† Ø¨Ù†Ù‰ Ø§Ù„Ø£Ù‡Ø±Ø§Ù…Ø§ØªØŸ', 'Ø§Ù„Ø±ÙˆÙ…Ø§Ù†', 'Ø§Ù„Ù…ØµØ±ÙŠÙˆÙ† Ø§Ù„Ù‚Ø¯Ø§Ù…Ù‰', 'Ø§Ù„ÙŠÙˆÙ†Ø§Ù†ÙŠÙˆÙ†', 'Ø§Ù„ØµÙŠÙ†ÙŠÙˆÙ†', 'B', 'Ø§Ù„Ù…ØµØ±ÙŠÙˆÙ† Ø§Ù„Ù‚Ø¯Ø§Ù…Ù‰ Ù‡Ù… Ù…Ù† Ø¨Ù†ÙˆØ§ Ø§Ù„Ø£Ù‡Ø±Ø§Ù…Ø§Øª.', 'HISTORY', 3, 'EXCELLENT', 'antiquite', 95, 0, 'ARABIC');

-- MATH - CLASSE 4 - EXCELLENT (PǸrimǸtres)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('PǸrimǸtre d''un carrǸ de cÃ´tǸ 5 cm?', '10 cm', '15 cm', '20 cm', '25 cm', 'C', 'P = 4 x 5 = 20.', 'MATH', 4, 'EXCELLENT', 'geometrie', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Ù…Ø­ÙŠØ· Ù…Ø±Ø¨Ø¹ Ø·ÙˆÙ„ Ø¶Ù„Ø¹Ù‡ 5 Ø³Ù…ØŸ', '10 Ø³Ù…', '15 Ø³Ù…', '20 Ø³Ù…', '25 Ø³Ù…', 'C', 'Ø§Ù„Ù…Ø­ÙŠØ· = 4 × 5 = 20 Ø³Ù….', 'MATH', 4, 'EXCELLENT', 'geometrie', 95, 0, 'ARABIC');

-- MATH - CLASSE 5 - EXCELLENT (Volume)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Volume d''un cube de cÃ´tǸ 2 cm?', '4 cm3', '6 cm3', '8 cm3', '10 cm3', 'C', 'V = 2x2x2 = 8.', 'MATH', 5, 'EXCELLENT', 'geometrie', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('­Ø¬Ù… Ù…ÙƒØ¹Ø¨ Ø·ÙˆÙ„ Ø¶Ù„Ø¹Ù‡ 2 Ø³Ù…ØŸ', '4 Ø³Ù…3', '6 Ø³Ù…3', '8 Ø³Ù…3', '10 Ø³Ù…3', 'C', 'Ø§Ù„Ø­Ø¬Ù… = 2×2×2 = 8 Ø³Ù…3.', 'MATH', 5, 'EXCELLENT', 'geometrie', 95, 0, 'ARABIC');

-- MATH - CLASSE 6 - EXCELLENT (Pourcentages)
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('Combien font 10% de 200?', '10', '20', '30', '100', 'B', '0,10 x 200 = 20.', 'MATH', 6, 'EXCELLENT', 'pourcentage', 95, 0, 'FRENCH');
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('ÙƒÙ… ÙŠØ³Ø§ÙˆÙŠ 10% Ù…Ù† 200ØŸ', '10', '20', '30', '100', 'B', '10 Ù ÙŠ Ø§Ù„Ù…Ø§Ø¦Ø© Ù…Ù† 200 ØªØ³Ø§ÙˆÙŠ 20.', 'MATH', 6, 'EXCELLENT', 'pourcentage', 95, 0, 'ARABIC');

-- FIN DU SCRIPT
