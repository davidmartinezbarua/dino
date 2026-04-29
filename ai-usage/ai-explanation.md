# AI-Assisted Development - Explicación

Se utilizó inteligencia artificial (Claude) como herramienta de productividad para tareas repetitivas y de configuración técnica. 
Esto me permitió concentrar el esfuerzo en el diseño de la arquitectura, la lógica de dominio y la implementación del challenge.

### Áreas donde se utilizó IA
- Generación de boilerplate y configuración de infraestructura:
    - Configuración de RabbitMQ con `TopicExchange`
    - Setup de Testcontainers para PostgreSQL y RabbitMQ usando `@ServiceConnection`
    - Dockerfile multistage para Spring Boot + Java 21
- Resolución de dudas puntuales de configuración (serialización de `LocalDateTime`, comportamiento de `@Scheduled`, healthchecks en Docker Compose)
- Escritura inicial de tests unitarios y de integracion

### Sugerencias descartadas
Se descartaron varias sugerencias de la IA, entre ellas:
- Implementaciones de RabbitMQ que utilizaban `SimpleMessageListenerContainer` en lugar de la configuración recomendada con `RabbitListener` + `RabbitTemplate` (menos mantenible y más propensa a errores).
- Algunas propuestas de configuración de Testcontainers que no respetaban el patrón `@ServiceConnection` o que generaban tests frágiles.
- Código de scheduler que utilizaba `fixedRate` en lugar de `@Scheduled(cron = ...)` , el cual da mayor control.

### Validación del código generado

- Revisión manual y refactorización para cumplir con la arquitectura hexagonal solicitada, principios SOLID y estandares de calidad del proyecto.


