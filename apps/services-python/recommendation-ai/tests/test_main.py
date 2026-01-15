from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_recommend_classes_success():
    response = client.get("/recommend/classes?member_id=123")
    assert response.status_code == 200
    data = response.json()
    assert isinstance(data, list)
    assert len(data) > 0
    assert "item_name" in data[0]
    assert data[0]["score"] > 0

def test_validation_error_member_id():
    # member_id requires gt=0
    response = client.get("/recommend/classes?member_id=-5")
    assert response.status_code == 422
