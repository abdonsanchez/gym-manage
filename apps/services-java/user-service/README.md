# User Service

Servicio de gestión de miembros para el sistema GestorGYM.

## Descripción
Este microservicio maneja:
- Registro de usuarios
- Perfiles de miembros
- Historial médico básico
- Tipos de membresía

## Stack Tecnológico
- Java 17
- Spring Boot 3.2.0
- MySQL 8.0
- Spring Data JPA
- MapStruct

## Ejecución Local
Este servicio está diseñado para correr dentro del entorno de Docker Compose del proyecto principal.

Para correrlo individualmente:
```bash
./mvnw spring-boot:run
```

## API Endpoints
- `GET /api/members`: Listar todos los miembros
- `GET /api/members/{id}`: Obtener detalle de miembro
- `POST /api/members`: Crear miembro
- `PUT /api/members/{id}`: Actualizar miembro
- `DELETE /api/members/{id}`: Eliminar miembro

## Base de Datos
Utiliza la base de datos `gym_users`. Referirse a `infrastructure/databases/init-scripts/02-gym-users.sql` para el esquema.
