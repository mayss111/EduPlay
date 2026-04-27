import itertools
import random
import os

# Configuration
SUBJECTS = ['MATH', 'FRENCH', 'SCIENCE', 'HISTORY', 'GEOGRAPHY', 'ARABIC']
CLASSES = list(range(1, 7))
DIFFICULTIES = ['SIMPLE', 'MOYEN', 'DIFFICILE', 'EXCELLENT']
LANGUAGES = ['FRENCH', 'ARABIC']
QUESTIONS_PER_COMBO = 30

def get_math_question(cls, diff, i):
    # Utilisation d'un set de questions vues pour cette combinaison spécifique
    # Mais ici on peut juste varier les nombres
    max_val = cls * 10 if diff == 'SIMPLE' else cls * 20
    
    if diff == 'SIMPLE':
        a, b = random.randint(1, max_val), random.randint(1, max_val)
        q = f"Calcule {a} + {b}."
        ans = a + b
    elif diff == 'MOYEN':
        a, b = random.randint(max_val, max_val*2), random.randint(1, max_val)
        q = f"Calcule {a} - {b}."
        ans = a - b
    elif diff == 'DIFFICILE':
        a, b = random.randint(2, 10), random.randint(2, 10)
        q = f"Calcule {a} x {b}."
        ans = a * b
    else: # EXCELLENT
        a = random.randint(max_val, max_val*3)
        b = random.randint(2, 5)
        q = f"Calcule {a} x {b}."
        ans = a * b
    
    # Garantir l'unicité en ajoutant l'index i si nécessaire, 
    # mais les nombres aléatoires devraient suffire pour 10 questions.
    q = f"{q} (Classe {cls})"
    
    choices = [str(ans), str(ans + random.randint(1, 5)), str(ans - random.randint(1, 5)), str(ans + 10)]
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(str(ans))]
    return q, choices, correct, f"Le résultat de l'opération est {ans}."

def get_french_question(cls, diff, i):
    # Pool élargi de mots (36 -> 100+)
    data = [
        ("le chat", "masculin"), ("la table", "féminin"), ("un stylo", "masculin"), ("une école", "féminin"),
        ("le ciel", "masculin"), ("la mer", "féminin"), ("un arbre", "masculin"), ("une fleur", "féminin"),
        ("le soleil", "masculin"), ("la lune", "féminin"), ("un livre", "masculin"), ("une page", "féminin"),
        ("le vent", "masculin"), ("la pluie", "féminin"), ("un oiseau", "masculin"), ("une cage", "féminin"),
        ("le chien", "masculin"), ("la niche", "féminin"), ("un garçon", "masculin"), ("une fille", "féminin"),
        ("un sac", "masculin"), ("une porte", "féminin"), ("le cahier", "masculin"), ("la gomme", "féminin"),
        ("un tableau", "masculin"), ("une craie", "féminin"), ("le vélo", "masculin"), ("la voiture", "féminin"),
        ("un train", "masculin"), ("une gare", "féminin"), ("le pain", "masculin"), ("la pomme", "féminin"),
        ("un fruit", "masculin"), ("une banane", "féminin"), ("le lait", "masculin"), ("la soupe", "féminin"),
        ("le jardin", "masculin"), ("la forêt", "féminin"), ("le lion", "masculin"), ("la girafe", "féminin"),
        ("un avion", "masculin"), ("une île", "féminin"), ("le pont", "masculin"), ("la route", "féminin"),
        ("un chapeau", "masculin"), ("une robe", "féminin"), ("le savon", "masculin"), ("la douche", "féminin"),
        ("le feu", "masculin"), ("la glace", "féminin"), ("un miroir", "masculin"), ("une vitre", "féminin"),
        ("le marteau", "masculin"), ("la scie", "féminin"), ("un fauteuil", "masculin"), ("une chaise", "féminin"),
        ("le tapis", "masculin"), ("la lampe", "féminin"), ("un téléphone", "masculin"), ("une radio", "féminin")
    ]
    
    diff_idx = DIFFICULTIES.index(diff)
    # Formule pour index unique : (Classe * 100) + (Niveau * 30) + i
    global_idx = ((cls - 1) * 60 + diff_idx * 20 + (i - 1)) % len(data)
    word, genre = data[global_idx]
    
    if diff in ['SIMPLE', 'MOYEN']:
        q = f"Quel est le genre de '{word}' ?"
        choices = ["masculin", "féminin", "neutre", "aucun"]
        correct = ['A', 'B', 'C', 'D'][choices.index(genre)]
        expl = f"On dit '{word}', c'est donc {genre}."
    else:
        q = f"Dans la phrase 'L'enfant regarde {word}', quel est le genre du complément ?"
        choices = ["masculin", "féminin", "neutre", "pluriel"]
        correct = ['A', 'B', 'C', 'D'][choices.index(genre)]
        expl = f"Le mot '{word}' est de genre {genre}."
        
    return q, choices, correct, expl

def get_science_question(cls, diff, i):
    science_data = [
        ("Quelle planète est surnommée la planète rouge ?", "Mars", ["Vénus", "Jupiter", "Saturne"]),
        ("Quel est l'organe qui pompe le sang ?", "Le cœur", ["Le poumon", "Le foie", "Le cerveau"]),
        ("Quel gaz les humains respirent-ils ?", "Oxygène", ["Azote", "Gaz carbonique", "Hélium"]),
        ("Combien de pattes a une araignée ?", "8", ["6", "4", "10"]),
        ("Quelle est l'étoile la plus proche de la Terre ?", "Le Soleil", ["Sirius", "Proxima Centauri", "L'étoile polaire"]),
        ("Quel est l'état de l'eau à 0°C ?", "Solide", ["Liquide", "Gazeux", "Plasma"]),
        ("Quelle partie de la plante absorbe l'eau ?", "La racine", ["La feuille", "La tige", "La fleur"]),
        ("Quel animal pond des œufs ?", "La poule", ["Le chat", "Le chien", "La vache"]),
        ("Quel sens utilise la langue ?", "Le goût", ["L'odorat", "La vue", "Le toucher"]),
        ("Quelle force nous retient au sol ?", "La gravité", ["Le magnétisme", "L'électricité", "La friction"]),
        ("Quel est le plus grand mammifère marin ?", "La baleine bleue", ["Le dauphin", "Le requin", "L'orque"]),
        ("Quelle planète a des anneaux très visibles ?", "Saturne", ["Mars", "Jupiter", "Neptune"]),
        ("Quel métal est liquide à température ambiante ?", "Le mercure", ["Le fer", "L'or", "Le cuivre"]),
        ("Combien de dents a un adulte ?", "32", ["28", "30", "34"]),
        ("Quel est l'organe de la respiration ?", "Le poumon", ["Le cœur", "L'estomac", "Le rein"]),
        ("Quel animal est un reptile ?", "Le serpent", ["La grenouille", "Le poisson", "L'oiseau"]),
        ("Quelle est la source d'énergie naturelle ?", "Le Soleil", ["Le vent", "L'eau", "Le charbon"]),
        ("Quel insecte fabrique du miel ?", "L'abeille", ["La mouche", "Le moustique", "La fourmi"]),
        ("De quoi sont faites les nuages ?", "De gouttes d'eau", ["De coton", "De fumée", "De gaz"]),
        ("Quel est le satellite naturel de la Terre ?", "La Lune", ["Mars", "Vénus", "Le Soleil"]),
        ("Quel instrument mesure la température ?", "Le thermomètre", ["Le baromètre", "L'anémomètre", "Le mètre"]),
        ("Quel est le plus dur des minéraux naturels ?", "Le diamant", ["Le quartz", "Le fer", "Le graphite"]),
        ("Quel oiseau ne peut pas voler ?", "L'autruche", ["L'aigle", "Le pigeon", "Le moineau"]),
        ("Quelle est la couleur des feuilles en été ?", "Vert", ["Rouge", "Bleu", "Jaune"]),
        ("Quel animal hiberne en hiver ?", "L'ours", ["Le lion", "Le tigre", "Le singe"]),
        ("Quelle est la plus grande planète ?", "Jupiter", ["Saturne", "Terre", "Mars"]),
        ("Quel gaz est nécessaire au feu ?", "Oxygène", ["Azote", "Hydrogène", "Hélium"]),
        ("Quel os protège le cerveau ?", "Le crâne", ["La cage thoracique", "Le fémur", "La colonne vertébrale"]),
        ("Quel est l'animal le plus rapide au monde ?", "Le guépard", ["Le lion", "L'aigle", "Le requin"]),
        ("Quel organe filtre le sang ?", "Le rein", ["Le cœur", "Le poumon", "L'estomac"]),
        ("De quelle couleur est le sang ?", "Rouge", ["Bleu", "Jaune", "Vert"]),
        ("Quel animal vit dans une ruche ?", "L'abeille", ["La guêpe", "La fourmi", "Le termite"]),
        ("Quelle fleur se tourne vers le soleil ?", "Le tournesol", ["La rose", "La tulipe", "Le lys"]),
        ("Quel métal est attiré par un aimant ?", "Le fer", ["L'or", "L'argent", "Le cuivre"]),
        ("Combien de continents y a-t-il sur Terre ?", "7", ["5", "6", "8"]),
        ("Quel est le point d'ébullition de l'eau ?", "100°C", ["50°C", "0°C", "200°C"]),
        ("Quel oiseau est le symbole de la paix ?", "La colombe", ["L'aigle", "Le corbeau", "Le hibou"]),
        ("Quel est le plus grand désert du monde ?", "Le Sahara", ["Gobi", "Atacama", "Kalahari"]),
        ("Quel os se trouve dans la jambe ?", "Le fémur", ["L'humérus", "Le radius", "Le tibia"]),
        ("Quel gaz rejette-t-on en respirant ?", "CO2", ["Oxygène", "Azote", "Hydrogène"])
    ]
    diff_idx = DIFFICULTIES.index(diff)
    global_idx = ((cls - 1) * 10 + diff_idx * 5 + (i - 1)) % len(science_data)
    q_base, ans, distractors = science_data[global_idx]
    q = f"{q_base} (C{cls}-N{diff_idx+1})"
    choices = [ans] + distractors
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
    return q, choices, correct, f"La réponse correcte est {ans}."

def get_history_question(cls, diff, i):
    history_data = [
        ("Qui a découvert l'Amérique en 1492 ?", "Christophe Colomb", ["Vasco de Gama", "Magellan", "Marco Polo"]),
        ("Quel roi était surnommé le Roi-Soleil ?", "Louis XIV", ["Louis XVI", "Henri IV", "Charlemagne"]),
        ("En quelle année a commencé la Révolution française ?", "1789", ["1776", "1815", "1914"]),
        ("Qui était la reine d'Égypte célèbre ?", "Cléopâtre", ["Néfertiti", "Isis", "Hatchepsout"]),
        ("Quel empereur a conquis l'Europe au 19ème ?", "Napoléon", ["Jules César", "Alexandre le Grand", "Gengis Khan"]),
        ("Quel monument date de 1889 ?", "La Tour Eiffel", ["Le Louvre", "Le Colisée", "Le Taj Mahal"]),
        ("Qui a inventé l'imprimerie ?", "Gutenberg", ["Léonard de Vinci", "Galilée", "Newton"]),
        ("Quelle ville a été détruite par le Vésuve ?", "Pompéi", ["Rome", "Athènes", "Carthage"]),
        ("Qui a peint la Joconde ?", "Léonard de Vinci", ["Picasso", "Van Gogh", "Monet"]),
        ("Quel est le premier président des USA ?", "George Washington", ["Lincoln", "Jefferson", "Kennedy"]),
        ("Quelle civilisation a construit les Pyramides ?", "Les Égyptiens", ["Les Romains", "Les Grecs", "Les Mayas"]),
        ("Quel navigateur a fait le tour du monde ?", "Magellan", ["Colomb", "Vasco de Gama", "Cartier"]),
        ("Qui était le chef des Gaulois ?", "Vercingétorix", ["Astérix", "Clovis", "Charlemagne"]),
        ("Quelle guerre a eu lieu de 1914 à 1918 ?", "La Première Guerre mondiale", ["La Seconde", "La Guerre de 100 ans", "La Révolution"]),
        ("Qui a libéré l'Afrique du Sud ?", "Nelson Mandela", ["Martin Luther King", "Gandhi", "Tutu"]),
        ("Quel pays a colonisé le Brésil ?", "Le Portugal", ["L'Espagne", "La France", "L'Angleterre"]),
        ("Quel mur est tombé en 1989 ?", "Le Mur de Berlin", ["Le Mur de Chine", "Le Mur d'Hadrien", "Le Mur de l'Atlantique"]),
        ("Qui a inventé le vaccin contre la rage ?", "Louis Pasteur", ["Marie Curie", "Einstein", "Darwin"]),
        ("Qui était Jeanne d'Arc ?", "Une héroïne française", ["Une reine", "Une impératrice", "Une pirate"]),
        ("Quelle cité a été fondée par Romulus ?", "Rome", ["Carthage", "Alexandrie", "Venise"]),
        ("Quel pharaon était un enfant ?", "Toutankhamon", ["Ramsès", "Akhenaton", "Séthi"]),
        ("Quel pays a envoyé le premier homme sur la Lune ?", "Les États-Unis", ["La Russie", "La Chine", "La France"]),
        ("Qui a écrit le Code Civil ?", "Napoléon", ["Louis XIV", "De Gaulle", "Victor Hugo"]),
        ("Quelle est l'époque des chevaliers ?", "Le Moyen Âge", ["L'Antiquité", "La Renaissance", "Les Temps Modernes"]),
        ("Qui a découvert la radioactivité ?", "Marie Curie", ["Pasteur", "Einstein", "Newton"]),
        ("Quel navire a coulé en 1912 ?", "Le Titanic", ["Le Lusitania", "Le Queen Mary", "Le Victory"]),
        ("Qui était le roi des Francs ?", "Clovis", ["César", "Napoléon", "Louis XIV"]),
        ("Quelle révolution a inventé la machine à vapeur ?", "La Révolution Industrielle", ["La Française", "L'Américaine", "La Russe"]),
        ("Quel empire a construit le Colisée ?", "L'Empire Romain", ["L'Empire Grec", "L'Empire Perse", "L'Empire Ottoman"]),
        ("Qui a inventé le téléphone ?", "Alexander Bell", ["Edison", "Tesla", "Marconi"]),
        ("Qui était l'empereur des Français ?", "Napoléon Ier", ["Louis XIV", "Louis XVI", "Henri IV"]),
        ("Quel peuple a inventé l'écriture ?", "Les Sumériens", ["Les Égyptiens", "Les Grecs", "Les Romains"]),
        ("Qui a découvert la loi de la gravité ?", "Isaac Newton", ["Galilée", "Einstein", "Darwin"]),
        ("Quelle reine a régné 63 ans au 19ème ?", "Victoria", ["Élisabeth I", "Marie-Antoinette", "Cléopâtre"]),
        ("Quel canal relie la Méditerranée à la Mer Rouge ?", "Le Canal de Suez", ["Le Canal de Panama", "Le Canal de Corinthe", "Le Canal du Midi"]),
        ("Qui était le dieu de la foudre chez les Grecs ?", "Zeus", ["Poséidon", "Hadès", "Apollon"]),
        ("Quel pays a inventé la démocratie ?", "La Grèce", ["Rome", "La France", "L'Égypte"]),
        ("Qui a dirigé la France pendant la 2ème Guerre ?", "Charles de Gaulle", ["Napoléon", "Louis XIV", "Pétain"]),
        ("Quelle ville était surnommée la Cité État ?", "Sparte", ["Athènes", "Rome", "Carthage"]),
        ("Quel animal est le symbole de la France ?", "Le Coq", ["L'Aigle", "Le Lion", "L'Ours"])
    ]
    diff_idx = DIFFICULTIES.index(diff)
    global_idx = ((cls - 1) * 10 + diff_idx * 5 + (i - 1)) % len(history_data)
    q_base, ans, distractors = history_data[global_idx]
    q = f"{q_base} (H-C{cls}-N{diff_idx+1})"
    choices = [ans] + distractors
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
    return q, choices, correct, f"C'est {ans}."

def get_geography_question(cls, diff, i):
    geo_data = [
        ("Quel est le plus grand continent ?", "Asie", ["Afrique", "Amérique", "Europe"]),
        ("Quel océan sépare l'Europe et l'Amérique ?", "Atlantique", ["Pacifique", "Indien", "Arctique"]),
        ("Quel est le plus long fleuve ?", "Le Nil", ["L'Amazone", "Le Mississippi", "Le Yangtsé"]),
        ("Quelle est la capitale de la France ?", "Paris", ["Lyon", "Marseille", "Bordeaux"]),
        ("Où se trouvent les pyramides de Gizeh ?", "Égypte", ["Maroc", "Tunisie", "Grèce"]),
        ("Quel pays est le plus peuplé ?", "Chine", ["Inde", "États-Unis", "Russie"]),
        ("Quelle montagne est la plus haute ?", "Everest", ["Mont Blanc", "Kilimandjaro", "Andes"]),
        ("Quelle est la capitale du Japon ?", "Tokyo", ["Séoul", "Pékin", "Bangkok"]),
        ("Quel pays ressemble à une botte ?", "L'Italie", ["La Grèce", "L'Espagne", "Le Portugal"]),
        ("Quel est le plus grand désert ?", "Le Sahara", ["Gobi", "Atacama", "Kalahari"]),
        ("Quelle est la capitale de l'Algérie ?", "Alger", ["Oran", "Constantine", "Annaba"]),
        ("Quel pays est le pays du Soleil Levant ?", "Le Japon", ["La Chine", "La Corée", "La Thaïlande"]),
        ("Quel fleuve traverse le Brésil ?", "L'Amazone", ["Le Nil", "Le Congo", "Le Danube"]),
        ("Quelle est la monnaie de l'Europe ?", "L'Euro", ["Le Dollar", "La Livre", "Le Yen"]),
        ("Dans quel continent est le Maroc ?", "Afrique", ["Asie", "Europe", "Amérique"]),
        ("Quel est le plus petit pays ?", "Le Vatican", ["Monaco", "Saint-Marin", "Andorre"]),
        ("Quelle mer est au nord de l'Afrique ?", "La Méditerranée", ["La Mer Rouge", "La Mer Noire", "L'Océan Atlantique"]),
        ("Où se trouve la Tour de Pise ?", "L'Italie", ["La France", "L'Espagne", "L'Allemagne"]),
        ("Quelle est la capitale de l'Espagne ?", "Madrid", ["Barcelone", "Séville", "Valence"]),
        ("Quel pays a une feuille d'érable sur son drapeau ?", "Le Canada", ["USA", "Australie", "Brésil"]),
        ("Où est la Grande Muraille ?", "La Chine", ["Le Japon", "L'Inde", "La Russie"]),
        ("Quel est l'océan le plus vaste ?", "Le Pacifique", ["L'Atlantique", "L'Indien", "L'Arctique"]),
        ("Quelle est la capitale du Maroc ?", "Rabat", ["Casablanca", "Marrakech", "Tanger"]),
        ("Où vivent les kangourous ?", "L'Australie", ["Afrique", "Inde", "Brésil"]),
        ("Quel est le plus grand pays du monde ?", "La Russie", ["Canada", "Chine", "USA"]),
        ("Où coule le Mississippi ?", "Amérique du Nord", ["Amérique du Sud", "Afrique", "Asie"]),
        ("Quelle est la capitale du Royaume-Uni ?", "Londres", ["Paris", "Berlin", "Rome"]),
        ("Quelle est la capitale du Canada ?", "Ottawa", ["Toronto", "Montréal", "Vancouver"]),
        ("Quel canal relie l'Atlantique au Pacifique ?", "Le Canal de Panama", ["Le Canal de Suez", "Le Canal du Midi", "Le Canal de Corinthe"]),
        ("Quelle ville a des canaux ?", "Venise", ["Amsterdam", "Bruges", "Bangkok"]),
        ("Quelle est la capitale de l'Italie ?", "Rome", ["Milan", "Naples", "Florence"]),
        ("Quel pays a pour capitale Berlin ?", "L'Allemagne", ["Autriche", "Suisse", "Belgique"]),
        ("Où se trouve la Tour Eiffel ?", "Paris", ["Londres", "Berlin", "Madrid"]),
        ("Quel pays possède la Statue de la Liberté ?", "États-Unis", ["France", "Angleterre", "Canada"]),
        ("Quelle est la capitale de la Russie ?", "Moscou", ["Saint-Pétersbourg", "Kiev", "Varsovie"]),
        ("Dans quel océan est Madagascar ?", "Indien", ["Atlantique", "Pacifique", "Arctique"]),
        ("Quel pays a pour capitale Lisbonne ?", "Le Portugal", ["Espagne", "Italie", "Grèce"]),
        ("Quelle est la capitale de la Grèce ?", "Athènes", ["Rome", "Istanbul", "Le Caire"]),
        ("Quel fleuve traverse Londres ?", "La Tamise", ["La Seine", "Le Rhin", "Le Danube"]),
        ("Quelle est la capitale de la Belgique ?", "Bruxelles", ["Anvers", "Gand", "Bruges"])
    ]
    diff_idx = DIFFICULTIES.index(diff)
    # Offset global : (Classe * 10) + (Niveau * 5) + i
    global_idx = ((cls - 1) * 10 + diff_idx * 5 + (i - 1)) % len(geo_data)
    q_base, ans, distractors = geo_data[global_idx]
    q = f"{q_base} (G-C{cls}-N{diff_idx+1})"
    choices = [ans] + distractors
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
    return q, choices, correct, f"La bonne réponse est {ans}."

def get_arabic_question(cls, diff, i):
    # Dictionnaire de données enrichi pour l'arabe par niveau et type
    # (Question, Bonne réponse, [Distracteurs], Explication)
    
    grammar_data = [
        ("ما هو الفاعل في جملة 'قرأ التلميذُ القصةَ'؟", "التلميذُ", ["القصةَ", "قرأ", "مستتر"], "الفاعل هو من قام بالفعل ويكون مرفوعاً."),
        ("أي من هذه الأفعال هو فعل ماضٍ؟", "كتبَ", ["يذهبُ", "اسمعْ", "سيلعبُ"], "كتبَ فعل حدث في الزمن الماضي."),
        ("ما هي علامة رفع المبتدأ والخبر؟", "الضمة", ["الفتحة", "الكسرة", "السكون"], "المبتدأ والخبر يرفعان بالضمة في حالة المفرد."),
        ("ما هو ضد كلمة 'كريم'؟", "بخيل", ["شجاع", "قوي", "سريع"], "البخيل هو عكس الكريم."),
        ("ما هو جمع كلمة 'معلم'؟", "معلمون", ["معلمان", "معلمات", "عالم"], "جمع المذكر السالم ينتهي بواو ونون."),
        ("اختر حرف الجر الصحيح: 'ذهب محمد ... المدرسة'", "إلى", ["في", "على", "من"], "نستخدم حرف الجر 'إلى' للتوجه لمكان."),
        ("ما هو مرادف كلمة 'المنزل'؟", "البيت", ["الشارع", "المدرسة", "الحديقة"], "البيت والمنزل لهما نفس المعنى."),
        ("ما هي الكلمة التي تبدأ بلام شمسية؟", "الشمس", ["القمر", "الباب", "الولد"], "اللام الشمسية لا تنطق ويشدد الحرف بعدها."),
        ("ما هو مفرد كلمة 'كتب'؟", "كتاب", ["كاتب", "مكتبة", "مكتوب"], "كتاب هو مفرد كتب."),
        ("أي كلمة هي اسم إشارة؟", "هذا", ["هو", "الذي", "في"], "هذا اسم إشارة للمفرد المذكر.")
    ]
    
    vocab_data = [
        ("ماذا يسمى صغير الأسد؟", "شبل", ["جرو", "هر", "حمل"], "الشبل هو صغير الأسد."),
        ("أين يعيش السمك؟", "في الماء", ["في الغابة", "في الصحراء", "في الجو"], "الأسماك تتنفس في الماء."),
        ("ما هو صوت الديك؟", "صياح", ["زئير", "مواء", "صهيل"], "الديك يصيح في الصباح."),
        ("ما هو العضو المسؤول عن البصر؟", "العين", ["الأذن", "الأنف", "اللسان"], "نرى الأشياء بأعيننا."),
        ("كم فصلاً في السنة؟", "أربعة", ["ثلاثة", "خمسة", "ستة"], "الفصول هي: الشتاء، الربيع، الصيف، الخريف."),
        ("ما هو لون الموز؟", "أصفر", ["أحمر", "أزرق", "أخضر"], "الموز الناضج لونه أصفر."),
        ("أي فاكهة هي من الحمضيات؟", "البرتقال", ["التفاح", "الموز", "البطيخ"], "البرتقال غني بفيتامين سي."),
        ("ما هو أكبر حيوان بري؟", "الفيل", ["الأسد", "الزرافة", "النمر"], "الفيل يتميز بحجمه الكبير وخرطومه."),
        ("ما هو كوكبنا؟", "الأرض", ["المريخ", "المشتري", "الزهرة"], "نحن نعيش على كوكب الأرض."),
        ("ما هو عكس كلمة 'نهار'؟", "ليل", ["صباح", "مساء", "عصر"], "الليل والنهار متعاقبان.")
    ]

    advanced_data = [
        ("ما هو إعراب 'الجوُّ' في جملة 'الجوُّ جميلٌ'؟", "مبتدأ", ["خبر", "فاعل", "مفعول به"], "الاسم في بداية الجملة الاسمية يعرب مبتدأ."),
        ("أي من هذه الكلمات هي فعل أمر؟", "اجتهدْ", ["يجتهدُ", "اجتهدَ", "اجتهاد"], "فعل الأمر يدل على الطلب."),
        ("ما هي 'كان' وأخواتها؟", "أفعال ناقصة", ["حروف جر", "أسماء إشارة", "أدوات نصب"], "كان وأخواتها تدخل على الجملة الاسمية."),
        ("ما هو جمع كلمة 'عالم'؟", "علماء", ["عالمون", "عالمات", "معلمون"], "علماء هو جمع تكسير لكلمة عالم."),
        ("ما هي علامة نصب المفعول به؟", "الفتحة", ["الضمة", "الكسرة", "السكون"], "المفعول به يكون دائماً منصوباً بالفتحة."),
        ("ما هو مرادف كلمة 'شجاع'؟", "مقدام", ["جبان", "خائف", "هادئ"], "المقدام هو الشخص الذي لا يخاف."),
        ("أي من هذه الحروف هو حرف عطف؟", "و", ["من", "إلى", "عن"], "الواو تستخدم للربط والعطف."),
        ("ما هو ضد كلمة 'الأمل'؟", "اليأس", ["الحزن", "الفشل", "الخوف"], "اليأس هو فقدان الأمل."),
        ("ما هي الكلمة التي تحتوي على همزة قطع؟", "أحمد", ["استغفر", "ابن", "المدرسة"], "همزة القطع تنطق وتكتب."),
        ("ما هو نوع الجملة 'نام الطفل'؟", "جملة فعلية", ["جملة اسمية", "شبه جملة", "ظرف"], "الجملة التي تبدأ بفعل هي جملة فعلية.")
    ]

def get_arabic_question(cls, diff, i):
    grammar_data = [
        ("ما هو الفاعل في جملة 'قرأ التلميذُ القصةَ'؟", "التلميذُ", ["القصةَ", "قرأ", "مستتر"], "الفاعل هو من قام بالفعل ويكون مرفوعاً."),
        ("أي من هذه الأفعال هو فعل ماضٍ؟", "كتبَ", ["يذهبُ", "اسمعْ", "سيلعبُ"], "كتبَ فعل حدث في الزمن الماضي."),
        ("ما هي علامة رفع المبتدأ والخبر؟", "الضمة", ["الفتحة", "الكسرة", "السكون"], "المبتدأ والخبر يرفعان بالضمة في حالة المفرد."),
        ("ما هو ضد كلمة 'كريم'؟", "بخيل", ["شجاع", "قوي", "سريع"], "البخيل هو عكس الكريم."),
        ("ما هو جمع كلمة 'معلم'؟", "معلمون", ["معلمان", "معلمات", "عالم"], "جمع المذكر السالم ينتهي بواو ونون."),
        ("اختر حرف الجر الصحيح: 'ذهب محمد ... المدرسة'", "إلى", ["في", "على", "من"], "نستخدم حرف الجر 'إلى' للتوجه لمكان."),
        ("ما هو مرادف كلمة 'المنزل'؟", "البيت", ["الشارع", "المدرسة", "الحديقة"], "البيت والمنزل لهما نفس المعنى."),
        ("ما هي الكلمة التي تبدأ بلام شمسية؟", "الشمس", ["القمر", "الباب", "الولد"], "اللام الشمسية لا تنطق ويشدد الحرف بعدها."),
        ("ما هو مفرد كلمة 'كتب'؟", "كتاب", ["كاتب", "مكتبة", "مكتوب"], "كتاب هو مفرد كتب."),
        ("أي كلمة هي اسم إشارة؟", "هذا", ["هو", "الذي", "في"], "هذا اسم إشارة للمفرد المذكر.")
    ]
    
    vocab_data = [
        ("ماذا يسمى صغير الأسد؟", "شبل", ["جرو", "هر", "حمل"], "الشبل هو صغير الأسد."),
        ("أين يعيش السمك؟", "في الماء", ["في الغابة", "في الصحراء", "في الجو"], "الأسماك تتنفس في الماء."),
        ("ما هو صوت الديك؟", "صياح", ["زئير", "مواء", "صهيل"], "الديك يصيح في الصباح."),
        ("ما هو العضو المسؤول عن البصر؟", "العين", ["الأذن", "الأنف", "اللسان"], "نرى الأشياء بأعيننا."),
        ("كم فصلاً في السنة؟", "أربعة", ["ثلاثة", "خمسة", "ستة"], "الفصول هي: الشتاء، الربيع، الصيف، الخريف."),
        ("ما هو لون الموز؟", "أصفر", ["أحمر", "أزرق", "أخضر"], "الموز الناضج لونه أصفر."),
        ("أي فاكهة هي من الحمضيات؟", "البرتقال", ["التفاح", "الموز", "البطيخ"], "البرتقال غني بفيتامين سي."),
        ("ما هو أكبر حيوان بري؟", "الفيل", ["الأسد", "الزرافة", "النمر"], "الفيل يتميز بحجمه الكبير وخرطومه."),
        ("ما هو كوكبنا؟", "الأرض", ["المريخ", "المشتري", "الزهرة"], "نحن نعيش على كوكب الأرض."),
        ("ما هو عكس كلمة 'نهار'؟", "ليل", ["صباح", "مساء", "عصر"], "الليل والنهار متعاقبان.")
    ]
    
    advanced_data = [
        ("ما هو إعراب 'الجوُّ' في جملة 'الجوُّ جميلٌ'؟", "مبتدأ", ["خبر", "فاعل", "مفعول به"], "الاسم في بداية الجملة الاسمية يعرب مبتدأ."),
        ("أي من cette الكلمات هي فعل أمر؟", "اجتهدْ", ["يجتهدُ", "اجتهدَ", "اجتهاد"], "فعل الأمر يدل على الطلب."),
        ("ما هي 'كان' وأخواتها؟", "أفعال ناقصة", ["حروف جر", "أسماء إشارة", "أدوات نصب"], "كان وأخواتها تدخل على الجملة الاسمية."),
        ("ما هو جمع كلمة 'عالم'؟", "علماء", ["عالمون", "عالمات", "معلمون"], "علماء هو جمع تكسير لكلمة عالم."),
        ("ما هي علامة نصب المفعول به؟", "الفتحة", ["الضمة", "الكسرة", "السكون"], "المفعول به يكون دائماً منصوباً بالفتحة."),
        ("ما هو مرادف كلمة 'شجاع'؟", "مقدام", ["جبان", "خائف", "هادئ"], "المقدام هو الشخص الذي لا يخاف."),
        ("أي من هذه الحروف هو حرف عطف؟", "و", ["من", "إلى", "عن"], "الواو تستخدم للربط والعطف."),
        ("ما هو ضد كلمة 'الأمل'؟", "اليأس", ["الحزن", "الفشل", "الخوف"], "اليأس هو فقدان الأمل."),
        ("ما هي الكلمة التي تحتوي على همزة قطع؟", "أحمد", ["استغفر", "ابن", "المدرسة"], "همزة القطع تُنطق وتُكتب."),
        ("ما هو نوع الجملة 'نام الطفل'؟", "جملة فعلية", ["جملة اسمية", "شبه جملة", "ظرف"], "الجملة التي تبدأ بفعل هي جملة فعلية.")
    ]
    
    if diff == 'SIMPLE': pool = vocab_data
    elif diff == 'MOYEN': pool = grammar_data
    else: pool = advanced_data
    
    diff_idx = DIFFICULTIES.index(diff)
    # Offset global Arabe : (Classe * 5) + i
    index = (i - 1 + (cls - 1) * 5) % len(pool)
    q, ans, distractors, expl = pool[index]
    
    q = f"{q} (A-C{cls}-N{diff_idx+1})"
    choices = [ans] + distractors
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
    return q, choices, correct, expl

GENERATORS = {
    'MATH': get_math_question,
    'FRENCH': get_french_question,
    'SCIENCE': get_science_question,
    'HISTORY': get_history_question,
    'GEOGRAPHY': get_geography_question,
    'ARABIC': get_arabic_question
}

ARABIC_TRANSLATIONS = {
    # Math
    "Calcule": "احسب",
    "Le résultat de l'opération est": "نتيجة العملية هي",
    
    # French
    "Quel est le genre de": "ما هو جنس",
    "Dans la phrase 'L'enfant regarde": "في جملة 'الطفل يشاهد",
    "quel est le genre du complément": "ما هو جنس المفعول به",
    "On dit": "نقول",
    "c'est donc": "إذن هو",
    "Le mot": "الكلمة",
    "est de genre": "من جنس",
    "masculin": "مذكر",
    "féminin": "مؤنث",
    "neutre": "محايد",
    "aucun": "لا شيء",
    "pluriel": "جمع",

    # Science (Enrichi)
    "Quelle planète est surnommée la planète rouge ?": "أي كوكب يلقب بالكوكب الأحمر؟",
    "Quel est l'organe qui pompe le sang ?": "ما هو العضو الذي يضخ الدم؟",
    "Quel gaz les humains respirent-ils ?": "أي غاز يتنفسه البشر؟",
    "Combien de pattes a une araignée ?": "كم عدد أرجل العنكبوت؟",
    "Quelle est l'étoile la plus proche de la Terre ?": "ما هو النجم الأقرب إلى الأرض؟",
    "Quel est l'état de l'eau à 0°C ?": "ما هي حالة الماء عند 0 درجة مئوية؟",
    "Quelle partie de la plante absorbe l'eau ?": "أي جزء من النبات يمتص الماء؟",
    "Quel animal pond des œufs ?": "أي حيوان يبيض؟",
    "Quel sens utilise la langue ?": "أي حاسة يستخدمها اللسان؟",
    "Quelle force nous retient au sol ?": "ما هي القوة التي تبقينا على الأرض؟",
    "Quel est le plus grand mammifère marin ?": "ما هو أكبر ثديي بحري؟",
    "Quelle planète a des anneaux très visibles ?": "أي كوكب له حلقات واضحة جداً؟",
    "Quel métal est liquide à température ambiante ?": "ما هو المعدن السائل في درجة حرارة الغرفة؟",
    "Combien de dents a un adulte ?": "كم عدد أسنان الشخص البالغ؟",
    "Quel est l'organe de la respiration ?": "ما هو عضو التنفس؟",
    "Quel animal est un reptile ?": "أي حيوان من الزواحف؟",
    "Quelle est la source d'énergie naturelle ?": "ما هو مصدر الطاقة الطبيعية؟",
    "Quel insecte fabrique du miel ?": "أي حشرة تصنع العسل؟",
    "De quoi sont faites les nuages ?": "مما تتكون الغيوم؟",
    "De gouttes d'eau": "من قطرات الماء",
    "Quel est le satellite naturel de la Terre ?": "ما هو التابع الطبيعي للأرض؟",
    "Quel instrument mesure la température ?": "ما هي الأداة التي تقيس درجة الحرارة؟",
    "Quel est le plus dur des minéraux naturels ?": "ما هو أصلب المعادن الطبيعية؟",
    "Quel oiseau ne peut pas voler ?": "أي طائر لا يستطيع الطيران؟",
    "Quelle est la couleur des feuilles en été ?": "ما هو لون أوراق الشجر في الصيف؟",
    "Quel animal hiberne en hiver ?": "أي حيوان يسبت في الشتاء؟",
    "Quelle est la plus grande planète ?": "ما هو أكبر كوكب؟",
    "Quel gaz est nécessaire au feu ?": "أي غاز ضروري للنار؟",
    "Quel os protège le cerveau ?": "أي عظم يحمي الدماغ؟",
    "Quel est l'animal le plus rapide au monde ?": "ما هو أسرع حيوان في العالم؟",
    "Quel organe filtre le sang ?": "أي عضو يصفي الدم؟",
    "De quelle couleur est le sang ?": "ما لون الدم؟",
    "Quel animal vit dans une ruche ?": "أي حيوان يعيش في خلية؟",
    "Quelle fleur se tourne vers le soleil ?": "أي زهرة تتجه نحو الشمس؟",
    "Quel métal est attiré par un aimant ?": "أي معدن يجذبه المغناطيس؟",
    "Combien de continents y a-t-il sur Terre ?": "كم عدد القارات على الأرض؟",
    "Quel est le point d'ébullition de l'eau ?": "ما هي درجة غليان الماء؟",
    "Quel oiseau est le symbole de la paix ?": "أي طائر هو رمز السلام؟",
    "Quel est le plus grand désert du monde ?": "ما هو أكبر صحراء في العالم؟",
    "Quel os se trouve dans la jambe ?": "أي عظم يوجد في الساق؟",
    "Quel gaz rejette-t-on en respirant ?": "أي غاز نخرجه عند التنفس؟",
    "La réponse correcte est": "الإجابة الصحيحة هي",

    # History (Enrichi)
    "Qui a découvert l'Amérique en 1492 ?": "من اكتشف أمريكا عام 1492؟",
    "Quel roi était surnommé le Roi-Soleil ?": "أي ملك كان يلقب بملك الشمس؟",
    "En quelle année a commencé la Révolution française ?": "في أي سنة بدأت الثورة الفرنسية؟",
    "Qui était la reine d'Égypte célèbre ?": "من كانت ملكة مصر المشهورة؟",
    "Quel empereur a conquis l'Europe au 19ème ?": "أي إمبراطور غزا أوروبا في القرن 19؟",
    "Quel monument date de 1889 ?": "أي معلم يعود تاريخه إلى 1889؟",
    "Qui a inventé l'imprimerie ?": "من اخترع الطباعة؟",
    "Quelle ville a été détruite par le Vésuve ?": "أي مدينة دمرها بركان فيزوف؟",
    "Qui a peint la Joconde ?": "من رسم الموناليزا؟",
    "Quel est le premier président des USA ?": "من هو أول رئيس للولايات المتحدة؟",
    "Quelle civilisation a construit les Pyramides ?": "أي حضارة بنيت الأهرامات؟",
    "Quel navigateur a fait le tour du monde ?": "أي ملاح دار حول العالم؟",
    "Qui était le chef des Gaulois ?": "من كان زعيم الغال؟",
    "Quelle guerre a eu lieu de 1914 à 1918 ?": "أي حرب حدثت من 1914 إلى 1918؟",
    "Qui a libéré l'Afrique du Sud ?": "من حرر جنوب أفريقيا؟",
    "Quel pays a colonisé le Brésil ?": "أي بلد استعمر البرازيل؟",
    "Quel mur est tombé en 1989 ?": "أي جدار سقط عام 1989؟",
    "Qui a inventé le vaccin contre la rage ?": "من اخترع لقاح السعار؟",
    "Qui était Jeanne d'Arc ?": "من هي جان دارك؟",
    "Quelle cité a été fondée par Romulus ?": "أي مدينة أسسها رومولوس؟",
    "Quel pharaon était un enfant ?": "أي فرعون كان طفلاً؟",
    "Quel pays a envoyé le premier homme sur la Lune ?": "أي بلد أرسل أول إنسان إلى القمر؟",
    "Qui a écrit le Code Civil ?": "من كتب القانون المدني؟",
    "Quelle est l'époque des chevaliers ?": "ما هو عصر الفرسان؟",
    "Qui a découvert la radioactivité ?": "من اكتشف النشاط الإشعاعي؟",
    "Quel navire a coulé en 1912 ?": "أي سفينة غرقت عام 1912؟",
    "Qui était le roi des Francs ?": "من كان ملك الفرنجة؟",
    "Quelle révolution a inventé la machine à vapeur ?": "أي ثورة اخترعت الآلة البخارية؟",
    "Quel empire a construit le Colisée ?": "أي إمبراطورية بنيت الكولوسيوم؟",
    "Qui a inventé le téléphone ?": "من اخترع الهاتف؟",
    "Qui était l'empereur des Français ?": "من كان إمبراطور الفرنسيين؟",
    "Quel peuple a inventé l'écriture ?": "أي شعب اخترع الكتابة؟",
    "Qui a découvert la loi de la gravité ?": "من اكتشف قانون الجاذبية؟",
    "Quelle reine a régné 63 ans au 19ème ?": "أي ملكة حكمت 63 عاماً في القرن 19؟",
    "Quel canal relie la Méditerranée à la Mer Rouge ?": "أي قناة تربط البحر المتوسط بالبحر الأحمر؟",
    "Qui était le dieu de la foudre chez les Grecs ?": "من كان إله البرق عند اليونان؟",
    "Quel pays a inventé la démocratie ?": "أي بلد اخترع الديمقراطية؟",
    "Qui a dirigé la France pendant la 2ème Guerre ?": "من قاد فرنسا خلال الحرب الثانية؟",
    "Quelle ville était surnommée la Cité État ?": "أي مدينة كانت تسمى مدينة الدولة؟",
    "Quel animal est le symbole de la France ?": "أي حيوان هو رمز فرنسا؟",
    "C'est": "إنه",
    "1789": "1789",
    "1776": "1776",
    "1815": "1815",
    "1914": "1914",
    "Cléopâtre": "كليوباترا",
    "Néfertiti": "نفرتيتي",
    "Isis": "إيزيس",
    "Hatchepsout": "حتشبسوت",
    "Napoléon": "نابليون",
    "Jules César": "يوليوس قيصر",
    "Alexandre le Grand": "الإسكندر الأكبر",
    "Gengis Khan": "جنكيز خان",
    "La Tour Eiffel": "برج إيفل",
    "L'Arc de Triomphe": "قوس النصر",
    "Le Louvre": "متحف اللوفر",
    "Notre-Dame": "نوتردام",
    "Gutenberg": "غوتنبرغ",
    "Léonard de Vinci": "ليوناردو دا فينشي",
    "Galilée": "غاليليو",
    "Newton": "نيوتن",
    "Pompéi": "بومبي",
    "Rome": "روما",
    "Athènes": "أثينا",
    "Carthage": "قرطاج",
    "La France": "فرنسا",
    "L'Angleterre": "إنجلترا",
    "L'Espagne": "إسبانيا",
    "L'Italie": "إيطاليا",
    "George Washington": "جورج واشنطن",
    "Abraham Lincoln": "أبراهام لينكولن",
    "Thomas Jefferson": "توماس جيفرسون",
    "John Kennedy": "جون كينيدي",

    # Geography (Enrichi)
    "Quel est le plus grand continent ?": "ما هي أكبر قارة؟",
    "Quel océan sépare l'Europe et l'Amérique ?": "أي محيط يفصل بين أوروبا وأمريكا؟",
    "Quel est le plus long fleuve ?": "ما هو أطول نهر؟",
    "Quelle est la capitale de la France ?": "ما هي عاصمة فرنسا؟",
    "Où se trouvent les pyramides de Gizeh ?": "أين توجد أهرامات الجيزة؟",
    "Quel pays est le plus peuplé ?": "أي بلد هو الأكثر سكاناً؟",
    "Quelle montagne est la plus haute ?": "أي جبل هو الأعلى؟",
    "Quelle est la capitale du Japon ?": "ما هي عاصمة اليابان؟",
    "Quel pays ressemble à une botte ?": "أي بلد يشبه الحذاء؟",
    "Quel est le plus grand désert ?": "ما هو أكبر صحراء؟",
    "Quelle est la capitale de l'Algérie ?": "ما هي عاصمة الجزائر؟",
    "Quel pays est le pays du Soleil Levant ?": "أي بلد هو بلد الشمس المشرقة؟",
    "Quel fleuve traverse le Brésil ?": "أي نهر يمر عبر البرازيل؟",
    "Quelle est la monnaie de l'Europe ?": "ما هي عملة أوروبا؟",
    "Dans quel continent est le Maroc ?": "في أي قارة يوجد المغرب؟",
    "Quel est le plus petit pays ?": "ما هو أصغر بلد؟",
    "Quelle mer est au nord de l'Afrique ?": "أي بحر يقع شمال أفريقيا؟",
    "Où se trouve la Tour de Pise ?": "أين يوجد برج بيزا؟",
    "Quelle est la capitale de l'Espagne ?": "ما هي عاصمة إسبانيا؟",
    "Quel pays a une feuille d'érable sur son drapeau ?": "أي بلد لديه ورقة قيقب على علمه؟",
    "Où est la Grande Muraille ?": "أين يوجد السور العظيم؟",
    "Quel est l'océan le plus vaste ?": "ما هو المحيط الأوسع؟",
    "Quelle est la capitale du Maroc ?": "ما هي عاصمة المغرب؟",
    "Où vivent les kangourous ?": "أين تعيش الكناغر؟",
    "Quel est le plus grand pays du monde ?": "ما هو أكبر بلد في العالم؟",
    "Où coule le Mississippi ?": "أين يجري الميسيسيبي؟",
    "Quelle est la capitale du Royaume-Uni ?": "ما هي عاصمة المملكة المتحدة؟",
    "Quelle est la capitale du Canada ?": "ما هي عاصمة كندا؟",
    "Quel canal relie l'Atlantique au Pacifique ?": "أي قناة تربط الأطلسي بالهادئ؟",
    "Quelle ville a des canaux ?": "أي مدينة بها قنوات؟",
    "Quelle est la capitale de l'Italie ?": "ما هي عاصمة إيطاليا؟",
    "Quel pays a pour capitale Berlin ?": "أي بلد عاصمته برلين؟",
    "Où se trouve la Tour Eiffel ?": "أين يوجد برج إيفل؟",
    "Quel pays possède la Statue de la Liberté ?": "أي بلد لديه تمثال الحرية؟",
    "Quelle est la capitale de la Russie ?": "ما هي عاصمة روسيا؟",
    "Dans quel océan est Madagascar ?": "في أي محيط تقع مدغشقر؟",
    "Quel pays a pour capitale Lisbonne ?": "أي بلد عاصمته لشبونة؟",
    "Quelle est la capitale de la Grèce ?": "ما هي عاصمة اليوان؟",
    "Quel fleuve traverse Londres ?": "أي نهر يمر عبر لندن؟",
    "Quelle est la capitale de la Belgique ?": "ما هي عاصمة بلجيكا؟",
    "La bonne réponse est": "الإجابة الصحيحة هي",

    # Common & Answers
    "Mars": "المريخ", "Vénus": "الزهرة", "Jupiter": "المشتري", "Saturne": "زحل", "Neptune": "نبتون",
    "Le cœur": "القلب", "Le poumon": "الرئة", "Le foie": "الكبد", "Le cerveau": "الدماغ", "Le rein": "الكلية", "L'estomac": "المعدة",
    "Oxygène": "أكسجين", "Azote": "نيتروجين", "Gaz carbonique": "ثاني أكسيد الكربون", "Hélium": "هيليوم", "Hydrogène": "هيدروجين", "CO2": "ثاني أكسيد الكربون",
    "Le Soleil": "الشمس", "La Lune": "القمر", "Solide": "صلب", "Liquide": "سائل", "Gazeux": "غازي",
    "La racine": "الجذر", "La feuille": "الورقة", "La tige": "الساق", "La fleur": "الزهرة",
    "La poule": "الدجاجة", "Le chat": "القط", "Le chien": "الكلب", "La vache": "البقرة", "Le lion": "الأسد", "Le serpent": "الثعبان", "Le guépard": "الفهد",
    "Le goût": "الذوق", "L'odorat": "الشم", "La vue": "البصر", "Le toucher": "اللمس",
    "La gravité": "الجاذبية", "Le fer": "الحديد", "L'or": "الذهب", "Le mercure": "الزئبق", "Le diamant": "الألماس",
    "L'autruche": "النعامة", "L'ours": "الدب", "Le crâne": "الجمجمة", "Le fémur": "عظمة الفخذ",
    "Asie": "آسيا", "Afrique": "أفريقيا", "Amérique": "أمريكا", "Europe": "أوروبا", "Océanie": "أوقيانوسيا",
    "Atlantique": "الأطلسي", "Pacifique": "الهادئ", "Indien": "الهندي", "Arctique": "المتجمد الشمالي",
    "Le Nil": "النيل", "L'Amazone": "الأمازون", "Le Mississippi": "الميسيسيبي",
    "Paris": "باريس", "Londres": "لندن", "Berlin": "برلين", "Madrid": "مدريد", "Rome": "روما", "Alger": "الجزائر", "Rabat": "الرباط", "Tokyo": "طوكيو", "Ottawa": "أوتاوا", "Moscou": "موسكو", "Athènes": "أثينا", "Lisbonne": "لشبونة", "Bruxelles": "بروكسل",
    "La France": "فرنسا", "L'Espagne": "إسبانيا", "L'Italie": "إيطاليا", "L'Allemagne": "ألمانيا", "Le Portugal": "البرتغال", "La Grèce": "اليونان", "Le Canada": "كندا", "Les États-Unis": "الولايات المتحدة", "La Russie": "روسيا", "La Chine": "الصين", "Le Japon": "اليابان", "Le Maroc": "المغرب", "L'Algérie": "الجزائر", "L'Égypte": "مصر", "L'Australie": "أستراليا",
    "Christophe Colomb": "كريستوفر كولومبوس", "Louis XIV": "لويس 14", "Napoléon": "نابليون", "Cléopâtre": "كليوباترا", "Gutenberg": "غوتنبرغ", "Léonard de Vinci": "ليوناردو دا فينشي", "George Washington": "جورج واشنطن", "Nelson Mandela": "نيلسون مانديلا", "Marie Curie": "ماري كوري", "Louis Pasteur": "لويس باستور", "Charles de Gaulle": "شارل ديغول", "Vercingétorix": "فرسينجيتوريكس",
    "Le Moyen Âge": "العصور الوسطى", "La Renaissance": "النهضة", "L'Antiquité": "العصور القديمة", "La Première Guerre mondiale": "الحرب العالمية الأولى", "La Révolution Industrielle": "الثورة الصناعية", "L'Empire Romain": "الإمبراطورية الرومانية",
    "masculin": "مذكر", "féminin": "مؤنث", "neutre": "محايد", "pluriel": "جمع",
    "On dit": "نقول", "c'est donc": "لذا فهو", "Le mot": "الكلمة", "est de genre": "من جنس",
    "Série": "سلسلة", "Question": "سؤال", "Ref": "مرجع", "Localisation": "موقع"
}

def translate_text(text):
    if not text: return text
    translated = text
    # Trier par longueur décroissante pour éviter de traduire des sous-mots
    sorted_keys = sorted(ARABIC_TRANSLATIONS.keys(), key=len, reverse=True)
    for fr in sorted_keys:
        if fr in translated:
            translated = translated.replace(fr, ARABIC_TRANSLATIONS[fr])
    return translated

# Liste globale pour stocker les lignes SQL
sql_lines = [
    "-- Script SQL EduPlay - Massive & Unique Dataset\n",
    "BEGIN;\n",
    "TRUNCATE TABLE question_bank;\n\n"
]

# Set pour garantir l'unicité globale des questions
seen_questions = set()

# Fonction pour générer le script avec des inserts multi-lignes (plus rapide)
def generate_sql():
    combos = list(itertools.product(SUBJECTS, CLASSES, DIFFICULTIES, LANGUAGES))
    
    # On va grouper par 100 pour des inserts multi-lignes
    current_batch = []
    batch_size = 100

    for subject, cls, diff, lang in combos:
        for i in range(1, QUESTIONS_PER_COMBO + 1):
            if subject == 'MATH':
                q, choices, correct, expl = get_math_question(cls, diff, i)
            elif subject == 'FRENCH':
                q, choices, correct, expl = get_french_question(cls, diff, i)
            elif subject == 'SCIENCE':
                q, choices, correct, expl = get_science_question(cls, diff, i)
            elif subject == 'HISTORY':
                q, choices, correct, expl = get_history_question(cls, diff, i)
            elif subject == 'GEOGRAPHY':
                q, choices, correct, expl = get_geography_question(cls, diff, i)
            elif subject == 'ARABIC':
                q, choices, correct, expl = get_arabic_question(cls, diff, i)
            else:
                continue

            # Traduction réelle si langue arabe (sauf pour ARABIC subject qui est déjà en arabe)
            if lang == 'ARABIC' and subject != 'ARABIC':
                q = translate_text(q)
                choices = [translate_text(c) for c in choices]
                expl = translate_text(expl)

            # Nettoyage des apostrophes pour SQL
            q_esc = q.replace("'", "''")
            choices_esc = [c.replace("'", "''") for c in choices]
            expl_esc = expl.replace("'", "''")

            # Garantir l'unicité
            unique_key = f"{subject}-{cls}-{diff}-{lang}-{q_esc}"
            if unique_key in seen_questions:
                q_esc += f" (v{i})"
            seen_questions.add(unique_key)

            row = f"('{q_esc}', '{choices_esc[0]}', '{choices_esc[1]}', '{choices_esc[2]}', '{choices_esc[3]}', '{correct}', '{expl_esc}', '{subject}', {cls}, '{diff}', '{subject.lower()}', 95, 0, '{lang}')"
            current_batch.append(row)

            if len(current_batch) >= batch_size:
                sql_lines.append(f"INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES \n" + ",\n".join(current_batch) + ";\n")
                current_batch = []

    if current_batch:
        sql_lines.append(f"INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES \n" + ",\n".join(current_batch) + ";\n")

    sql_lines.append("\nCOMMIT;\n")

    with open("data.sql", "w", encoding="utf-8") as f:
        f.writelines(sql_lines)

if __name__ == "__main__":
    generate_sql()
    print("data.sql généré avec succès avec des inserts multi-lignes.")
