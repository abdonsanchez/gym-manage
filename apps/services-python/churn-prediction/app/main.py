from fastapi import FastAPI
from pydantic import BaseModel
import random

app = FastAPI(title="GestorGYM Churn Prediction", version="1.0.0")

class ChurnResponse(BaseModel):
    member_id: int
    churn_probability: float
    risk_level: str

@app.get("/")
def read_root():
    return {"status": "online", "service": "churn-prediction"}

@app.get("/predict/churn/{member_id}", response_model=ChurnResponse)
async def predict_churn(member_id: int):
    # Mock Churn Logic (would be Random Forest/XGBoost)
    prob = random.random()
    risk = "low"
    if prob > 0.4: risk = "medium"
    if prob > 0.7: risk = "high"
    
    return ChurnResponse(
        member_id=member_id,
        churn_probability=round(prob, 4),
        risk_level=risk
    )
