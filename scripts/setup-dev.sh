#!/bin/bash

# Setup Development Environment for GestorGYM

# Exit on error
set -e

# Default values
START_APPS=false

# Parse arguments
while getopts "a" opt; do
  case $opt in
    a)
      START_APPS=true
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

echo "⚡ Starting GestorGYM Infrastructure..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
  echo "Error: Docker is not running. Please start Docker Desktop and try again."
  exit 1
fi

PROJECT_ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)
cd "$PROJECT_ROOT"

# Start Infrastructure (DB, Kafka, Redis)
echo "Starting Infrastructure containers..."
docker-compose -f infrastructure/docker-compose.yml up -d

# Wait for essential services
echo "⏳ Waiting for MySQL to be ready..."
sleep 10

if [ "$START_APPS" = true ]; then
  echo "🚀 Starting Microservices & Frontend (This may take a while to build)..."
  docker-compose -f docker-compose.apps.yml up -d --build
  
  echo "✅ Application started!"
  echo "   - Frontend: http://localhost:3000"
  echo "   - Eureka:   http://localhost:8761"
  echo "   - Gateway:  http://localhost:8080"
  echo "   - MinIO:    http://localhost:9001"
else
  echo "✅ Infrastructure started!"
  echo "   - MySQL: localhost:3306"
  echo "   - Redis: localhost:6379"
  echo "   - Kafka: localhost:9092"
  echo "   To start apps as well, run: ./scripts/setup-dev.sh -a"
fi
