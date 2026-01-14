from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Dict

app = FastAPI(title="GestorGYM Nutrition AI", version="1.0.0")

class MealRequest(BaseModel):
    member_id: int
    calories: int
    diet_type: str # vegan, keto, balanced

class MealPlan(BaseModel):
    plan_id: str
    daily_calories: int
    meals: Dict[str, List[str]] # breakfast: [eggs, toast]

@app.get("/")
def read_root():
    return {"status": "online", "service": "nutrition-ai"}

@app.post("/generate/meal-plan", response_model=MealPlan)
async def generate_meal_plan(request: MealRequest):
    # Mock Nutrition Logic
    meals = {
        "breakfast": ["Oatmeal with berries", "Black coffee"],
        "lunch": ["Grilled Chicken Salad", "Olive oil dressing"],
        "dinner": ["Salmon with asparagus", "Quinoa"]
    }
    
    return MealPlan(plan_id="meal-mock-001", daily_calories=request.calories, meals=meals)
