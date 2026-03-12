# Arquitectura de Autenticación JWT

## 🏗️ Diagrama de Arquitectura General

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENTE                                  │
│                   (Navegador / App)                              │
└──────────────────────────────┬──────────────────────────────────┘
                               │
                ┌──────────────┴──────────────┐
                │ 1. POST /api/usuarios/login │
                │ (usuario + password)        │
                │                             │
                ▼                             │
        ┌──────────────────────────────────┐ │
        │     API GATEWAY (8080)           │ │
        │ ┌──────────────────────────────┐ │ │
        │ │  JwtAuthenticationFilter     │ │ │
        │ │ (Filtro Global)              │ │ │
        │ │ - Valida tokens              │ │ │
        │ │ - Permite rutas públicas      │ │ │
        │ └──────────────────────────────┘ │ │
        └──────────────┬───────────────────┘ │
                       │                      │
                       │ 2. Reenvía a        │
                       │ usuario-service    │
                       │                    │
                       ▼                    │
        ┌──────────────────────────────────┐ │
        │   USUARIO SERVICE (8081)         │ │
        │ ┌──────────────────────────────┐ │ │
        │ │  UsuarioController           │ │ │
        │ │  - POST /login  ────┐       │ │ │
        │ │  - POST /crear      │       │ │ │
        │ │  - PUT /actualizar  │       │ │ │
        │ └─────────────────────┼──────┘ │ │
        │            ▲          │        │ │
        │            │          │        │ │
        │ ┌──────────┴──────────▼──────┐ │ │
        │ │  JwtService               │ │ │
        │ │ - generateToken()         │ │ │
        │ │ - validateToken()         │ │ │
        │ └────────────────────────────┘ │ │
        │                                │ │
        │ ┌────────────────────────────┐ │ │
        │ │  UsuarioService           │ │ │
        │ │ - Valida credenciales     │ │ │
        │ │ - Busca en BD             │ │ │
        │ └────────────────────────────┘ │ │
        └──────────────────────────────────┘
                       █
                       █ 3. Devuelve JWT
                       █ + Rol
                       │
                       └──────────────┐
                                      │ 4. cliente recibe token
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ { token: "...",         │
                         │   rol: "ADMIN" }        │
                         └──────┬──────────────────┘
                                │
                                │ 5. Siguiente solicitud:
                                │ Authorization: Bearer token
                                │
                                ▼
                    ┌──────────────────────────────┐
                    │  API GATEWAY (8080)         │
                    │  ┌──────────────────────────│
                    │  │ JwtAuthenticationFilter  │
                    │  │ - Extrae token           │
                    │  │ - Valida con JwtUtil     │
                    │  │ - Extrae X-Usuario       │
                    │  │ - Extrae X-Rol          │
                    │  │ - Agrega headers         │
                    │  └──────────────────────────│
                    └──────┬──────────────────────┘
                           │
                    ┌──────▼────────────────────┐
                    │ Reenvía a microservicio:  │
                    │ X-Usuario: juan           │
                    │ X-Rol: ADMIN              │
                    └───────────────────────────┘
                           │
            ┌──────────────┼──────────────┐
            │              │              │
            ▼              ▼              ▼
     ┌─────────────┐ ┌───────────┐ ┌──────────────┐
     │   CLIENTE   │ │ PRODUCTO  │ │    VENTA     │
     │  SERVICE    │ │ SERVICE   │ │   SERVICE    │
     │             │ │           │ │              │
     │ (8082)      │ │ (8083)    │ │   (8085)     │
     └─────────────┘ └───────────┘ └──────────────┘
            │              │              │
            └──────────────┴──────────────┘
                           │
                    ┌──────▼──────────┐
                    │  EUREKA SERVER  │
                    │   (8761)        │
                    │                 │
                    │ (Registro de    │
                    │  servicios)     │
                    └─────────────────┘
```

---

## 🔄 Flujo de Autenticación Detallado

### **Paso 1: Login**

```
Cliente → POST /api/usuarios/login?usuario=admin&password=admin123
         ↓
    API Gateway (sin verificar token - ruta pública)
         ↓
    Usuario Service
         ↓
    Valida usuario + password en BD
         ↓
    Genera JWT:
    {
      "sub": "admin",              ← usuario
      "rol": "ADMIN",              ← rol
      "iat": 1709529600,           ← fecha emisión
      "exp": 1709616000            ← fecha expiración (24h)
    }
         ↓
    Devuelve: { token: "eyJ...", rol: "ADMIN" }
         ↓
    Cliente
```

### **Paso 2: Solicitud Autenticada**

```
Cliente con Token
         ↓
POST /api/productos/mostrartodo
Authorization: Bearer eyJ...
         ↓
    API Gateway - JwtAuthenticationFilter
         ├─ Extrae token del header
         ├─ Valida firma JWT con clave secreta
         ├─ Verifica que no esté expirado
         ├─ Extrae usuario del token → X-Usuario: admin
         ├─ Extrae rol del token → X-Rol: ADMIN
         ├─ Agrega headers a solicitud
         ├─ Enruta a producto-service
         │
         ▼
    Producto Service
    (Recibe: X-Usuario: admin, X-Rol: ADMIN)
         ├─ Procesa solicitud
         ├─ Podría validar rol (opcional)
         │
         ▼
    Devuelve: [productos]
         ↓
    Cliente
```

---

## 🔐 Validación de Token

### **Pasos de Validación en API Gateway**

```
1. Extraer token del header
   Authorization: Bearer <TOKEN>
                         ↓
   token = "<TOKEN>"

2. Separar las 3 partes del JWT
   <HEADER>.<PAYLOAD>.<SIGNATURE>
       ↓       ↓        ↓
   [datos] [claims]  [firma]

3. Validar firma
   HMACSHA256(
     base64(header) + "." + base64(payload),
     secret_key
   ) == signature ?

4. Validar expiración
   current_time < exp_claim ?

5. Si TODO es válido → Permitir solicitud
   Si NO → Devolver 401 Unauthorized
```

---

## 📊 Comparación: Con vs Sin JWT

### **Sin JWT (Antes)**
```
Cliente → API Gateway → Microservicio
         (sin validación)
```
❌ Cualquiera puede acceder  
❌ Sin información de usuario  
❌ Sin control de roles

### **Con JWT (Ahora)**
```
Cliente → Login (obtiene token)
         ↓
      API Gateway (valida token)
         ├─ ✅ Verifica autenticación
         ├─ ✅ Extrae información de usuario
         ├─ ✅ Valida expiración
         └─ ✅ Agrega rol a headers
         ↓
      Microservicio recibe solicitud autenticada
         ├─ ✅ Sabe quién es el usuario
         ├─ ✅ Sabe el rol del usuario
         └─ ✅ Puede tomar decisiones basadas en rol
```

---

## 📦 Componentes Clave

### **Usuario Service**

```
┌────────────────────────────────┐
│     UsuarioController          │
│  (REST Endpoints)              │
│  - POST /login                 │
│  - POST /crear                 │
│  - PUT /actualizar/{id}        │
└────────┬───────────────────────┘
         ↓
┌────────────────────────────────┐
│     UsuarioService             │
│  (Lógica de Negocio)           │
│  - Validar credenciales        │
│  - Gestionar usuarios          │
└────────┬───────────────────────┘
         ↓
┌────────────────────────────────┐
│      JwtService                │
│  (Generar/Validar JWT)         │
│  - generateToken(user, rol)    │
│  - validateToken(token)        │
│  - extractUsuario(token)       │
│  - extractRol(token)           │
└────────┬───────────────────────┘
         ↓
┌────────────────────────────────┐
│    UsuarioRepository           │
│  (Acceso a Base de Datos)      │
│  - findByUsuario(usuario)      │
│  - save(usuario)               │
└────────────────────────────────┘
```

### **API Gateway**

```
┌──────────────────────────────────────┐
│   GatewayConfiguration               │
│   (Configura rutas y filtros)        │
└──────────────┬───────────────────────┘
               ↓
┌──────────────────────────────────────┐
│   JwtAuthenticationFilter            │
│   (Intercepta todas las solicitudes) │
│   - Valida token (si no es pública)  │
│   - Extrae usuario y rol             │
│   - Agrega headers                   │
└──────────────┬───────────────────────┘
               ↓
┌──────────────────────────────────────┐
│      JwtUtil                         │
│   (Valida tokens JWT)                │
│   - validateToken(token)             │
│   - extractClaims(token)             │
└──────────────────────────────────────┘
```

---

## 🔑 Rutas y Permisos

```
┌─────────────────────────────────────────────────┐
│         RUTAS PÚBLICAS (sin token)              │
├─────────────────────────────────────────────────┤
│ POST   /api/usuarios/login                      │
│ POST   /api/usuarios/crear                      │
│ GET    /api/usuarios/v3/api-docs               │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│      RUTAS PROTEGIDAS (requieren token)        │
├─────────────────────────────────────────────────┤
│ /api/usuarios/**       (excepto login y crear) │
│ /api/clientes/**                                │
│ /api/productos/**                               │
│ /api/proveedores/**                             │
│ /api/ventas/**                                  │
│ /api/detalles-venta/**                          │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│         ROLES Y PERMISOS (opcional)              │
├─────────────────────────────────────────────────┤
│ USER:  Solo lectura (GET)                       │
│ ADMIN: Acceso completo (CRUD)                   │
└─────────────────────────────────────────────────┘
```

---

## ⚙️ Configuración y Secretos

```
┌─────────────────────────────────────────┐
│   application.properties                 │
│   (Usuario Service & API Gateway)        │
├─────────────────────────────────────────┤
│                                         │
│ jwt.secret=MismaClaveEnAmbosServicios  │
│            (mínimo 32 caracteres)       │
│                                         │
│ jwt.expiration=86400000                │
│                (24 horas en ms)         │
│                                         │
└─────────────────────────────────────────┘
```

---

**Diagrama actualizado:** Marzo 4, 2026
