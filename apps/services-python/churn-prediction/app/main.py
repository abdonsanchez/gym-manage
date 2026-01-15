from fastapi import FastAPI, Path, Query
from pydantic import BaseModel, Field
import random

app = FastAPI(
    title="GestorGYM Churn Prediction AI",
    description="Microservicio de IA para predecir la probabilidad de abandono (Churn Rate) de los miembros.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Predict", "description": "Endpoints de predicción de IA"},
        {"name": "System", "description": "Health check y estado del sistema"}
    ]
)

class ChurnResponse(BaseModel):
    member_id: int = Field(..., description="ID del miembro analizado")
    churn_probability: float = Field(..., description="Probabilidad de abandono (0.0 - 1.0)")
    risk_level: str = Field(..., description="Nivel de riesgo: low, medium, high")

@app.get("/", tags=["System"])
def read_root():
    """
    Health check endpoint to verify service availability.
    """
    return {"status": "online", "service": "churn-prediction", "version": "1.0.0"}

@app.get("/predict/churn/{member_id}", response_model=ChurnResponse, tags=["Predict"])
async def predict_churn(
    member_id: int = Path(..., title="Member ID", description="El ID único del miembro a analizar", gt=0)
):
    """
    **Predice el riesgo de abandono de un miembro.**
    
    Analiza métricas históricas (simuladas por ahora) para determinar la probabilidad
    de que un miembro cancele su suscripción.
    
    - **Low Risk**: < 0.4
    - **Medium Risk**: 0.4 - 0.7
    - **High Risk**: > 0.7
    """
    # Mock Churn Logic (would be Random Forest/XGBoost model inference)
    # In a real scenario, we would fetch features from Feature Store or DB
    prob = random.random()
    risk = "low"
    if prob > 0.4: risk = "medium"
    if prob > 0.7: risk = "high"
    
    return ChurnResponse(
        member_id=member_id,
        churn_probability=round(prob, 4),
        risk_level=risk
    )
