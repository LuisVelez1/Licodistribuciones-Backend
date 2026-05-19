# 🏢 Backend Intranet - Licodistribuidores

API REST desarrollada con **Spring Boot** para gestionar los módulos principales de una **intranet corporativa**, incluyendo:

* 🔐 Autenticación de usuarios
* 🏢 Gestión de empresas
* 🌎 Ciudades
* 🏭 Centros de operación
* 📂 Áreas
* 👔 Cargos
* 👥 Usuarios

El sistema está diseñado con **arquitectura modular**, separación de responsabilidades y buenas prácticas en desarrollo backend.

---

# ⚙️ Tecnologías Utilizadas

* ☕ Java 21+
* 🌱 Spring Boot
* 🔐 Spring Security
* 🪪 JWT (Auth0)
* 🗄 Spring Data JPA
* 🐘 PostgreSQL / MySQL
* 📦 Maven

---

# 📁 Estructura del Proyecto

```
src/main/java/com/lico/intranet/
│
├── BackendIntranetApplication.java
│
├── config/                          # Configuración global
│   ├── SecurityConfig.java          # Spring Security + filtros
│   ├── JwtConfig.java               # Propiedades JWT (@ConfigurationProperties)
│   └── CorsConfig.java              # CORS para el frontend
│
├── shared/                          # Código reutilizable entre módulos
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ResourceNotFoundException.java
│   │   └── BusinessException.java
│   │
│   ├── response/
│   │   └── ApiResponse.java         # Wrapper estándar { data, message, status }
│   │
│   └── security/
│       ├── JwtFilter.java           # Filtro JWT (OncePerRequestFilter)
│       ├── JwtService.java          # Generar/validar tokens con Auth0
│       └── UserDetailsServiceImpl.java
│
├── auth/                            # Módulo de autenticación
│   ├── AuthController.java          # POST /api/auth/login
│   ├── AuthService.java
│   └── dto/
│       ├── LoginRequest.java
│       └── LoginResponse.java       # { token, usuario }
│
├── empresa/                         # Módulo empresas
│   ├── Empresa.java
│   ├── EmpresaRepository.java
│   ├── EmpresaService.java
│   ├── EmpresaController.java       # /api/empresas
│   └── dto/
│
├── ciudad/                          # Módulo ciudades
│   ├── Ciudad.java
│   ├── CiudadRepository.java
│   ├── CiudadService.java
│   ├── CiudadController.java        # /api/ciudades
│   └── dto/
│
├── centro/                          # Módulo centros de operación
│   ├── CentroOperacion.java
│   ├── CentroOperacionRepository.java
│   ├── CentroOperacionService.java
│   ├── CentroOperacionController.java  # /api/centros
│   └── dto/
│
├── area/                            # Módulo áreas
│   ├── Area.java
│   ├── AreaRepository.java
│   ├── AreaService.java
│   ├── AreaController.java          # /api/areas
│   └── dto/
│
├── cargo/                           # Módulo cargos
│   ├── Cargo.java
│   ├── CargoRepository.java
│   ├── CargoService.java
│   ├── CargoController.java         # /api/cargos
│   └── dto/
│
└── usuario/                         # Módulo usuarios
    ├── Usuario.java
    ├── UsuarioRepository.java
    ├── UsuarioService.java
    ├── UsuarioController.java        # /api/usuarios
    └── dto/
        ├── UsuarioRequest.java
        ├── UsuarioResponse.java
        └── ChangePasswordRequest.java
```

---

# 🔐 Autenticación

La API utiliza **JWT (JSON Web Token)** para autenticar usuarios.

## Endpoint Login

```
POST /api/auth/login
```

### Request

```json
{
  "email": "usuario@empresa.com",
  "password": "123456"
}
```

### Response

```json
{
  "token": "jwt_token",
  "usuario": {
    "id": 1,
    "nombre": "Luis",
    "email": "usuario@empresa.com"
  }
}
```

El token debe enviarse en cada petición protegida:

```
Authorization: Bearer TOKEN
```

---

# 📦 Formato de Respuesta API

Todas las respuestas utilizan un formato estándar:

```json
{
  "status": 200,
  "message": "Operación exitosa",
  "data": {}
}
```

Esto facilita la integración con el frontend.

---

# 📚 Endpoints Principales

| Módulo               | Endpoint        |
| -------------------- | --------------- |
| Auth                 | `/api/auth`     |
| Empresas             | `/api/empresas` |
| Ciudades             | `/api/ciudades` |
| Centros de operación | `/api/centros`  |
| Áreas                | `/api/areas`    |
| Cargos               | `/api/cargos`   |
| Usuarios             | `/api/usuarios` |

Todos los módulos implementan **operaciones CRUD**.

---

# 🚀 Ejecución del Proyecto

## 1️⃣ Clonar repositorio

```bash
git clone https://github.com/tu-repo/backend-intranet.git
```

---

## 2️⃣ Configurar base de datos

Editar el archivo:

```
application.yml
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/intranet
spring.datasource.username=luis
spring.datasource.password=mysql123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 3️⃣ Ejecutar la aplicación

```bash
mvn spring-boot:run
```

o

```bash
./mvnw spring-boot:run
```

La API quedará disponible en:

```
http://localhost:8080
```

---

# 🧠 Buenas Prácticas Implementadas

✔ Arquitectura modular por dominio
✔ Separación Controller / Service / Repository
✔ Uso de DTOs
✔ Manejo global de excepciones
✔ Seguridad con JWT
✔ Respuestas API estandarizadas
✔ Código reutilizable en módulo `shared`

---

# 🔮 Mejoras Futuras

* 📚 Documentación con **Swagger / OpenAPI**
* 🔑 Sistema de **roles y permisos**
* 📎 Upload de archivos
* 📰 Gestión de **noticias internas**
* 💻 Gestión de **requerimientos**
* 🧾 Auditoría de cambios

---

# 👨‍💻 Autor

**Luis Eduardo Vélez**

Backend desarrollado como parte de un sistema de **Intranet Corporativa**.
