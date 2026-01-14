# GestorGYM - Intelligent Gym Management System

A comprehensive, microservices-based platform for managing modern fitness centers, powered by AI.

## 🏗 Architecture

GestorGYM follows a **Microservices Architecture** with the following components:

### 🔧 Infrastructure
- **MySQL**: 14 dedicated databases (Database per Service)
- **Redis**: Caching & distributed locks
- **Kafka**: Event streaming & async communication
- **MinIO**: Object storage (images, videos)
- **Eureka**: Service Discovery
- **API Gateway**: Unified entry point (Port 8080)

### ☕ Java Services (Spring Boot)
1.  **User Service**: Member management
2.  **Payment Service**: Billing & subscriptions
3.  **Access Control**: Bio-metric entry logs
4.  **Booking Service**: Class scheduling
5.  **Notification Service**: Email/Push alerts
6.  **Analytics Service**: Business intelligence reports

### 🐍 Python Services (FastAPI - AI)
1.  **Chatbot AI**: Virtual assistant
2.  **Vision AI**: Correction of exercise form
3.  **Attendance Prediction**: Crowd forecasting
4.  **Workout Planner**: Personalized routines
5.  **Nutrition AI**: Meal planning
6.  **Recommendation AI**: Class suggesters
7.  **Churn Prediction**: Member retention analysis
8.  **Sentiment Analysis**: Feedback processing

### ⚛️ Frontend
- **React (Vite)**: Modern, responsive dashboard
- **Premium UI**: Custom dark theme design

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose
- Java 17+ (optional, for local dev)
- Python 3.11+ (optional, for local dev)
- Node.js 18+ (optional, for local dev)

### Quick Start

1.  **Start Infrastructure Only** (Databases, etc.)
    ```bash
    ./scripts/setup-dev.sh
    ```

2.  **Start Full System** (Infra + All Apps)
    *Warning: Requires 16GB+ RAM*
    ```bash
    ./scripts/setup-dev.sh -a
    ```

3.  **Access the System**
    - **Frontend**: [http://localhost:3000](http://localhost:3000)
    - **API Gateway**: [http://localhost:8080](http://localhost:8080)
    - **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)

## 📁 Project Structure

```
gymbro/
├── apps/
│   ├── services-java/    # Spring Boot Microservices
│   ├── services-python/  # FastAPI AI Services
│   └── frontend/         # React Application
├── infrastructure/       # Docker Compose & SQL Init Scripts
└── scripts/              # Helper shell scripts
```

## 🛠 Tech Stack
- **Backend**: Java 17, Spring Boot 3, Python 3.11, FastAPI
- **Frontend**: React 18, Vite, Recharts, Lucide
- **Data**: MySQL 8, Redis 7
- **DevOps**: Docker, Kafka, Zookeeper
