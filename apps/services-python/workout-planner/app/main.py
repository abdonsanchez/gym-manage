from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Optional

app = FastAPI(title="GestorGYM Workout Planner", version="1.0.0")

class WorkoutRequest(BaseModel):
    member_id: int
    goal: str
    level: str # beginner, intermediate, advanced
    days_per_week: int

class Exercise(BaseModel):
    name: str
    sets: int
    reps: str
    rest: str

class WorkoutPlan(BaseModel):
    plan_id: str
    schedule: List[Exercise]

@app.get("/")
def read_root():
    return {"status": "online", "service": "workout-planner"}

@app.post("/generate/workout", response_model=WorkoutPlan)
async def generate_workout(request: WorkoutRequest):
    # Mock Generation Logic
    plan = [
        Exercise(name="Squat", sets=3, reps="12", rest="60s"),
        Exercise(name="Push Up", sets=3, reps="15", rest="45s"),
        Exercise(name="Lunges", sets=3, reps="12 each", rest="60s")
    ]
    
    return WorkoutPlan(plan_id="plan-mock-001", schedule=plan)
