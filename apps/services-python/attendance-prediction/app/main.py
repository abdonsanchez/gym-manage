from fastapi import FastAPI
from pydantic import BaseModel
import random
from datetime import datetime, time

app = FastAPI(title="GestorGYM Attendance Prediction", version="1.0.0")

class PredictionResponse(BaseModel):
    datetime: str
    predicted_occupancy: int
    occupancy_level: str # low, medium, high

@app.get("/")
def read_root():
    return {"status": "online", "service": "attendance-prediction"}

@app.get("/predict/occupancy", response_model=PredictionResponse)
async def predict_occupancy(target_time: str = None):
    # Mock prediction logic
    # In reality, this would load a trained LSTM/Prophet model
    
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
