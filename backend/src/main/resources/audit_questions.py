import re
from collections import Counter
import os

def audit_data_sql(file_path):
    if not os.path.exists(file_path):
        print(f"Erreur : Le fichier {file_path} n'existe pas.")
        return

    # Regex pour extraire les valeurs du INSERT INTO
    # On cherche les valeurs entre parenthèses après VALUES
    # On gère les apostrophes doublées ('' -> ')
    insert_pattern = re.compile(r"INSERT INTO question_bank .*? VALUES \((.*?)\);", re.IGNORECASE | re.DOTALL)
    
    combinations = []
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
        matches = insert_pattern.findall(content)
        
        for val_str in matches:
            # Séparer les valeurs en faisant attention aux virgules dans les chaînes
            # Approche simplifiée : on split par ', ' mais c'est risqué.
            # Meilleure approche : utiliser un tokenizer simple pour les valeurs SQL
            parts = []
            current = []
            in_quotes = False
            i = 0
            while i < len(val_str):
                char = val_str[i]
                if char == "'":
                    if i + 1 < len(val_str) and val_str[i+1] == "'":
                        current.append("'")
                        i += 1
                    else:
                        in_quotes = not in_quotes
                elif char == "," and not in_quotes:
                    parts.append("".join(current).strip())
                    current = []
                else:
                    current.append(char)
                i += 1
            parts.append("".join(current).strip())
            
            if len(parts) >= 14:
                # Indices basés sur la structure observée :
                # 7: subject, 8: class_level, 9: difficulty, 13: app_language
                subj = parts[7].strip("'")
                cls = parts[8].strip("'")
                diff = parts[9].strip("'")
                lang = parts[13].strip("'")
                combinations.append((subj, cls, diff, lang))

    # Comptage
    counts = Counter(combinations)
    
    # Affichage du tableau
    header = f"{'Matière':<12} | {'Classe':<6} | {'Niveau':<10} | {'Langue':<10} | {'Nombre'}"
    print(header)
    print("-" * len(header))
    
    # Tri par matière, classe, difficulté, langue
    sorted_keys = sorted(counts.keys(), key=lambda x: (x[0], x[1], x[2], x[3]))
    
    under_provisioned = []
    target = 10
    
    for key in sorted_keys:
        subj, cls, diff, lang = key
        count = counts[key]
        status = ""
        if count < target:
            status = " <--- MANQUE"
            under_provisioned.append((key, count))
        
        print(f"{subj:<12} | {cls:<6} | {diff:<10} | {lang:<10} | {count}{status}")

    print("\n" + "="*30)
    print(f"TOTAL QUESTIONS : {len(combinations)}")
    print(f"GROUPES SOUS-APPROVISIONNÉS (<{target}) : {len(under_provisioned)}")
    print("="*30)

if __name__ == "__main__":
    script_dir = os.path.dirname(os.path.abspath(__file__))
    data_sql_path = os.path.join(script_dir, 'data.sql')
    audit_data_sql(data_sql_path)
