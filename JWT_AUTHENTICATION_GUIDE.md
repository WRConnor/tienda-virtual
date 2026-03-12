# Sistema de Autenticación JWT - Tienda Genérica

## 📋 Descripción General

Se ha implementado un sistema de autenticación basado en JWT (JSON Web Tokens) que:
- Requiere login para acceder a cualquier microservicio a través del API Gateway
- Soporta dos tipos de usuarios: **USER** (lectura) y **ADMIN** (acceso total CRUD)
- Mantiene la arquitectura simple y centralizada

## 🔐 Flujo de Autenticación

```
1. Cliente
   ↓
2. POST /api/usuarios/login (usuario, password)
   ↓
3. Usuario-Service valida credenciales
   ↓
4. Devuelve JWT Token + Rol
   ↓
5. Cliente incluye token en header: Authorization: Bearer <token>
   ↓
6. API Gateway valida el token con cada solicitud
   ↓
7. Si es válido → solicitud permitida
   Si NO es válido → 401 Unauthorized
```

## 🚀 Cómo Usar

### 1. **Login**

```bash
POST http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMIN"
}
```

### 2. **Usar el Token en Solicitudes**

Para cualquier solicitud a otros microservicios, include el token en el header:

```bash
curl -H "Authorization: Bearer <tu_token_aqui>" \
  http://localhost:8080/api/productos/mostrartodo
```

### 3. **Crear Nuevo Usuario**

Este es un endpoint público para permitir registro:

```bash
POST http://localhost:8080/api/usuarios/crear
Content-Type: application/json

{
  "cedulaUsuario": 1234567890,
  "nombreUsuario": "Juan Pérez",
  "emailUsuario": "juan@example.com",
  "usuario": "juan",
  "password": "pass123",
  "rol": "USER"  // USER o ADMIN (default es USER)
}
```

## 🔑 Rutas Públicas (sin token requerido)

- `POST /api/usuarios/login` - Login
- `POST /api/usuarios/crear` - Crear nuevo usuario
- `GET /api/usuarios/v3/api-docs` - Documentación API

## 🛡️ Rutas Protegidas (requieren token)

Estas rutas **requieren** un JWT válido en el header `Authorization`:

- `/api/usuarios/**` - Todas excepto login y crear
- `/api/clientes/**` - Cliente Service
- `/api/productos/**` - Producto Service
- `/api/proveedores/**` - Proveedor Service
- `/api/ventas/**` - Venta Service
- `/api/detalles-venta/**` - Detalle Venta Service

## 👥 Tipos de Usuario

### USER (Lectura)
- ✅ Puede ver datos (`GET`)
- ❌ No puede crear, editar o eliminar
- En futuro: se permiten permisos más granulares

### ADMIN (Control Total)
- ✅ Acceso completo a CRUD (CREATE, READ, UPDATE, DELETE)
- ✅ Puede administrar usuarios

## 📋 Detalles Técnicos

### JWT Details
- **Algoritmo:** HS256 (HMAC with SHA-256)
- **Expiración:** 24 horas (86400000 ms)
- **Secret Key:** Configurado en `application.properties`

### Ubicación de Configuración

**Usuario Service** ([usuario-service/src/main/resources/application.properties](usuario-service/src/main/resources/application.properties)):
```properties
jwt.secret=mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequiredForHS256
jwt.expiration=86400000  # 24 horas en ms
```

**API Gateway** ([api-gateway/src/main/resources/application.properties](api-gateway/src/main/resources/application.properties)):
```properties
jwt.secret=mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequiredForHS256
```

⚠️ **IMPORTANTE:** Las claves secretas deben ser idénticas en ambos servicios.

### Clases Principales

**Usuario Service:**
- [JwtService](usuario-service/src/main/java/co/edu/unbosque/usuario/service/JwtService.java) - Generador y validador de JWT
- [UsuarioController](usuario-service/src/main/java/co/edu/unbosque/usuario/controller/UsuarioController.java) - Endpoint de login

**API Gateway:**
- [JwtUtil](api-gateway/src/main/java/co/edu/unbosque/gateway/util/JwtUtil.java) - Utilidad para validar tokens
- [JwtAuthenticationFilter](api-gateway/src/main/java/co/edu/unbosque/gateway/filter/JwtAuthenticationFilter.java) - Filtro que valida JWT
- [GatewayConfiguration](api-gateway/src/main/java/co/edu/unbosque/gateway/config/GatewayConfiguration.java) - Configuración de rutas y filtros

## 🧪 Ejemplo de Uso Completo

```bash
# 1. Login
TOKEN=$(curl -s -X POST "http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123" \
  | jq -r '.token')

echo "Token: $TOKEN"

# 2. Usar token para obtener productos
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/productos/mostrartodo

# 3. Crear nuevo usuario (sin token - endpoint público)
curl -X POST http://localhost:8080/api/usuarios/crear \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaUsuario": 9876543210,
    "nombreUsuario": "María García",
    "emailUsuario": "maria@example.com",
    "usuario": "maria",
    "password": "pass456",
    "rol": "USER"
  }'

# 4. Actualizar usuario (requiere token)
curl -X PUT "http://localhost:8080/api/usuarios/actualizar/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaUsuario": 1234567890,
    "nombreUsuario": "Juan Carlos",
    "emailUsuario": "juan.carlos@example.com",
    "usuario": "juan",
    "password": "newpass123",
    "rol": "USER"
  }'
```

## ⚙️ Cambios Realizados

### 1. **Usuario Model** 
   - ✅ Agregado campo `rol` (USER o ADMIN)

### 2. **Usuario Service**
   - ✅ Agregado `JwtService` para generar/validar tokens
   - ✅ Modificado `login()` para devolver Usuario completo
   - ✅ Agregado `RequestParam` en controlador para parámetros GET

### 3. **API Gateway**
   - ✅ Agregado `JwtUtil` para validar tokens
   - ✅ Agregado `JwtAuthenticationFilter` para interceptar solicitudes
   - ✅ Agregado `GatewayConfiguration` para aplicar filtro globalmente
   - ✅ Definidas rutas públicas (login, crear, docs)

### 4. **Dependencias Maven**
   - ✅ Agregado JJWT (JSON Web Token library) en ambos servicios

## 📝 Próximas Mejoras (Futuro)

- [ ] Agregar permisos más granulares por endpoint
- [ ] Implementar refresh tokens
- [ ] Agregar rate limiting
- [ ] Implementar logout (blacklist de tokens)
- [ ] Agregar auditoría de accesos
- [ ] Integrar con OAuth2/OIDC

## ❓ Preguntas Frecuentes

**P: ¿Qué pasa si envío un token inválido?**
R: Recibirás un error 401 Unauthorized desde el API Gateway.

**P: ¿Cuánto tiempo dura el token?**
R: 24 horas. Después debes hacer login nuevamente.

**P: ¿Puedo modificar la duración del token?**
R: Sí, cambia `jwt.expiration` en `application.properties` (en ms).

**P: ¿Dónde se almacenan las contraseñas?**
R: En la base de datos MariaDB. Actualmente sin encriptación - considera agregar BCrypt en producción.

---

**Creado:** Marzo 4, 2026  
**Última actualización:** Marzo 4, 2026
