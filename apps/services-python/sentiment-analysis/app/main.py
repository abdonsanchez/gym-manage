from fastapi import FastAPI
from pydantic import BaseModel
import random

app = FastAPI(title="GestorGYM Sentiment Analysis", version="1.0.0")

class SentimentRequest(BaseModel):
    text: str

class SentimentResponse(BaseModel):
    sentiment: str # positive, neutral, negative
    score: float

@app.get("/")
def read_root():
    return {"status": "online", "service": "sentiment-analysis"}

@app.post("/analyze/feedback", response_model=SentimentResponse)
async def analyze_feedback(request: SentimentRequest):
    # Mock Sentiment Logic (would be BERT/Transformers)
    # Simple rule for demo
    text_lower = request.text.lower()
    score = 0.5
    sentiment = "neutral"
    
    if "bueno" in text_lower or "excelente" in text_lower:
        score = 0.9
        sentiment = "positive"
    elif "malo" in text_lower or "terrible" in text_lower:
        score = 0.1
        sentiment = "negative"
        
    return SentimentResponse(sentiment=sentiment, score=score)
