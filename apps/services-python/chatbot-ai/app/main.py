from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
import uuid
from datetime import datetime

app = FastAPI(title="GestorGYM Chatbot AI", version="1.0.0")

# --- Models ---
class Message(BaseModel):
    role: str
    content: str

class ChatRequest(BaseModel):
    conversation_id: Optional[str] = None
    member_id: Optional[int] = None
    message: str

class ChatResponse(BaseModel):
    conversation_id: str
    reply: str

# --- In-memory mock storage for demo ---
conversations = {}

@app.get("/")
def read_root():
    return {"status": "online", "service": "chatbot-ai"}

@app.post("/chat", response_model=ChatResponse)
async def chat(request: ChatRequest):
    conv_id = request.conversation_id or str(uuid.uuid4())
    
    if conv_id not in conversations:
        conversations[conv_id] = []
    
    # Store user message
    conversations[conv_id].append({"role": "user", "content": request.message})
    
    # Mock AI Logic (Replace with OpenAI/LLM call later)
    # Simple keyword-based response for now
    user_msg_lower = request.message.lower()
    reply_content = "Lo siento, no entendí tu consulta."
    
    if "hola" in user_msg_lower:
        reply_content = "¡Hola! Soy tu asistente virtual de GestorGYM. ¿En qué puedo ayudarte hoy?"
    elif "horario" in user_msg_lower:
        reply_content = "Nuestro horario es de Lunes a Viernes de 6:00 a 22:00, y Sábados de 8:00 a 20:00."
    elif "precio" in user_msg_lower or "membresía" in user_msg_lower:
        reply_content = "Tenemos varios planos. El Plan Básico cuesta $30/mes y el Plan Premium $50/mes."
    else:
        reply_content = "Interesante pregunta. Como soy una IA en desarrollo, aún estoy aprendiendo sobre eso."

    # Store assistant reply
    conversations[conv_id].append({"role": "assistant", "content": reply_content})
    
    return ChatResponse(conversation_id=conv_id, reply=reply_content)

@app.get("/history/{conversation_id}")
async def get_history(conversation_id: str):
    if conversation_id not in conversations:
        raise HTTPException(status_code=404, detail="Conversation not found")
    return {"conversation_id": conversation_id, "messages": conversations[conversation_id]}
