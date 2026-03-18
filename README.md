# 🎬 Microservices Movies App (en desarrollo)

## 🚀 Descripción
Aplicación backend basada en arquitectura de microservicios desarrollada con Spring Boot.

Permite a los usuarios autenticarse y gestionar películas, incluyendo valoraciones, visualizaciones y comentarios.

---

## 🧠 ¿Qué demuestra este proyecto?
- Arquitectura de microservicios
- Autenticación con JWT
- Comunicación entre servicios
- Diseño de APIs REST
- Separación de responsabilidades (controller, service, repository)

---

## 🧱 Arquitectura

Servicios:

- 🧑 Users Service → autenticación y gestión de usuarios
- 🎥 Movies Service → gestión de películas y ratings
- 💬 Comments Service → comentarios (en desarrollo)

Flujo:
1. Usuario se autentica → recibe JWT
2. JWT se usa en Movies para identificar usuario
3. Movies gestiona lógica de negocio

---

## ⚙️ Tecnologías
- Java
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- SQL

---

## 📡 Endpoints principales

### Users
- POST /auth/login
- POST /auth/register

### Movies
## 📡 Endpoints – Movies Service

### 🔐 Autenticación requerida
La mayoría de endpoints requieren un JWT válido en la cabecera:
Authorization: Bearer <token>

---

## 🎬 Películas

### 📥 Obtener todas las películas
- **GET** `/api/movies`
- 🔐 Requiere autenticación

### 🔍 Obtener detalles de una película
- **GET** `/api/movies/details/{id}`
- 🔐 Requiere autenticación

### ➕ Crear una película
- **POST** `/api/movies`
- 🔐 Requiere autenticación
- 📦 Body: `MovieRequestDTO`

### ✏️ Actualizar película
- **PUT** `/api/movies/{id}`
- 🔐 Requiere rol: `ADMIN`
- 📦 Body: `MovieRequestDTO`

### ❌ Eliminar película
- **DELETE** `/api/movies/{id}`
- 🔐 Requiere rol: `ADMIN`

---

## ⭐ Interacciones con películas

### 👍 Votar una película
- **PUT** `/api/movies/{id}/vote`
- 🔐 Requiere autenticación
- 📦 Body: `VoteRequest`
- 📊 Devuelve media de votos y total

---

### 👁️ Marcar película como vista
- **PUT** `/api/movies/{idMovie}/saw`
- 🔐 Requiere autenticación

---

### 👥 Obtener usuarios que han visto una película
- **GET** `/api/movies/{idMovie}/users`
- 🔐 Requiere autenticación
- 📥 Header: Authorization (token)

---

## ▶️ Cómo ejecutar

1. Clonar repositorio
2. Configurar base de datos
3. Ejecutar cada microservicio:
   - users-service
   - movies-service
   - comments-service
4. Probar con Postman

---

## 📌 Estado
En desarrollo – añadiendo microservicio de comentarios

---

## 👨‍💻 Autor
Antonio – Backend Developer Junior
