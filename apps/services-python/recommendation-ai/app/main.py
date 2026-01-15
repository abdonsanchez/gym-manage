from fastapi import FastAPI, Query
from pydantic import BaseModel, Field
from typing import List

app = FastAPI(
    title="GestorGYM Recommendation AI",
    description="Microservicio de IA para recomendar clases y entrenamientos personalizados.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Recommender", "description": "Endpoints de recomendación"},
        {"name": "System", "description": "Health check y estado"}
    ]
)

class Recommendation(BaseModel):
    item_type: str = Field(..., description="Tipo de recomendación (class, workout, product)")
    item_name: str = Field(..., description="Nombre del ítem recomendado")
    score: float = Field(..., description="Puntuación de relevancia (0.0 - 1.0)")
    reason: str = Field(..., description="Explicación de la recomendación")

@app.get("/", tags=["System"])
def read_root():
    """Health check endpoint."""
    return {"status": "online", "service": "recommendation-ai", "version": "1.0.0"}

@app.get("/recommend/classes", response_model=List[Recommendation], tags=["Recommender"])
async def recommend_classes(
    member_id: int = Query(..., description="ID del miembro para personalizar resultados", gt=0)
):
    """
    **Recomienda clases para un miembro.**
    
    Analiza el historial de asistencia y preferencias para sugerir clases relevantes.
    """
    # Mock Recommendation Logic
    return [
        Recommendation(
            item_type="class", 
            item_name="Yoga Sunrise", 
            score=0.95, 
            reason="Based on your history of morning check-ins"
        ),
        Recommendation(
            item_type="class", 
            item_name="HIIT Blast", 
            score=0.88, 
            reason="Popular with users similar to you"
        )
    ]
