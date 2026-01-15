from fastapi.testclient import TestClient
from app.main import app
import io

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json()["status"] == "online"

def test_analyze_video_success():
    # Mock file upload
    file_content = b"fake video content"
    files = {"file": ("video.mp4", file_content, "video/mp4")}
    
    response = client.post("/analyze/video", files=files)
    assert response.status_code == 200
    data = response.json()
    assert "analysis_id" in data
    assert "score" in data
    assert "feedback" in data
    assert data["score"] == 85

def test_analyze_video_missing_file():
    response = client.post("/analyze/video")
    assert response.status_code == 422
