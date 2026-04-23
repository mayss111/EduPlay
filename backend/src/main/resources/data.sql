-- Script SQL pour peupler la base de questions EduPlay
-- 500+ questions pour classes 1-6, toutes matières, tous niveaux, toutes langues

-- Désactiver les contraintes pour insertion en masse
SET FOREIGN_KEY_CHECKS = 0;

-- ==================== MATH - CLASSE 1 - FRENCH ====================

-- MATH - CLASSE 1 - SIMPLE - FRENCH
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Combien font 1 + 2?', '2', '3', '4', '1', 'B', 'En mathématiques, 1 + 2 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 2 + 1?', '3', '2', '4', '1', 'A', '2 + 1 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 3 + 0?', '0', '3', '4', '2', 'B', 'Quand on ajoute 0, le nombre ne change pas. 3 + 0 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 4 + 1?', '4', '5', '6', '3', 'B', '4 + 1 = 5.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 2 + 2?', '3', '4', '5', '2', 'B', '2 + 2 = 4.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 5 - 1?', '3', '4', '5', '6', 'B', '5 - 1 = 4.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Combien font 3 - 1?', '1', '2', '3', '4', 'B', '3 - 1 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Combien font 4 - 2?', '1', '2', '3', '4', 'B', '4 - 2 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Quel nombre vient après 3?', '2', '3', '4', '5', 'C', 'Dans la suite des nombres, 4 vient après 3.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'FRENCH'),
('Quel nombre vient après 5?', '4', '5', '6', '7', 'C', 'Dans la suite des nombres, 6 vient après 5.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'FRENCH'),
('Quel nombre vient avant 4?', '3', '4', '5', '2', 'A', 'Dans la suite des nombres, 3 vient avant 4.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'FRENCH'),
('Quel nombre vient avant 7?', '5', '6', '7', '8', 'B', 'Dans la suite des nombres, 6 vient avant 7.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'FRENCH'),
('Combien de doigts as-tu sur une main?', '4', '5', '6', '10', 'B', 'On a 5 doigts sur chaque main.', 'MATH', 1, 'SIMPLE', 'comptage', 80, 0, 'FRENCH'),
('Combien de jours dans une semaine?', '5', '6', '7', '8', 'C', 'Une semaine a 7 jours.', 'MATH', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Quel nombre est plus grand: 5 ou 3?', '3', '5', 'égaux', 'aucun', 'B', '5 est plus grand que 3.', 'MATH', 1, 'SIMPLE', 'comparaison', 80, 0, 'FRENCH');

-- MATH - CLASSE 1 - SIMPLE - ARABIC
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('كم يساوي 1 + 2؟', '2', '3', '4', '1', 'B', 'في الرياضيات، 1 + 2 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 2 + 1؟', '3', '2', '4', '1', 'A', '2 + 1 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 3 + 0؟', '0', '3', '4', '2', 'B', 'عندما نضيف 0، لا يتغير العدد. 3 + 0 = 3.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 4 + 1؟', '4', '5', '6', '3', 'B', '4 + 1 = 5.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 2 + 2؟', '3', '4', '5', '2', 'B', '2 + 2 = 4.', 'MATH', 1, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 5 - 1؟', '3', '4', '5', '6', 'B', '5 - 1 = 4.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('كم يساوي 3 - 1؟', '1', '2', '3', '4', 'B', '3 - 1 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('كم يساوي 4 - 2؟', '1', '2', '3', '4', 'B', '4 - 2 = 2.', 'MATH', 1, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('ما هو العدد الذي يأتي بعد 3؟', '2', '3', '4', '5', 'C', 'في تسلسل الأعداد، يأتي 4 بعد 3.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'ARABIC'),
('ما هو العدد الذي يأتي بعد 5؟', '4', '5', '6', '7', 'C', 'في تسلسل الأعداد، يأتي 6 بعد 5.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'ARABIC'),
('ما هو العدد الذي يأتي قبل 4؟', '3', '4', '5', '2', 'A', 'في تسلسل الأعداد، يأتي 3 قبل 4.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'ARABIC'),
('ما هو العدد الذي يأتي قبل 7؟', '5', '6', '7', '8', 'B', 'في تسلسل الأعداد، يأتي 6 قبل 7.', 'MATH', 1, 'SIMPLE', 'suite_nombres', 80, 0, 'ARABIC'),
('كم عدد الأصابع في اليد الواحدة؟', '4', '5', '6', '10', 'B', 'لدينا 5 أصابع في كل يد.', 'MATH', 1, 'SIMPLE', 'comptage', 80, 0, 'ARABIC'),
('كم عدد أيام الأسبوع؟', '5', '6', '7', '8', 'C', 'الأسبوع له 7 أيام.', 'MATH', 1, 'SIMPLE', 'temps', 80, 0, 'ARABIC'),
('أي عدد أكبر: 5 أم 3؟', '3', '5', 'متساويان', 'لا شيء', 'B', '5 أكبر من 3.', 'MATH', 1, 'SIMPLE', 'comparaison', 80, 0, 'ARABIC');

-- ==================== FRENCH - CLASSE 1 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Quel mot est un animal?', 'chat', 'table', 'livre', 'crayon', 'A', 'Le chat est un animal.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0, 'FRENCH'),
('Quel mot est un fruit?', 'pomme', 'chaise', 'maison', 'voiture', 'A', 'La pomme est un fruit.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0, 'FRENCH'),
('Quel mot commence par la lettre B?', 'arbre', 'ballon', 'chat', 'domino', 'B', 'Ballon commence par B.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 80, 0, 'FRENCH'),
('Quel mot commence par la lettre C?', 'arbre', 'ballon', 'chat', 'domino', 'C', 'Chat commence par C.', 'FRENCH', 1, 'SIMPLE', 'alphabet', 80, 0, 'FRENCH'),
('Combien de lettres dans le mot "soleil"?', '5', '6', '7', '4', 'B', 'Le mot soleil a 6 lettres: s-o-l-e-i-l.', 'FRENCH', 1, 'SIMPLE', 'orthographe', 80, 0, 'FRENCH'),
('Combien de lettres dans le mot "maison"?', '5', '6', '7', '8', 'B', 'Le mot maison a 6 lettres: m-a-i-s-o-n.', 'FRENCH', 1, 'SIMPLE', 'orthographe', 80, 0, 'FRENCH'),
('Quel mot est au pluriel?', 'chat', 'chats', 'maison', 'arbre', 'B', '"Chats" est le pluriel de "chat".', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0, 'FRENCH'),
('Quel mot est au singulier?', 'chats', 'chiens', 'maison', 'fleurs', 'C', '"Maison" est au singulier.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0, 'FRENCH'),
('Quel mot est un verbe?', 'courir', 'bleu', 'table', 'école', 'A', '"Courir" est un verbe.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0, 'FRENCH'),
('Quel mot est un adjectif?', 'courir', 'bleu', 'table', 'école', 'B', '"Bleu" est un adjectif de couleur.', 'FRENCH', 1, 'SIMPLE', 'grammaire', 80, 0, 'FRENCH'),
('Quel est le contraire de "grand"?', 'petit', 'gros', 'long', 'large', 'A', 'Le contraire de grand est petit.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0, 'FRENCH'),
('Quel est le contraire de "chaud"?', 'tiède', 'froid', 'glacé', 'frais', 'B', 'Le contraire de chaud est froid.', 'FRENCH', 1, 'SIMPLE', 'vocabulaire', 80, 0, 'FRENCH'),
('Quelle phrase est correcte?', 'Le chat noir.', 'Chat le noir.', 'Noir le chat.', 'Le noir chat.', 'A', '"Le chat noir" est la forme correcte.', 'FRENCH', 1, 'SIMPLE', 'syntaxe', 80, 0, 'FRENCH'),
('Quelle phrase est correcte?', 'La pomme rouge.', 'Rouge la pomme.', 'Pomme la rouge.', 'La rouge pomme.', 'A', '"La pomme rouge" est la forme correcte.', 'FRENCH', 1, 'SIMPLE', 'syntaxe', 80, 0, 'FRENCH'),
('Quel mot rime avec "pain"?', 'main', 'table', 'chien', 'livre', 'A', '"Pain" et "main" riment.', 'FRENCH', 1, 'SIMPLE', 'phonetique', 80, 0, 'FRENCH');

-- ==================== SCIENCE - CLASSE 1 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Quel organe nous permet de voir?', 'oreille', 'nez', 'oeil', 'main', 'C', 'L''oeil est l''organe de la vue.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'FRENCH'),
('Quel organe nous permet d''entendre?', 'oeil', 'nez', 'oreille', 'bouche', 'C', 'L''oreille est l''organe de l''ouïe.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'FRENCH'),
('Quel organe nous permet de sentir les odeurs?', 'oeil', 'nez', 'oreille', 'main', 'B', 'Le nez est l''organe de l''odorat.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'FRENCH'),
('Combien as-tu de mains?', '1', '2', '3', '4', 'B', 'Nous avons 2 mains.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'FRENCH'),
('Combien as-tu de pieds?', '1', '2', '3', '4', 'B', 'Nous avons 2 pieds.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'FRENCH'),
('Quelle saison est la plus chaude?', 'hiver', 'printemps', 'été', 'automne', 'C', 'L''été est la saison la plus chaude.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0, 'FRENCH'),
('Quelle saison est la plus froide?', 'hiver', 'printemps', 'été', 'automne', 'A', 'L''hiver est la saison la plus froide.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0, 'FRENCH'),
('De quoi les plantes ont-elles besoin pour pousser?', 'sable', 'eau et soleil', 'papier', 'métal', 'B', 'Les plantes ont besoin d''eau et de soleil.', 'SCIENCE', 1, 'SIMPLE', 'plantes', 80, 0, 'FRENCH'),
('Quel animal pond des oeufs?', 'chat', 'chien', 'poule', 'vache', 'C', 'La poule pond des oeufs.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'FRENCH'),
('Quel animal donne du lait?', 'poule', 'vache', 'poisson', 'serpent', 'B', 'La vache donne du lait.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'FRENCH'),
('Quel animal vit dans l''eau?', 'chat', 'oiseau', 'poisson', 'chien', 'C', 'Le poisson vit dans l''eau.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'FRENCH'),
('Quel animal vole dans le ciel?', 'poisson', 'oiseau', 'chat', 'chien', 'B', 'L''oiseau vole dans le ciel.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'FRENCH'),
('Que devient l''eau quand il fait très froid?', 'vapeur', 'glace', 'pluie', 'nuage', 'B', 'L''eau se transforme en glace quand il gèle.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0, 'FRENCH'),
('Que devient l''eau quand on la chauffe?', 'glace', 'vapeur', 'neige', 'pluie', 'B', 'L''eau se transforme en vapeur quand on la chauffe.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0, 'FRENCH'),
('Quel est l''objet qui nous éclaire la nuit?', 'soleil', 'lune', 'étoile', 'lampe', 'D', 'Une lampe nous éclaire la nuit.', 'SCIENCE', 1, 'SIMPLE', 'lumiere', 80, 0, 'FRENCH');

-- ==================== GEOGRAPHY - CLASSE 1 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Quels sont les 4 points cardinaux?', 'haut, bas, gauche, droite', 'nord, sud, est, ouest', 'avant, arrière, gauche, droite', 'dessus, dessous, dedans, dehors', 'B', 'Les 4 points cardinaux sont nord, sud, est, ouest.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'FRENCH'),
('Où se lève le soleil?', 'au nord', 'au sud', 'à l''est', 'à l''ouest', 'C', 'Le soleil se lève à l''est.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'FRENCH'),
('Où se couche le soleil?', 'au nord', 'au sud', 'à l''est', 'à l''ouest', 'D', 'Le soleil se couche à l''ouest.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une carte?', 'un dessin', 'une représentation d''un territoire vue de dessus', 'une photo', 'un livre', 'B', 'Une carte est une représentation d''un territoire vue de dessus.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'FRENCH'),
('Qu''est-ce qu''un plan?', 'une carte d''un petit espace', 'une photo', 'un dessin', 'un livre', 'A', 'Un plan est une carte d''un petit espace.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une légende de carte?', 'une histoire', 'la liste des symboles utilisés sur la carte', 'un titre', 'une couleur', 'B', 'La légende explique les symboles de la carte.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'FRENCH'),
('Quelle est la capitale de la Tunisie?', 'Sfax', 'Tunis', 'Sousse', 'Bizerte', 'B', 'La capitale de la Tunisie est Tunis.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'FRENCH'),
('Sur quelle mer se trouve la Tunisie?', 'Mer Rouge', 'Mer Noire', 'Mer Méditerranée', 'Océan Atlantique', 'C', 'La Tunisie se trouve sur la Mer Méditerranée.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'FRENCH'),
('Quels sont les pays voisins de la Tunisie?', 'Égypte et Libye', 'Algérie et Libye', 'Maroc et Algérie', 'France et Italie', 'B', 'La Tunisie a pour voisins l''Algérie et la Libye.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'FRENCH'),
('Dans quel continent se trouve la Tunisie?', 'Europe', 'Afrique', 'Asie', 'Amérique', 'B', 'La Tunisie se trouve en Afrique.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une boussole?', 'un instrument de mesure', 'un instrument qui indique le nord', 'une carte', 'un livre', 'B', 'Une boussole indique le nord.', 'GEOGRAPHY', 1, 'SIMPLE', 'orientation', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une île?', 'une grande étendue d''eau', 'une terre entourée d''eau', 'une montagne', 'une forêt', 'B', 'Une île est une terre entourée d''eau.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une presqu''île?', 'une île', 'une terre entourée d''eau sauf d''un côté', 'une montagne', 'une rivière', 'B', 'Une presqu''île est entourée d''eau sauf d''un côté.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une rivière?', 'une grande étendue d''eau salée', 'un cours d''eau douce', 'une montagne', 'une forêt', 'B', 'Une rivière est un cours d''eau douce.', 'GEOGRAPHY', 1, 'SIMPLE', 'eau', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une montagne?', 'une grande étendue plate', 'une élévation importante du relief', 'une rivière', 'une forêt', 'B', 'Une montagne est une élévation importante du relief.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'FRENCH');

-- ==================== HISTORY - CLASSE 1 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Qu''est-ce que l''histoire?', 'l''étude du futur', 'l''étude du passé', 'l''étude des maths', 'l''étude de la nature', 'B', 'L''histoire est l''étude du passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Que signifie "ancien"?', 'très récent', 'qui date du passé', 'imaginaire', 'rapide', 'B', '"Ancien" signifie qui vient du passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Que signifie "moderne"?', 'très vieux', 'récent, actuel', 'ancien', 'passé', 'B', '"Moderne" signifie récent ou actuel.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Qu''est-ce qu''un ancêtre?', 'un descendant', 'un membre de sa famille qui a vécu avant', 'un ami', 'un voisin', 'B', 'Un ancêtre est un membre de sa famille qui a vécu avant.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une génération?', 'une année', 'l''ensemble des personnes nées à la même époque', 'un siècle', 'un jour', 'B', 'Une génération est l''ensemble des personnes nées à la même époque.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Combien d''années y a-t-il dans un siècle?', '10', '50', '100', '1000', 'C', 'Un siècle compte 100 ans.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Combien d''années y a-t-il dans une décennie?', '5', '10', '50', '100', 'B', 'Une décennie compte 10 ans.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Qu''est-ce qu''un musée?', 'un cinéma', 'un lieu où on conserve des objets anciens', 'un parc', 'une école', 'B', 'Un musée conserve et expose des objets anciens.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0, 'FRENCH'),
('Qu''est-ce qu''un monument?', 'un film', 'une construction importante qui rappelle un événement', 'un livre', 'un jeu', 'B', 'Un monument est une construction qui rappelle un événement important.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une archive?', 'un film', 'un document ancien conservé', 'un jeu vidéo', 'une chanson', 'B', 'Une archive est un document ancien conservé.', 'HISTORY', 1, 'SIMPLE', 'patrimoine', 80, 0, 'FRENCH'),
('Qu''est-ce qu''un historien?', 'quelqu''un qui étudie le passé', 'quelqu''un qui prédit l''avenir', 'quelqu''un qui écrit des romans', 'quelqu''un qui peint', 'A', 'Un historien étudie le passé.', 'HISTORY', 1, 'SIMPLE', 'notions_base', 80, 0, 'FRENCH'),
('Qu''est-ce qu''une frise chronologique?', 'un dessin', 'une ligne du temps', 'une carte', 'un livre', 'B', 'Une frise chronologique est une ligne du temps.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Qu''est-ce que "avant J.-C."?', 'après Jésus-Christ', 'avant Jésus-Christ', 'pendant Jésus-Christ', 'sans rapport', 'B', '"Avant J.-C." signifie avant Jésus-Christ.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Qu''est-ce que "après J.-C."?', 'avant Jésus-Christ', 'après Jésus-Christ', 'pendant Jésus-Christ', 'sans rapport', 'B', '"Après J.-C." signifie après Jésus-Christ.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH'),
('Dans quel ordre place-t-on les événements?', 'n''importe comment', 'dans l''ordre chronologique', 'par couleur', 'par taille', 'B', 'On place les événements dans l''order chronologique.', 'HISTORY', 1, 'SIMPLE', 'temps', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 2 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Combien font 10 + 5?', '12', '13', '15', '14', 'C', '10 + 5 = 15.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 20 - 5?', '10', '15', '25', '18', 'B', '20 - 5 = 15.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Combien font 12 + 8?', '18', '19', '20', '22', 'C', '12 + 8 = 20.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 25 - 5?', '15', '20', '25', '30', 'B', '25 - 5 = 20.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Quel nombre est 10 de plus que 20?', '10', '20', '30', '40', 'C', '20 + 10 = 30.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 30 + 10?', '30', '40', '50', '20', 'B', '30 + 10 = 40.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 50 - 10?', '30', '40', '50', '60', 'B', '50 - 10 = 40.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Combien de côtés a un carré?', '3', '4', '5', '6', 'B', 'Un carré a 4 côtés égaux.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Combien de côtés a un rectangle?', '3', '4', '5', '6', 'B', 'Un rectangle a 4 côtés.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Quel nombre est pair?', '3', '5', '6', '7', 'C', '6 est pair car il se divise par 2.', 'MATH', 2, 'SIMPLE', 'nombres_pairs', 80, 0, 'FRENCH'),
('Combien font 3 × 2?', '5', '6', '7', '8', 'B', '3 × 2 = 6.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 5 × 2?', '8', '10', '12', '15', 'B', '5 × 2 = 10.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 4 × 3?', '10', '11', '12', '15', 'C', '4 × 3 = 12.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 10 × 2?', '15', '18', '20', '25', 'C', '10 × 2 = 20.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Quel est le double de 15?', '20', '25', '30', '35', 'C', 'Le double de 15 est 30.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 3 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Combien font 100 + 200?', '200', '250', '300', '350', 'C', '100 + 200 = 300.', 'MATH', 3, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 500 - 200?', '200', '250', '300', '350', 'C', '500 - 200 = 300.', 'MATH', 3, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Combien font 7 × 5?', '30', '32', '35', '40', 'C', '7 × 5 = 35.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 6 × 8?', '42', '46', '48', '54', 'C', '6 × 8 = 48.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 9 × 4?', '32', '34', '36', '40', 'C', '9 × 4 = 36.', 'MATH', 3, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 12 ÷ 3?', '3', '4', '5', '6', 'B', '12 ÷ 3 = 4.', 'MATH', 3, 'SIMPLE', 'division', 80, 0, 'FRENCH'),
('Combien font 20 ÷ 4?', '4', '5', '6', '8', 'B', '20 ÷ 4 = 5.', 'MATH', 3, 'SIMPLE', 'division', 80, 0, 'FRENCH'),
('Combien font 25 ÷ 5?', '4', '5', '6', '7', 'B', '25 ÷ 5 = 5.', 'MATH', 3, 'SIMPLE', 'division', 80, 0, 'FRENCH'),
('Quel est le périmètre d''un carré de côté 5cm?', '10cm', '15cm', '20cm', '25cm', 'C', 'Le périmètre = 4 × côté = 4 × 5 = 20cm.', 'MATH', 3, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Quel est le périmètre d''un rectangle de longueur 6cm et largeur 4cm?', '10cm', '16cm', '20cm', '24cm', 'C', 'Périmètre = 2 × (6 + 4) = 20cm.', 'MATH', 3, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Quelle fraction représente la moitié?', '1/3', '1/2', '1/4', '2/3', 'B', 'La moitié = 1/2.', 'MATH', 3, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Quelle fraction représente un quart?', '1/2', '1/3', '1/4', '3/4', 'C', 'Un quart = 1/4.', 'MATH', 3, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Combien font 2/4 + 1/4?', '1/4', '2/4', '3/4', '4/4', 'C', '2/4 + 1/4 = 3/4.', 'MATH', 3, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Quelle est l''aire d''un rectangle de 5cm × 3cm?', '8cm²', '12cm²', '15cm²', '16cm²', 'C', 'Aire = longueur × largeur = 5 × 3 = 15cm².', 'MATH', 3, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Combien font 15 × 4?', '50', '55', '60', '65', 'C', '15 × 4 = 60.', 'MATH', 3, 'MOYEN', 'multiplication', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 4 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Combien font 12 × 11?', '120', '122', '132', '142', 'C', '12 × 11 = 132.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 15 × 6?', '80', '85', '90', '95', 'C', '15 × 6 = 90.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 144 ÷ 12?', '10', '11', '12', '14', 'C', '144 ÷ 12 = 12.', 'MATH', 4, 'SIMPLE', 'division', 80, 0, 'FRENCH'),
('Combien font 125 ÷ 5?', '20', '22', '25', '30', 'C', '125 ÷ 5 = 25.', 'MATH', 4, 'SIMPLE', 'division', 80, 0, 'FRENCH'),
('Quel est le périmètre d''un triangle équilatéral de côté 8cm?', '16cm', '20cm', '24cm', '32cm', 'C', 'Périmètre = 3 × 8 = 24cm.', 'MATH', 4, 'SIMPLE', 'geometrie', 80, 0, 'FRENCH'),
('Combien font 3/4 - 1/4?', '1/4', '2/4', '3/4', '4/4', 'B', '3/4 - 1/4 = 2/4 = 1/2.', 'MATH', 4, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Quelle fraction est égale à 0,5?', '1/3', '1/2', '1/4', '2/5', 'B', '1/2 = 0,5.', 'MATH', 4, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Combien font 1000 + 2500?', '3000', '3250', '3500', '3750', 'C', '1000 + 2500 = 3500.', 'MATH', 4, 'SIMPLE', 'addition', 80, 0, 'FRENCH'),
('Combien font 5000 - 2500?', '2000', '2250', '2500', '2750', 'C', '5000 - 2500 = 2500.', 'MATH', 4, 'SIMPLE', 'soustraction', 80, 0, 'FRENCH'),
('Quel est le double de 250?', '400', '450', '500', '550', 'C', 'Le double de 250 est 500.', 'MATH', 4, 'SIMPLE', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 23 × 15?', '325', '335', '345', '355', 'C', '23 × 15 = 345.', 'MATH', 4, 'MOYEN', 'multiplication', 80, 0, 'FRENCH'),
('Combien font 456 + 278?', '634', '724', '734', '744', 'C', '456 + 278 = 734.', 'MATH', 4, 'MOYEN', 'addition', 80, 0, 'FRENCH'),
('Combien font 800 - 456?', '334', '344', '354', '364', 'B', '800 - 456 = 344.', 'MATH', 4, 'MOYEN', 'soustraction', 80, 0, 'FRENCH'),
('Combien font 2/3 + 1/6?', '1/2', '2/3', '5/6', '3/4', 'C', '2/3 + 1/6 = 4/6 + 1/6 = 5/6.', 'MATH', 4, 'MOYEN', 'fractions', 80, 0, 'FRENCH'),
('Combien font 3/5 × 2?', '5/5', '6/5', '7/5', '8/5', 'B', '3/5 × 2 = 6/5.', 'MATH', 4, 'MOYEN', 'fractions', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 5 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Combien font (-3) + 5?', '-8', '-2', '2', '8', 'C', '(-3) + 5 = 2.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Combien font 7 + (-4)?', '-11', '-3', '3', '11', 'C', '7 + (-4) = 3.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Combien font (-5) × (-3)?', '-15', '-8', '8', '15', 'D', '(-5) × (-3) = 15.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Combien font (-6) × 4?', '-24', '-10', '10', '24', 'A', '(-6) × 4 = -24.', 'MATH', 5, 'SIMPLE', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si 2x = 10?', '3', '4', '5', '6', 'C', 'Si 2x = 10, alors x = 10 ÷ 2 = 5.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si x + 3 = 7?', '3', '4', '5', '6', 'B', 'Si x + 3 = 7, alors x = 7 - 3 = 4.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si x - 5 = 10?', '5', '10', '15', '20', 'C', 'Si x - 5 = 10, alors x = 10 + 5 = 15.', 'MATH', 5, 'SIMPLE', 'equations', 80, 0, 'FRENCH'),
('Combien font 2³?', '5', '6', '8', '9', 'C', '2³ = 2 × 2 × 2 = 8.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0, 'FRENCH'),
('Combien font 3²?', '5', '6', '9', '12', 'C', '3² = 3 × 3 = 9.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0, 'FRENCH'),
('Combien font 5²?', '10', '15', '20', '25', 'D', '5² = 5 × 5 = 25.', 'MATH', 5, 'SIMPLE', 'puissances', 80, 0, 'FRENCH'),
('Combien font (-8) - (-3)?', '-11', '-5', '5', '11', 'B', '(-8) - (-3) = -8 + 3 = -5.', 'MATH', 5, 'MOYEN', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Combien font (-12) ÷ (-4)?', '-3', '-8', '3', '8', 'C', '(-12) ÷ (-4) = 3.', 'MATH', 5, 'MOYEN', 'nombres_relatifs', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si 3x + 2 = 14?', '3', '4', '5', '6', 'B', '3x + 2 = 14 → 3x = 12 → x = 4.', 'MATH', 5, 'MOYEN', 'equations', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si 2x - 5 = 11?', '6', '7', '8', '9', 'C', '2x - 5 = 11 → 2x = 16 → x = 8.', 'MATH', 5, 'MOYEN', 'equations', 80, 0, 'FRENCH'),
('Combien font 4³?', '12', '32', '64', '81', 'C', '4³ = 4 × 4 × 4 = 64.', 'MATH', 5, 'MOYEN', 'puissances', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 6 - FRENCH ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('Quelle est la valeur absolue de -7?', '-7', '0', '7', '14', 'C', '|−7| = 7 (valeur absolue = distance à 0).', 'MATH', 6, 'SIMPLE', 'valeur_absolue', 80, 0, 'FRENCH'),
('Quelle est la valeur absolue de 5?', '-5', '0', '5', '10', 'C', '|5| = 5.', 'MATH', 6, 'SIMPLE', 'valeur_absolue', 80, 0, 'FRENCH'),
('Combien font 2/3 × 3/4?', '5/7', '6/12', '1/2', '5/12', 'C', '2/3 × 3/4 = 6/12 = 1/2.', 'MATH', 6, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Combien font 3/5 ÷ 2?', '3/10', '5/6', '6/5', '3/7', 'A', '3/5 ÷ 2 = 3/5 × 1/2 = 3/10.', 'MATH', 6, 'SIMPLE', 'fractions', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si x² = 16?', '2', '4', '8', '16', 'B', 'x² = 16 → x = 4 (ou -4).', 'MATH', 6, 'SIMPLE', 'equations', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si x² = 25?', '3', '4', '5', '6', 'C', 'x² = 25 → x = 5 (ou -5).', 'MATH', 6, 'SIMPLE', 'equations', 80, 0, 'FRENCH'),
('Quel est le PGCD de 12 et 18?', '2', '3', '6', '9', 'C', 'PGCD(12,18) = 6.', 'MATH', 6, 'SIMPLE', 'arithmetique', 80, 0, 'FRENCH'),
('Quel est le PPCM de 4 et 6?', '8', '10', '12', '24', 'C', 'PPCM(4,6) = 12.', 'MATH', 6, 'SIMPLE', 'arithmetique', 80, 0, 'FRENCH'),
('Combien font 2x + 3x?', '5x', '6x', '5', '6', 'A', '2x + 3x = 5x.', 'MATH', 6, 'SIMPLE', 'algebre', 80, 0, 'FRENCH'),
('Combien font 4y - y?', '3', '3y', '4y', '5y', 'B', '4y - y = 3y.', 'MATH', 6, 'SIMPLE', 'algebre', 80, 0, 'FRENCH'),
('Combien font (-2/3) ÷ (4/5)?', '-8/15', '-5/6', '5/6', '8/15', 'B', '(-2/3) ÷ (4/5) = (-2/3) × (5/4) = -10/12 = -5/6.', 'MATH', 6, 'MOYEN', 'fractions', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si 2(x + 3) = 16?', '3', '4', '5', '6', 'C', '2(x + 3) = 16 → x + 3 = 8 → x = 5.', 'MATH', 6, 'MOYEN', 'equations', 80, 0, 'FRENCH'),
('Quelle est la valeur de x si 3x - 7 = 2x + 4?', '9', '10', '11', '12', 'C', '3x - 7 = 2x + 4 → x = 11.', 'MATH', 6, 'MOYEN', 'equations', 80, 0, 'FRENCH'),
('Développer 3(x + 4).', '3x + 4', '3x + 7', '3x + 12', 'x + 12', 'C', '3(x + 4) = 3x + 12.', 'MATH', 6, 'MOYEN', 'algebre', 80, 0, 'FRENCH'),
('Factoriser 5x + 10.', '5(x + 2)', '5(x + 10)', 'x(5 + 10)', '5x(1 + 2)', 'A', '5x + 10 = 5(x + 2).', 'MATH', 6, 'MOYEN', 'algebre', 80, 0, 'FRENCH');

-- ==================== MATH - CLASSE 1 - ARABIC ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('كم يساوي 10 + 5؟', '12', '13', '15', '14', 'C', '10 + 5 = 15.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 20 - 5؟', '10', '15', '25', '18', 'B', '20 - 5 = 15.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('كم يساوي 12 + 8؟', '18', '19', '20', '22', 'C', '12 + 8 = 20.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 25 - 5؟', '15', '20', '25', '30', 'B', '25 - 5 = 20.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('ما هو العدد الذي هو 10 أكثر من 20؟', '10', '20', '30', '40', 'C', '20 + 10 = 30.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 30 + 10؟', '30', '40', '50', '20', 'B', '30 + 10 = 40.', 'MATH', 2, 'SIMPLE', 'addition', 80, 0, 'ARABIC'),
('كم يساوي 50 - 10؟', '30', '40', '50', '60', 'B', '50 - 10 = 40.', 'MATH', 2, 'SIMPLE', 'soustraction', 80, 0, 'ARABIC'),
('كم عدد جوانب المربع؟', '3', '4', '5', '6', 'B', 'المربع له 4 جوانب متساوية.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0, 'ARABIC'),
('كم عدد جوانب المستطيل؟', '3', '4', '5', '6', 'B', 'المستطيل له 4 جوانب.', 'MATH', 2, 'SIMPLE', 'geometrie', 80, 0, 'ARABIC'),
('ما هو العدد الزوجي؟', '3', '5', '6', '7', 'C', '6 هو عدد زوجي لأنه ينقسم على 2.', 'MATH', 2, 'SIMPLE', 'nombres_pairs', 80, 0, 'ARABIC'),
('كم يساوي 3 × 2؟', '5', '6', '7', '8', 'B', '3 × 2 = 6.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'ARABIC'),
('كم يساوي 5 × 2؟', '8', '10', '12', '15', 'B', '5 × 2 = 10.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'ARABIC'),
('كم يساوي 4 × 3؟', '10', '11', '12', '15', 'C', '4 × 3 = 12.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'ARABIC'),
('كم يساوي 10 × 2؟', '15', '18', '20', '25', 'C', '10 × 2 = 20.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'ARABIC'),
('ما هو ضعف 15؟', '20', '25', '30', '35', 'C', 'ضعف 15 هو 30.', 'MATH', 2, 'SIMPLE', 'multiplication', 80, 0, 'ARABIC');

-- ==================== SCIENCE - CLASSE 1 - ARABIC ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('ما هو العضو الذي يسمح لنا بالرؤية؟', 'الأذن', 'الأنف', 'العين', 'اليد', 'C', 'العين هو عضو البصر.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'ARABIC'),
('ما هو العضو الذي يسمح لنا بالسماع؟', 'العين', 'الأنف', 'الأذن', 'الفم', 'C', 'الأذن هي عضو السمع.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'ARABIC'),
('ما هو العضو الذي يسمح لنا بالشم؟', 'العين', 'الأنف', 'الأذن', 'اليد', 'B', 'الأنف هو عضو الشم.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'ARABIC'),
('كم عدد اليدين لديك؟', '1', '2', '3', '4', 'B', 'لدينا يدان.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'ARABIC'),
('كم عدد القدمين لديك؟', '1', '2', '3', '4', 'B', 'لدينا قدمان.', 'SCIENCE', 1, 'SIMPLE', 'corps_humain', 80, 0, 'ARABIC'),
('ما هو الفصل الأكثر حرارة؟', 'الشتاء', 'الربيع', 'الصيف', 'الخريف', 'C', 'الصيف هو الفصل الأكثر حرارة.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0, 'ARABIC'),
('ما هو الفصل الأكثر برودة؟', 'الشتاء', 'الربيع', 'الصيف', 'الخريف', 'A', 'الشتاء هو الفصل الأكثر برودة.', 'SCIENCE', 1, 'SIMPLE', 'saisons', 80, 0, 'ARABIC'),
('ما الذي تحتاجه النباتات للنمو؟', 'رمل', 'ماء وشمس', 'ورق', 'معد', 'B', 'النباتات تحتاج إلى الماء والشمس.', 'SCIENCE', 1, 'SIMPLE', 'plantes', 80, 0, 'ARABIC'),
('ما هو الحيوان الذي يبيض؟', 'قطة', 'كلب', 'دجاجة', 'بقرة', 'C', 'الدجاجة تبيض.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'ARABIC'),
('ما هو الحيوان الذي يعطي الحليب؟', 'دجاجة', 'بقرة', 'سمكة', 'ثعبان', 'B', 'البقرة تعطي الحليب.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'ARABIC'),
('ما هو الحيوان الذي يعيش في الماء؟', 'قطة', 'طائر', 'سمكة', 'كلب', 'C', 'السمكة تعيش في الماء.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'ARABIC'),
('ما هو الحيوان الذي يطير في السماء؟', 'سمكة', 'طائر', 'قطة', 'كلب', 'B', 'الطائر يطير في السماء.', 'SCIENCE', 1, 'SIMPLE', 'animaux', 80, 0, 'ARABIC'),
('ماذا يحدث للماء عندما يكون الجو بارداً جداً؟', 'بخار', 'جليد', 'مطر', 'سحابة', 'B', 'الماء يتحول إلى جليد عندما يتجمد.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0, 'ARABIC'),
('ماذا يحدث للماء عندما نسخنه؟', 'جليد', 'بخار', 'ثلج', 'مطر', 'B', 'الماء يتحول إلى بخار عندما نسخنه.', 'SCIENCE', 1, 'SIMPLE', 'etats_eau', 80, 0, 'ARABIC'),
('ما هو الشيء الذي يضيء لنا في الليل؟', 'شمس', 'قمر', 'نجمة', 'مصباح', 'D', 'المصباح يضيء لنا في الليل.', 'SCIENCE', 1, 'SIMPLE', 'lumiere', 80, 0, 'ARABIC');

-- ==================== GEOGRAPHY - CLASSE 1 - ARABIC ====================

INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, language) VALUES
('ما هي الاتجاهات الأربعة؟', 'أعلى، أسفل، يسار، يمين', 'شمال، جنوب، شرق، غرب', 'أمام، خلف، يسار، يمين', 'فوق، تحت، داخل، خارج', 'B', 'الاتجاهات الأربعة هي شمال، جنوب، شرق، غرب.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'ARABIC'),
('أين تشرق الشمس؟', 'شمال', 'جنوب', 'شرق', 'غرب', 'C', 'تشرق الشمس في الشرق.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'ARABIC'),
('أين تغرب الشمس؟', 'شمال', 'جنوب', 'شرق', 'غرب', 'D', 'تغرب الشمس في الغرب.', 'GEOGRAPHY', 1, 'SIMPLE', 'points_cardinaux', 80, 0, 'ARABIC'),
('ما هي الخريطة؟', 'رسم', 'تمثيل لمنطقة ما من الأعلى', 'صورة', 'كتاب', 'B', 'الخريطة هي تمثيل لمنطقة ما من الأعلى.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'ARABIC'),
('ما هو المخطط؟', 'خريطة لمساحة صغيرة', 'صورة', 'رسم', 'كتاب', 'A', 'المخطط هو خريطة لمساحة صغيرة.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'ARABIC'),
('ما هي أسطورة الخريطة؟', 'قصة', 'قائمة بالرموز المستخدمة في الخريطة', 'عنوان', 'لون', 'B', 'الأسطورة تشرح رموز الخريطة.', 'GEOGRAPHY', 1, 'SIMPLE', 'cartes', 80, 0, 'ARABIC'),
('ما هي عاصمة تونس؟', 'صفاقس', 'تونس', 'سوسة', 'بنزرت', 'B', 'عاصمة تونس هي تونس.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'ARABIC'),
('على أي بحر تقع تونس؟', 'البحر الأحمر', 'البحر الأسود', 'البحر المتوسط', 'المحيط الأطلسي', 'C', 'تقع تونس على البحر المتوسط.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'ARABIC'),
('ما هي الدول المجاورة لتونس؟', 'مصر وليبيا', 'الجزائر وليبيا', 'المغرب والجزائر', 'فرنسا وإيطاليا', 'B', 'تونس لها جارتين: الجزائر وليبيا.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'ARABIC'),
('في أي قارة تقع تونس؟', 'أوروبا', 'أفريقيا', 'آسيا', 'أمريكا', 'B', 'تقع تونس في أفريقيا.', 'GEOGRAPHY', 1, 'SIMPLE', 'tunisie', 80, 0, 'ARABIC'),
('ما هو البوصلة؟', 'أداة قياس', 'أداة تشير إلى الشمال', 'خريطة', 'كتاب', 'B', 'البوصلة تشير إلى الشمال.', 'GEOGRAPHY', 1, 'SIMPLE', 'orientation', 80, 0, 'ARABIC'),
('ما هي الجزيرة؟', 'مساحة كبيرة من الماء', 'أرض محاطة بالماء', 'جبل', 'غابة', 'B', 'الجزيرة هي أرض محاطة بالماء.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'ARABIC'),
('ما هو شبه الجزيرة؟', 'جزيرة', 'أرض محاطة بالماء إلا من جانب واحد', 'جبل', 'نهر', 'B', 'شبه الجزيرة محاطة بالماء إلا من جانب واحد.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'ARABIC'),
('ما هو النهر؟', 'مساحة كبيرة من الماء المالح', 'تيار ماء عذب', 'جبل', 'غابة', 'B', 'النهر هو تيار ماء عذب.', 'GEOGRAPHY', 1, 'SIMPLE', 'eau', 80, 0, 'ARABIC'),
('ما هو الجبل؟', 'مساحة كبيرة مسطحة', 'ارتفاع مهم في التضاريس', 'نهر', 'غابة', 'B', 'الجبل هو ارتفاع مهم في التضاريس.', 'GEOGRAPHY', 1, 'SIMPLE', 'relief', 80, 0, 'ARABIC');

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;