from fastapi import FastAPI, Query
from pydantic import BaseModel, Field
import random
from datetime import datetime

app = FastAPI(
    title="GestorGYM Attendance Prediction AI",
    description="Microservicio de IA para predecir la afluencia y ocupación del gimnasio.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Predict", "description": "Endpoints de predicción de ocupación"},
        {"name": "System", "description": "Health check y estado del sistema"}
    ]
)

class PredictionResponse(BaseModel):
    datetime: str = Field(..., description="Fecha y hora de la predicción")
    predicted_occupancy: int = Field(..., description="Cantidad estimada de personas")
    occupancy_level: str = Field(..., description="Nivel de ocupación (low, medium, high)")

@app.get("/", tags=["System"])
def read_root():
    """
    Health check endpoint.
    """
    return {"status": "online", "service": "attendance-prediction", "version": "1.0.0"}

@app.get("/predict/occupancy", response_model=PredictionResponse, tags=["Predict"])
async def predict_occupancy(
    target_time: str = Query(None, description="Fecha/Hora objetivo (ISO 8601). Si se omite es 'ahora'.")
):
    """
    **Predice la ocupación del gimnasio.**
    
    Utiliza modelos de series temporales (simulado) para estimar cuántas personas
    habrá en el gimnasio en un momento dado.
    """
    # Mock prediction logic (Prophet/LSTM placeholder)
    if not target_time:
        target_time = datetime.now().isoformat()
        
    # Mocking based on randomness for now
    occupancy = random.randint(10, 150)
    level = "low"
    if occupancy > 50: level = "medium"
    if occupancy > 100: level = "high"
    
    return PredictionResponse(
        datetime=target_time,
        predicted_occupancy=occupancy,
        occupancy_level=level
    )
