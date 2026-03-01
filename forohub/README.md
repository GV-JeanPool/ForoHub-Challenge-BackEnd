# ForoHub - API REST Backend

## Descripción del Proyecto

ForoHub es una API REST profesional desarrollada con Java 17 y Spring Boot 3.5.11 para la gestión de un sistema de foros, inspirado en el foro de Alura. Permite realizar operaciones CRUD completas sobre tópicos, con autenticación JWT y persistencia en MySQL.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.11**
- **Spring Security** (Autenticación y Autorización)
- **JWT** (JSON Web Tokens)
- **Spring Data JPA** (Persistencia)
- **Flyway** (Migraciones de base de datos)
- **MySQL 8.x**
- **Maven**
- **Lombok**

## ⚙️ Configuración del Entorno

### Prerequisites

- Java 17 o superior
- Maven 3.8+
- MySQL 8.x

### Configuración de Base de Datos

El proyecto está configurado para usar MySQL en el puerto 3307:

```
properties
spring.datasource.url=jdbc:mysql://localhost:3307/forohub
spring.datasource.username=root
spring.datasource.password=root
```

### Configuración JWT

```
properties
jwt.secret=clave_super_secreta_para_el_proyecto_de_forohub_alura_2026
jwt.expiration=3600000
```

## 📋 Endpoints de la API

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/login` | Iniciar sesión y obtener token JWT |
| POST | `/auth/register` | Registrar nuevo usuario |

### Tópicos (CRUD)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/topicos` | Listar todos los tópicos (con paginación) |
| GET | `/topicos/{id}` | Obtener un tópico específico |
| POST | `/topicos` | Crear un nuevo tópico |
| PUT | `/topicos/{id}` | Actualizar un tópico existente |
| DELETE | `/topicos/{id}` | Eliminar un tópico |

### Parámetros de Query

- `page`: Número de página (0-indexed)
- `size`: Tamaño de página
- `sort`: Campo de ordenamiento (ej: `fechaCreacion`)
- `curso`: Filtrar por nombre de curso

## 📥 Ejemplos de Requests y Responses

### Registrar Usuario

**Request:**
```
json
POST /auth/register
{
  "nombre": "Jean Pool",
  "correoElectronico": "jean@ejemplo.com",
  "contrasena": "password123"
}
```

**Response:**
```
json
{
  "id": 1,
  "nombre": "Jean Pool",
  "correoElectronico": "jean@ejemplo.com",
  "perfiles": ["ROLE_USER", "ROLE_ADMIN"]
}
```

### Iniciar Sesión

**Request:**
```
json
POST /auth/login
{
  "correoElectronico": "jean@ejemplo.com",
  "contrasena": "password123"
}
```

**Response:**
```
json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

### Crear Tópico

**Request:**
```
json
POST /topicos
Authorization: Bearer {TOKEN}

{
  "titulo": "Cómo usar Spring Boot?",
  "mensaje": "Necesito ayuda para comenzar con Spring Boot",
  "cursoId": 1
}
```

**Response (201 Created):**
```
json
{
  "id": 1,
  "titulo": "Cómo usar Spring Boot?",
  "mensaje": "Necesito ayuda para comenzar con Spring Boot",
  "fechaCreacion": "2026-01-15T10:30:00",
  "status": "NO_RESPONDIDO",
  "autorNombre": "Jean Pool",
  "autorCorreo": "jean@ejemplo.com",
  "cursoNombre": "Java Spring Boot",
  "cursoCategoria": "Backend"
}
```

### Listar Tópicos

**Request:**
```
http
GET /topicos?page=0&size=10&sort=fechaCreacion,asc
Authorization: Bearer {TOKEN}
```

**Response:**
```
json
{
  "content": [...],
  "totalElements": 50,
  "totalPages": 5,
  "size": 10,
  "number": 0
}
```

## 🏗️ Diagrama de Base de Datos

```
┌─────────────┐       ┌─────────────┐
│   curso     │       │   perfil    │
├─────────────┤       ├─────────────┤
│ id (PK)     │       │ id (PK)     │
│ nombre      │       │ nombre      │
│ categoria   │       └─────────────┘
└─────────────┘            │
                           │
┌─────────────┐            │
│  usuario    │            │
├─────────────┤       ┌──────────────────┐
│ id (PK)     │       │ usuario_perfil   │
│ nombre      │       ├──────────────────┤
│ correo      │       │ usuario_id (FK)  │
│ contrasena  │◄──────│ perfil_id (FK)   │
└─────────────┘       └──────────────────┘
       │
       │                 ┌─────────────┐
       │                 │   topico    │
       │                 ├─────────────┤
       │                 │ id (PK)     │
       │                 │ titulo      │
       │                 │ mensaje     │
       │                 │ fecha_creacion
       │                 │ status      │
       │                 │ autor_id(FK)│
       └───────────────►│ curso_id(FK)│
                         └─────────────┘
                                │
                                │                 ┌─────────────┐
                                │                 │  respuesta  │
                                │                 ├─────────────┤
                                └───────────────►│ id (PK)     │
                                                 │ mensaje     │
                                                 │ topico_id   │
                                                 │ fecha_creac │
                                                 │ autor_id    │
                                                 │ solucion    │
                                                 └─────────────┘
```

## 🔒 Seguridad

- Autenticación basada en JWT (JSON Web Tokens)
- Contraseñas encriptadas con BCrypt
- Roles: ROLE_USER, ROLE_ADMIN
- Endpoints protegidos con Spring Security

## 📦 Cómo Ejecutar el Proyecto

1. **Clonar el repositorio:**
   
```
bash
   git clone https://github.com/GV-JeanPool/ForoHub-Challenge-BackEnd.git
   cd forohub
   
```

2. **Configurar MySQL:**
   - Asegurarse de tener MySQL corriendo en el puerto 3307
   - Crear la base de datos:
   
```
sql
   CREATE DATABASE forohub;
   
```

3. **Compilar y ejecutar:**
   
```
bash
   ./mvnw spring-boot:run
   
```

4. **La API estará disponible en:**
   
```
   http://localhost:8080
   
```

## 🔑 Cómo Generar Token JWT

1. Registrar un usuario:
   
```
bash
   curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{"nombre":"Usuario","correoElectronico":"test@test.com","contrasena":"123456"}'
   
```

2. Iniciar sesión para obtener el token:
   
```
bash
   curl -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{"correoElectronico":"test@test.com","contrasena":"123456"}'
   
```

3. Usar el token en las requests:
   
```
bash
   curl -X GET http://localhost:8080/topicos \
     -H "Authorization: Bearer {TOKEN}"
   
```

## 📝 Validaciones Implementadas

- Todos los campos son obligatorios
- No se permiten tópicos duplicados (título + mensaje)
- Solo usuarios autenticados pueden interactuar con la API
- Validación de correo electrónico

## 📊 Códigos de Respuesta HTTP

| Código | Descripción |
|--------|-------------|
| 200 OK | Solicitud exitosa |
| 201 Created | Recurso creado exitosamente |
| 204 No Content | Eliminación exitosa |
| 400 Bad Request | Error de validación |
| 401 Unauthorized | No autenticado |
| 404 Not Found | Recurso no encontrado |
| 500 Internal Server Error | Error del servidor |

## 📄 Licencia

Este proyecto es parte del Challenge de Alura - ONE (Oracle Next Education).

---

**Autor:** GV-JeanPool  
**Versión:** 1.0.0
