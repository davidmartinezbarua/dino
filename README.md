# Dino Service

Microservicio REST para gestión ABM de dinosaurios. Se implemento un scheduler cada 10 minutos que actualiza automaticamente los estados de los dinosaurios (ALIVE - ENDANGERED - EXTINCT).
Cada cambio de estado genera un mensaje en RabbitMQ (topic dinosaur.status).
El proyecto sigue arquitectura hexagonal con separacion entre dominio, aplicación e infraestructura.

## Tecnologias
- Spring Boot
- PostgreSQL 
- RabbitMQ
- Testcontainers para tests de integracion

## Requisitos

- Docker
- Docker Compose

## Ejecucion

```bash
docker-compose up --build
```

La app se levanta en `http://localhost:8080`.
La UI de RabbitMQ se accede desde `http://localhost:15672`.

## Endpoints

| Metodo | Path | Descripcion                        |
|--------|------|------------------------------------|
| POST   | /dinosaur | Crear dinosaurio                   |
| GET    | /dinosaur | Lista de Dinosaurios               |
| GET    | /dinosaur/{id} | Obtener Dinosaurio por ID          |
| PUT    | /dinosaur/{id} | Actualizar un Dinosaurio existente |
| DELETE | /dinosaur/{id} | Elimina un dinosaurio              |

## Correr los tests

```bash
./mvnw test
```

Los tests de integracion levantan sus propios contenedores via Testcontainers.