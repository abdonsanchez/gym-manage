from fastapi.testclient import TestClient
from app.main import app
import uuid

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_chat_flow():
    # 1. New conversation
    response = client.post("/chat", json={"message": "Hola, quiero información"})
    assert response.status_code == 200
    data = response.json()
    assert "conversation_id" in data
    assert "reply" in data
    
    conv_id = data["conversation_id"]
    
    # 2. Continue conversation
    response = client.post("/chat", json={"conversation_id": conv_id, "message": "Precios"})
    assert response.status_code == 200
    assert response.json()["conversation_id"] == conv_id
    
    # 3. Check history
    response = client.get(f"/history/{conv_id}")
    assert response.status_code == 200
    history = response.json()
    assert len(history["messages"]) >= 4 # 2 user + 2 assistant messages

def test_history_not_found():
    random_id = str(uuid.uuid4())
    response = client.get(f"/history/{random_id}")
    assert response.status_code == 404
