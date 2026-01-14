from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI(title="GestorGYM Recommendation AI", version="1.0.0")

class Recommendation(BaseModel):
    item_type: str
    item_name: str
    score: float
    reason: str

@app.get("/")
def read_root():
    return {"status": "online", "service": "recommendation-ai"}

@app.get("/recommend/classes", response_model=List[Recommendation])
async def recommend_classes(member_id: int):
    # Mock Recommendation Logic
    return [
        Recommendation(item_type="class", item_name="Yoga Sunrise", score=0.95, reason="Based on your history of morning check-ins"),
        Recommendation(item_type="class", item_name="HIIT Blast", score=0.88, reason="Popular with users similar to you")
    ]
