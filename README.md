# 🎓 EduManager - Sistema de Gestión Académica

API y plataforma web desarrollada con **Java y Spring Boot** para la gestión de instituciones educativas, permitiendo administrar cursos, estudiantes, inscripciones y calificaciones.

Este proyecto demuestra el desarrollo de aplicaciones backend utilizando **arquitectura por capas, APIs REST, Spring Security y JPA**, aplicando buenas prácticas utilizadas en entornos empresariales.

---

## 🚀 Funcionalidades Principales

### 🛠️ Módulo Administrativo (Admin)
- **Control Total (CRUD):** Crear, editar y eliminar Cursos y Estudiantes.
- **Gestión de Inscripciones:** Aprobar o rechazar solicitudes de alumnos con comentarios/motivos personalizados.
- **Sistema de Calificaciones:** Registrar evaluaciones detalladas (Nombre del test y puntaje).
- **Monitorización:** Vista global de usuarios registrados y boletín histórico de notas.

### 👤 Módulo del Estudiante (User)
- **Catálogo de Cursos:** Visualización de oferta académica disponible.
- **Inscripciones en Tiempo Real:** Solicitar ingreso a cursos y seguir el estado de la solicitud (Pendiente/Aprobado/Rechazado).
- **Boletín Personal:** Acceso inmediato a las calificaciones obtenidas.

---

## 🔐 Seguridad y Roles

El sistema implementa **Spring Security** con una arquitectura híbrida (Base de Datos + Memoria):

| Usuario | Username | Password | Rol | Permisos |
| :--- | :--- | :--- | :--- | :--- |
| **Administrador** | `admin` | `admin123` | `ROLE_ADMIN` | Acceso total al panel y gestión. |
| **Estudiante** | `[email]` | `user123` | `ROLE_USER` | Inscripciones y ver notas. |

---

## 📡 API REST - Documentación de Endpoints (v1)

La API permite la integración con sistemas externos y soporta operaciones de lectura y escritura.

### 📖 Consultas (GET)
- `GET /api/v1/cursos`: Lista cursos con sus alumnos aprobados.
- `GET /api/v1/estudiantes`: Lista estudiantes con su historial de notas.
- `GET /api/v1/admin/pendientes`: Lista solicitudes de inscripción por procesar.

### ✍️ Acciones (POST)
- `POST /api/v1/estudiantes`: Registra un nuevo alumno. 
  - *Body (JSON):* `{"nombre": "Juan", "email": "juan@mail.com"}`
- `POST /api/v1/inscripciones/gestionar`: Aprueba/Rechaza solicitudes.
  - *Params:* `id`, `estado` (APROBADO/RECHAZADO), `motivo`.
- `POST /api/v1/notas`: Registra una nueva calificación.
  - *Params:* `estudianteId`, `cursoId`, `nombreTest`, `nota`.

---

## 🛠️ Stack Tecnológico
- **Lenguaje:** JavaSE-21
- **Framework:** Spring Boot 3.x
- **Seguridad:** Spring Security (Auth basada en Formulario y API)
- **Persistencia:** Spring Data JPA + H2 Database (In-Memory)
- **Frontend:** Thymeleaf + Bootstrap 5 (Responsive Design)
- **Arquitectura:** MVC + DTOs para la API

---

## ⚙️ Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Hatecrew171/SpringEduManager/

2. **Importar en IDE: Importar como proyecto Maven en Eclipse o IntelliJ.**

3.- **Ejecutar: Correr la clase SpringEduManagerApplication.java.**

4.- **Acceder: Abrir http://localhost:8080 en el navegador.**
