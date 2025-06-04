# Fortify SCA Web Service

This project demonstrates a full stack application integrating Fortify SCA static analysis.
It contains a Spring Boot backend and a React frontend.

## Backend
- Java 21, Spring Boot 3
- Spring Security with JWT
- Spring Data JPA (MariaDB)
- Endpoints:
  - `POST /api/scan` - run Fortify scan
  - `POST /api/fpr/upload` - upload FPR file
  - `GET /api/vulnerabilities` - list vulnerabilities
  - `GET /api/report` - download report (XLSX)
  - `POST /api/auth/login` - user login

Build backend:
```bash
cd backend
mvn package
```

## Frontend
- React + TypeScript + Tailwind CSS (Vite)

Run frontend:
```bash
cd frontend
npm install
npm run dev
```
