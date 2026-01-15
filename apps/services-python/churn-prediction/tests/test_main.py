from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"
    assert response.json()["service"] == "churn-prediction"

def test_predict_churn_success():
    response = client.get("/predict/churn/123")
    assert response.status_code == 200
    data = response.json()
    assert data["member_id"] == 123
    assert "churn_probability" in data
    assert "risk_level" in data
    assert 0.0 <= data["churn_probability"] <= 1.0
    assert data["risk_level"] in ["low", "medium", "high"]

def test_predict_churn_invalid_id():
    response = client.get("/predict/churn/-1")
    assert response.status_code == 422 # Validation Error (gt=0)
