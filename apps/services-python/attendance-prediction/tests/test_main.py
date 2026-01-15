from fastapi.testclient import TestClient
from app.main import app
from datetime import datetime

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_predict_occupancy_now():
    response = client.get("/predict/occupancy")
    assert response.status_code == 200
    data = response.json()
    assert "predicted_occupancy" in data
    assert "occupancy_level" in data
    assert data["predicted_occupancy"] >= 10

def test_predict_occupancy_with_param():
    target = datetime(2025, 12, 25, 10, 0, 0).isoformat()
    response = client.get(f"/predict/occupancy?target_time={target}")
    assert response.status_code == 200
    assert response.json()["datetime"] == target
