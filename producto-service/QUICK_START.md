# Guía Rápida - Sistema de Autenticación JWT

## 🚀 Inicio Rápido (5 minutos)

### 1. **Construir y Arrancar Servicios**

```bash
# Asegúrate de que todos los servicios están ejecutándose:
# - Eureka Server (puerto 8761)
# - Usuario Service (puerto 8081)
# - API Gateway (puerto 8080)
# - Otros microservicios...

# Si usas Maven:
mvn clean install

# Inicia cada servicio en una terminal diferente:
# Terminal 1: cd eureka-server && mvn spring-boot:run
# Terminal 2: cd usuario-service && mvn spring-boot:run
# Terminal 3: cd api-gateway && mvn spring-boot:run
```

### 2. **Crear un Usuario Admin (si no existe)**

```bash
curl -X POST http://localhost:8080/api/usuarios/crear \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaUsuario": 1234567890,
    "nombreUsuario": "Administrador",
    "emailUsuario": "admin@tienda.com",
    "usuario": "admin",
    "password": "admin123",
    "rol": "ADMIN"
  }'
```

### 3. **Obtener Token JWT**

```bash
curl -X POST "http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123"
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMIN"
}
```

**Guarda el token en una variable:**
```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 4. **Usar el Token**

Cualquier solicitud a los microservicios debe incluir:

```bash
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/productos/mostrartodo
```

---

## 🔑 Endpoints Públicos (Sin Token)

| Método | Ruta | Descripción |
|--------|------|------------|
| POST | `/api/usuarios/login` | Login y obtener token |
| POST | `/api/usuarios/crear` | Registrar nuevo usuario |
| GET | `/api/usuarios/v3/api-docs` | Documentación OpenAPI |

---

## ✅ Endpoints Protegidos (Requieren Token)

**Agregar header a TODAS estas solicitudes:**
```
Authorization: Bearer <tu_token>
```

| Método | Ruta | 
|--------|------|
| GET | `/api/usuarios/mostrartodo` |
| PUT | `/api/usuarios/actualizar/{id}` |
| DELETE | `/api/usuarios/eliminar/{id}` |
| GET | `/api/clientes/**` |
| GET/POST | `/api/productos/**` |
| GET/POST | `/api/proveedores/**` |
| GET/POST | `/api/ventas/**` |
| GET/POST | `/api/detalles-venta/**` |

---

## 🧪 Script de Prueba Completo

```bash
#!/bin/bash

# 1. Login
echo "=== 1. LOGIN ==="
RESPONSE=$(curl -s -X POST "http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123")
TOKEN=$(echo $RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
ROL=$(echo $RESPONSE | grep -o '"rol":"[^"]*' | cut -d'"' -f4)

echo "Token: $TOKEN"
echo "Rol: $ROL"
echo ""

# 2. Ver usuarios (con token)
echo "=== 2. VER USUARIOS (CON TOKEN) ==="
curl -s -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/usuarios/mostrartodo | jq .

echo ""
echo "=== 3. INTENTAR SIN TOKEN (debe fallar con 401) ==="
curl -s http://localhost:8080/api/usuarios/mostrartodo | jq .

echo ""
echo "=== 4. TOKEN INVÁLIDO (debe fallar con 401) ==="
curl -s -H "Authorization: Bearer TOKEN_INVALIDO" \
  http://localhost:8080/api/usuarios/mostrartodo | jq .
```

---

## 🛑 Errores Comunes

### 401 Unauthorized
**Causa:** Token inválido o expirado
**Solución:** 
- Verifica que estés incluyendo el header `Authorization: Bearer <token>`
- El token dura 24 horas
- Haz login nuevamente para obtener un nuevo token

### 404 Not Found
**Causa:** Ruta incorrecta
**Solución:** 
- Verifica la ruta exacta del endpoint
- Recuerda que TODO va a través del API Gateway (puerto 8080)

### 500 Internal Server Error
**Causa:** error en el servicio
**Solución:**
- Revisa los logs del microservicio correspondiente
- Verifica que la base de datos está accesible

---

## 📝 Cambios Realizados Resumen

✅ **Usuario Model:** Agregado campo `rol`  
✅ **Usuario Service:** Agregado `JwtService` y modificado login  
✅ **API Gateway:** Agregado filtro JWT global  
✅ **Dependencias:** JJWT agregado a pom.xml  
✅ **Configuración:** JWT secret en `application.properties`

---

## 🔐 Seguridad

⚠️ **Antes de Producción:**

1. **Cambiar JWT Secret** - Usa una clave larga y aleatoria:
   ```properties
   jwt.secret=tu_clave_super_segura_muy_larga_y_aleatoria_aqui
   ```

2. **Encriptar Contraseñas** - Implementa BCrypt en `UsuarioService`:
   ```java
   BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
   usuario.setPassword(encoder.encode(usuario.getPassword()));
   ```

3. **HTTPS** - Usa certificados SSL en producción

4. **CORS** - Usa originenes específicas (no `"*"`)

---

## 📚 Documentación Adicional

- [JWT_AUTHENTICATION_GUIDE.md](JWT_AUTHENTICATION_GUIDE.md) - Guía completa
- [ROLES_VALIDATION_OPTIONAL.md](ROLES_VALIDATION_OPTIONAL.md) - Cómo validar roles en microservicios

---

**¿Listo? ¡Comienza con el paso 1 y prueba login!** 🚀
