# EduPlay Deployment Guide

This guide provides step-by-step instructions for deploying EduPlay to production using Render (backend) and Vercel (frontend).

## Prerequisites

- GitHub account
- Render account (for backend)
- Vercel account (for frontend)
- Basic knowledge of Git and command line

## Architecture Overview

- **Backend**: Spring Boot application deployed on Render
- **Frontend**: Angular application deployed on Vercel
- **Database**: PostgreSQL on Render
- **Storage**: Static files on Vercel

## 1. Backend Deployment (Render)

### 1.1 Create Render Account
1. Go to [render.com](https://render.com) and sign up
2. Connect your GitHub account

### 1.2 Create PostgreSQL Database
1. In Render Dashboard, click "New +" → "PostgreSQL"
2. Name: `eduplay-db`
3. Region: Choose closest to your users
4. Database Name: `eduplay_db`
5. User: `eduplay_user`
6. Click "Create Database"

### 1.3 Create Web Service
1. In Render Dashboard, click "New +" → "Web Service"
2. Connect your EduPlay repository
3. Name: `eduplay-backend`
4. Region: Same as database
5. Runtime: Java
6. Build Command: `cd backend && ./mvnw clean package -DskipTests`
7. Start Command: `cd backend && java -jar target/eduplay-0.0.1-SNAPSHOT.jar`

### 1.4 Configure Environment Variables
Add these environment variables in Render:

```bash
# Database
DATABASE_URL=postgresql://eduplay_user:password@ep-example-123456.us-east-2.aws.neon.tech:5432/eduplay_db

# Application
SERVER_PORT=8081
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# JWT
APP_JWT_SECRET=YourStrongSecretKeyHere_ChangeThis!
APP_JWT_EXPIRATION=86400000

# CORS
APP_CORS_ALLOWED_ORIGINS=https://eduplay-frontend.vercel.app,https://eduplay.vercel.app

# Optional: Redis (if using caching)
SPRING_DATA_REDIS_HOST=
SPRING_DATA_REDIS_PORT=6379
SPRING_CACHE_TYPE=none

# Optional: Ollama (if using AI generation)
OLLAMA_BASE_URL=
OLLAMA_MODEL=qwen2.5:7b-instruct
OLLAMA_TEMPERATURE=0.25
```

### 1.5 Deploy
1. Click "Create Web Service"
2. Wait for deployment to complete
3. Note the URL (e.g., `https://eduplay-backend.onrender.com`)

## 2. Frontend Deployment (Vercel)

### 2.1 Create Vercel Account
1. Go to [vercel.com](https://vercel.com) and sign up
2. Connect your GitHub account

### 2.2 Create Project
1. In Vercel Dashboard, click "New Project"
2. Import your EduPlay repository
3. Configure project:
   - Framework Preset: Other
   - Root Directory: `frontend/eduplay-frontend`
   - Build Command: `npm install && npm run build --configuration=production`
   - Output Directory: `dist/eduplay-frontend`
   - Install Command: `npm install`

### 2.3 Configure Environment Variables
Add these environment variables in Vercel:

```bash
# API Configuration
VITE_API_URL=https://eduplay-backend.onrender.com/api

# Optional: Analytics
VITE_GOOGLE_ANALYTICS_ID=
```

### 2.4 Deploy
1. Click "Deploy"
2. Wait for deployment to complete
3. Note the URL (e.g., `https://eduplay-frontend.vercel.app`)

## 3. Configuration

### 3.1 Update CORS Settings
In Render, update `APP_CORS_ALLOWED_ORIGINS` to include your Vercel URL:

```bash
APP_CORS_ALLOWED_ORIGINS=https://eduplay-frontend.vercel.app,https://your-custom-domain.com
```

### 3.2 Update API URL
In Vercel, update `VITE_API_URL` to point to your Render backend:

```bash
VITE_API_URL=https://eduplay-backend.onrender.com/api
```

## 4. Database Initialization

### 4.1 Automatic Migration
The application will automatically create tables on first startup using JPA.

### 4.2 Seed Data
To populate with initial questions:

1. SSH into your Render service or use the Render console
2. Run the SQL script:

```bash
cd backend
java -jar target/eduplay-0.0.1-SNAPSHOT.jar --spring.profiles.active=seed
```

Or manually execute the SQL from `backend/src/main/resources/data.sql`.

## 5. Custom Domain Setup

### 5.1 Backend Domain (Render)
1. In Render Dashboard, go to your web service
2. Click "Domains" → "Add Domain"
3. Add your custom domain (e.g., `api.eduplay.com`)
4. Update DNS settings as instructed

### 5.2 Frontend Domain (Vercel)
1. In Vercel Dashboard, go to your project
2. Click "Settings" → "Domains"
3. Add your custom domain (e.g., `eduplay.com`)
4. Update DNS settings as instructed

### 5.3 Update CORS and API URLs
Update environment variables with your custom domains:

```bash
# Render (Backend)
APP_CORS_ALLOWED_ORIGINS=https://eduplay.com,https://www.eduplay.com

# Vercel (Frontend)
VITE_API_URL=https://api.eduplay.com/api
```

## 6. Monitoring and Maintenance

### 6.1 Health Checks
- Backend: `GET /api/health`
- Frontend: Check Vercel dashboard

### 6.2 Logs
- Render: View logs in service dashboard
- Vercel: View logs in project dashboard

### 6.3 Database Backups
- Render automatically creates daily backups
- Configure retention period in database settings

### 6.4 Performance Monitoring
- Enable Redis caching for better performance
- Monitor response times in Render dashboard
- Use Vercel Analytics for frontend metrics

## 7. Troubleshooting

### 7.1 Common Issues

**Database Connection Errors:**
- Check `DATABASE_URL` format
- Verify database is running
- Check firewall settings

**CORS Errors:**
- Verify `APP_CORS_ALLOWED_ORIGINS` includes frontend URL
- Check for trailing slashes

**Build Failures:**
- Check Node.js and Java versions
- Verify environment variables
- Check build logs for specific errors

### 7.2 Debug Commands

```bash
# Check backend health
curl https://eduplay-backend.onrender.com/api/health

# Check database connection
curl https://eduplay-backend.onrender.com/api/db/health

# Test API endpoints
curl https://eduplay-backend.onrender.com/api/game/questions?subject=MATH&difficulty=SIMPLE
```

## 8. Security Best Practices

### 8.1 Environment Variables
- Never commit secrets to git
- Use strong, unique secrets for JWT
- Rotate secrets regularly

### 8.2 Database Security
- Use strong database passwords
- Enable SSL connections
- Restrict database access

### 8.3 Application Security
- Keep dependencies updated
- Enable HTTPS only
- Implement rate limiting if needed

## 9. Scaling

### 9.1 Backend Scaling
- Increase instance size in Render
- Enable horizontal scaling
- Consider Redis for caching

### 9.2 Frontend Scaling
- Vercel handles scaling automatically
- Use CDN for static assets
- Optimize bundle size

## 10. Support

For issues or questions:
1. Check the logs in Render/Vercel dashboards
2. Review this deployment guide
3. Check the main README for development setup
4. Create an issue in the repository

## Quick Start Checklist

- [ ] Create Render account and connect GitHub
- [ ] Create PostgreSQL database on Render
- [ ] Deploy backend web service
- [ ] Configure environment variables
- [ ] Create Vercel account and connect GitHub
- [ ] Deploy frontend project
- [ ] Configure CORS and API URLs
- [ ] Test the deployment
- [ ] Set up custom domains (optional)
- [ ] Configure monitoring and backups

## Estimated Time
- Initial setup: 30 minutes
- First deployment: 15 minutes
- Custom domains: 10 minutes
- Total: ~1 hour

## Cost Estimate
- Render: Free tier available, paid plans start at $7/month
- Vercel: Free tier available, paid plans start at $20/month
- Database: Free tier available, paid plans start at $7/month

Total estimated cost: $0-34/month depending on usage and plan selection.