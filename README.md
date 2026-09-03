# Lico Distribuciones — Backend

Backend REST desarrollado con **Java 21 y Spring Boot** para soportar una plataforma web de gestión interna para **Lico Distribuciones**.

La aplicación centraliza diferentes procesos corporativos mediante una API REST, implementando autenticación basada en JWT, persistencia con JPA/Hibernate, validación de datos, manejo de archivos, gestión de usuarios y una arquitectura organizada por capas.

## Overview

El backend proporciona servicios para los principales módulos de la plataforma:

* Autenticación y gestión de sesiones
* Gestión de usuarios
* Gestión de requerimientos
* Comentarios asociados a requerimientos
* Gestión documental
* Gestión de activos fijos
* Noticias internas y comentarios
* Reservas de espacios
* Gestión de áreas y tipos de requerimientos
* Seguridad y autorización mediante JWT
* Monitoreo mediante Spring Boot Actuator

El proyecto está estructurado siguiendo una separación clara de responsabilidades entre **controllers, services, repositories, DTOs, entities y configuración de seguridad**.

---

## Features

### Authentication & Security

* Login mediante API REST.
* Autenticación basada en **JWT**.
* Filtro personalizado para validar tokens.
* Integración con Spring Security.
* Protección de endpoints.
* Gestión de usuarios autenticados.
* Configuración de CORS.

### User Management

Permite administrar los usuarios de la plataforma y sus operaciones asociadas:

* Consulta de usuarios.
* Creación y actualización.
* Cambio de contraseña.
* Gestión de estado.
* Consulta de información del usuario autenticado.

### Requirements

Sistema para gestionar requerimientos internos:

* Creación y consulta de requerimientos.
* Gestión de estados.
* Tipos de requerimientos.
* Asignación y seguimiento.
* Comentarios sobre requerimientos.
* Gestión de requerimientos por áreas.

### Documents

Módulo destinado a la gestión de documentos internos.

Incluye soporte para operaciones relacionadas con archivos mediante `multipart/form-data`.

### Fixed Assets

Módulo para administrar activos fijos de la organización.

Permite centralizar información relacionada con los activos y sus operaciones dentro de la plataforma.

### Internal News

Sistema de noticias internas:

* Publicación y consulta de noticias.
* Gestión de contenido.
* Comentarios asociados a publicaciones.

### Meeting Room Reservations

Sistema de reservas para espacios o salas de reuniones:

* Creación de reservas.
* Consulta de reservas.
* Gestión de disponibilidad.

### Administration

El backend también proporciona servicios para la administración de información utilizada por la plataforma, incluyendo:

* Áreas.
* Tipos de requerimientos.
* Usuarios.
* Configuración relacionada con los módulos internos.

---

## Architecture

El proyecto utiliza una arquitectura backend basada en capas:

```text
Client
   │
   ▼
REST Controllers
   │
   ▼
Services
   │
   ▼
Repositories
   │
   ▼
JPA / Hibernate
   │
   ▼
MySQL
```

La seguridad se integra mediante un flujo independiente:

```text
HTTP Request
     │
     ▼
JWT Authentication Filter
     │
     ▼
Spring Security
     │
     ▼
Protected Controller
     │
     ▼
Service Layer
```

Esta separación permite mantener responsabilidades independientes y facilita la evolución y mantenimiento del sistema.

---

## Project Structure

```text
src/main/java/com/backendintranet/
│
├── BackendIntranetApplication.java
│
├── config/
│   ├── FileConfig.java
│   └── security/
│       ├── CustomUserDetailsService.java
│       ├── JwtAuthenticationFilter.java
│       └── SecurityConfig.java
│
├── controller/
│   ├── AreaController.java
│   ├── AuthController.java
│   ├── DocumentController.java
│   ├── FixedAssetController.java
│   ├── NewsController.java
│   ├── NewsCommentController.java
│   ├── RequirementController.java
│   ├── RequirementCommentController.java
│   ├── RequirementTypeController.java
│   ├── ReservationController.java
│   └── UserController.java
│
├── dto/
│   └── request/
│
├── entity/
│
├── repository/
│
├── service/
│   ├── AreaService.java
│   ├── AuthService.java
│   ├── DocumentService.java
│   ├── FixedAssetService.java
│   ├── NewsService.java
│   ├── NewsCommentService.java
│   ├── RequirementService.java
│   ├── RequirementCommentService.java
│   ├── ReservationService.java
│   ├── UserService.java
│   └── impl/
│
└── ...
```

---

## Technology Stack

| Technology           | Purpose                          |
| -------------------- | -------------------------------- |
| Java 21              | Programming language             |
| Spring Boot 4.0.6    | Backend framework                |
| Spring Web           | REST API                         |
| Spring Security      | Authentication and authorization |
| JJWT 0.12.6          | JWT generation and validation    |
| Spring Data JPA      | Data persistence                 |
| Hibernate            | ORM                              |
| MySQL                | Relational database              |
| Spring Validation    | Request validation               |
| Spring Boot Actuator | Monitoring and health endpoints  |
| Lombok               | Boilerplate reduction            |
| Maven                | Dependency management and build  |
| Docker               | Containerization                 |

Las dependencias principales y versiones se encuentran definidas en `pom.xml`.

---

## API

La aplicación utiliza un contexto base:

```text
/api
```

El servidor está configurado para ejecutarse sobre el puerto:

```text
8081
```

Por lo tanto, localmente la API puede accederse mediante:

```text
http://localhost:8081/api
```

### Main Resources

| Resource             | Responsibility            |
| -------------------- | ------------------------- |
| `/auth`              | Authentication            |
| `/users`             | User management           |
| `/requirements`      | Internal requirements     |
| `/requirement-types` | Requirement categories    |
| `/documents`         | Document management       |
| `/fixed-assets`      | Fixed asset management    |
| `/news`              | Internal news             |
| `/reservations`      | Meeting room reservations |
| `/areas`             | Organizational areas      |

> Los endpoints concretos pueden variar según la operación y configuración actual de cada controller.

---

## Authentication

Las rutas protegidas utilizan autenticación mediante **Bearer Token**.

```http
Authorization: Bearer <JWT>
```

El flujo general es:

```text
POST /api/auth/login
        │
        ▼
Validate credentials
        │
        ▼
Generate JWT
        │
        ▼
Return authentication response
        │
        ▼
Client stores token
        │
        ▼
Authorization: Bearer <JWT>
        │
        ▼
JwtAuthenticationFilter
        │
        ▼
Protected resource
```

La implementación utiliza `JwtAuthenticationFilter`, `CustomUserDetailsService` y `SecurityConfig` dentro de la configuración de seguridad del proyecto.

---

## Database

El backend utiliza **MySQL** como sistema de gestión de base de datos y **Spring Data JPA/Hibernate** para la persistencia.

La configuración actual utiliza:

```text
Database: intranet_corporativa
Driver: MySQL
ORM: Hibernate / JPA
DDL Strategy: validate
```

La aplicación también configura un pool de conexiones mediante HikariCP.

### Database Configuration

Para ejecutar el proyecto localmente es necesario disponer de una instancia MySQL y configurar las credenciales correspondientes.

**No se deben almacenar credenciales reales, secretos JWT ni información sensible directamente en el repositorio.**

---

## File Management

El backend cuenta con soporte para carga de archivos mediante `multipart/form-data`.

La configuración actual contempla:

```text
Maximum file size: 1GB
Maximum request size: 1GB
```

Esta funcionalidad es utilizada por los módulos que requieren gestión documental o archivos asociados.

---

## Monitoring

El proyecto integra **Spring Boot Actuator** para exponer información operacional de la aplicación.

Los endpoints habilitados incluyen:

```text
/actuator/health
/actuator/info
/actuator/metrics
```

Esto permite disponer de información básica para monitoreo y diagnóstico de la aplicación.

---

## Docker

El proyecto incluye un `Dockerfile` basado en un proceso **multi-stage build**.

### Build stage

```text
Maven 3.9.6
Eclipse Temurin 21
```

### Runtime stage

```text
Eclipse Temurin 21 JRE Alpine
```

Además, el contenedor ejecuta la aplicación utilizando un usuario no-root, reduciendo privilegios dentro del entorno de ejecución.

### Build

```bash
docker build -t lico-distribuciones-backend .
```

### Run

```bash
docker run -p 8081:8081 lico-distribuciones-backend
```

---

## Local Development

### Requirements

Antes de ejecutar el proyecto necesitas:

* Java 21
* Maven
* MySQL
* Git

Opcionalmente:

* Docker

### Clone

```bash
git clone https://github.com/LuisVelez1/Licodistribuciones-Backend.git
cd Licodistribuciones-Backend
```

### Configure Database

Configura las credenciales de tu entorno local antes de iniciar la aplicación.

Se recomienda utilizar variables de entorno para:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

### Run with Maven

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

También puedes utilizar Maven directamente:

```bash
mvn spring-boot:run
```

### Build

```bash
./mvnw clean package
```

### Run JAR

```bash
java -jar target/*.jar
```

---

## Engineering Practices

El proyecto aplica diferentes prácticas orientadas al desarrollo de APIs mantenibles:

* Separación de responsabilidades.
* Arquitectura por capas.
* DTOs para transferencia de datos.
* Spring Data JPA.
* Validación de requests.
* Manejo de autenticación mediante JWT.
* Integración con Spring Security.
* Configuración centralizada.
* Uso de servicios para encapsular lógica de negocio.
* Repositories para acceso a datos.
* Spring Boot Actuator para observabilidad.
* Containerización mediante Docker.
* Ejecución del contenedor con usuario no-root.

---

## Future Improvements

Algunas mejoras que pueden incorporarse posteriormente:

* Documentación interactiva mediante Swagger / OpenAPI.
* Tests unitarios y de integración más completos.
* Gestión avanzada de roles y permisos.
* Auditoría de operaciones.
* Centralización de configuración mediante variables de entorno.
* Rate limiting.
* Logging estructurado.
* CI/CD.
* Migraciones de base de datos mediante Flyway o Liquibase.
* Mejoras de observabilidad y métricas.
* Optimización de almacenamiento de archivos.

---

## Related Project

Este backend está diseñado para trabajar junto con el frontend de la plataforma:

**Lico Distribuciones — Frontend**

La separación frontend/backend permite mantener una arquitectura desacoplada y facilita el desarrollo independiente de cada capa.

---

## Project Status

**Active development**

El proyecto continúa evolucionando con la incorporación de nuevos módulos y mejoras en seguridad, administración y gestión de procesos internos.

---

## Author

**Luis Eduardo Vélez**

Software Developer / Systems & Computing Engineering Student

Backend development focused on:

* Java
* Spring Boot
* REST APIs
* Spring Security
* JWT
* JPA / Hibernate
* MySQL
* Docker
* Software architecture
