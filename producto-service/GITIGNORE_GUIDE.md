# Guía de .gitignore - Tienda Genérica

## 📋 Descripción General

El archivo `.gitignore` ha sido configurado para ignorar archivos que **NO deben guardarse en Git**, considerando:

- ✅ **Windows 11** (tu sistema operativo)
- ✅ **Linux** (sistema de tu colaborador)
- ✅ **macOS** (por compatibilidad general)
- ✅ **Java/Maven/Spring Boot** (8 microservicios)
- ✅ **React/Node.js** (para futuro frontend)
- ✅ **IDEs** (VSCode, IntelliJ, Eclipse, Sublime)

---

## 🎯 Categorías de Archivos Ignorados

### 1️⃣ **Java / Maven / Spring Boot**

```
target/                  ← Directorio de build (generado)
build/                   ← Build gradle (si se usa)
*.jar                    ← Archivos compilados
*.war                    ← Web Applications
*.class                  ← Clases compiladas
hs_err_pid*.log         ← Crash logs de JVM
```

**Por qué:** Los archivos generados por Maven (`target/`) son reproducibles. Cada desarrollador los regenera con `mvn clean install`.

### 2️⃣ **IDEs (VSCode, IntelliJ, Eclipse)**

```
.vscode/                 ← Configuración VSCode (settings.json, extensions)
.idea/                   ← Configuración IntelliJ IDEA
.classpath               ← Eclipse configuration
.project                 ← Eclipse project file
.settings/               ← Eclipse settings
```

**Por qué:** Cada desarrollador tiene sus propias preferencias. Los archivos de IDE pueden variar entre Windows y Linux.

### 3️⃣ **Node.js / React Frontend** (Futuro)

```
node_modules/            ← Dependencias npm (se reinstalan con npm install)
package-lock.json        ← Caché de versiones
yarn.lock                ← Caché de yarn
npm-debug.log            ← Logs de npm
dist/                    ← Build output del frontend
build/                   ← Build output alternativo
```

### 4️⃣ **Sistema Operativo**

#### Windows 11
```
Thumbs.db                ← Caché de miniaturas de carpetas
$RECYCLE.BIN/            ← Papelera de reciclaje
*.lnk                    ← Accesos directos
```

#### Linux
```
.directory               ← Configuración de Nautilus
.fuse_hidden*            ← Archivos temporales FUSE
.nfs*                    ← Archivos NFS temporales
```

#### macOS
```
.DS_Store                ← Metadatos de Finder
.Spotlight-V100          ← Índice Spotlight
.Trashes                 ← Papelera
```

### 5️⃣ **Variables de Entorno y Secretos**

```
.env                     ← Variables globales (NUNCA incluir!)
.env.local              ← Config local (NUNCA incluir!)
credentials.json        ← Credenciales BD
secrets.yaml            ← Secretos de la aplicación
```

**⚠️ CRÍTICO:** Nunca guardes archivos `.env` con contraseñas reales en Git.

**Alternativa segura:**
```bash
# Crear .env.example (SÍ guardar en Git)
DB_HOST=localhost
DB_USER=admin_usuario
DB_PASSWORD=CAMBIAR_EN_LOCAL

# Documentar en README.md qué variables se necesitan
```

### 6️⃣ **Logs y Temporales**

```
*.log                    ← Archivos de log
logs/                    ← Directorio de logs
*.tmp                    ← Archivos temporales
*.swp, *.swo            ← Backups de vim/neovim
*~                      ← Backups de editores
```

---

## 📂 Estructura Específica Ignorada

### Para Cada Microservicio

```
usuario-service/
├── target/              ← ❌ IGNORADO (build output)
├── .classpath           ← ❌ IGNORADO (Eclipse)
├── .project             ← ❌ IGNORADO (Eclipse)
├── .settings/           ← ❌ IGNORADO (Eclipse settings)
├── pom.xml              ← ✅ INCLUIDO (dependencias)
└── src/                 ← ✅ INCLUIDO (código fuente)
```

### Build output por servicio

```
api-gateway/target/          ← ❌ Ignorado
cliente-service/target/      ← ❌ Ignorado
producto-service/target/     ← ❌ Ignorado
usuario-service/target/      ← ❌ Ignorado
venta-service/target/        ← ❌ Ignorado
detalle-venta-service/target/← ❌ Ignorado
eureka-server/target/        ← ❌ Ignorado
proveedor-service/target/    ← ❌ Ignorado
```

---

## ✅ Archivos QUE SÍ se Incluyen en Git

```
pom.xml                          ← Dependencias Maven (CRÍTICO)
src/                             ← Código fuente Java
application.properties           ← Configuración (sin secretos)
.env.example                     ← Template de variables (sin valores)
README.md                        ← Documentación
JWT_AUTHENTICATION_GUIDE.md      ← Documentación JWT
QUICK_START.md                   ← Guía de inicio
ARCHITECTURE_DIAGRAM.md          ← Diagramas
ROLES_VALIDATION_OPTIONAL.md     ← Validación de roles
.gitignore                       ← Este archivo
.gitkeep                         ← Archivos para mantener directorios vacíos
```

---

## 🚀 Uso en Diferentes Sistemas Operativos

### Windows 11 (Tu Sistema)

```bash
# El comando git automáticamente respeta .gitignore
git add .
git commit -m "Initial commit"
git push origin main

# Windows ignorará:
# - Thumbs.db
# - $RECYCLE.BIN/
# - hs_err_pid*.log (si JVM crashea)
```

### Linux (Sistema del Colaborador)

```bash
# Igual flujo
git add .
git commit -m "Initial commit"
git push origin main

# Linux ignorará:
# - .directory
# - .fuse_hidden*
# - Permisos de archivo (guardados por Git)
```

### Verificar qué se va a commitear

```bash
# Ver qué archivos se commitearán (antes de hacer commit)
git diff --cached --name-only

# Ver archivos ignorados
git check-ignore -v *
```

---

## 🔍 Qué NO Incluir Nunca en Git

### ❌ NUNCA commits estos archivos:

```
.env (con contraseñas reales)       ← RIESGO DE SEGURIDAD
credentials.json                     ← Credenciales de BD
secrets.yaml                         ← Tokens API
target/                              ← Build output (8 servicios)
node_modules/                        ← Dependencias npm (miles de archivos)
*.log                                ← Logs de ejecución
.idea/, .vscode/                     ← IDE configuration personal
```

### ✅ SI commits estos archivos:

```
pom.xml                              ← Define dependencias
.env.example                         ← Template sin valores reales
application.properties               ← Configuración genérica
src/                                 ← Código fuente
.gitignore                           ← Este archivo
README.md                            ← Documentación
```

---

## 📏 Tamaño del Repositorio

### Sin .gitignore adecuado (~500 MB):
```
api-gateway/target/          50 MB
usuario-service/target/      50 MB
cliente-service/target/      50 MB
...
node_modules/ (si existe)    200+ MB
.idea/ caches               100+ MB
```

### Con .gitignore (~2 MB):
```
Código fuente               ~1 MB
Archivos de config          ~0.5 MB
Documentación               ~0.5 MB
```

**Ahorro: 250x más pequeño** ✅

---

## 🔐 Protección de Secretos

### Paso 1: Crear .env.example

```bash
# .env.example (SÍ guardar en Git)
# Copiar este archivo a .env y llenar con valores locales
spring.datasource.url=jdbc:mariadb://localhost:3306/tiendagenerica_usuario
spring.datasource.username=CAMBIAR_EN_LOCAL
spring.datasource.password=CAMBIAR_EN_LOCAL
jwt.secret=CAMBIAR_EN_LOCAL_CON_CLAVE_SEGURA

# api-gateway
jwt.secret=CAMBIAR_EN_LOCAL_CON_CLAVE_SEGURA
```

### Paso 2: Documentar en README.md

```markdown
## Configuración Local

1. Copia `.env.example` a `.env`:
   ```bash
   cp .env.example .env
   ```

2. Completa con tus credenciales locales:
   ```
   DB_PASSWORD=tu_contraseña_local
   JWT_SECRET=tu_clave_segura_local
   ```

3. Nunca commits `.env`
```

---

## 🧪 Verificar Configuración del .gitignore

```bash
# 1. Ver archivos que será ignorados
git status

# 2. Verificar si un archivo específico será ignorado
git check-ignore -v target/classes/

# Output: "target/classes/.gitignore:16:target/             target/classes"

# 3. Forzar agregar un archivo ignorado (si necesitas)
git add -f archivo_ignorado.jar

# 4. Actualizar .gitignore después de commits anteriores
git rm -r --cached target/   # Ambientes locales
git commit -m "Remove target/ from version control"
git push
```

---

## 📋 Resumen Rápido

| Elemento | ¿Se ignora? | Razón |
|----------|-----------|-------|
| `target/` | ✅ Sí | Generado por Maven |
| `node_modules/` | ✅ Sí | Se instala con npm install |
| `pom.xml` | ❌ No | Define dependencias |
| `.env` | ✅ Sí | Contiene secretos |
| `.env.example` | ❌ No | Template sin secretos |
| `.idea/` | ✅ Sí | Configuración IDE |
| `src/` | ❌ No | Código fuente |
| `*.log` | ✅ Sí | Logs de ejecución |
| `.gitignore` | ❌ No | Configuración del repo |

---

## 💡 Recomendaciones Finales

1. **Antes de cada push:** Revisa con `git status` qué se enviará
2. **Mantén .env.example actualizado:** Si agregas nuevas variables
3. **Documenta secretos:** Crea README.md explicando qué valores ir en .env
4. **Colaboración:** Comparte .env.example (no .env) con colaborador
5. **GitHub Secrets:** Para CI/CD, usa GitHub Actions secrets en lugar de .env en Git

---

**Creado:** Marzo 4, 2026  
**Última revisión:** Marzo 4, 2026

