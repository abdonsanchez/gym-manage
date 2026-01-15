from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_generate_meal_plan_balanced():
    payload = {
        "member_id": 1,
        "calories": 2000,
        "diet_type": "balanced"
    }
    response = client.post("/generate/meal-plan", json=payload)
    assert response.status_code == 200
    data = response.json()
    assert data["daily_calories"] == 2000
    assert "breakfast" in data["meals"]
    
def test_generate_meal_plan_vegan():
    payload = {
        "member_id": 2,
        "calories": 1500,
        "diet_type": "vegan"
    }
    response = client.post("/generate/meal-plan", json=payload)
    assert response.status_code == 200
    data = response.json()
    # Check if vegan mock logic worked
    assert "Tofu" in str(data["meals"]) or "Lentil" in str(data["meals"])
    
def test_validation_error_calories():
    # Calories too low
    payload = {
        "member_id": 3,
        "calories": 100,
        "diet_type": "keto"
    }
    response = client.post("/generate/meal-plan", json=payload)
    assert response.status_code == 422
