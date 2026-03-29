# SoloLink API RESTful — Sistema de Reservas para Profesores

API RESTful desarrollada en **Java y Spring Boot** para gestionar perfiles de profesores, disponibilidad de horarios y reservas de clases particulares. El sistema permite a los profesores ofrecer sus servicios de manera segura, con transparencia en los pagos y sin necesidad de logins para sus Alumnos.

---

## Tecnologías

| Área | Stack |
|---|---|
| Backend | Java 24, Spring Boot 3 |
| Seguridad | Spring Security, JWT, BCrypt |
| Base de datos | PostgreSQL, Spring Data JPA, Hibernate |
| Arquitectura | Capas (Controllers / Services / Repositories), patrón DTO |
| Validaciones | Spring Boot Starter Validation |

---

## Funcionalidades — Fase 1

### Autenticación y Seguridad

- Login con generación de tokens JWT.
- Rutas privadas protegidas mediante filtros personalizados (`JwtAuthenticationFilter`).
- Contraseñas encriptadas en base de datos con BCrypt.
- Identidad del usuario extraída directamente del token (`Principal`), eliminando vulnerabilidades de autorización por URL (BOLA).

### Motor de Disponibilidad y Slots

- Algoritmo que interpreta reglas generales de disponibilidad (por ejemplo, "Lunes de 14 a 18 hs") y genera bloques de exactamente una hora.
- Intersección inteligente: los turnos generados se cruzan con las reservas existentes y los horarios ocupados se ocultan automáticamente.

### Panel de Gestión del Profesor

- Endpoints privados para actualizar la descripción del perfil público.
- Listado de todas las reservas de alumnos ordenadas por fecha próxima.
- Actualización del estado de reservas: `PENDING`, `CONFIRMED`, `CANCELLED`.

---

## API Endpoints

### Rutas Públicas

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/public/teachers/{publicId}` | Perfil público del profesor |
| `GET` | `/public/teachers/{publicId}/slots?date=YYYY-MM-DD` | Horarios disponibles de un día |
| `POST` | `/public/teachers/{publicId}/bookings` | Crea una reserva para un alumno |
| `POST` | `/api/teachers` | Registro de nuevo profesor |
| `POST` | `/api/auth/login` | Inicio de sesión — devuelve token JWT |

### Rutas Privadas (requieren token JWT)

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/teachers/me/availabilities` | Agrega una regla de disponibilidad horaria |
| `GET` | `/api/teachers/me/bookings` | Lista todas las reservas recibidas |
| `PATCH` | `/api/teachers/me/bookings/{id}/status` | Actualiza el estado de una reserva |
| `PATCH` | `/api/teachers/me/profile` | Actualiza la descripción pública del perfil |

---

## Instalación Local

1. Clonar el repositorio.
2. Configurar las credenciales de PostgreSQL en `application.yml`.
3. Ejecutar con Maven:

```bash
./mvnw spring-boot:run
```

---

## Roadmap

- [ ] Cliente frontend con React y Vite.
- [ ] Integración de pasarela de pagos (MercadoPago).
- [ ] Almacenamiento en la nube para fotos de perfil (Amazon S3 o Cloudinary).
