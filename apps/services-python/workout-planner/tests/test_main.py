from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_generate_workout_beginner():
    payload = {
        "member_id": 1,
        "goal": "weight_loss",
        "level": "beginner",
        "days_per_week": 3
    }
    response = client.post("/generate/workout", json=payload)
    assert response.status_code == 200
    data = response.json()
    assert len(data["schedule"]) > 0
    # Should NOT have advanced exercises
    names = [ex["name"] for ex in data["schedule"]]
    assert "Deadlift" not in names

def test_generate_workout_advanced():
    payload = {
        "member_id": 1,
        "goal": "strength",
        "level": "advanced",
        "days_per_week": 5
    }
    response = client.post("/generate/workout", json=payload)
    assert response.status_code == 200
    data = response.json()
    # Should have advanced exercises
    names = [ex["name"] for ex in data["schedule"]]
    assert "Deadlift" in names
