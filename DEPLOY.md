# Deploiement production EduPlay

## 1) Prerequis serveur
- Ubuntu 22.04+ (ou Debian recent)
- Docker + Docker Compose plugin
- Port 80 ouvert

## 2) Cloner et configurer
```bash
git clone https://github.com/mayss111/EduPlay.git
cd EduPlay
cp .env.example .env
```

## 3) Variables importantes (`.env`)
```env
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_DATABASE=eduplay_db
MYSQL_USER=eduplay
MYSQL_PASSWORD=StrongPassword123!
APP_JWT_SECRET=ChangeMeToAVeryLongRandomSecret
APP_CORS_ALLOWED_ORIGINS=https://votre-domaine.com
OLLAMA_BASE_URL=http://ollama:11434
OLLAMA_MODEL=qwen2.5:7b-instruct
OLLAMA_TEMPERATURE=0.25
```

## 4) Lancer en production
```bash
docker compose -f docker-compose.prod.yml up -d --build
```

## 5) Verifier
```bash
docker compose -f docker-compose.prod.yml ps
docker compose -f docker-compose.prod.yml logs -f backend
```

Application accessible sur:
- `http://IP_DU_SERVEUR`

## HTTPS recommande (Nginx Proxy Manager ou Caddy)
- Mettre un reverse proxy devant le port 80
- Ajouter certificat TLS (Let's Encrypt)
- Conserver `APP_CORS_ALLOWED_ORIGINS` sur le domaine HTTPS

## Notes
- Le frontend proxy automatiquement `/api/*` vers le backend.
- Si Ollama n'est pas disponible, le service utilise le fallback local des questions.
