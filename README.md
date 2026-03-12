# 🛒 Tienda Genérica - Sistema de Microservicios

Sistema de e-commerce basado en arquitectura de microservicios con **Spring Cloud**, **Eureka**, autenticación **JWT** y **MariaDB**.

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Cómo Ejecutar en Eclipse](#cómo-ejecutar-en-eclipse)
- [Cosas Importantes a Tener en Cuenta](#cosas-importantes-a-tener-en-cuenta)
- [Endpoints Principales](#endpoints-principales)

---

## 📖 Descripción del Proyecto

**Tienda Genérica** es una plataforma de comercio electrónico escalable construida con una arquitectura de microservicios. El sistema está diseñado para:

- ✅ Gestionar usuarios y autenticación con JWT
- ✅ Administrar clientes, productos y proveedores
- ✅ Procesar ventas y detalles de venta
- ✅ Usar descubrimiento de servicios con Eureka
- ✅ Centralizar el acceso a través de un API Gateway
- ✅ Escalar independientemente cada microservicio

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────┐
│          CLIENTE (Navegador/App)            │
└──────────────────┬──────────────────────────┘
                   │ Solicitud HTTP
                   ▼
        ┌──────────────────────────┐
        │    API GATEWAY (8080)    │
        │ ┌──────────────────────┐ │
        │ │ JwtAuthenticationFilter│ │
        │ │ (Valida tokens JWT)  │ │
        │ └──────────────────────┘ │
        └──────┬───────────────────┘
               │ Enrutamiento
   ┌───────────┼───────────┬─────────────┐
   │           │           │             │
   ▼           ▼           ▼             ▼
┌────────┐ ┌────────┐ ┌────────┐ ┌──────────┐
│Usuario │ │Cliente │ │Producto│ │  Venta   │
│Service │ │Service │ │Service │ │ Service  │
│(8081)  │ │(8082)  │ │(8083)  │ │ (8085)   │
└──────┬─┘ └──────┬─┘ └──────┬─┘ └────┬─────┘
       │         │         │        │
       └─────────┼─────────┼────────┘
                 │
        ┌────────▼────────┐
        │ EUREKA SERVER   │
        │ (8761)          │
        │ Descubrimiento  │
        │ de servicios    │
        └─────────────────┘
                 │
                 │
                 ▼                   
           ┌────────────┐    
           │  MariaDB   │    
           │            │    
           └────────────┘    
```

### Microservicios Incluidos:

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **Eureka Server** | 8761 | Registro y descubrimiento de servicios |
| **API Gateway** | 8080 | Puerta de entrada, enrutamiento y autenticación |
| **Usuario Service** | 8081 | Gestión de usuarios y autenticación JWT |
| **Cliente Service** | 8082 | Información de clientes |
| **Producto Service** | 8083 | Catálogo de productos |
| **Proveedor Service** | 8084 | Gestión de proveedores |
| **Venta Service** | 8085 | Gestión de transacciones |


---

## 🛠️ Tecnologías

- **Java 17+** - Lenguaje principal
- **Spring Boot 3.x** - Framework base
- **Spring Cloud 2023.0.0** - Arquitectura de microservicios
- **Spring Cloud Gateway** - API Gateway
- **Netflix Eureka** - Descubrimiento de servicios
- **JWT (JJWT)** - Autenticación y autorización
- **Spring Data JPA** - Acceso a datos
- **MariaDB / MySQL** - Base de datos
- **Maven** - Gestor de dependencias
- **SpringDoc OpenAPI** - Documentación automática (Swagger)

---

## ✅ Requisitos Previos

### Software Necesario:

1. **Java Development Kit (JDK) 17+**
   ```powershell
   java -version
   ```
   Descarga desde: https://www.oracle.com/java/technologies/downloads/

2. **Maven 3.6+**
   ```powershell
   mvn -version
   ```
   Descarga desde: https://maven.apache.org/download.cgi

3. **Eclipse IDE** (con soporte para Maven)
   ```
   Descarga desde: https://www.eclipse.org/downloads/
   ```

4. **MariaDB / MySQL Server**
   ```powershell
   mysql --version
   ```
   Descarga desde: https://mariadb.org/download/

5. **Git**
   ```powershell
   git --version
   ```

### Verificación rápida:
```powershell
java -version
mvn -version
git --version
mysql --version
```

---

## 🚀 Instalación y Configuración

### Paso 1: Clonar el Repositorio

```powershell
git clone <URL_DEL_REPOSITORIO>
cd tienda-generica
```

Cada microservicio debe trabajar con **su propio schema (base de datos)**.  
Esto es una **buena práctica en arquitectura de microservicios**, ya que evita dependencias directas entre servicios y permite escalar o modificar cada uno de forma independiente.

La base de datos puede estar:

- En tu **máquina local**
- En una **máquina virtual (por ejemplo Rocky Linux con MariaDB)**
- En un **servidor remoto**

Lo importante es que **todos los microservicios puedan conectarse al servidor de base de datos**.

---

#### Iniciar MariaDB

En **Windows PowerShell o CMD**:

```powershell
# Verificar que MariaDB esté ejecutándose
net start MariaDB103  # Ajusta el nombre del servicio según tu versión

# Conectarse a MariaDB
mysql -u root -p
```

---

#### Crear Schemas para cada Microservicio

Dentro de la consola de MariaDB/MySQL ejecutar:

```sql
-- Crear un schema independiente para cada microservicio

CREATE DATABASE tiendagenerica_usuario;
CREATE DATABASE tiendagenerica_cliente;
CREATE DATABASE tiendagenerica_producto;
CREATE DATABASE tiendagenerica_proveedores;
CREATE DATABASE tiendagenerica_venta;
```

Cada microservicio **solo debe conectarse a su propio schema**.

**De ser posible crear 5 diferentes instancias de MariaDB para no tener todos los schemas en una sola instancia**

Ejemplo:

| Microservicio | Schema |
|---------------|--------|
| usuario-service | tiendagenerica_usuario |
| cliente-service | tiendagenerica_cliente |
| producto-service | tiendagenerica_producto |
| proveedor-service | tiendagenerica_proveedores |
| venta-service | tiendagenerica_venta |

---

#### Crear Usuario de Base de Datos (Opcional)

Para mayor seguridad se recomienda no usar `root`.

```sql
CREATE USER 'tienda_admin'@'%' IDENTIFIED BY 'tienda@2024';

GRANT ALL PRIVILEGES ON tiendagenerica_usuario.* TO 'tienda_admin'@'%';
GRANT ALL PRIVILEGES ON tiendagenerica_cliente.* TO 'tienda_admin'@'%';
GRANT ALL PRIVILEGES ON tiendagenerica_producto.* TO 'tienda_admin'@'%';
GRANT ALL PRIVILEGES ON tiendagenerica_proveedores.* TO 'tienda_admin'@'%';
GRANT ALL PRIVILEGES ON tiendagenerica_venta.* TO 'tienda_admin'@'%';

FLUSH PRIVILEGES;
EXIT;
```

El uso de `'%'` permite que los microservicios puedan conectarse **desde otras máquinas**, por ejemplo desde **máquinas virtuales**.

---

### Paso 3: Configurar Properties de cada Servicio

Cada microservicio debe conectarse **solo a su propio schema**.

Ejemplo:

**`usuario-service/src/main/resources/application.properties`**

```properties
spring.datasource.url=jdbc:mysql://IP_DEL_SERVIDOR:3306/tiendagenerica_usuario
spring.datasource.username=tienda_admin
spring.datasource.password=tienda@2024

spring.jpa.hibernate.ddl-auto=update
spring.application.name=usuario-service
server.port=8081
```

Ejemplo si la base de datos está:

- **Local:** `localhost`
- **Máquina virtual:** `192.168.X.X`
- **Servidor remoto:** IP del servidor

---


## 🖥️ Cómo Ejecutar en Eclipse

### 1. **Importar el Proyecto en Eclipse**

- Abre **Eclipse IDE**
- Ve a `File → Open Projects from File System`
- Selecciona la carpeta `tienda-generica`
- Haz clic en `Finish`
- Espera a que descargue todas las dependencias de Maven

### 2. **Configurar Ejecutables de Maven (Opcional)**

Para facilitar la ejecución:

1. Ve a `Run → Run Configurations`
2. Haz clic derecho en **Maven Build** → **New**
3. Crea una configuración para cada servicio:

**Ejemplo para Eureka Server:**
- Name: `eureka-server`
- Base directory: `${workspace_loc}/tienda-generica/eureka-server`
- Goals: `clean spring-boot:run`
- Aplica

Repite para cada servicio.

### 3. **Ejecutar en Orden de Dependencias**

Abre **6 terminales** en Eclipse o consolas separadas:

#### Terminal 1 - Eureka Server (Primero siempre)
```powershell
cd eureka-server
mvn clean spring-boot:run
```
✅ Verifica en: http://localhost:8761

#### Terminal 2 - Usuario Service
```powershell
cd usuario-service
mvn clean spring-boot:run
```
✅ Debería registrarse en Eureka

#### Terminal 3 - API Gateway
```powershell
cd api-gateway
mvn clean spring-boot:run
```
✅ Actúa como proxy central

#### Terminal 4, 5, 6... - Otros Servicios (En cualquier orden)
```powershell
cd cliente-service && mvn clean spring-boot:run
cd producto-service && mvn clean spring-boot:run
cd venta-service && mvn clean spring-boot:run
cd detalle-venta-service && mvn clean spring-boot:run
# Etc.
```

### 4. **Alternativa: Usar Run Configurations de Eclipse**

En lugar de terminales:

1. Click derecho en `eureka-server` → `Run As → Maven Build` o `Run As → Spring Boot App`
2. Si create un Maven Build selecciona la configuración que creaste y haz click en `Run`
3. Haz lo mismo para los demás servicios

---

## ⚠️ Cosas Importantes a Tener en Cuenta

### 1. **Siempre Inicia Eureka Primero**
   - ❌ No inicies otros servicios antes de Eureka
   - ✅ Eureka (8761) debe estár listo PRIMERO
   - ✅ Luego inicia los demás servicios

### 2. **Orden de Ejecución Recomendado**
   ```
   1. Eureka Server (8761)
   2. Usuario Service (8081) - Necesario para autenticación
   3. API Gateway (8080) - Reenvía a otros servicios
   4. Cliente, Producto, Venta Services (en cualquier orden)
   ```

### 3. **Verificación de Puertos**
   - Asegúrate de que los puertos estén disponibles
   - Si usas otra aplicación en esos puertos, cambia los números en `application.properties`:
   ```properties
   server.port=8090  # Ejemplo: cambiar puerto del gateway
   ```

### 4. **Base de Datos**
   - 🗄️ MariaDB/MySQL debe estar ejecutándose
   - 🔑 Verifica `user` y `password` en cada `application.properties`
   - 🗂️ Las tablas se crean automáticamente con `spring.jpa.hibernate.ddl-auto=update`

### 5. **Autenticación JWT**
   - El token se obtiene en el endpoint `/api/usuarios/login`
   - Todos los demás endpoints protegidos requieren el header:
   ```
   Authorization: Bearer <tu_token>
   ```
   - El token expira después del tiempo configurado (revisa `JwtService`)

### 6. **Problemas Comunes**

| Problema | Solución |
|----------|----------|
| `Connection refused` en puerto X | Verifica que el servicio esté ejecutándose en ese puerto |
| `No Eureka server` | Asegúrate que Eureka está corriendo en http://localhost:8761 |
| `401 Unauthorized` | Genera un token válido en `/api/usuarios/login` |
| `404 Not Found` | Verifica que el servicio está registrado en Eureka |
| `MySQL connection failed` | Verifica credenciales y que MariaDB está ejecutándose |
| `Port already in use` | Cambia el puerto en `application.properties` o mata el proceso que lo usa |

### 7. **Estructura de Carpetas**
   ```
   tienda-generica/
   ├── eureka-server/          (Debe ejecutarse primero)
   ├── api-gateway/            (Punto de entrada)
   ├── usuario-service/        (Autenticación)
   ├── cliente-service/        (Datos de clientes)
   ├── producto-service/       (Catálogo)
   ├── venta-service/          (Transacciones)
   ├── proveedor-service/      (Proveedores)
   └── [.md files]             (Documentación)
   ```

### 8. **Debugging en Eclipse**
   - Abre la vista de **Debug Perspective** (Window → Perspective → Debug)
   - Establece breakpoints haciendo clic en el margen izquierdo
   - Ejecuta con `Debug As → Maven Build`

### 9. **Logs Útiles**
   Observa la consola para mensajes como:
   ```
   ✅ "Located in Eureka server" - El servicio fue registrado
   ✅ "Started ApiGatewayApplication" - Gateway listo
   ❌ "Failed to register with Service Registry" - Problema con Eureka
   ```

### 10. **Configuración de Roles y Seguridad**
   - Por defecto existen roles: `ADMIN`, `USUARIO`, `CLIENTE`
   - Revisa [ROLES_VALIDATION_OPTIONAL.md](./ROLES_VALIDATION_OPTIONAL.md) para validación de roles

---

## 📡 Endpoints Principales

### Autenticación (Públicos - Sin Token)
```bash
# Crear usuario
POST http://localhost:8080/api/usuarios/crear
Body: {
  "cedulaUsuario": 1234567890,
  "nombreUsuario": "Juan",
  "emailUsuario": "juan@example.com",
  "usuario": "juan",
  "password": "contraseña123",
  "rol": "ADMIN"
}

# Login y Obtener Token
POST http://localhost:8080/api/usuarios/login?usuario=juan&password=contraseña123
```

### Respuesta Login:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMIN"
}
```

### Endpoints Protegidos (Requieren Token)
Agrega el header a todas las solicitudes:
```
Authorization: Bearer <tu_token>
```

**Usuarios:**
```
GET    /api/usuarios/mostrartodo
PUT    /api/usuarios/actualizar/{id}
DELETE /api/usuarios/eliminar/{id}
```

**Productos:**
```
GET    /api/productos/mostrartodo
POST   /api/productos/crear
PUT    /api/productos/actualizar/{id}
DELETE /api/productos/eliminar/{id}
```

**Clientes:**
```
GET    /api/clientes/mostrartodo
POST   /api/clientes/crear
```

**Ventas:**
```
GET    /api/ventas/mostrartodo
POST   /api/ventas/crear
```

---

## 🧪 Prueba Rápida

```powershell
# 1. Obtener token
$response = curl -X POST "http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123"
$token = ($response | ConvertFrom-Json).token

# 2. Usar el token
curl -H "Authorization: Bearer $token" http://localhost:8080/api/productos/mostrartodo
```

---



