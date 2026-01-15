@echo off
echo ========================================================
echo       GymManage - All-in-One Startup Script
echo ========================================================

echo [1/3] Creating shared network if not exists...
docker network create gym-network 2>nul

echo [2/3] Starting Infrastructure (DB, Kafka, Redis, MinIO)...
docker-compose -f infrastructure/docker-compose.yml up -d
if %ERRORLEVEL% NEQ 0 (
    echo Error starting infrastructure!
    pause
    exit /b %ERRORLEVEL%
)

echo Waiting 30 seconds for databases to initialize...
timeout /t 30 /nobreak

echo [3/3] Starting Applications (Services, AI, Frontend)...
docker-compose -f docker-compose.apps.yml up -d --build
if %ERRORLEVEL% NEQ 0 (
    echo Error starting applications!
    pause
    exit /b %ERRORLEVEL%
)

echo ========================================================
echo       System Started Successfully!
echo ========================================================
echo Access Frontend: http://localhost:5173
echo Access Eureka:   http://localhost:8761
echo Access Gateway:  http://localhost:8080/swagger-ui.html
echo ========================================================
pause
