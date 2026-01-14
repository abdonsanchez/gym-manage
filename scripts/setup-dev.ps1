[CmdletBinding()]
param (
    [Alias("a")]
    [Switch]$All
)

$ErrorActionPreference = "Stop"

Write-Host "⚡ Starting GestorGYM Infrastructure..." -ForegroundColor Cyan

# Check if Docker is running
docker info | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: Docker is not running. Please start Docker Desktop and try again." -ForegroundColor Red
    exit 1
}

$ProjectRoot = Resolve-Path "$PSScriptRoot/.."
Set-Location $ProjectRoot

# Start Infrastructure
Write-Host "Starting Infrastructure containers..." -ForegroundColor Green
docker-compose -f infrastructure/docker-compose.yml up -d

# Wait slightly
Write-Host "⏳ Waiting for MySQL initialization..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

if ($All) {
    Write-Host "🚀 Starting Microservices & Frontend (This may take a while to build)..." -ForegroundColor Cyan
    docker-compose -f docker-compose.apps.yml up -d --build
    
    Write-Host "✅ Application started!" -ForegroundColor Green
    Write-Host "   - Frontend: http://localhost:3000"
    Write-Host "   - Eureka:   http://localhost:8761"
    Write-Host "   - Gateway:  http://localhost:8080"
} else {
    Write-Host "✅ Infrastructure started!" -ForegroundColor Green
    Write-Host "   To start apps as well, run: ./scripts/setup-dev.ps1 -All"
}
