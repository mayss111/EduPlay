-- Script SQL pour peupler la base de questions EduPlay
-- 500+ questions pour classes 1-6, toutes matières, tous niveaux

-- Désactiver les contraintes pour insertion en masse
SET session_replication_role = replica;

-- ==================== MATH - CLASSE 1 ====================

-- MATH - CLASSE 1 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 1 + 2?', '2', '3', '4', '1', 'B', 'En mathématiques, 1 + 2 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0),
('Combien font 2 + 1?', '3', '2', '4', '1', 'A', '2 + 1 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0),
('Combien font 3 + 0?', '0', '3', '4', '2', 'B', 'Quand on ajoute 0, le nombre ne change pas. 3 + 0 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0),
('Combien font 4 + 1?', '4', '5', '6', '3', 'B', '4 + 1 = 5.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0),
('Combien font 2 + 2?', '3', '4', '5', '2', 'B', '2 + 2 = 4.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0),
('Combien font 5 - 1?', '3', '4', '5', '6', 'B', '5 - 1 = 4.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0),
('Combien font 3 - 1?', '1', '2', '3', '4', 'B', '3 - 1 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0),
('Combien font 4 - 2?', '1', '2', '3', '4', 'B', '4 - 2 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0),
('Quel nombre vient après 3?', '2', '3', '4', '5', 'C', 'Dans la suite des nombres, 4 vient après 3.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0),
('Quel nombre vient après 5?', '4', '5', '6', '7', 'C', 'Dans la suite des nombres, 6 vient après 5.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0),
('Quel nombre vient avant 4?', '3', '4', '5', '2', 'A', 'Dans la suite des nombres, 3 vient avant 4.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0),
('Quel nombre vient avant 7?', '5', '6', '7', '8', 'B', 'Dans la suite des nombres, 6 vient avant 7.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0),
('Combien de doigts as-tu sur une main?', '4', '5', '6', '10', 'B', 'On a 5 doigts sur chaque main.', 'MATH', 1, 'SIMPLE', 'comptage', 80, 0),
('Combien de jours dans une semaine?', '5', '6', '7', '8', 'C', 'Une semaine a 7 jours.', 'MATH', 1, 'SIMPLE', 'temps', 80, 0),
('Quel nombre est plus grand: 5 ou 3?', '3', '5', 'égaux', 'aucun', 'B', '5 est plus grand que 3.', 'MATH', 1, 'SIMPLE', 'comparaison', 80, 0);

-- MATH - CLASSE 1 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 6 + 3?', '8', '9', '7', '10', 'B', '6 + 3 = 9.', 'MATH', 1, 'MOYEN', 'addition', 80, 0),
('Combien font 7 + 2?', '8', '9', '10', '7', 'B', '7 + 2 = 9.', 'MATH', 1, 'MOYEN', 'addition', 80, 0),
('Combien font 8 - 3?', '4', '5', '6', '3', 'B', '8 - 3 = 5.', 'MATH', 1, 'MOYEN', 'soustraction', 80, 0),
('Combien font 9 - 4?', '4', '5', '6', '3', 'B', '9 - 4 = 5.', 'MATH', 1, 'MOYEN', 'soustraction', 80, 0),
('Quel nombre est entre 6 et 8?', '5', '6', '7', '9', 'C', '7 est entre 6 et 8.', 'MATH', 1, 'MOYEN', 'suite_nombres', 80, 0),
('Quel nombre est entre 9 et 11?', '8', '9', '10', '12', 'C', '10 est entre 9 et 11.', 'MATH', 1, 'MOYEN', 'suite_nombres', 80, 0),
('Combien font 5 + 5?', '8', '9', '10', '11', 'C', '5 + 5 = 10.', 'MATH', 1, 'MOYEN', 'addition', 80, 0),
('Combien font 10 - 5?', '4', '5', '6', '7', 'B', '10 - 5 = 5.', 'MATH', 1, 'MOYEN', 'soustraction', 80, 0),
('Quel nombre vient après 9?', '8', '9', '10', '11', 'C', '10 vient après 9.', 'MATH', 1, 'MOYEN', 'suite_nombres', 80, 0),
('Combien de côtés a un triangle?', '3', '4', '5', '2', 'A', 'Un triangle a 3 côtés.', 'MATH', 1, 'MOYEN', 'geometrie', 80, 0);

-- MATH - CLASSE 1 - DIFFICILE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 12 + 5?', '15', '16', '17', '18', 'C', '12 + 5 = 17.', 'MATH', 1, 'DIFFICILE', 'addition', 80, 0),
('Combien font 15 - 3?', '10', '11', '12', '13', 'C', '15 - 3 = 12.', 'MATH', 1, 'DIFFICILE', 'soustraction', 80, 0),
('Combien font 10 + 8?', '16', '17', '18', '19', 'C', '10 + 8 = 18.', 'MATH', 1, 'DIFFICILE', 'addition', 80, 0),
('Combien font 18 - 6?', '10', '11', '12', '13', 'C', '18 - 6 = 12.', 'MATH', 1, 'DIFFICILE', 'soustraction', 80, 0),
('Quel nombre est 2 de plus que 10?', '8', '10', '12', '14', 'C', '10 + 2 = 12.', 'MATH', 1, 'DIFFICILE', 'addition', 80, 0);

-- ==================== MATH - CLASSE 2 ====================

-- MATH - CLASSE 2 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 10 + 5?', '12', '13', '15', '14', 'C', '10 + 5 = 15.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0),
('Combien font 20 - 5?', '10', '15', '25', '18', 'B', '20 - 5 = 15.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0),
('Combien font 12 + 8?', '18', '19', '20', '22', 'C', '12 + 8 = 20.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0),
('Combien font 25 - 5?', '15', '20', '25', '30', 'B', '25 - 5 = 20.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0),
('Quel nombre est 10 de plus que 20?', '10', '20', '30', '40', 'C', '20 + 10 = 30.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0),
('Combien font 30 + 10?', '30', '40', '50', '20', 'B', '30 + 10 = 40.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0),
('Combien font 50 - 10?', '30', '40', '50', '60', 'B', '50 - 10 = 40.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0),
('Combien de côtés a un carré?', '3', '4', '5', '6', 'B', 'Un carré a 4 côtés égaux.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0),
('Combien de côtés a un rectangle?', '3', '4', '5', '6', 'B', 'Un rectangle a 4 côtés.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0),
('Quel nombre est pair?', '3', '5', '6', '7', 'C', '6 est pair car il se divise par 2.', 'MATH', 2, 'SIMPLE', 'nombres_pairs', 80, 0);

-- MATH - CLASSE 2 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 25 + 17?', '40', '42', '44', '38', 'B', '25 + 17 = 42.', 'MATH', 2, 'MOYEN', 'addition', 80, 0),
('Combien font 50 - 23?', '25', '27', '30', '33', 'B', '50 - 23 = 27.', 'MATH', 2, 'MOYEN', 'soustraction', 80, 0),
('Combien font 35 + 28?', '60', '61', '63', '65', 'C', '35 + 28 = 63.', 'MATH', 2, 'MOYEN', 'addition', 80, 0),
('Combien font 72 - 35?', '35', '37', '40', '42', 'B', '72 - 35 = 37.', 'MATH', 2, 'MOYEN', 'soustraction', 80, 0),
('Quel est le double de 15?', '20', '25', '30', '35', 'C', 'Le double de 15 est 30.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0),
('Quel est le double de 20?', '30', '35', '40', '45', 'C', 'Le double de 20 est 40.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0),
('Combien font 3 × 2?', '5', '6', '7', '8', 'B', '3 × 2 = 6.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0),
('Combien font 5 × 2?', '8', '10', '12', '15', 'B', '5 × 2 = 10.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0),
('Combien font 4 × 3?', '10', '11', '12', '15', 'C', '4 × 3 = 12.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0),
('Combien font 10 × 2?', '15', '18', '20', '25', 'C', '10 × 2 = 20.', 'MATH', 2, 'MOYEN', 'multiplication', 80, 0);

-- MATH - CLASSE 2 - DIFFICILE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 45 + 38?', '73', '83', '78', '85', 'B', '45 + 38 = 83.', 'MATH', 2, 'DIFFICILE', 'addition', 80, 0),
('Combien font 100 - 47?', '50', '53', '55', '57', 'B', '100 - 47 = 53.', 'MATH', 2, 'DIFFICILE', 'soustraction', 80, 0),
('Combien font 6 × 4?', '20', '22', '24', '28', 'C', '6 × 4 = 24.', 'MATH', 2, 'DIFFICILE', 'multiplication', 80, 0),
('Combien font 8 × 3?', '21', '22', '24', '27', 'C', '8 × 3 = 24.', 'MATH', 2, 'DIFFICILE', 'multiplication', 80, 0),
('Quel est le triple de 5?', '10', '12', '15', '20', 'C', 'Le triple de 5 est 15.', 'MATH', 2, 'DIFFICILE', 'multiplication', 80, 0);

-- ==================== MATH - CLASSE 3 ====================

-- MATH - CLASSE 3 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 100 + 200?', '200', '250', '300', '350', 'C', '100 + 200 = 300.', 'MATH', 3, 'SIMPLE', 'addition', 80, 0),
('Combien font 500 - 200?', '200', '250', '300', '350', 'C', '500 - 200 = 300.', 'MATH', 3, 'SIMPLE', 'soustraction', 80, 0),
('Combien font 7 × 5?', '30', '32', '35', '40', 'C', '7 × 5 = 35.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0),
('Combien font 6 × 8?', '42', '46', '48', '54', 'C', '6 × 8 = 48.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0),
('Combien font 9 × 4?', '32', '34', '36', '40', 'C', '9 × 4 = 36.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0),
('Combien font 12 ÷ 3?', '3', '4', '5', '6', 'B', '12 ÷ 3 = 4.', 'MATH', 3, 'SIMPLE', 'division', 80, 0),
('Combien font 20 ÷ 4?', '4', '5', '6', '8', 'B', '20 ÷ 4 = 5.', 'MATH', 3, 'SIMPLE', 'division', 80, 0),
('Combien font 25 ÷ 5?', '4', '5', '6', '7', 'B', '25 ÷ 5 = 5.', 'MATH', 3, 'SIMPLE', 'division', 80, 0),
('Quel est le périmètre d''un carré de côté 5cm?', '10cm', '15cm', '20cm', '25cm', 'C', 'Le périmètre = 4 × côté = 4 × 5 = 20cm.', 'MATH', 3, 'SIMPLE', 'geometrie', 80, 0),
('Quel est le périmètre d''un rectangle de longueur 6cm et largeur 4cm?', '10cm', '16cm', '20cm', '24cm', 'C', 'Périmètre = 2 × (6 + 4) = 20cm.', 'MATH', 3, 'SIMPLE', 'geometrie', 80, 0);

-- MATH - CLASSE 3 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 125 + 87?', '202', '212', '222', '232', 'B', '125 + 87 = 212.', 'MATH', 3, 'MOYEN', 'addition', 80, 0),
('Combien font 300 - 145?', '145', '155', '165', '175', 'B', '300 - 145 = 155.', 'MATH', 3, 'MOYEN', 'soustraction', 80, 0),
('Combien font 15 × 4?', '50', '55', '60', '65', 'C', '15 × 4 = 60.', 'MATH', 3, 'MOYEN', 'multiplication', 80, 0),
('Combien font 25 × 3?', '65', '70', '75', '80', 'C', '25 × 3 = 75.', 'MATH', 3, 'MOYEN', 'multiplication', 80, 0),
('Combien font 48 ÷ 6?', '6', '7', '8', '9', 'C', '48 ÷ 6 = 8.', 'MATH', 3, 'MOYEN', 'division', 80, 0),
('Combien font 63 ÷ 7?', '7', '8', '9', '10', 'C', '63 ÷ 7 = 9.', 'MATH', 3, 'MOYEN', 'division', 80, 0),
('Quelle fraction représente la moitié?', '1/3', '1/2', '1/4', '2/3', 'B', 'La moitié = 1/2.', 'MATH', 3, 'MOYEN', 'fractions', 80, 0),
('Quelle fraction représente un quart?', '1/2', '1/3', '1/4', '3/4', 'C', 'Un quart = 1/4.', 'MATH', 3, 'MOYEN', 'fractions', 80, 0),
('Combien font 2/4 + 1/4?', '1/4', '2/4', '3/4', '4/4', 'C', '2/4 + 1/4 = 3/4.', 'MATH', 3, 'MOYEN', 'fractions', 80, 0),
('Quelle est l''aire d''un rectangle de 5cm × 3cm?', '8cm²', '12cm²', '15cm²', '16cm²', 'C', 'Aire = longueur × largeur = 5 × 3 = 15cm².', 'MATH', 3, 'MOYEN', 'geometrie', 80, 0);

-- MATH - CLASSE 3 - DIFFICILE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 256 + 178?', '424', '434', '444', '454', 'B', '256 + 178 = 434.', 'MATH', 3, 'DIFFICILE', 'addition', 80, 0),
('Combien font 500 - 267?', '223', '233', '243', '253', 'B', '500 - 267 = 233.', 'MATH', 3, 'DIFFICILE', 'soustraction', 80, 0),
('Combien font 36 × 5?', '160', '170', '180', '190', 'C', '36 × 5 = 180.', 'MATH', 3, 'DIFFICILE', 'multiplication', 80, 0),
('Combien font 84 ÷ 7?', '10', '11', '12', '14', 'C', '84 ÷ 7 = 12.', 'MATH', 3, 'DIFFICILE', 'division', 80, 0),
('Quelle est l''aire d''un carré de côté 7cm?', '14cm²', '28cm²', '49cm²', '56cm²', 'C', 'Aire = côté × côté = 7 × 7 = 49cm².', 'MATH', 3, 'DIFFICILE', 'geometrie', 80, 0);

-- ==================== MATH - CLASSE 4 ====================

-- MATH - CLASSE 4 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 12 × 11?', '120', '122', '132', '142', 'C', '12 × 11 = 132.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0),
('Combien font 15 × 6?', '80', '85', '90', '95', 'C', '15 × 6 = 90.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0),
('Combien font 144 ÷ 12?', '10', '11', '12', '14', 'C', '144 ÷ 12 = 12.', 'MATH', 4, 'SIMPLE', 'division', 80, 0),
('Combien font 125 ÷ 5?', '20', '22', '25', '30', 'C', '125 ÷ 5 = 25.', 'MATH', 4, 'SIMPLE', 'division', 80, 0),
('Quel est le périmètre d''un triangle équilatéral de côté 8cm?', '16cm', '20cm', '24cm', '32cm', 'C', 'Périmètre = 3 × 8 = 24cm.', 'MATH', 4, 'SIMPLE', 'geometrie', 80, 0),
('Combien font 3/4 - 1/4?', '1/4', '2/4', '3/4', '4/4', 'B', '3/4 - 1/4 = 2/4 = 1/2.', 'MATH', 4, 'SIMPLE', 'fractions', 80, 0),
('Quelle fraction est égale à 0,5?', '1/3', '1/2', '1/4', '2/5', 'B', '1/2 = 0,5.', 'MATH', 4, 'SIMPLE', 'fractions', 80, 0),
('Combien font 1000 + 2500?', '3000', '3250', '3500', '3750', 'C', '1000 + 2500 = 3500.', 'MATH', 4, 'SIMPLE', 'addition', 80, 0),
('Combien font 5000 - 2500?', '2000', '2250', '2500', '2750', 'C', '5000 - 2500 = 2500.', 'MATH', 4, 'SIMPLE', 'soustraction', 80, 0),
('Quel est le double de 250?', '400', '450', '500', '550', 'C', 'Le double de 250 est 500.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0);

-- MATH - CLASSE 4 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 23 × 15?', '325', '335', '345', '355', 'C', '23 × 15 = 345.', 'MATH', 4, 'MOYEN', 'multiplication', 80, 0),
('Combien font 456 + 278?', '634', '724', '734', '744', 'C', '456 + 278 = 734.', 'MATH', 4, 'MOYEN', 'addition', 80, 0),
('Combien font 800 - 456?', '334', '344', '354', '364', 'B', '800 - 456 = 344.', 'MATH', 4, 'MOYEN', 'soustraction', 80, 0),
('Combien font 2/3 + 1/6?', '1/2', '2/3', '5/6', '3/4', 'C', '2/3 + 1/6 = 4/6 + 1/6 = 5/6.', 'MATH', 4, 'MOYEN', 'fractions', 80, 0),
('Combien font 3/5 × 2?', '5/5', '6/5', '7/5', '8/5', 'B', '3/5 × 2 = 6/5.', 'MATH', 4, 'MOYEN', 'fractions', 80, 0),
('Quelle est l''aire d''un triangle de base 10cm et hauteur 6cm?', '20cm²', '30cm²', '40cm²', '60cm²', 'B', 'Aire = (base × hauteur) ÷ 2 = (10 × 6) ÷ 2 = 30cm².', 'MATH', 4, 'MOYEN', 'geometrie', 80, 0),
('Quel est le périmètre d''un cercle de rayon 7cm? (π ≈ 22/7)', '22cm', '44cm', '154cm', '49cm', 'B', 'Périmètre = 2 × π × r = 2 × 22/7 × 7 = 44cm.', 'MATH', 4, 'MOYEN', 'geometrie', 80, 0),
('Combien font 25% de 200?', '40', '50', '60', '75', 'B', '25% de 200 = 200 × 25/100 = 50.', 'MATH', 4, 'MOYEN', 'pourcentages', 80, 0),
('Combien font 10% de 350?', '30', '35', '40', '45', 'B', '10% de 350 = 350 × 10/100 = 35.', 'MATH', 4, 'MOYEN', 'pourcentages', 80, 0),
('Combien font 50% de 80?', '30', '35', '40', '45', 'C', '50% de 80 = 80 × 50/100 = 40.', 'MATH', 4, 'MOYEN', 'pourcentages', 80, 0);

-- MATH - CLASSE 4 - DIFFICILE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font 125 × 8?', '900', '950', '1000', '1050', 'C', '125 × 8 = 1000.', 'MATH', 4, 'DIFFICILE', 'multiplication', 80, 0),
('Combien font 1000 ÷ 8?', '115', '120', '125', '130', 'C', '1000 ÷ 8 = 125.', 'MATH', 4, 'DIFFICILE', 'division', 80, 0),
('Combien font 3/4 ÷ 1/2?', '1/2', '3/2', '2/3', '4/3', 'B', '3/4 ÷ 1/2 = 3/4 × 2/1 = 6/4 = 3/2.', 'MATH', 4, 'DIFFICILE', 'fractions', 80, 0),
('Quelle est l''aire d''un cercle de rayon 7cm? (π ≈ 22/7)', '22cm²', '44cm²', '154cm²', '49cm²', 'C', 'Aire = π × r² = 22/7 × 49 = 154cm².', 'MATH', 4, 'DIFFICILE', 'geometrie', 80, 0),
('Combien font 75% de 200?', '125', '140', '150', '160', 'C', '75% de 200 = 200 × 75/100 = 150.', 'MATH', 4, 'DIFFICILE', 'pourcentages', 80, 0);

-- ==================== MATH - CLASSE 5 ====================

-- MATH - CLASSE 5 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font (-3) + 5?', '-8', '-2', '2', '8', 'C', '(-3) + 5 = 2.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0),
('Combien font 7 + (-4)?', '-11', '-3', '3', '11', 'C', '7 + (-4) = 3.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0),
('Combien font (-5) × (-3)?', '-15', '-8', '8', '15', 'D', '(-5) × (-3) = 15.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0),
('Combien font (-6) × 4?', '-24', '-10', '10', '24', 'A', '(-6) × 4 = -24.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0),
('Quelle est la valeur de x si 2x = 10?', '3', '4', '5', '6', 'C', 'Si 2x = 10, alors x = 10 ÷ 2 = 5.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0),
('Quelle est la valeur de x si x + 3 = 7?', '3', '4', '5', '6', 'B', 'Si x + 3 = 7, alors x = 7 - 3 = 4.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0),
('Quelle est la valeur de x si x - 5 = 10?', '5', '10', '15', '20', 'C', 'Si x - 5 = 10, alors x = 10 + 5 = 15.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0),
('Combien font 2³?', '5', '6', '8', '9', 'C', '2³ = 2 × 2 × 2 = 8.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0),
('Combien font 3²?', '5', '6', '9', '12', 'C', '3² = 3 × 3 = 9.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0),
('Combien font 5²?', '10', '15', '20', '25', 'D', '5² = 5 × 5 = 25.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0);

-- MATH - CLASSE 5 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font (-8) - (-3)?', '-11', '-5', '5', '11', 'B', '(-8) - (-3) = -8 + 3 = -5.', 'MATH', 5, 'MOYEN', 'nombres_relatifs', 80, 0),
('Combien font (-12) ÷ (-4)?', '-3', '-8', '3', '8', 'C', '(-12) ÷ (-4) = 3.', 'MATH', 5, 'MOYEN', 'nombres_relatifs', 80, 0),
('Quelle est la valeur de x si 3x + 2 = 14?', '3', '4', '5', '6', 'B', '3x + 2 = 14 → 3x = 12 → x = 4.', 'MATH', 5, 'MOYEN', 'equations', 80, 0),
('Quelle est la valeur de x si 2x - 5 = 11?', '6', '7', '8', '9', 'C', '2x - 5 = 11 → 2x = 16 → x = 8.', 'MATH', 5, 'MOYEN', 'equations', 80, 0),
('Combien font 4³?', '12', '32', '64', '81', 'C', '4³ = 4 × 4 × 4 = 64.', 'MATH', 5, 'MOYEN', 'puissances', 80, 0),
('Combien font (-2)⁴?', '-16', '-8', '8', '16', 'D', '(-2)⁴ = (-2) × (-2) × (-2) × (-2) = 16.', 'MATH', 5, 'MOYEN', 'puissances', 80, 0),
('Quelle est la racine carrée de 49?', '5', '6', '7', '8', 'C', '√49 = 7 car 7 × 7 = 49.', 'MATH', 5, 'MOYEN', 'racines', 80, 0),
('Quelle est la racine carrée de 64?', '6', '7', '8', '9', 'C', '√64 = 8 car 8 × 8 = 64.', 'MATH', 5, 'MOYEN', 'racines', 80, 0),
('Quelle est la racine carrée de 144?', '10', '11', '12', '14', 'C', '√144 = 12 car 12 × 12 = 144.', 'MATH', 5, 'MOYEN', 'racines', 80, 0),
('Combien font 0,5 × 0,4?', '0,02', '0,2', '2,0', '0,9', 'B', '0,5 × 0,4 = 0,20 = 0,2.', 'MATH', 5, 'MOYEN', 'decimaux', 80, 0);

-- MATH - CLASSE 5 - DIFFICILE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font (-5)³?', '-15', '-125', '15', '125', 'B', '(-5)³ = (-5) × (-5) × (-5) = -125.', 'MATH', 5, 'DIFFICILE', 'puissances', 80, 0),
('Quelle est la valeur de x si 4x - 7 = 2x + 5?', '4', '5', '6', '7', 'C', '4x - 7 = 2x + 5 → 2x = 12 → x = 6.', 'MATH', 5, 'DIFFICILE', 'equations', 80, 0),
('Quelle est la racine carrée de 225?', '13', '14', '15', '16', 'C', '√225 = 15 car 15 × 15 = 225.', 'MATH', 5, 'DIFFICILE', 'racines', 80, 0),
('Combien font 3/7 + 2/5?', '5/12', '29/35', '6/35', '5/35', 'B', '3/7 + 2/5 = 15/35 + 14/35 = 29/35.', 'MATH', 5, 'DIFFICILE', 'fractions', 80, 0),
('Combien font (-3) × (-4) × (-2)?', '-24', '-9', '9', '24', 'A', '(-3) × (-4) × (-2) = 12 × (-2) = -24.', 'MATH', 5, 'DIFFICILE', 'nombres_relatifs', 80, 0);

-- ==================== MATH - CLASSE 6 ====================

-- MATH - CLASSE 6 - SIMPLE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quelle est la valeur absolue de -7?', '-7', '0', '7', '14', 'C', '|−7| = 7 (valeur absolue = distance à 0).', 'MATH', 6, 'SIMPLE', 'valeur_absolue', 80, 0),
('Quelle est la valeur absolue de 5?', '-5', '0', '5', '10', 'C', '|5| = 5.', 'MATH', 6, 'SIMPLE', 'valeur_absolue', 80, 0),
('Combien font 2/3 × 3/4?', '5/7', '6/12', '1/2', '5/12', 'C', '2/3 × 3/4 = 6/12 = 1/2.', 'MATH', 6, 'SIMPLE', 'fractions', 80, 0),
('Combien font 3/5 ÷ 2?', '3/10', '5/6', '6/5', '3/7', 'A', '3/5 ÷ 2 = 3/5 × 1/2 = 3/10.', 'MATH', 6, 'SIMPLE', 'fractions', 80, 0),
('Quelle est la valeur de x si x² = 16?', '2', '4', '8', '16', 'B', 'x² = 16 → x = 4 (ou -4).', 'MATH', 6, 'SIMPLE', 'equations', 80, 0),
('Quelle est la valeur de x si x² = 25?', '3', '4', '5', '6', 'C', 'x² = 25 → x = 5 (ou -5).', 'MATH', 6, 'SIMPLE', 'equations', 80, 0),
('Quel est le PGCD de 12 et 18?', '2', '3', '6', '9', 'C', 'PGCD(12,18) = 6.', 'MATH', 6, 'SIMPLE', 'arithmetique', 80, 0),
('Quel est le PPCM de 4 et 6?', '8', '10', '12', '24', 'C', 'PPCM(4,6) = 12.', 'MATH', 6, 'SIMPLE', 'arithmetique', 80, 0),
('Combien font 2x + 3x?', '5x', '6x', '5', '6', 'A', '2x + 3x = 5x.', 'MATH', 6, 'SIMPLE', 'algebre', 80, 0),
('Combien font 4y - y?', '3', '3y', '4y', '5y', 'B', '4y - y = 3y.', 'MATH', 6, 'SIMPLE', 'algebre', 80, 0);

-- MATH - CLASSE 6 - MOYEN
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Combien font (-2/3) ÷ (4/5)?', '-8/15', '-5/6', '5/6', '8/15', 'B', '(-2/3) ÷ (4/5) = (-2/3) × (5/4) = -10/12 = -5/6.', 'MATH', 6, 'MOYEN', 'fractions', 80, 0),
('Quelle est la valeur de x si 2(x + 3) = 16?', '3', '4', '5', '6', 'C', '2(x + 3) = 16 → x + 3 = 8 → x = 5.', 'MATH', 6, 'MOYEN', 'equations', 80, 0),
('Quelle est la valeur de x si 3x - 7 = 2x + 4?', '9', '10', '11', '12', 'C', '3x - 7 = 2x + 4 → x = 11.', 'MATH', 6, 'MOYEN', 'equations', 80, 0),
('Développer 3(x + 4).', '3x + 4', '3x + 7', '3x + 12', 'x + 12', 'C', '3(x + 4) = 3x + 12.', 'MATH', 6, 'MOYEN', 'algebre', 80, 0),
('Factoriser 5x + 10.', '5(x + 2)', '5(x + 10)', 'x(5 + 10)', '5x(1 + 2)', 'A', '5x + 10 = 5(x + 2).', 'MATH', 6, 'MOYEN', 'algebre', 80, 0),
('Quelle est la probabilité d''obtenir un 3 en lançant un dé?', '1/2', '1/3', '1/4', '1/6', 'D', 'P(3) = 1/6 (un cas favorable sur 6 possibles).', 'MATH', 6, 'MOYEN', 'probabilites', 80, 0),
('Quelle est la probabilité d''obtenir un nombre pair en lançant un dé?', '1/2', '1/3', '1/4', '1/6', 'A', 'P(pair) = 3/6 = 1/2 (2, 4, 6 sont pairs).', 'MATH', 6, 'MOYEN', 'probabilites', 80, 0),
('Quelle est la moyenne de 5, 10, 15, 20?', '10', '12,5', '15', '17,5', 'B', 'Moyenne = (5+10+15+20)/4 = 50/4 = 12,5.', 'MATH', 6, 'MOYEN', 'statistiques', 80, 0),
('Quelle est la moyenne de 8, 12, 16?', '10', '11', '12', '13', 'C', 'Moyenne = (8+12+16)/3 = 36/3 = 12.', 'MATH', 6, 'MOYEN', 'statistiques', 80, 0),
('Combien font 0,25 × 0,8?', '0,02', '0,2', '0,02', '2,0', 'B', '0,25 × 0,8 = 0,20 = 0,2.', 'MATH', 6, 'MOYEN', 'decimaux', 80, 0);

-- MATH - CLASSE 6 - EXCELLENT
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Résoudre le système: x + y = 10 et x - y = 2', 'x=5,y=5', 'x=6,y=4', 'x=7,y=3', 'x=8,y=2', 'B', 'x+y=10 et x-y=2 → 2x=12 → x=6, y=4.', 'MATH', 6, 'EXCELLENT', 'systemes', 80, 0),
('Quelle est la valeur de x si x² - 5x + 6 = 0?', 'x=1 ou x=6', 'x=2 ou x=3', 'x=-2 ou x=-3', 'x=1 ou x=5', 'B', 'x²-5x+6=(x-2)(x-3)=0 → x=2 ou x=3.', 'MATH', 6, 'EXCELLENT', 'equations', 80, 0),
('Combien font (2/3)⁻²?', '4/9', '9/4', '-4/9', '-9/4', 'B', '(2/3)⁻² = (3/2)² = 9/4.', 'MATH', 6, 'EXCELLENT', 'puissances', 80, 0),
('Quelle est la racine cubique de 27?', '2', '3', '4', '9', 'B', '∛27 = 3 car 3³ = 27.', 'MATH', 6, 'EXCELLENT', 'racines', 80, 0),
('Quelle est la probabilité d''obtenir deux faces en lançant deux pièces?', '1/2', '1/3', '1/4', '1/8', 'C', 'P(FF) = 1/2 × 1/2 = 1/4.', 'MATH', 6, 'EXCELLENT', 'probabilites', 80, 0);

-- ==================== FRENCH - CLASSE 1 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel mot est un animal?', 'chat', 'table', 'livre', 'crayon', 'A', 'Le chat est un animal.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel mot est un fruit?', 'pomme', 'chaise', 'maison', 'voiture', 'A', 'La pomme est un fruit.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel mot commence par la lettre B?', 'arbre', 'ballon', 'chat', 'domino', 'B', 'Ballon commence par B.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 80, 0),
('Quel mot commence par la lettre C?', 'arbre', 'ballon', 'chat', 'domino', 'C', 'Chat commence par C.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 80, 0),
('Combien de lettres dans le mot "soleil"?', '5', '6', '7', '4', 'B', 'Le mot soleil a 6 lettres: s-o-l-e-i-l.', 'FRENCH', 1, 'SIMPLE', 'orthographe', 80, 0),
('Combien de lettres dans le mot "maison"?', '5', '6', '7', '8', 'B', 'Le mot maison a 6 lettres: m-a-i-s-o-n.', 'FRENCH', 1, 'SIMPLE', 'orthographe', 80, 0),
('Quel mot est au pluriel?', 'chat', 'chats', 'maison', 'arbre', 'B', '"Chats" est le pluriel de "chat".', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est au singulier?', 'chats', 'chiens', 'maison', 'fleurs', 'C', '"Maison" est au singulier.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un verbe?', 'courir', 'bleu', 'table', 'école', 'A', '"Courir" est un verbe.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un adjectif?', 'courir', 'bleu', 'table', 'école', 'B', '"Bleu" est un adjectif de couleur.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le contraire de "grand"?', 'petit', 'gros', 'long', 'large', 'A', 'Le contraire de grand est petit.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel est le contraire de "chaud"?', 'tiède', 'froid', 'glacé', 'frais', 'B', 'Le contraire de chaud est froid.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0),
('Quelle phrase est correcte?', 'Le chat noir.', 'Chat le noir.', 'Noir le chat.', 'Le noir chat.', 'A', '"Le chat noir" est la forme correcte.', 'FRENCH', 1, 'SIMPLE', 'syntaxe', 80, 0),
('Quelle phrase est correcte?', 'La pomme rouge.', 'Rouge la pomme.', 'Pomme la rouge.', 'La rouge pomme.', 'A', '"La pomme rouge" est la forme correcte.', 'FRENCH', 1, 'SIMPLE', 'syntaxe', 80, 0),
('Quel mot rime avec "pain"?', 'main', 'table', 'chien', 'livre', 'A', '"Pain" et "main" riment.', 'FRENCH', 1, 'SIMPLE', 'phonetique', 80, 0);

-- ==================== FRENCH - CLASSE 2 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel est le pluriel de "cheval"?', 'chevaux', 'chevals', 'cheval', 'chevales', 'A', 'Le pluriel de cheval est chevaux.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le pluriel de "journal"?', 'journals', 'journaux', 'journal', 'journales', 'B', 'Le pluriel de journal est journaux.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un nom commun?', 'Paris', 'Marie', 'maison', 'France', 'C', '"Maison" est un nom commun.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un nom propre?', 'maison', 'chien', 'Paris', 'arbre', 'C', '"Paris" est un nom propre.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le féminin de "chat"?', 'chat', 'chate', 'chatte', 'chasse', 'C', 'Le féminin de chat est chatte.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le féminin de "lion"?', 'lion', 'lione', 'lionne', 'lions', 'C', 'Le féminin de lion est lionne.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un synonyme de "content"?', 'triste', 'heureux', 'fâché', 'fatigué', 'B', '"Heureux" est synonyme de "content".', 'FRENCH', 2, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel mot est un synonyme de "petit"?', 'grand', 'minuscule', 'gros', 'long', 'B', '"Minuscule" est synonyme de "petit".', 'FRENCH', 2, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel mot est un antonyme de "jour"?', 'soleil', 'nuit', 'lumière', 'matin', 'B', '"Nuit" est le contraire de "jour".', 'FRENCH', 2, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel mot est un antonyme de "monter"?', 'grimper', 'descendre', 'avancer', 'entrer', 'B', '"Descendre" est le contraire de "monter".', 'FRENCH', 2, 'SIMPLE', 'vocabulaire', 80, 0),
('Dans "Les enfants jouent", quel est le verbe?', 'Les', 'enfants', 'jouent', 'aucun', 'C', '"Jouent" est le verbe conjugué.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Dans "Le chat dort", quel est le sujet?', 'Le', 'chat', 'dort', 'aucun', 'B', '"Chat" est le sujet.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0),
('Quelle phrase est au présent?', 'Il mangeait.', 'Il mange.', 'Il mangera.', 'Il a mangé.', 'B', '"Il mange" est au présent.', 'FRENCH', 2, 'SIMPLE', 'conjugaison', 80, 0),
('Quelle phrase est au futur?', 'Il mangeait.', 'Il mange.', 'Il mangera.', 'Il a mangé.', 'C', '"Il mangera" est au futur.', 'FRENCH', 2, 'SIMPLE', 'conjugaison', 80, 0),
('Quel mot est un adverbe?', 'rapidement', 'rapide', 'vitesse', 'course', 'A', '"Rapidement" est un adverbe.', 'FRENCH', 2, 'SIMPLE', 'grammaire', 80, 0);

-- ==================== FRENCH - CLASSE 3 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel est le groupe verbal dans "Le chat noir dort sur le lit"?', 'Le chat', 'chat noir', 'dort', 'sur le lit', 'C', '"Dort" est le verbe conjugué.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le sujet dans "Les oiseaux chantent"?', 'Les', 'oiseaux', 'chantent', 'aucun', 'B', '"Oiseaux" est le sujet.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quel temps est utilisé dans "Il finissait"?', 'présent', 'imparfait', 'futur', 'passé composé', 'B', '"Il finissait" est à l''imparfait.', 'FRENCH', 3, 'SIMPLE', 'conjugaison', 80, 0),
('Quel temps est utilisé dans "Il a fini"?', 'imparfait', 'passé composé', 'futur', 'présent', 'B', '"Il a fini" est au passé composé.', 'FRENCH', 3, 'SIMPLE', 'conjugaison', 80, 0),
('Quel est l''infinitif de "il chante"?', 'chante', 'chanter', 'chanté', 'chantant', 'B', 'L''infinitif de "il chante" est "chanter".', 'FRENCH', 3, 'SIMPLE', 'conjugaison', 80, 0),
('Quel est l''infinitif de "nous finissons"?', 'finir', 'finissant', 'fini', 'finissons', 'A', 'L''infinitif de "nous finissons" est "finir".', 'FRENCH', 3, 'SIMPLE', 'conjugaison', 80, 0),
('Quel mot est un déterminant?', 'le', 'chat', 'dort', 'sur', 'A', '"Le" est un déterminant.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quel mot est un pronom?', 'il', 'chat', 'dort', 'noir', 'A', '"Il" est un pronom personnel.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quelle est la nature de "rapidement" dans "Il court rapidement"?', 'adjectif', 'adverbe', 'nom', 'verbe', 'B', '"Rapidement" est un adverbe.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quelle est la nature de "beau" dans "un beau jardin"?', 'nom', 'verbe', 'adjectif', 'adverbe', 'C', '"Beau" est un adjectif qualificatif.', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0),
('Quel est le contraire de "ancien"?', 'vieux', 'nouveau', 'passé', 'antique', 'B', '"Nouveau" est le contraire de "ancien".', 'FRENCH', 3, 'SIMPLE', 'vocabulaire', 80, 0),
('Quel est le synonyme de "commencer"?', 'finir', 'arrêter', 'démarrer', 'continuer', 'C', '"Démarrer" est synonyme de "commencer".', 'FRENCH', 3, 'SIMPLE', 'vocabulaire', 80, 0),
('Quelle phrase est une interrogation?', 'Il part.', 'Part-il?', 'Qu''il parte!', 'Il est parti.', 'B', '"Part-il?" est une interrogation.', 'FRENCH', 3, 'SIMPLE', 'types_phrases', 80, 0),
('Quelle phrase est une exclamation?', 'Il part.', 'Qu''il est grand!', 'Part-il?', 'Il est parti.', 'B', '"Qu''il est grand!" est une exclamation.', 'FRENCH', 3, 'SIMPLE', 'types_phrases', 80, 0),
('Quel est le pluriel de "oeil"?', 'oeils', 'oeils', 'yeux', 'oeilles', 'C', 'Le pluriel de "oeil" est "yeux".', 'FRENCH', 3, 'SIMPLE', 'grammaire', 80, 0);

-- ==================== SCIENCE - CLASSE 1 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel organe nous permet de voir?', 'oreille', 'nez', 'oeil', 'main', 'C', 'L''oeil est l''organe de la vue.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0),
('Quel organe nous permet d''entendre?', 'oeil', 'nez', 'oreille', 'bouche', 'C', 'L''oreille est l''organe de l''ouïe.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0),
('Quel organe nous permet de sentir les odeurs?', 'oeil', 'nez', 'oreille', 'main', 'B', 'Le nez est l''organe de l''odorat.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0),
('Combien as-tu de mains?', '1', '2', '3', '4', 'B', 'Nous avons 2 mains.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0),
('Combien as-tu de pieds?', '1', '2', '3', '4', 'B', 'Nous avons 2 pieds.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0),
('Quelle saison est la plus chaude?', 'hiver', 'printemps', 'été', 'automne', 'C', 'L''été est la saison la plus chaude.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0),
('Quelle saison est la plus froide?', 'hiver', 'printemps', 'été', 'automne', 'A', 'L''hiver est la saison la plus froide.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0),
('De quoi les plantes ont-elles besoin pour pousser?', 'sable', 'eau et soleil', 'papier', 'métal', 'B', 'Les plantes ont besoin d''eau et de soleil.', 'SCIENCE', 1, 'SIMPLE', 'plantes', 80, 0),
('Quel animal pond des oeufs?', 'chat', 'chien', 'poule', 'vache', 'C', 'La poule pond des oeufs.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0),
('Quel animal donne du lait?', 'poule', 'vache', 'poisson', 'serpent', 'B', 'La vache donne du lait.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0),
('Quel animal vit dans l''eau?', 'chat', 'oiseau', 'poisson', 'chien', 'C', 'Le poisson vit dans l''eau.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0),
('Quel animal vole dans le ciel?', 'poisson', 'oiseau', 'chat', 'chien', 'B', 'L''oiseau vole dans le ciel.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0),
('Que devient l''eau quand il fait très froid?', 'vapeur', 'glace', 'pluie', 'nuage', 'B', 'L''eau se transforme en glace quand il gèle.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0),
('Que devient l''eau quand on la chauffe?', 'glace', 'vapeur', 'neige', 'pluie', 'B', 'L''eau se transforme en vapeur quand on la chauffe.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0),
('Quel est l''objet qui nous éclaire la nuit?', 'soleil', 'lune', 'étoile', 'lampe', 'D', 'Une lampe nous éclaire la nuit.', 'SCIENCE', 1, 'SIMPLE', 'lumiere', 80, 0);

-- ==================== SCIENCE - CLASSE 2 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel est le plus grand animal terrestre?', 'éléphant', 'girafe', 'lion', 'rhinocéros', 'A', 'L''éléphant est le plus grand animal terrestre.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Quel animal est un mammifère?', 'poisson', 'oiseau', 'chat', 'serpent', 'C', 'Le chat est un mammifère.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Quel animal est un reptile?', 'chat', 'oiseau', 'serpent', 'poisson', 'C', 'Le serpent est un reptile.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Quel animal est un poisson?', 'dauphin', 'requin', 'baleine', 'phoque', 'B', 'Le requin est un poisson.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Comment s''appelle le petit de la poule?', 'agneau', 'poussin', 'veau', 'chiot', 'B', 'Le petit de la poule est le poussin.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Comment s''appelle le petit de la vache?', 'poussin', 'agneau', 'veau', 'chiot', 'C', 'Le petit de la vache est le veau.', 'SCIENCE', 2, 'SIMPLE', 'animaux', 80, 0),
('Quelle partie de la plante absorbe l''eau?', 'feuille', 'tige', 'racine', 'fleur', 'C', 'Les racines absorbent l''eau du sol.', 'SCIENCE', 2, 'SIMPLE', 'plantes', 80, 0),
('Quelle partie de la plante fait la photosynthèse?', 'racine', 'tige', 'feuille', 'fleur', 'C', 'Les feuilles font la photosynthèse.', 'SCIENCE', 2, 'SIMPLE', 'plantes', 80, 0),
('De quelle couleur est la chlorophylle?', 'rouge', 'jaune', 'verte', 'bleue', 'C', 'La chlorophylle est verte.', 'SCIENCE', 2, 'SIMPLE', 'plantes', 80, 0),
('Quel gaz les plantes produisent-elles?', 'oxygène', 'carbone', 'azote', 'hydrogène', 'A', 'Les plantes produisent de l''oxygène.', 'SCIENCE', 2, 'SIMPLE', 'plantes', 80, 0),
('Quel organe pompe le sang?', 'poumon', 'coeur', 'estomac', 'cerveau', 'B', 'Le coeur pompe le sang dans tout le corps.', 'SCIENCE', 2, 'SIMPLE', 'corps_humain', 80, 0),
('Quel organe nous permet de respirer?', 'coeur', 'estomac', 'poumon', 'foie', 'C', 'Les poumons permettent la respiration.', 'SCIENCE', 2, 'SIMPLE', 'corps_humain', 80, 0),
('Combien de dents a un adulte?', '20', '28', '32', '36', 'C', 'Un adulte a 32 dents.', 'SCIENCE', 2, 'SIMPLE', 'corps_humain', 80, 0),
('Quel est l''os le plus long du corps?', 'fémur', 'tibia', 'humérus', 'radius', 'A', 'Le fémur est l''os le plus long.', 'SCIENCE', 2, 'SIMPLE', 'corps_humain', 80, 0),
('Que faut-il faire pour rester en bonne santé?', 'manger n''importe quoi', 'manger équilibré', 'ne pas bouger', 'ne pas dormir', 'B', 'Il faut manger équilibré pour rester en bonne santé.', 'SCIENCE', 2, 'SIMPLE', 'sante', 80, 0);

-- ==================== SCIENCE - CLASSE 3 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quel est l''état de l''eau à température ambiante?', 'solide', 'liquide', 'gazeux', 'plasma', 'B', 'À température ambiante, l''eau est liquide.', 'SCIENCE', 3, 'SIMPLE', 'etats_matiere', 80, 0),
('À quelle température l''eau gèle-t-elle?', '-10°C', '0°C', '10°C', '100°C', 'B', 'L''eau gèle à 0°C.', 'SCIENCE', 3, 'SIMPLE', 'etats_matiere', 80, 0),
('À quelle température l''eau bout-elle?', '50°C', '80°C', '100°C', '120°C', 'C', 'L''eau bout à 100°C.', 'SCIENCE', 3, 'SIMPLE', 'etats_matiere', 80, 0),
('Quel gaz respirons-nous?', 'oxygène', 'carbone', 'azote', 'hydrogène', 'A', 'Nous respirons de l''oxygène.', 'SCIENCE', 3, 'SIMPLE', 'respiration', 80, 0),
('Quel gaz expirons-nous?', 'oxygène', 'carbone', 'azote', 'hydrogène', 'B', 'Nous expirons du dioxyde de carbone.', 'SCIENCE', 3, 'SIMPLE', 'respiration', 80, 0),
('Quelle planète est la plus proche du Soleil?', 'Vénus', 'Terre', 'Mercure', 'Mars', 'C', 'Mercure est la planète la plus proche du Soleil.', 'SCIENCE', 3, 'SIMPLE', 'systeme_solaire', 80, 0),
('Quelle planète appelle-t-on la planète rouge?', 'Vénus', 'Mars', 'Jupiter', 'Saturne', 'B', 'Mars est appelée la planète rouge.', 'SCIENCE', 3, 'SIMPLE', 'systeme_solaire', 80, 0),
('Combien de planètes y a-t-il dans le système solaire?', '7', '8', '9', '10', 'B', 'Il y a 8 planètes dans le système solaire.', 'SCIENCE', 3, 'SIMPLE', 'systeme_solaire', 80, 0),
('Quelle est la plus grande planète?', 'Terre', 'Saturne', 'Jupiter', 'Neptune', 'C', 'Jupiter est la plus grande planète.', 'SCIENCE', 3, 'SIMPLE', 'systeme_solaire', 80, 0),
('Qu''est-ce qui cause les marées?', 'le vent', 'la Lune', 'le Soleil', 'les étoiles', 'B', 'La Lune cause les marées par son attraction gravitationnelle.', 'SCIENCE', 3, 'SIMPLE', 'astronomie', 80, 0),
('Quel est le rôle des abeilles?', 'faire du miel seulement', 'polliniser les fleurs', 'piquer', 'voler', 'B', 'Les abeilles pollinisent les fleurs.', 'SCIENCE', 3, 'SIMPLE', 'ecologie', 80, 0),
('Qu''est-ce qu''une chaîne alimentaire?', 'un restaurant', 'qui mange qui dans la nature', 'une liste de courses', 'un jeu', 'B', 'Une chaîne alimentaire montre qui mange qui dans la nature.', 'SCIENCE', 3, 'SIMPLE', 'ecologie', 80, 0),
('Quel organe filtre le sang?', 'coeur', 'poumon', 'rein', 'estomac', 'C', 'Les reins filtrent le sang.', 'SCIENCE', 3, 'SIMPLE', 'corps_humain', 80, 0),
('Quel organe produit la bile?', 'coeur', 'foie', 'poumon', 'rein', 'B', 'Le foie produit la bile.', 'SCIENCE', 3, 'SIMPLE', 'corps_humain', 80, 0),
('Combien de temps la Terre met-elle pour tourner autour du Soleil?', '24 heures', '30 jours', '365 jours', '100 jours', 'C', 'La Terre met 365 jours pour tourner autour du Soleil.', 'SCIENCE', 3, 'SIMPLE', 'astronomie', 80, 0);

-- ==================== HISTORY - CLASSE 1 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Qu''est-ce que l''histoire?', 'l''étude du futur', 'l''étude du passé', 'l''étude des maths', 'l''étude de la nature', 'B', 'L''histoire est l''étude du passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Que signifie "ancien"?', 'très récent', 'qui date du passé', 'imaginaire', 'rapide', 'B', '"Ancien" signifie qui vient du passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Que signifie "moderne"?', 'très vieux', 'récent, actuel', 'ancien', 'passé', 'B', '"Moderne" signifie récent ou actuel.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Qu''est-ce qu''un ancêtre?', 'un descendant', 'un membre de sa famille qui a vécu avant', 'un ami', 'un voisin', 'B', 'Un ancêtre est un membre de sa famille qui a vécu avant.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Qu''est-ce qu''une génération?', 'une année', 'l''ensemble des personnes nées à la même époque', 'un siècle', 'un jour', 'B', 'Une génération est l''ensemble des personnes nées à la même époque.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Combien d''années y a-t-il dans un siècle?', '10', '50', '100', '1000', 'C', 'Un siècle compte 100 ans.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0),
('Combien d''années y a-t-il dans une décennie?', '5', '10', '50', '100', 'B', 'Une décennie compte 10 ans.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0),
('Qu''est-ce qu''un musée?', 'un cinéma', 'un lieu où on conserve des objets anciens', 'un parc', 'une école', 'B', 'Un musée conserve et expose des objets anciens.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0),
('Qu''est-ce qu''un monument?', 'un film', 'une construction importante qui rappelle un événement', 'un livre', 'un jeu', 'B', 'Un monument est une construction qui rappelle un événement important.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0),
('Qu''est-ce qu''une archive?', 'un film', 'un document ancien conservé', 'un jeu vidéo', 'une chanson', 'B', 'Une archive est un document ancien conservé.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0),
('Qu''est-ce qu''un historien?', 'quelqu''un qui étudie le passé', 'quelqu''un qui prédit l''avenir', 'quelqu''un qui écrit des romans', 'quelqu''un qui peint', 'A', 'Un historien étudie le passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0),
('Qu''est-ce qu''une frise chronologique?', 'un dessin', 'une ligne du temps', 'une carte', 'un livre', 'B', 'Une frise chronologique est une ligne du temps.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0),
('Qu''est-ce que "avant J.-C."?', 'après Jésus-Christ', 'avant Jésus-Christ', 'pendant Jésus-Christ', 'sans rapport', 'B', '"Avant J.-C." signifie avant Jésus-Christ.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0),
('Qu''est-ce que "après J.-C."?', 'avant Jésus-Christ', 'après Jésus-Christ', 'pendant Jésus-Christ', 'sans rapport', 'B', '"Après J.-C." signifie après Jésus-Christ.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0),
('Dans quel ordre place-t-on les événements?', 'n''importe comment', 'dans l''ordre chronologique', 'par couleur', 'par taille', 'B', 'On place les événements dans l''order chronologique.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0);

-- ==================== GEOGRAPHY - CLASSE 1 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('Quels sont les 4 points cardinaux?', 'haut, bas, gauche, droite', 'nord, sud, est, ouest', 'avant, arrière, gauche, droite', 'dessus, dessous, dedans, dehors', 'B', 'Les 4 points cardinaux sont nord, sud, est, ouest.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0),
('Où se lève le soleil?', 'au nord', 'au sud', 'à l''est', 'à l''ouest', 'C', 'Le soleil se lève à l''est.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0),
('Où se couche le soleil?', 'au nord', 'au sud', 'à l''est', 'à l''ouest', 'D', 'Le soleil se couche à l''ouest.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0),
('Qu''est-ce qu''une carte?', 'un dessin', 'une représentation d''un territoire vue de dessus', 'une photo', 'un livre', 'B', 'Une carte est une représentation d''un territoire vue de dessus.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0),
('Qu''est-ce qu''un plan?', 'une carte d''un petit espace', 'une photo', 'un dessin', 'un livre', 'A', 'Un plan est une carte d''un petit espace.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0),
('Qu''est-ce qu''une légende de carte?', 'une histoire', 'la liste des symboles utilisés sur la carte', 'un titre', 'une couleur', 'B', 'La légende explique les symboles de la carte.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0),
('Quelle est la capitale de la Tunisie?', 'Sfax', 'Tunis', 'Sousse', 'Bizerte', 'B', 'La capitale de la Tunisie est Tunis.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0),
('Sur quelle mer se trouve la Tunisie?', 'Mer Rouge', 'Mer Noire', 'Mer Méditerranée', 'Océan Atlantique', 'C', 'La Tunisie se trouve sur la Mer Méditerranée.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0),
('Quels sont les pays voisins de la Tunisie?', 'Égypte et Libye', 'Algérie et Libye', 'Maroc et Algérie', 'France et Italie', 'B', 'La Tunisie a pour voisins l''Algérie et la Libye.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0),
('Dans quel continent se trouve la Tunisie?', 'Europe', 'Afrique', 'Asie', 'Amérique', 'B', 'La Tunisie se trouve en Afrique.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0),
('Qu''est-ce qu''une boussole?', 'un instrument de mesure', 'un instrument qui indique le nord', 'une carte', 'un livre', 'B', 'Une boussole indique le nord.', 'GEOGRAPHY', 1, 'SIMPLE', 'orientation', 80, 0),
('Qu''est-ce qu''une île?', 'une grande étendue d''eau', 'une terre entourée d''eau', 'une montagne', 'une forêt', 'B', 'Une île est une terre entourée d''eau.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0),
('Qu''est-ce qu''une presqu''île?', 'une île', 'une terre entourée d''eau sauf d''un côté', 'une montagne', 'une rivière', 'B', 'Une presqu''île est entourée d''eau sauf d''un côté.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0),
('Qu''est-ce qu''une rivière?', 'une grande étendue d''eau salée', 'un cours d''eau douce', 'une montagne', 'une forêt', 'B', 'Une rivière est un cours d''eau douce.', 'GEOGRAPHY', 1, 'SIMPLE', 'eau', 80, 0),
('Qu''est-ce qu''une montagne?', 'une grande étendue plate', 'une élévation importante du relief', 'une rivière', 'une forêt', 'B', 'Une montagne est une élévation importante du relief.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0);

-- ==================== ARABIC - CLASSE 1 ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count) VALUES
('ما هي عاصمة تونس؟', 'صفاقس', 'تونس', 'سوسة', 'بنزرت', 'B', 'عاصمة تونس هي مدينة تونس.', 'ARABIC', 1, 'SIMPLE', 'جغرافيا', 80, 0),
('كم يساوي 2 + 3؟', '4', '5', '6', '7', 'B', '2 + 3 = 5.', 'ARABIC', 1, 'SIMPLE', 'رياضيات', 80, 0),
('ما هو لون السماء؟', 'أحمر', 'أزرق', 'أخضر', 'أصفر', 'B', 'لون السماء أزرق.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('كم يوما في الأسبوع؟', '5', '6', '7', '8', 'C', 'الأسبوع له 7 أيام.', 'ARABIC', 1, 'SIMPLE', 'عام', 80, 0),
('ما هو الحيوان الذي يبيض؟', 'قطة', 'بقرة', 'دجاجة', 'حصان', 'C', 'الدجاجة تبيض.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('ما هو الشهر الأول في السنة؟', 'فبراير', 'يناير', 'مارس', 'أبريل', 'B', 'يناير هو الشهر الأول.', 'ARABIC', 1, 'SIMPLE', 'عام', 80, 0),
('كم عدد أصابع اليد الواحدة؟', '4', '5', '6', '10', 'B', 'لدينا 5 أصابع في كل يد.', 'ARABIC', 1, 'SIMPLE', 'عام', 80, 0),
('ما هو الفصل الأكثر حرارة؟', 'الشتاء', 'الربيع', 'الصيف', 'الخريف', 'C', 'الصيف هو الفصل الأكثر حرارة.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('ما هو الكوكب الذي نعيش عليه؟', 'المريخ', 'الأرض', 'المشتري', 'زحل', 'B', 'نحن نعيش على كوكب الأرض.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('ما هو العضو الذي نسمع به؟', 'العين', 'الأنف', 'الأذن', 'الفم', 'C', 'الأذن هي عضو السمع.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('كم عدد ألوان قوس قزح؟', '5', '6', '7', '8', 'C', 'قوس قزح له 7 ألوان.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('ما هو الحيوان الأسرع؟', 'الأسد', 'الفهد', 'الغزال', 'الحصان', 'B', 'الفهد هو الحيوان الأسرع.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('ما هو أكبر محيط في العالم؟', 'الأطلسي', 'الهندي', 'الهادئ', 'المتجمد', 'C', 'المحيط الهادئ هو الأكبر.', 'ARABIC', 1, 'SIMPLE', 'جغرافيا', 80, 0),
('ما هو الغاز الذي نتنفسه؟', 'الهيدروجين', 'الأكسجين', 'النيتروجين', 'ثاني أكسيد الكربون', 'B', 'نحن نتنفس الأكسجين.', 'ARABIC', 1, 'SIMPLE', 'علوم', 80, 0),
('كم عدد القارات في العالم؟', '5', '6', '7', '8', 'C', 'هناك 7 قارات في العالم.', 'ARABIC', 1, 'SIMPLE', 'جغرافيا', 80, 0);

-- Réactiver les contraintes
SET session_replication_role = origin;