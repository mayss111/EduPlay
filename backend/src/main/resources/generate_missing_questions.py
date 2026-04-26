import re, os, json, random, string

SQL_PATH = os.path.abspath(os.path.join(os.path.dirname(__file__), 'data.sql'))

# Load existing lines
with open(SQL_PATH, 'r', encoding='utf-8') as f:
    lines = f.readlines()

pattern = re.compile(r"INSERT INTO question_bank \(.*?\) VALUES \((.+?)\);", re.IGNORECASE)

def parse_values(value_str):
    # Split on commas not within quotes
    parts = []
    current = ''
    in_quote = False
    for ch in value_str:
        if ch == "'":
            in_quote = not in_quote
            current += ch
        elif ch == ',' and not in_quote:
            parts.append(current.strip())
            current = ''
        else:
            current += ch
    parts.append(current.strip())
    return parts

counts = {}
for line in lines:
    m = pattern.search(line)
    if not m:
        continue
    vals = parse_values(m.group(1))
    # Expected order: question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language
    # Extract needed fields (positions are fixed)
    subject = vals[7].strip("' ")
    class_level = int(vals[8])
    difficulty = vals[9].strip("' ")
    language = vals[13].strip("' ")
    key = (subject, class_level, difficulty, language)
    counts[key] = counts.get(key, 0) + 1

# Ensure each combination has at least 10 entries
subjects = ['MATH','FRENCH','SCIENCE','HISTORY','GEOGRAPHY']
class_levels = range(1,7)
difficulties = ['SIMPLE','MOYEN','DIFFICILE','EXCELLENT']
languages = ['FRENCH','ARABIC']

def random_text():
    return ''.join(random.choices(string.ascii_letters + string.digits + ' ', k=30))

new_inserts = []
for sub in subjects:
    for lvl in class_levels:
        for diff in difficulties:
            for lang in languages:
                key = (sub, lvl, diff, lang)
                existing = counts.get(key, 0)
                needed = 10 - existing
                for i in range(needed):
                    qtext = f'Placeholder {sub} lvl{lvl} {diff} {lang} Q{i+1}'
                    a = 'Option A'
                    b = 'Option B'
                    c = 'Option C'
                    d = 'Option D'
                    correct = random.choice(['A','B','C','D'])
                    explanation = 'Generated placeholder question.'
                    topic = sub.lower()
                    quality = 95
                    usage = 0
                    insert = f"INSERT INTO question_bank (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language) VALUES ('{qtext}', '{a}', '{b}', '{c}', '{d}', '{correct}', '{explanation}', '{sub}', {lvl}, '{diff}', '{topic}', {quality}, {usage}, '{lang}');"
                    new_inserts.append(insert)

# Append new inserts to file
if new_inserts:
    with open(SQL_PATH, 'a', encoding='utf-8') as f:
        f.write('\n-- Auto‑generated missing questions\n')
        for ins in new_inserts:
            f.write(ins + '\n')
print(f'Added {len(new_inserts)} INSERT statements to data.sql.')
