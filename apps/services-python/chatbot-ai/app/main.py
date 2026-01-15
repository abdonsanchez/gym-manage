from fastapi import FastAPI, HTTPException, Path
from pydantic import BaseModel, Field
from typing import List, Optional
import uuid

app = FastAPI(
    title="GestorGYM Chatbot AI",
    description="Microservicio de Asistente Virtual para atención al cliente.",
    version="1.0.0",
    openapi_tags=[
        {"name": "Chat", "description": "Endpoints de interacción con el bot"},
        {"name": "History", "description": "Historial de conversaciones"},
        {"name": "System", "description": "Health check y estado"}
    ]
)

# --- Models ---
class ChatRequest(BaseModel):
    conversation_id: Optional[str] = Field(None, description="ID de la conversación (si existe)")
    member_id: Optional[int] = Field(None, description="ID del miembro (opcional)")
    message: str = Field(..., description="Mensaje del usuario")

class ChatResponse(BaseModel):
    conversation_id: str = Field(..., description="ID único de la conversación")
    reply: str = Field(..., description="Respuesta del asistente")

# --- In-memory mock storage for demo ---
conversations = {}

@app.get("/", tags=["System"])
def read_root():
    """Health check endpoint."""
    return {"status": "online", "service": "chatbot-ai", "version": "1.0.0"}

@app.post("/chat", response_model=ChatResponse, tags=["Chat"])
async def chat(request: ChatRequest):
    """
    **Envía un mensaje al chatbot.**
    
    Procesa la entrada del usuario y devuelve una respuesta simulada (Mock).
    En producción, esto se conectaría a la API de OpenAI.
    """
    conv_id = request.conversation_id or str(uuid.uuid4())
    
    if conv_id not in conversations:
        conversations[conv_id] = []
    
    # Store user message
    conversations[conv_id].append({"role": "user", "content": request.message})
    
    # Mock AI Logic
    user_msg_lower = request.message.lower()
    reply_content = "Lo siento, no entendí tu consulta."
    
    if "hola" in user_msg_lower:
        reply_content = "¡Hola! Soy tu asistente virtual de GestorGYM. ¿En qué puedo ayudarte hoy?"
    elif "horario" in user_msg_lower:
        reply_content = "Nuestro horario es de Lunes a Viernes de 6:00 a 22:00, y Sábados de 8:00 a 20:00."
    elif "precio" in user_msg_lower or "membresía" in user_msg_lower:
        reply_content = "Tenemos varios planes. El Plan Básico cuesta $30/mes y el Plan Premium $50/mes."
    else:
        reply_content = "Interesante pregunta. Como soy una IA en desarrollo, aún estoy aprendiendo sobre eso."

    # Store assistant reply
    conversations[conv_id].append({"role": "assistant", "content": reply_content})
    
    return ChatResponse(conversation_id=conv_id, reply=reply_content)

@app.get("/history/{conversation_id}", tags=["History"])
async def get_history(
    conversation_id: str = Path(..., description="ID de la conversación a recuperar")
):
    """Recupera el historial de mensajes de una conversación."""
    if conversation_id not in conversations:
        raise HTTPException(status_code=404, detail="Conversation not found")
    return {"conversation_id": conversation_id, "messages": conversations[conversation_id]}
