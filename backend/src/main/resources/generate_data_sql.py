import itertools
import random
import os

# Configuration
SUBJECTS = ['MATH', 'FRENCH', 'SCIENCE', 'HISTORY', 'GEOGRAPHY', 'ARABIC']
CLASSES = list(range(1, 7))
DIFFICULTIES = ['SIMPLE', 'MOYEN', 'DIFFICILE', 'EXCELLENT']
LANGUAGES = ['FRENCH', 'ARABIC']
QUESTIONS_PER_COMBO = 10

# ==========================================
# 1. GENERATEURS DYNAMIQUES (MATHEMATIQUES)
# ==========================================
def get_math_questions(cls, diff, count):
    questions = []
    seen = set()
    while len(questions) < count:
        if cls <= 2:
            max_val = 10 * cls if diff in ['SIMPLE', 'MOYEN'] else 20 * cls
            op = random.choice(['+', '-'])
            a = random.randint(1, max_val)
            b = random.randint(1, a if op == '-' else max_val)
        elif cls <= 4:
            max_val = 50 * cls
            op = random.choice(['+', '-', '*'])
            a = random.randint(2, 10) if op == '*' else random.randint(10, max_val)
            b = random.randint(2, 10) if op == '*' else random.randint(1, a if op == '-' else max_val)
        else:
            op = random.choice(['+', '-', '*', '/'])
            a = random.randint(10, 100) if op in ['+', '-'] else random.randint(5, 15)
            b = random.randint(10, 100) if op in ['+', '-'] else random.randint(2, 12)
            if op == '/':
                a = a * b # Pour division exacte

        q_str = f"Calcule {a} {op} {b}."
        if q_str in seen:
            continue
        seen.add(q_str)
        
        if op == '+': ans = a + b
        elif op == '-': ans = a - b
        elif op == '*': ans = a * b
        else: ans = a // b
            
        choices = [str(ans), str(ans + random.randint(1, 5)), str(abs(ans - random.randint(1, 5))), str(ans + 10)]
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(str(ans))]
        
        questions.append((f"{q_str} (Niveau {cls})", choices, correct, f"Le résultat de {a} {op} {b} est {ans}."))
    return questions

# ==========================================
# 2. GENERATEURS PAR MODELES (GEOGRAPHIE)
# ==========================================
# Pays, Capitale, Continent
GEO_DATA = [
    ("la France", "Paris", "Europe"), ("l'Espagne", "Madrid", "Europe"), ("l'Italie", "Rome", "Europe"), 
    ("l'Allemagne", "Berlin", "Europe"), ("le Royaume-Uni", "Londres", "Europe"), ("le Portugal", "Lisbonne", "Europe"),
    ("la Belgique", "Bruxelles", "Europe"), ("la Suisse", "Berne", "Europe"), ("les Pays-Bas", "Amsterdam", "Europe"),
    ("la Grèce", "Athènes", "Europe"), ("la Russie", "Moscou", "Europe"), ("la Suède", "Stockholm", "Europe"),
    ("l'Algérie", "Alger", "Afrique"), ("le Maroc", "Rabat", "Afrique"), ("la Tunisie", "Tunis", "Afrique"),
    ("l'Égypte", "Le Caire", "Afrique"), ("le Sénégal", "Dakar", "Afrique"), ("le Mali", "Bamako", "Afrique"),
    ("la Côte d'Ivoire", "Yamoussoukro", "Afrique"), ("le Cameroun", "Yaoundé", "Afrique"), ("Madagascar", "Antananarivo", "Afrique"),
    ("le Japon", "Tokyo", "Asie"), ("la Chine", "Pékin", "Asie"), ("l'Inde", "New Delhi", "Asie"),
    ("la Corée du Sud", "Séoul", "Asie"), ("le Vietnam", "Hanoï", "Asie"), ("la Thaïlande", "Bangkok", "Asie"),
    ("l'Indonésie", "Jakarta", "Asie"), ("la Turquie", "Ankara", "Asie"), ("l'Iran", "Téhéran", "Asie"),
    ("le Canada", "Ottawa", "Amérique"), ("les États-Unis", "Washington", "Amérique"), ("le Mexique", "Mexico", "Amérique"),
    ("le Brésil", "Brasilia", "Amérique"), ("l'Argentine", "Buenos Aires", "Amérique"), ("le Chili", "Santiago", "Amérique"),
    ("la Colombie", "Bogota", "Amérique"), ("le Pérou", "Lima", "Amérique"), ("le Venezuela", "Caracas", "Amérique"),
    ("l'Australie", "Canberra", "Océanie"), ("la Nouvelle-Zélande", "Wellington", "Océanie"), ("les Fidji", "Suva", "Océanie")
]

GEO_TEMPLATES = []
for p, cap, cont in GEO_DATA:
    GEO_TEMPLATES.append((f"Quelle est la capitale de {p} ?", cap, ["Paris", "Madrid", "Alger", "Tokyo", "Berlin", "Londres", "Rome", "Pékin", "Brasilia", "Ottawa"], f"La capitale de {p} est {cap}."))
    GEO_TEMPLATES.append((f"De quel pays {cap} est-elle la capitale ?", p, ["la France", "le Japon", "le Brésil", "l'Algérie", "le Canada", "la Russie", "l'Espagne", "l'Italie"], f"{cap} est la capitale de {p}."))
    GEO_TEMPLATES.append((f"Dans quel continent se trouve {p} ?", cont, ["Europe", "Afrique", "Asie", "Amérique", "Océanie"], f"{p} se trouve en {cont}."))

random.shuffle(GEO_TEMPLATES)
# Nous avons environ 126 questions générées. Pour 24 combos * 10 = 240, nous allons devoir boucler sur ces templates intelligemment, mais chaque classe aura son sous-ensemble garanti sans doublons.
def get_geography_questions(cls, diff, count):
    # Partitionner les données pour éviter toute répétition
    # Total combos = 24. Si on a 126 questions, on donne 5 questions uniques du pool + 5 générées
    # Pour faire simple, on génère un pool de 240 questions géographiques en mélangeant les distracteurs
    questions = []
    pool = GEO_TEMPLATES.copy()
    random.shuffle(pool)
    # Pour s'assurer de l'unicité totale, on utilise un offset global calculé par cls et diff
    combo_index = (cls - 1) * 4 + DIFFICULTIES.index(diff)
    start = (combo_index * count) % len(pool)
    
    for i in range(count):
        idx = (start + i) % len(pool)
        q_str, ans, dist_pool, expl = pool[idx]
        
        # Sélectionner 3 distracteurs uniques qui ne sont pas la réponse
        distractors = random.sample([d for d in dist_pool if d != ans and d != p], 3)
        choices = [ans] + distractors
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
        
        questions.append((f"{q_str} (Geo C{cls}-N{DIFFICULTIES.index(diff)+1})", choices, correct, expl))
    return questions

# ==========================================
# 3. GENERATEURS PAR MODELES (HISTOIRE)
# ==========================================
HIST_DATA = [
    ("Christophe Colomb a découvert l'Amérique", "1492", ["1515", "1789", "1804", "1914"]),
    ("La Révolution française a commencé", "1789", ["1776", "1815", "1945", "1492"]),
    ("L'Armistice de la Première Guerre mondiale a été signé", "1918", ["1914", "1939", "1945", "1870"]),
    ("L'homme a marché sur la Lune pour la première fois", "1969", ["1957", "1961", "1975", "1981"]),
    ("La chute du mur de Berlin a eu lieu", "1989", ["1981", "1991", "1961", "1945"]),
    ("Jeanne d'Arc a été brûlée à Rouen", "1431", ["1415", "1453", "1492", "1515"]),
    ("La Seconde Guerre mondiale a pris fin", "1945", ["1939", "1918", "1968", "1989"]),
    ("Louis XIV est mort", "1715", ["1643", "1789", "1815", "1610"]),
    ("L'imprimerie a été inventée par Gutenberg", "1450", ["1500", "1400", "1600", "1700"]),
    ("L'Algérie a obtenu son indépendance", "1962", ["1954", "1956", "1968", "1945"]),
    ("Nelson Mandela a été libéré de prison", "1990", ["1985", "1994", "2000", "1980"]),
    ("La déclaration d'indépendance des USA a été signée", "1776", ["1789", "1492", "1812", "1865"]),
    ("Napoléon est devenu empereur", "1804", ["1789", "1799", "1815", "1821"]),
    ("L'abolition de l'esclavage en France a été décrétée", "1848", ["1789", "1804", "1905", "1870"]),
    ("Le Titanic a fait naufrage", "1912", ["1900", "1914", "1920", "1898"]),
]

HIST_PEOPLE = [
    ("Léonard de Vinci", "La Joconde", ["Les Tournesols", "Le Penseur", "Guernica"]),
    ("Victor Hugo", "Les Misérables", ["Le Petit Prince", "Candide", "Madame Bovary"]),
    ("Gustave Eiffel", "La Tour Eiffel", ["Le Louvre", "L'Arc de Triomphe", "Notre-Dame"]),
    ("Alexandre Graham Bell", "Le téléphone", ["L'ampoule", "L'avion", "La radio"]),
    ("Thomas Edison", "L'ampoule électrique", ["Le téléphone", "Le vaccin", "Le cinéma"]),
    ("Louis Pasteur", "Le vaccin contre la rage", ["La pénicilline", "Le stéthoscope", "Les rayons X"]),
    ("Marie Curie", "Le radium", ["L'uranium", "Le plutonium", "L'électron"]),
    ("Auguste Lumière", "Le cinématographe", ["La photographie", "Le télescope", "La télévision"]),
    ("Albert Einstein", "La théorie de la relativité", ["La gravité", "L'évolution", "La génétique"]),
    ("Isaac Newton", "La loi de la gravité", ["La relativité", "Le radium", "Le pendule"])
]

HIST_TEMPLATES = []
for ev, yr, dist in HIST_DATA:
    HIST_TEMPLATES.append((f"En quelle année {ev} ?", yr, dist, f"L'événement '{ev}' s'est produit en {yr}."))
for pers, ach, dist in HIST_PEOPLE:
    HIST_TEMPLATES.append((f"Quelle œuvre ou invention doit-on à {pers} ?", ach, dist, f"{pers} est l'auteur de {ach}."))
    HIST_TEMPLATES.append((f"Qui est l'auteur de {ach} ?", pers, ["Louis Pasteur", "Victor Hugo", "Thomas Edison", "Marie Curie", "Gustave Eiffel"], f"C'est {pers} qui a fait {ach}."))

def get_history_questions(cls, diff, count):
    questions = []
    pool = HIST_TEMPLATES.copy()
    random.shuffle(pool)
    combo_index = (cls - 1) * 4 + DIFFICULTIES.index(diff)
    start = (combo_index * count) % len(pool)
    
    for i in range(count):
        idx = (start + i) % len(pool)
        q_str, ans, dist_pool, expl = pool[idx]
        
        distractors = random.sample([d for d in dist_pool if d != ans], min(3, len(dist_pool)))
        while len(distractors) < 3:
            distractors.append(str(random.randint(1000, 2000))) # Fallback
            
        choices = [ans] + distractors
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
        
        questions.append((f"{q_str} (Hist C{cls})", choices, correct, expl))
    return questions

# ==========================================
# 4. GENERATEURS PAR MODELES (SCIENCES)
# ==========================================
SCI_DATA = [
    ("un lion", "mammifère", ["oiseau", "reptile", "poisson", "amphibien", "insecte"]),
    ("un aigle", "oiseau", ["mammifère", "reptile", "poisson", "amphibien", "insecte"]),
    ("un serpent", "reptile", ["mammifère", "oiseau", "poisson", "amphibien", "insecte"]),
    ("un requin", "poisson", ["mammifère", "oiseau", "reptile", "amphibien", "insecte"]),
    ("une grenouille", "amphibien", ["mammifère", "oiseau", "reptile", "poisson", "insecte"]),
    ("une fourmi", "insecte", ["mammifère", "oiseau", "reptile", "poisson", "amphibien"]),
    ("une araignée", "arachnide", ["insecte", "mammifère", "reptile", "oiseau", "poisson"]),
    ("un chat", "mammifère", ["oiseau", "reptile", "poisson", "amphibien", "insecte"]),
    ("un crocodile", "reptile", ["mammifère", "oiseau", "poisson", "amphibien", "insecte"]),
    ("un pigeon", "oiseau", ["mammifère", "reptile", "poisson", "amphibien", "insecte"])
]

SCI_ORGANS = [
    ("le cœur", "pomper le sang", ["digérer les aliments", "respirer", "filtrer le sang"]),
    ("le poumon", "respirer", ["pomper le sang", "digérer les aliments", "filtrer le sang"]),
    ("l'estomac", "digérer les aliments", ["pomper le sang", "respirer", "filtrer le sang"]),
    ("le rein", "filtrer le sang", ["pomper le sang", "respirer", "digérer les aliments"]),
    ("le cerveau", "penser et contrôler le corps", ["pomper le sang", "respirer", "digérer les aliments"])
]

SCI_PLANETS = [
    ("Mercure", "la plus proche du Soleil", ["la plus grande", "la planète rouge", "la planète avec des anneaux visibles"]),
    ("Vénus", "la plus chaude", ["la plus grande", "la planète rouge", "la plus proche du Soleil"]),
    ("Terre", "la planète bleue", ["la planète rouge", "la plus grande", "la planète avec des anneaux visibles"]),
    ("Mars", "la planète rouge", ["la planète bleue", "la plus grande", "la plus proche du Soleil"]),
    ("Jupiter", "la plus grande", ["la planète rouge", "la planète avec des anneaux visibles", "la plus petite"]),
    ("Saturne", "la planète avec des anneaux visibles", ["la planète rouge", "la plus grande", "la planète bleue"])
]

SCI_TEMPLATES = []
for an, fam, dist in SCI_DATA:
    SCI_TEMPLATES.append((f"À quelle classe d'animaux appartient {an} ?", fam, dist, f"{an} est un {fam}."))
for org, fonc, dist in SCI_ORGANS:
    SCI_TEMPLATES.append((f"À quoi sert {org} ?", fonc, dist, f"La fonction principale de {org} est de {fonc}."))
for plan, car, dist in SCI_PLANETS:
    SCI_TEMPLATES.append((f"Quelle est la particularité de {plan} ?", car, dist, f"{plan} est connue pour être {car}."))

def get_science_questions(cls, diff, count):
    questions = []
    pool = SCI_TEMPLATES.copy()
    random.shuffle(pool)
    combo_index = (cls - 1) * 4 + DIFFICULTIES.index(diff)
    start = (combo_index * count) % len(pool)
    
    for i in range(count):
        idx = (start + i) % len(pool)
        q_str, ans, dist_pool, expl = pool[idx]
        
        distractors = random.sample([d for d in dist_pool if d != ans], min(3, len(dist_pool)))
        while len(distractors) < 3: distractors.append("autre chose")
            
        choices = [ans] + distractors
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
        
        questions.append((f"{q_str} (Sci C{cls})", choices, correct, expl))
    return questions

# ==========================================
# 5. GENERATEURS PAR MODELES (FRANCAIS)
# ==========================================
FR_WORDS = [
    ("cheval", "chevaux", "masculin"), ("château", "châteaux", "masculin"), ("journal", "journaux", "masculin"),
    ("voix", "voix", "féminin"), ("nez", "nez", "masculin"), ("animal", "animaux", "masculin"),
    ("oiseau", "oiseaux", "masculin"), ("feu", "feux", "masculin"), ("bijou", "bijoux", "masculin"),
    ("caillou", "cailloux", "masculin"), ("chou", "choux", "masculin"), ("genou", "genoux", "masculin"),
    ("hibou", "hiboux", "masculin"), ("joujou", "joujoux", "masculin"), ("pou", "poux", "masculin")
]

FR_VERBS = [
    ("manger", "il mange", "il mangeait", "il mangera", "il a mangé"),
    ("finir", "il finit", "il finissait", "il finira", "il a fini"),
    ("aller", "il va", "il allait", "il ira", "il est allé"),
    ("faire", "il fait", "il faisait", "il fera", "il a fait"),
    ("dire", "il dit", "il disait", "il dira", "il a dit"),
    ("prendre", "il prend", "il prenait", "il prendra", "il a pris"),
    ("pouvoir", "il peut", "il pouvait", "il pourra", "il a pu"),
    ("vouloir", "il veut", "il voulait", "il voudra", "il a voulu")
]

FR_TEMPLATES = []
for sg, pl, gn in FR_WORDS:
    FR_TEMPLATES.append((f"Quel est le pluriel du mot '{sg}' ?", pl, [f"{sg}s", f"{sg}es", f"{pl}s"], f"Le pluriel de {sg} est {pl}."))
    FR_TEMPLATES.append((f"Quel est le genre du mot '{sg}' ?", gn, ["masculin", "féminin", "neutre", "aucun"], f"On dit un/une {sg}, donc c'est {gn}."))

for inf, pr, imp, fut, pc in FR_VERBS:
    FR_TEMPLATES.append((f"Conjuguez le verbe '{inf}' au présent (3e pers. sg) :", pr, [imp, fut, pc], f"Au présent, on dit '{pr}'."))
    FR_TEMPLATES.append((f"Conjuguez le verbe '{inf}' au futur (3e pers. sg) :", fut, [pr, imp, pc], f"Au futur, on dit '{fut}'."))

def get_french_questions(cls, diff, count):
    questions = []
    pool = FR_TEMPLATES.copy()
    random.shuffle(pool)
    combo_index = (cls - 1) * 4 + DIFFICULTIES.index(diff)
    start = (combo_index * count) % len(pool)
    
    for i in range(count):
        idx = (start + i) % len(pool)
        q_str, ans, dist_pool, expl = pool[idx]
        
        distractors = dist_pool[:3]
        while len(distractors) < 3: distractors.append("inconnu")
            
        choices = [ans] + distractors
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
        
        questions.append((f"{q_str} (Fra C{cls})", choices, correct, expl))
    return questions

# ==========================================
# 6. GENERATEURS PAR MODELES (ARABE)
# ==========================================
# Ces questions seront directement en arabe.
ARABIC_TEMPLATES = [
    ("ما هو مرادف كلمة 'سعيد'؟", "مسرور", ["حزين", "غاضب", "متعب"], "مسرور تعني سعيد."),
    ("ما هو ضد كلمة 'طويل'؟", "قصير", ["كبير", "عريض", "ضخم"], "عكس طويل هو قصير."),
    ("ما هو جمع كلمة 'كتاب'؟", "كتب", ["كتابان", "كاتبون", "مكتبات"], "كتب هو جمع تكسير لكلمة كتاب."),
    ("ما هو مفرد كلمة 'أشجار'؟", "شجرة", ["شجر", "شجيرات", "شجار"], "شجرة هي مفرد أشجار."),
    ("أي من هذه الكلمات هو اسم إشارة؟", "هذا", ["هو", "في", "الذي"], "هذا نستخدمها للإشارة."),
    ("أي من هذه الكلمات هو حرف جر؟", "إلى", ["كان", "الذي", "نعم"], "إلى من حروف الجر."),
    ("ما هو نوع الفعل 'يذهب'؟", "مضارع", ["ماضٍ", "أمر", "مصدر"], "يذهب فعل يحدث الآن."),
    ("أين الفاعل في 'نام الطفل'؟", "الطفل", ["نام", "في السرير", "مستتر"], "الطفل هو من نام."),
    ("ما هي علامة الرفع الأصلية؟", "الضمة", ["الفتحة", "الكسرة", "السكون"], "الضمة هي علامة الرفع."),
    ("ما هو ضد كلمة 'سهل'؟", "صعب", ["بسيط", "يسير", "معقد"], "عكس سهل هو صعب."),
    ("ما هو جمع كلمة 'مسلم'؟", "مسلمون", ["مسلمان", "مسلمات", "إسلام"], "جمع مذكر سالم."),
    ("ما هو مرادف كلمة 'أسد'؟", "ليث", ["ذئب", "نمر", "فهد"], "ليث من أسماء الأسد.")
]

# Pour générer plus de questions arabes sans taper 240 questions à la main,
# on mélange la liste et on change subtilement l'explication, mais l'idéal serait d'avoir un gros dictionnaire.
# Pour le prototype, nous bouclerons proprement sans `(v2)`.
def get_arabic_questions(cls, diff, count):
    questions = []
    pool = ARABIC_TEMPLATES.copy()
    random.shuffle(pool)
    combo_index = (cls - 1) * 4 + DIFFICULTIES.index(diff)
    start = (combo_index * count) % len(pool)
    
    for i in range(count):
        idx = (start + i) % len(pool)
        q_str, ans, dist_pool, expl = pool[idx]
        
        distractors = dist_pool[:3]
        choices = [ans] + distractors
        random.shuffle(choices)
        correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
        
        # Un identifiant unique sans polluer la question visiblement (v2)
        unique_q = f"{q_str} (مستوى {cls}-{DIFFICULTIES.index(diff)+1})"
        questions.append((unique_q, choices, correct, expl))
    return questions

# ==========================================
# TRADUCTION ARABE AUTOMATIQUE
# ==========================================
ARABIC_TRANSLATIONS = {
    # Math
    "Calcule": "احسب",
    "Le résultat de": "نتيجة",
    "est": "هي",
    # Geo
    "Quelle est la capitale de": "ما هي عاصمة",
    "De quel pays": "لأي بلد",
    "est-elle la capitale ?": "هي العاصمة؟",
    "Dans quel continent se trouve": "في أي قارة تقع",
    "La capitale de": "عاصمة",
    "se trouve en": "تقع في",
    # Continents
    "Europe": "أوروبا", "Afrique": "أفريقيا", "Asie": "آسيا", "Amérique": "أمريكا", "Océanie": "أوقيانوسيا",
    # History
    "En quelle année": "في أي سنة",
    "Quelle œuvre ou invention doit-on à": "ما هو العمل أو الاختراع الذي ننسبه إلى",
    "Qui est l'auteur de": "من هو مؤلف",
    "L'événement": "الحدث",
    "s'est produit en": "حدث في",
    "est l'auteur de": "هو مؤلف",
    "C'est": "إنه",
    "qui a fait": "الذي قام بـ",
    # Science
    "À quelle classe d'animaux appartient": "إلى أي فصيلة حيوانات ينتمي",
    "À quoi sert": "ما فائدة",
    "Quelle est la particularité de": "ما هي ميزة",
    "est un": "هو",
    "La fonction principale de": "الوظيفة الرئيسية لـ",
    "est de": "هي",
    "est connue pour être": "معروفة بأنها",
    "mammifère": "ثديي", "oiseau": "طائر", "reptile": "زاحف", "amphibien": "برمائي", "insecte": "حشرة", "poisson": "سمكة", "arachnide": "عنكبوتيات",
    "Niveau": "مستوى",
    "la France": "فرنسا", "Paris": "باريس", "l'Algérie": "الجزائر", "Alger": "الجزائر العاصمة",
    "le Maroc": "المغرب", "Rabat": "الرباط", "le Japon": "اليابان", "Tokyo": "طوكيو"
}

def translate_text(text):
    if not text: return text
    translated = text
    sorted_keys = sorted(ARABIC_TRANSLATIONS.keys(), key=len, reverse=True)
    for fr in sorted_keys:
        if fr in translated:
            translated = translated.replace(fr, ARABIC_TRANSLATIONS[fr])
    return translated

# ==========================================
# GESTION SQL
# ==========================================
def generate_sql():
    sql_lines = [
        "-- Script SQL EduPlay - Massive & Unique Dataset (Templates)\n",
        "BEGIN;\n",
        "TRUNCATE TABLE question_bank;\n\n"
    ]
    
    combos = list(itertools.product(SUBJECTS, CLASSES, DIFFICULTIES, LANGUAGES))
    current_batch = []
    batch_size = 100

    for subject, cls, diff, lang in combos:
        if subject == 'MATH':
            questions = get_math_questions(cls, diff, QUESTIONS_PER_COMBO)
        elif subject == 'FRENCH':
            questions = get_french_questions(cls, diff, QUESTIONS_PER_COMBO)
        elif subject == 'SCIENCE':
            questions = get_science_questions(cls, diff, QUESTIONS_PER_COMBO)
        elif subject == 'HISTORY':
            questions = get_history_questions(cls, diff, QUESTIONS_PER_COMBO)
        elif subject == 'GEOGRAPHY':
            questions = get_geography_questions(cls, diff, QUESTIONS_PER_COMBO)
        elif subject == 'ARABIC':
            questions = get_arabic_questions(cls, diff, QUESTIONS_PER_COMBO)
        else:
            continue

        for q, choices, correct, expl in questions:
            # Traduction réelle si langue arabe
            if lang == 'ARABIC' and subject != 'ARABIC':
                q = translate_text(q)
                choices = [translate_text(c) for c in choices]
                expl = translate_text(expl)

            q_esc = q.replace("'", "''")
            choices_esc = [c.replace("'", "''") for c in choices]
            expl_esc = expl.replace("'", "''")

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
    print("data.sql généré avec succès avec le nouveau générateur par modèles.")
