
import re
import os
from collections import Counter

def analyze_questions(file_path):
    if not os.path.exists(file_path):
        print(f"File {file_path} not found.")
        return

    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Regex to match INSERT statements and extract fields
    # Values: (question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, explanation, subject, class_level, difficulty, topic_tag, quality_score, usage_count, app_language)
    pattern = re.compile(r"INSERT INTO question_bank .*? VALUES\s*\((.*?)\);", re.IGNORECASE | re.DOTALL)
    matches = pattern.findall(content)

    stats = {}
    duplicates = Counter()
    total_count = 0

    def parse_values(val_str):
        parts = []
        current = []
        in_string = False
        escaped = False
        for char in val_str:
            if char == "'" and not escaped:
                in_string = not in_string
                current.append(char)
            elif char == "\\" and not escaped:
                escaped = True
                current.append(char)
            elif char == "," and not in_string:
                parts.append("".join(current).strip())
                current = []
            else:
                escaped = False
                current.append(char)
        parts.append("".join(current).strip())
        return parts

    for match in matches:
        # Handle multi-line values if any, though our generator uses single lines mostly
        # Actually, our generator might use multi-row inserts: VALUES (...), (...);
        # Let's adjust to find all rows
        rows = re.findall(r"\((.*?)\)(?:,|$)", match, re.DOTALL)
        for row in rows:
            total_count += 1
            vals = parse_values(row)
            if len(vals) < 14:
                continue
            
            q_text = vals[0].strip("'")
            subject = vals[7].strip("'")
            level = vals[8]
            difficulty = vals[9].strip("'")
            lang = vals[13].strip("'")

            key = (subject, level, difficulty, lang)
            if key not in stats:
                stats[key] = {"count": 0, "texts": set()}
            
            stats[key]["count"] += 1
            if q_text in stats[key]["texts"]:
                duplicates[q_text] += 1
            else:
                stats[key]["texts"].add(q_text)

    print(f"--- Analyse Globale ---")
    print(f"Nombre total de questions : {total_count}")
    print(f"Nombre de doublons exacts : {sum(duplicates.values())}")
    
    print(f"\n--- Répartition par Catégorie (Sujet, Niveau, Difficulté, Langue) ---")
    sorted_keys = sorted(stats.keys())
    for key in sorted_keys:
        print(f"{key}: {stats[key]['count']} questions")

    if duplicates:
        print(f"\n--- Top 5 des questions répétées ---")
        for q, count in duplicates.most_common(5):
            print(f"'{q}': {count} répétitions")

if __name__ == "__main__":
    analyze_questions('c:/Users/user/Desktop/game/EduPlay/backend/src/main/resources/data.sql')
