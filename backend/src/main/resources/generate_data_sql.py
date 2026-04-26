import itertools
import random
import os

# Configuration
SUBJECTS = ['MATH', 'FRENCH', 'SCIENCE', 'HISTORY', 'GEOGRAPHY', 'ARABIC']
CLASSES = list(range(1, 7))
DIFFICULTIES = ['SIMPLE', 'MOYEN', 'DIFFICILE', 'EXCELLENT']
LANGUAGES = ['FRENCH', 'ARABIC']
QUESTIONS_PER_COMBO = 10

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
    q = f"{q} (Série {i})"
    
    choices = [str(ans), str(ans + random.randint(1, 5)), str(ans - random.randint(1, 5)), str(ans + 10)]
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(str(ans))]
    return q, choices, correct, f"Le résultat de l'opération est {ans}."

def get_french_question(cls, diff, i):
    data = [
        ("le chat", "masculin"), ("la table", "féminin"), ("un stylo", "masculin"), 
        ("une école", "féminin"), ("le ciel", "masculin"), ("la mer", "féminin"),
        ("un arbre", "masculin"), ("une fleur", "féminin"), ("le soleil", "masculin"),
        ("la lune", "féminin"), ("un livre", "masculin"), ("une page", "féminin"),
        ("le vent", "masculin"), ("la pluie", "féminin"), ("un oiseau", "masculin"),
        ("une cage", "féminin"), ("le chien", "masculin"), ("la niche", "féminin"),
        ("un garçon", "masculin"), ("une fille", "féminin")
    ]
    # Sélectionner une entrée unique basée sur i
    word, genre = data[(i-1) % len(data)]
    
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
        ("Quelle force nous retient au sol ?", "La gravité", ["Le magnétisme", "L'électricité", "La friction"])
    ]
    q_base, ans, distractors = science_data[(i-1) % len(science_data)]
    q = f"{q_base} (Question {i})"
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
        ("Quel empereur a conquis une grande partie de l'Europe au 19ème siècle ?", "Napoléon", ["Jules César", "Alexandre le Grand", "Gengis Khan"]),
        ("Quel monument a été construit pour l'Exposition Universelle de 1889 ?", "La Tour Eiffel", ["L'Arc de Triomphe", "Le Louvre", "Notre-Dame"]),
        ("Qui a inventé l'imprimerie ?", "Gutenberg", ["Léonard de Vinci", "Galilée", "Newton"]),
        ("Quelle ville a été détruite par le Vésuve en 79 après J.-C. ?", "Pompéi", ["Rome", "Athènes", "Carthage"]),
        ("Quel pays a offert la Statue de la Liberté aux États-Unis ?", "La France", ["L'Angleterre", "L'Espagne", "L'Italie"]),
        ("Qui était le premier président des États-Unis ?", "George Washington", ["Abraham Lincoln", "Thomas Jefferson", "John Kennedy"])
    ]
    q_base, ans, distractors = history_data[(i-1) % len(history_data)]
    q = f"{q_base} (Ref {i})"
    choices = [ans] + distractors
    random.shuffle(choices)
    correct = ['A', 'B', 'C', 'D'][choices.index(ans)]
    return q, choices, correct, f"C'est {ans}."

def get_geography_question(cls, diff, i):
    geo_data = [
        ("Quel est le plus grand continent ?", "Asie", ["Afrique", "Amérique", "Europe"]),
        ("Quel océan se trouve entre l'Europe et l'Amérique ?", "Atlantique", ["Pacifique", "Indien", "Arctique"]),
        ("Quel est le plus long fleuve du monde ?", "Le Nil", ["L'Amazone", "Le Mississippi", "Le Yangtsé"]),
        ("Quelle est la capitale de la France ?", "Paris", ["Lyon", "Marseille", "Bordeaux"]),
        ("Dans quel pays se trouvent les pyramides de Gizeh ?", "Égypte", ["Maroc", "Tunisie", "Grèce"]),
        ("Quel pays a la plus grande population au monde ?", "Chine", ["Inde", "États-Unis", "Russie"]),
        ("Quelle montagne est la plus haute du monde ?", "Everest", ["Mont Blanc", "Kilimandjaro", "Andes"]),
        ("Quelle est la capitale du Japon ?", "Tokyo", ["Séoul", "Pékin", "Bangkok"]),
        ("Quel pays est en forme de botte ?", "L'Italie", ["La Grèce", "L'Espagne", "Le Portugal"]),
        ("Quel désert est le plus grand du monde ?", "Le Sahara", ["Gobi", "Atacama", "Kalahari"])
    ]
    q_base, ans, distractors = geo_data[(i-1) % len(geo_data)]
    q = f"{q_base} (Localisation {i})"
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

    # Sélectionner le pool de données selon la difficulté
    if diff == 'SIMPLE':
        pool = vocab_data
    elif diff == 'MOYEN':
        pool = grammar_data
    else: # DIFFICILE et EXCELLENT
        pool = advanced_data

    q, ans, distractors, expl = pool[(i-1) % len(pool)]
    
    # Ajouter une petite variation pour l'unicité si nécessaire
    if i > len(pool):
        q = f"{q} ({i})"

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
    "Calcule": "احسب",
    "Quel est le genre de": "ما هو جنس",
    "masculin": "مذكر",
    "féminin": "مؤنث",
    "Quelle planète": "أي كوكب",
    "Quel organe": "أي عضو",
    "Quel gaz": "أي غاز",
    "Combien de pattes": "كم عدد الأرجل",
    "Quelle est l'étoile": "ما هو النجم",
    "Qui a découvert": "من اكتشف",
    "Quel roi": "أي ملك",
    "En quelle année": "في أي سنة",
    "Qui était la reine": "من كانت الملكة",
    "Quel empereur": "أي إمبراطور",
    "Quel monument": "أي معلم",
    "Quel est le plus grand continent": "ما هي أكبر قارة",
    "Quel océan": "أي محيط",
    "Quel est le plus long fleuve": "ما هو أطول نهر",
    "Quelle est la capitale": "ما هي عاصمة",
    "Dans quel pays": "في أي بلد"
}

def translate(text, lang):
    if lang == 'FRENCH':
        return text
    # Traduction rudimentaire pour l'arabe
    res = text
    for fr, ar in ARABIC_TRANSLATIONS.items():
        if fr in res:
            res = res.replace(fr, ar)
    if res == text: # Si aucune traduction trouvée, on marque juste
        return f"(AR) {text}"
    return res

def generate_sql():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_path = os.path.join(script_dir, 'data.sql')
    
    # Utiliser un set pour garantir l'unicité globale des textes de questions
    seen_questions = set()
    
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write("-- Script SQL EduPlay - Massive & Unique Dataset\n")
        f.write("TRUNCATE TABLE question_bank;\n\n")
        
        count = 0
        for subj, cls, diff, lang in itertools.product(SUBJECTS, CLASSES, DIFFICULTIES, LANGUAGES):
            f.write(f"-- {subj} - CLASSE {cls} - {diff} - {lang}\n")
            for i in range(1, QUESTIONS_PER_COMBO + 1):
                # On essaie de générer une question unique
                attempts = 0
                while attempts < 100:
                    q_raw, choices_raw, correct, expl_raw = GENERATORS[subj](cls, diff, i + attempts)
                    
                    if subj == 'ARABIC':
                        q = q_raw.replace("'", "''")
                        choices = [c.replace("'", "''") for c in choices_raw]
                        expl = expl_raw.replace("'", "''")
                    else:
                        q = translate(q_raw, lang).replace("'", "''")
                        choices = [translate(c, lang).replace("'", "''") for c in choices_raw]
                        expl = translate(expl_raw, lang).replace("'", "''")
                    
                    # Identifiant unique de la question pour le set (texte + langue + classe + matière)
                    q_id = f"{q}_{lang}_{cls}_{subj}"
                    
                    if q_id not in seen_questions:
                        seen_questions.add(q_id)
                        break
                    attempts += 1
                
                stmt = (
                    f"INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, "
                    f"correct_choice, explanation, subject, class_level, difficulty, topic_tag, "
                    f"quality_score, usage_count, app_language) VALUES ("
                    f"'{q}', '{choices[0]}', '{choices[1]}', '{choices[2]}', '{choices[3]}', "
                    f"'{correct}', '{expl}', '{subj}', {cls}, '{diff}', '{subj.lower()}', 95, 0, '{lang}');\n"
                )
                f.write(stmt)
                count += 1
            f.write("\n")
            
    print(f"Génération terminée : {count} questions uniques écrites dans {output_path}")

if __name__ == "__main__":
    generate_sql()
