-- ===================================================================
-- QUESTIONS ADDITIONNELLES POUR EDUPLAY
-- À exécuter manuellement sur la base de données Render
-- ===================================================================

-- MATH - NIVEAUX AVANCÉS
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('Un commerçant achète 120 chemises à 15 dinars chacune. Il en vend 80 à 25 dinars et le reste à 20 dinars. Quel est son bénéfice total ?', '600 dinars', '700 dinars', '800 dinars', '1000 dinars', 'D', 'Coût: 120x15=1800. Vente: (80x25)+(40x20)=2000+800=2800. Bénéfice: 2800-1800=1000.', 'MATH', 5, 'DIFFICILE', 'FRENCH', 85, 'problème_commercial', 0),
('Dans un triangle ABC, l angle A mesure 45° et l angle B mesure 75°. Quelle est la mesure de l angle C ?', '50°', '55°', '60°', '65°', 'C', 'Somme des angles = 180°. C = 180-45-75 = 60°.', 'MATH', 5, 'DIFFICILE', 'FRENCH', 90, 'geometrie', 0),
('Le périmètre d un rectangle est 48 cm. Sa longueur est le double de sa largeur. Quelle is son aire ?', '96 cm2', '108 cm2', '128 cm2', '144 cm2', 'C', 'P=2(L+l)=48. L=2l. Donc 6l=48, l=8, L=16. Aire=16x8=128.', 'MATH', 6, 'EXCELLENT', 'FRENCH', 90, 'geometrie_algebre', 0),
('Un nombre est tel que son triple augmenté de 7 égale son quadruple diminué de 5. Quel est ce nombre ?', '10', '12', '14', '16', 'B', '3x+7=4x-5 → 7+5=4x-3x → x=12.', 'MATH', 6, 'EXCELLENT', 'FRENCH', 88, 'equation', 0),
('Un réservoir contient 2400 litres. On utilise 3/8 le premier jour, puis 1/3 du reste le deuxième jour. Combien reste-t-il ?', '800 L', '900 L', '1000 L', '1100 L', 'C', 'Jour 1: 2400x3/8=900. Reste: 1500. Jour 2: 1500x1/3=500. Reste: 1000.', 'MATH', 5, 'DIFFICILE', 'FRENCH', 88, 'fractions', 0);

-- SCIENCE - CONTEXTE TUNISIE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('Pourquoi les dattiers du sud tunisien produisent-ils beaucoup de dattes en été ?', 'Parce qu il pleut beaucoup', 'Parce qu il fait chaud et sec', 'Parce qu il neige', 'Parce qu il y a du vent', 'B', 'Les dattiers ont besoin de chaleur et de sécheresse pour produire des dattes de qualité.', 'SCIENCE', 4, 'MOYEN', 'FRENCH', 92, 'agriculture_tunisie', 0),
('Comment les plantes du désert tunisien survivent-elles sans pluie pendant des mois ?', 'Elles stockent l eau dans leurs tiges', 'Elles n ont pas besoin d eau', 'Elles boivent la rosée', 'Elles ont de longues racines', 'A', 'Les cactus stockent l eau dans leurs tiges épaisses.', 'SCIENCE', 4, 'MOYEN', 'FRENCH', 90, 'adaptation_desert', 0),
('Pourquoi la mer Méditerranée est-elle moins salée que la mer Rouge ?', 'Parce qu elle est plus froide', 'Parce qu elle reçoit plus d eau douce', 'Parce qu elle est plus petite', 'Parce qu elle est plus profonde', 'B', 'La Méditerranée reçoit l eau douce de nombreux fleuves.', 'SCIENCE', 5, 'DIFFICILE', 'FRENCH', 85, 'geographie_science', 0);

-- HISTORY - TUNISIE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('Quand la Tunisie a-t-elle obtenu son indépendance ?', '1945', '1950', '1956', '1960', 'C', 'La Tunisie est devenue indépendante le 20 mars 1956.', 'HISTORY', 4, 'SIMPLE', 'FRENCH', 95, 'independance_tunisie', 0),
('Qui était le premier président de la République tunisienne ?', 'Habib Bourguiba', 'Ben Ali', 'Beji Caid Essebsi', 'Hédi Nouira', 'A', 'Habib Bourguiba a été le premier président de 1957 à 1987.', 'HISTORY', 4, 'SIMPLE', 'FRENCH', 95, 'bourguiba', 0),
('Quelle ville tunisienne abrite l amphithéâtre d El Jem ?', 'Tunis', 'Sfax', 'El Jem', 'Carthage', 'C', 'L amphithéâtre d El Jem est inscrit au patrimoine UNESCO.', 'HISTORY', 4, 'SIMPLE', 'FRENCH', 92, 'patrimoine_unesco', 0),
('Quelle civilisation a fondé Carthage ?', 'Les Romains', 'Les Phéniciens', 'Les Grecs', 'Les Berbères', 'B', 'Carthage a été fondée par des colons phéniciens vers 814 av. J.-C.', 'HISTORY', 4, 'SIMPLE', 'FRENCH', 90, 'carthage', 0);

-- GEOGRAPHY - TUNISIE
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('Quelle est la plus grande ville de Tunisie ?', 'Sfax', 'Tunis', 'Sousse', 'Kairouan', 'B', 'Tunis, la capitale, compte environ 2,7 millions d habitants.', 'GEOGRAPHY', 4, 'SIMPLE', 'FRENCH', 92, 'villes_tunisie', 0),
('Quel est le plus long fleuve de Tunisie ?', 'Le Medjerda', 'Le Nil', 'L Euphrate', 'Le Rhône', 'A', 'Le Medjerda, long de 460 km, est le seul fleuve permanent.', 'GEOGRAPHY', 4, 'SIMPLE', 'FRENCH', 90, 'fleuves_tunisie', 0),
('Quel désert couvre le sud de la Tunisie ?', 'Le désert du Sahara', 'Le désert de Gobi', 'Le désert d Arabie', 'Le désert du Kalahari', 'A', 'Le Sahara s étend sur le sud tunisien.', 'GEOGRAPHY', 4, 'SIMPLE', 'FRENCH', 92, 'sahara', 0),
('Quelle île tunisienne est reliée au continent par un pont ?', 'Djerba', 'Kerkennah', 'Galite', 'Zembra', 'A', 'Djerba est reliée à la terre ferme par la chaussée romaine.', 'GEOGRAPHY', 4, 'SIMPLE', 'FRENCH', 90, 'djerba', 0),
('Quel est le point culminant de la Tunisie ?', 'Jebel Chambi', 'Jebel Zaghouan', 'Jebel Ressas', 'Jebel Serj', 'A', 'Le Jebel Chambi culmine à 1544 mètres.', 'GEOGRAPHY', 4, 'SIMPLE', 'FRENCH', 88, 'montagnes_tunisie', 0);

-- FRANÇAIS - NIVEAUX AVANCÉS
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('Dans la phrase "Les enfants que j ai vus jouer", pourquoi "vus" s accorde-t-il avec "enfants" ?', 'Parce que c est un participe passé', 'Parce que le COD est placé avant avoir', 'Parce que enfants est au pluriel', 'Parce que c est une règle spéciale', 'B', 'Avec avoir, le participe passé s accorde avec le COD placé avant.', 'FRENCH', 5, 'DIFFICILE', 'FRENCH', 88, 'accord_participe_passe', 0),
('Quel est le mode du verbe dans "Il faut que tu viennes" ?', 'Indicatif', 'Subjonctif', 'Conditionnel', 'Impératif', 'B', 'Après "il faut que", on utilise toujours le subjonctif.', 'FRENCH', 5, 'DIFFICILE', 'FRENCH', 85, 'subjonctif', 0),
('Quelle figure de style est utilisée dans "Ce garçon est un lion" ?', 'Une métaphore', 'Une comparaison', 'Une hyperbole', 'Une personnification', 'A', 'C est une métaphore: comparaison directe sans mot de comparaison.', 'FRENCH', 5, 'DIFFICILE', 'FRENCH', 90, 'figures_style', 0);

-- ARABE - NIVEAUX AVANCÉS
INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, language, quality_score, topic_tag, usage_count) VALUES
('ما هو إعراب كلمة "محمد" في جملة "قرأ محمد الكتاب"؟', 'فاعل مرفوع', 'مفعول به منصوب', 'مبتدأ مرفوع', 'خبر مرفوع', 'A', 'محمد هو فاعل للفعل قرأ، وهو مرفوع.', 'ARABIC', 5, 'DIFFICILE', 'ARABIC', 88, 'نحو', 0),
('ما هو نوع الجملة "الطالب مجتهد"؟', 'جملة فعلية', 'جملة اسمية', 'جملة شرطية', 'جملة تعجبية', 'B', 'هذه جملة اسمية لأنها تبدأ باسم.', 'ARABIC', 5, 'DIFFICILE', 'ARABIC', 90, 'نحو', 0),
('ما هو جمع كلمة "عالم"؟', 'عالمون', 'علماء', 'عالمات', 'عالمين', 'B', 'جمع عالم هو علماء (جمع تكسير).', 'ARABIC', 5, 'DIFFICILE', 'ARABIC', 85, 'صرف', 0);