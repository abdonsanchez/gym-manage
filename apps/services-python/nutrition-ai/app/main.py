from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from typing import List, Dict
import uuid

app = FastAPI(
    title="GestorGYM Nutrition AI",
    description="Microservicio de IA para generar planes nutricionales personalizados.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Nutrition", "description": "Endpoints de generación de dietas"},
        {"name": "System", "description": "Health check y estado"}
    ]
)

class MealRequest(BaseModel):
    member_id: int = Field(..., description="ID del miembro")
    calories: int = Field(..., description="Objetivo calórico diario", gt=500, lt=10000)
    diet_type: str = Field(..., description="Tipo de dieta (vegan, keto, balanced, etc.)")

class MealPlan(BaseModel):
    plan_id: str = Field(..., description="ID único del plan generado")
    daily_calories: int = Field(..., description="Calorías totales del plan")
    meals: Dict[str, List[str]] = Field(..., description="Distribución de comidas (breakfast, lunch, dinner)")

@app.get("/", tags=["System"])
def read_root():
    """Health check endpoint."""
    return {"status": "online", "service": "nutrition-ai", "version": "1.0.0"}

@app.post("/generate/meal-plan", response_model=MealPlan, tags=["Nutrition"])
async def generate_meal_plan(request: MealRequest):
    """
    **Genera un plan nutricional.**
    
    Crea una dieta personalizada basada en las calorías objetivo y preferencias del usuario.
    (Lógica simulada por ahora).
    """
    # Mock Nutrition Logic
    meals = {
        "breakfast": ["Oatmeal with berries", "Black coffee"],
        "lunch": ["Grilled Chicken Salad", "Olive oil dressing"],
        "dinner": ["Salmon with asparagus", "Quinoa"]
    }
    
    # Adjust mock content based on diet type for a bit of realism
    if request.diet_type.lower() == "vegan":
         meals["lunch"] = ["Tofu Salad", "Olive oil dressing"]
         meals["dinner"] = ["Lentil soup", "Quinoa"]
    elif request.diet_type.lower() == "keto":
         meals["breakfast"] = ["Scrambled eggs", "Bacon"]
         meals["dinner"] = ["Salmon with butter", "Asparagus"]

    return MealPlan(
        plan_id=f"meal-{uuid.uuid4()}", 
        daily_calories=request.calories, 
        meals=meals
    )
