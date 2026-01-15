from fastapi import FastAPI
from pydantic import BaseModel, Field

app = FastAPI(
    title="GestorGYM Sentiment Analysis AI",
    description="Microservicio de IA para análisis de sentimientos en feedback de usuarios.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Analysis", "description": "Endpoints de análisis de texto"},
        {"name": "System", "description": "Health check y estado"}
    ]
)

class SentimentRequest(BaseModel):
    text: str = Field(..., description="Texto a analizar (feedback, comentario, reseña)")

class SentimentResponse(BaseModel):
    sentiment: str = Field(..., description="Sentimiento detectado: positive, neutral, negative")
    score: float = Field(..., description="Puntuación de confianza (0.0 - 1.0)")

@app.get("/", tags=["System"])
def read_root():
    """Health check endpoint."""
    return {"status": "online", "service": "sentiment-analysis", "version": "1.0.0"}

@app.post("/analyze/feedback", response_model=SentimentResponse, tags=["Analysis"])
async def analyze_feedback(request: SentimentRequest):
    """
    **Analiza el sentimiento de un texto.**
    
    Clasifica el feedback del usuario en positivo, negativo o neutral.
    (Lógica simulada por reglas simples).
    """
    # Mock Sentiment Logic (would be BERT/Transformers)
    # Simple rule for demo
    text_lower = request.text.lower()
    score = 0.5
    sentiment = "neutral"
    
    if "bueno" in text_lower or "excelente" in text_lower or "encanta" in text_lower:
        score = 0.9
        sentiment = "positive"
    elif "malo" in text_lower or "terrible" in text_lower or "odio" in text_lower:
        score = 0.1
        sentiment = "negative"
        
    return SentimentResponse(sentiment=sentiment, score=score)
