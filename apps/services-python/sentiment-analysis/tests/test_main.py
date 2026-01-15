from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_analyze_sentiment_positive():
    response = client.post("/analyze/feedback", json={"text": "El gimnasio es excelente y me encanta"})
    assert response.status_code == 200
    data = response.json()
    assert data["sentiment"] == "positive"
    assert data["score"] > 0.8

def test_analyze_sentiment_negative():
    response = client.post("/analyze/feedback", json={"text": "El servicio es terrible, odio este lugar"})
    assert response.status_code == 200
    data = response.json()
    assert data["sentiment"] == "negative"
    assert data["score"] < 0.5
