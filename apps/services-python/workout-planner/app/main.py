from fastapi import FastAPI
from pydantic import BaseModel, Field
from typing import List, Optional

app = FastAPI(
    title="GestorGYM Workout Planner AI",
    description="Microservicio de IA para generar rutinas de entrenamiento personalizado.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Planner", "description": "Endpoints de generación de rutinas"},
        {"name": "System", "description": "Health check y estado"}
    ]
)

class WorkoutRequest(BaseModel):
    member_id: int = Field(..., description="ID del miembro")
    goal: str = Field(..., description="Objetivo (e.g., hypertrophy, weight_loss)")
    level: str = Field(..., description="Nivel de experiencia (beginner, intermediate, advanced)")
    days_per_week: int = Field(..., description="Días disponibles para entrenar (1-7)", ge=1, le=7)

class Exercise(BaseModel):
    name: str = Field(..., description="Nombre del ejercicio")
    sets: int = Field(..., description="Número de series")
    reps: str = Field(..., description="Repeticiones (e.g., '12' o '8-10')")
    rest: str = Field(..., description="Tiempo de descanso")

class WorkoutPlan(BaseModel):
    plan_id: str = Field(..., description="ID del plan generado")
    schedule: List[Exercise] = Field(..., description="Lista de ejercicios")

@app.get("/", tags=["System"])
def read_root():
    """Health check endpoint."""
    return {"status": "online", "service": "workout-planner", "version": "1.0.0"}

@app.post("/generate/workout", response_model=WorkoutPlan, tags=["Planner"])
async def generate_workout(request: WorkoutRequest):
    """
    **Genera una rutina de entrenamiento.**
    
    Crea un plan de ejercicios basado en el objetivo, nivel y disponibilidad del usuario.
    (Lógica simulada).
    """
    # Mock Generation Logic
    plan = [
        Exercise(name="Squat", sets=3, reps="12", rest="60s"),
        Exercise(name="Push Up", sets=3, reps="15", rest="45s"),
        Exercise(name="Lunges", sets=3, reps="12 each", rest="60s")
    ]
    
    if request.level == "advanced":
        plan.append(Exercise(name="Deadlift", sets=4, reps="5", rest="120s"))
    
    return WorkoutPlan(plan_id="plan-mock-001", schedule=plan)
