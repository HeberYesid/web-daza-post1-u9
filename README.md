# 🔐 Sistema de Autenticación con Spring Security 6

**Programación Web — Unidad 9: Seguridad en Aplicaciones Web**  
Post-Contenido 1 — Ingeniería de Sistemas 2026

---

## 📋 Descripción

Sistema de autenticación completo implementado con **Spring Security 6**, **Spring Boot 3.4**, **JPA + MySQL** y **Thymeleaf**. Incluye:

- Registro de usuarios con contraseñas hasheadas con **BCryptPasswordEncoder** (strength 12)
- Login basado en formulario con `UserDetailsService` que consulta MySQL
- Autorización diferenciada por roles **ADMIN** y **USER** con rutas protegidas
- Panel de administración solo accesible para `ROLE_ADMIN`
- Logout con invalidación de sesión y eliminación de cookies

---

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura en capas** clara:

```
com.universidad.seguridad/
├── config/           → SecurityConfig (SecurityFilterChain, PasswordEncoder)
├── controller/       → AuthController (login, registro, dashboard, admin)
├── model/            → Usuario (@Entity JPA)
├── repository/       → UsuarioRepository (JpaRepository)
├── service/          → UsuarioService, UsuarioDetailsService (UserDetailsService)
└── SeguridadApplication.java
```

---

## 🔧 Prerrequisitos

| Herramienta | Versión mínima |
|-------------|---------------|
| Java JDK    | 21            |
| Maven       | 3.9+          |
| MySQL       | 8.0+          |

---

## ⚙️ Configuración de MySQL

### 1. Crear la base de datos y el usuario

```sql
-- Conectarse como root
mysql -u root -p

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS estudiantes_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- Crear usuario de la aplicación
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'appuser123';
GRANT ALL PRIVILEGES ON estudiantes_db.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

EXIT;
```

### 2. Verificar la conexión

```bash
mysql -u appuser -p estudiantes_db
# Contraseña: appuser123
```

### 3. Configuración en `application.properties`

El archivo ya está configurado con los valores por defecto:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estudiantes_db?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true
spring.datasource.username=appuser
spring.datasource.password=appuser123
spring.jpa.hibernate.ddl-auto=update
```

> **Nota:** `ddl-auto=update` crea la tabla `usuarios` automáticamente al iniciar la aplicación.

---

## 🚀 Ejecución

### 1. Compilar y ejecutar

```bash
cd seguridad-app
mvn spring-boot:run
```

### 2. Acceder a la aplicación

- **Login:** http://localhost:8080/login
- **Registro:** http://localhost:8080/registro
- **Dashboard:** http://localhost:8080/dashboard (requiere autenticación)
- **Admin:** http://localhost:8080/admin (requiere ROLE_ADMIN)

---

## 👤 Usuarios de Prueba

### Crear usuario ADMIN en MySQL

Primero, generar el hash BCrypt ejecutando el test:

```bash
mvn test -Dtest=GenerarHash#generarHashAdmin
```

Copiar el hash generado y ejecutar en MySQL:

```sql
mysql -u appuser -p estudiantes_db

INSERT INTO usuarios (nombre, email, contrasenia, rol, activo)
VALUES ('Administrador', 'admin@universidad.edu',
        '[PEGAR_HASH_AQUI]', 'ROLE_ADMIN', 1);
EXIT;
```

### Credenciales de prueba

| Rol   | Email                     | Contraseña | Cómo crear                     |
|-------|---------------------------|------------|--------------------------------|
| ADMIN | admin@universidad.edu     | admin123   | INSERT manual en MySQL         |
| USER  | (registrado desde la app) | (elegida)  | Formulario /registro           |

---

## ✅ Checkpoints de Verificación

### Checkpoint 1 — Arranque y redirección
- [ ] La aplicación arranca sin errores
- [ ] Navegar a `/dashboard` redirige a `/login`
- [ ] La página de login muestra el formulario personalizado (no el de Spring por defecto)

### Checkpoint 2 — Registro y autenticación
- [ ] Registrar un nuevo usuario desde `/registro`
- [ ] En MySQL, la contraseña guardada es un hash BCrypt (comienza con `$2a$12$`)
- [ ] Iniciar sesión con las credenciales registradas
- [ ] El dashboard muestra el nombre del usuario
- [ ] Acceder a `/admin` con usuario USER muestra **error 403 Forbidden**

### Checkpoint 3 — Admin y logout
- [ ] Iniciar sesión como `admin@universidad.edu`
- [ ] `/admin` es accesible y muestra la lista de usuarios
- [ ] "Cerrar Sesión" invalida la sesión y redirige a `/login?logout`
- [ ] Acceder a `/dashboard` después del logout redirige a `/login`

---

## 📸 Capturas de Pantalla

Las capturas se encuentran en la carpeta `/capturas`:

1. **Formulario de login** — `capturas/01-login.png`
2. **Dashboard USER** — `capturas/02-dashboard-user.png`
3. **Panel ADMIN** — `capturas/03-admin-panel.png`
4. **Error 403** — `capturas/04-error-403.png`
5. **Contraseña BCrypt en MySQL** — `capturas/05-bcrypt-mysql.png`

---

## 🛡️ Seguridad Implementada

| Característica                        | Estado |
|---------------------------------------|--------|
| Contraseñas hasheadas con BCrypt(12)  | ✅     |
| Form-login personalizado              | ✅     |
| UserDetailsService desde BD           | ✅     |
| Autorización por roles (ADMIN/USER)   | ✅     |
| Protección CSRF automática            | ✅     |
| Invalidación de sesión en logout      | ✅     |
| Eliminación de cookie JSESSIONID      | ✅     |
| Sin contraseñas hardcodeadas          | ✅     |

---

## 📦 Dependencias Principales

- `spring-boot-starter-web` — Servidor web embebido
- `spring-boot-starter-security` — Spring Security 6
- `spring-boot-starter-data-jpa` — JPA + Hibernate
- `mysql-connector-j` — Driver MySQL
- `spring-boot-starter-thymeleaf` — Motor de plantillas
- `thymeleaf-extras-springsecurity6` — Integración Thymeleaf + Security
- `spring-boot-starter-validation` — Bean Validation (Jakarta)

---

## 📝 Commits Sugeridos

1. `feat: agregar dependencias Spring Security y configuración MySQL`
2. `feat: crear entidad Usuario, repositorio y servicio con BCrypt`
3. `feat: implementar controlador, vistas Thymeleaf y SecurityConfig`
