# Guía para Desarrolladores - Windows 11 y Linux

## 🚀 Configuración Inicial (Ambos Sistemas)

### Paso 1: Clonar el Repositorio

```bash
# Windows (PowerShell o CMD)
git clone <url_del_repositorio>
cd tienda-generica

# Linux (Bash)
git clone <url_del_repositorio>
cd tienda-generica
```

### Paso 2: Verificar .gitignore

```bash
# Ver archivos que serán ignorados
git status

# Debería mostrar algo como:
# On branch main
# Your branch is up to date with 'origin/main'.
# nothing to commit, working tree clean
```

### Paso 3: Configurar Variables de Entorno Local

```bash
# Copiar template
cp .env.example .env

# Editar con tus valores locales
# Windows: notepad .env
# Linux: nano .env o vim .env
```

---

## 💻 Configuración Específica por Sistema

### Windows 11

#### 1. **Instalar Dependencias**

```powershell
# Java 17+
Java -version

# Maven 3.6+
mvn -version

# Git
git --version

# MariaDB (descargar desde https://mariadb.org/download/)
mysql --version
```

#### 2. **Configurar MariaDB**

```powershell
# Iniciar servicio (si no está corriendo)
net start MariaDB103  # O la versión que instalaste

# Conectar a MySQL
mysql -u admin_usuario -p
# Ingresar password: admin12345
```

#### 3. **Crear Bases de Datos Locales**

```sql
-- En MySQL console
CREATE DATABASE tiendagenerica_usuario;
CREATE DATABASE tiendagenerica_cliente;
CREATE DATABASE tiendagenerica_producto;
CREATE DATABASE tiendagenerica_venta;
CREATE DATABASE tiendagenerica_detalle_venta;

-- Salir
EXIT;
```

#### 4. **Compilar y Ejecutar Proyecto**

```powershell
# Terminal 1 - Eureka Server
cd eureka-server
mvn clean spring-boot:run

# Terminal 2 - Usuario Service
cd usuario-service
mvn clean spring-boot:run

# Terminal 3 - API Gateway
cd api-gateway
mvn clean spring-boot:run

# Terminal 4+ - Otros servicios
cd cliente-service
mvn clean spring-boot:run
# ... etc
```

#### 5. **Verificar en Windows**

```powershell
# Ver puerto de Eureka
curl http://localhost:8761

# Ver Swagger Usuario Service
curl http://localhost:8081/swagger-ui.html

# Ver API Gateway
curl http://localhost:8080/health
```

---

### Linux

#### 1. **Instalar Dependencias**

```bash
# Java 17
java -version
sudo apt-get install openjdk-17-jdk  # Debian/Ubuntu

# Maven
mvn -version
sudo apt-get install maven  # Debian/Ubuntu

# Git
git --version

# MariaDB
sudo apt-get install mariadb-server
mysql --version
```

#### 2. **Configurar MariaDB**

```bash
# Iniciar servicio
sudo systemctl start mariadb
sudo systemctl enable mariadb  # Iniciar en boot

# Verificar estatus
sudo systemctl status mariadb

# Asegurar instalación
sudo mysql_secure_installation

# Conectar
mysql -u admin_usuario -p
# Ingresa: admin12345
```

#### 3. **Crear Bases de Datos**

```sql
-- En MySQL console
CREATE DATABASE tiendagenerica_usuario;
CREATE DATABASE tiendagenerica_cliente;
CREATE DATABASE tiendagenerica_producto;
CREATE DATABASE tiendagenerica_venta;
CREATE DATABASE tiendagenerica_detalle_venta;

EXIT;
```

#### 4. **Compilar y Ejecutar (con tmux para múltiples terminales)**

```bash
# Instalar tmux (si no lo tienes)
sudo apt-get install tmux

# Crear sesión
tmux new-session -d -s tienda-dev

# Panel 1: Eureka Server
tmux send-keys -t tienda-dev "cd eureka-server && mvn clean spring-boot:run" Enter

# Panel 2: Usuario Service
tmux new-window -t tienda-dev
tmux send-keys -t tienda-dev "cd usuario-service && mvn clean spring-boot:run" Enter

# Panel 3: API Gateway
tmux new-window -t tienda-dev
tmux send-keys -t tienda-dev "cd api-gateway && mvn clean spring-boot:run" Enter

# Ver sesión
tmux attach -t tienda-dev
```

#### 5. **Verificar en Linux**

```bash
# Ver puerto de Eureka
curl http://localhost:8761

# Ver Swagger Usuario Service
curl http://localhost:8081/swagger-ui.html

# Ver API Gateway
curl http://localhost:8080/health
```

---

## 🔄 Flujo de Desarrollo Colaborativo

### Desarrollador en Windows 11

```powershell
# 1. Actualizar código
git pull origin main

# 2. Compilar cambios
mvn clean install

# 3. Verificar que .gitignore funciona
git status
# Debería NO mostrar:
# - target/
# - .idea/
# - .classpath
# - .env

# 4. Hacer cambios
# ... edita archivos ...

# 5. Verificar cambios
git diff nombre-archivo.java

# 6. Agregar cambios
git add .

# 7. Commit
git commit -m "Cambio: descripción clara"

# 8. Push
git push origin main
```

### Desarrollador en Linux

```bash
# 1. Actualizar código
git pull origin main

# 2. Compilar cambios
mvn clean install

# 3. Verificar que .gitignore funciona
git status
# Debería NO mostrar:
# - target/
# - .idea/
# - .classpath
# - .env (está en .gitignore)

# 4. Hacer cambios
# ... edita archivos ...

# 5. Verificar cambios
git diff nombre-archivo.java

# 6. Agregar cambios
git add .

# 7. Commit
git commit -m "Cambio: descripción clara"

# 8. Push
git push origin main

# 9. Notar: Los permisos de archivo (755 vs 644) se preservan automáticamente en Git
```

---

## ⚠️ Problemas Comunes y Soluciones

### Error: "Puerto ya está en uso"

**Windows:**
```powershell
# Encontrar qué proceso usa el puerto
netstat -ano | findstr :8080

# Matar el proceso
taskkill /PID <PID> /F
```

**Linux:**
```bash
# Encontrar proceso
sudo lsof -i :8080
# o
sudo netstat -tulpn | grep :8080

# Matar proceso
sudo kill -9 <PID>
```

### Error: "Cannot find MariaDB"

**Windows:**
- Verificar que MariaDB está instalado: `mysql --version`
- Iniciar servicio: Control Panel → Services → MariaDB

**Linux:**
```bash
# Instalar MariaDB
sudo apt-get install mariadb-server

# Iniciar
sudo systemctl start mariadb
```

### Error: ".gitignore no funciona"

```bash
# Opción 1: Limpiar caché de Git
git rm --cached -r .
git add .
git commit -m "Apply .gitignore to all files"

# Opción 2: Ver qué está ignorado
git check-ignore -v src/

# Opción 3: Verificar sintaxis de .gitignore
cat .gitignore | grep -v "^#" | grep -v "^$"
```

### Conflictos entre Windows y Linux

**Saltos de línea (CRLF vs LF):**
```bash
# Configurar Git globalmente (recomendado)
git config --global core.autocrlf true   # Windows
git config --global core.autocrlf input  # Linux

# O por proyecto
git config core.autocrlf true   # Windows
git config core.autocrlf input  # Linux
```

---

## 📊 Checklist Pre-Commit

Antes de hacer `git push`, verificar:

```bash
# ✅ 1. Estado limpio (sin archivos ignorados)
git status
# Resultado esperado: "nothing to commit, working tree clean"

# ✅ 2. No hay secretos en el código
git diff --cached | grep -i "password\|secret\|token\|api_key"
# Resultado esperado: sin coincidencias

# ✅ 3. Código compila
mvn clean compile

# ✅ 4. No hay archivos de IDE
git check-ignore -v .vscode/ .idea/
# Resultado esperado: (ignorados correctamente)

# ✅ 5. .env no se commiteará
git check-ignore -v .env
# Resultado esperado: ".gitignore:XY:.env" o "is in .gitignore"

# ✅ 6. pom.xml SÍ se incluirá
git status | grep pom.xml
# Resultado esperado: pom.xml debe estar en Changes to be committed
```

---

## 🤝 Flujo de Integración Colaborativa

### Escenario: Ambos Desarrolladores Trabajan en Paralelo

```
Semana 1 - Developer Windows (Feature A)
├─ git checkout -b feature/autenticacion-a
├─ Hace cambios en UsuarioController.java
├─ git add .
├─ git commit -m "Feature: JWT Authentication"
└─ git push origin feature/autenticacion-a

Semana 1 - Developer Linux (Feature B)
├─ git checkout -b feature/validacion-roles
├─ Hace cambios en JwtUtil.java
├─ git add .
├─ git commit -m "Feature: Role Validation"
└─ git push origin feature/validacion-roles

Semana 2 - Code Review (Ambos)
├─ Crear Pull Request (Feature A)
├─ Crear Pull Request (Feature B)
├─ Revisar código mutuamente
├─ Resolver conflictos (si existen)
└─ Merge a main

Semana 2 - Sincronizar Local
├─ git pull origin main         ← Traer cambios
├─ mvn clean install             ← Compilar
└─ Verificar que todo funciona
```

---

## 📚 Guías Complementarias

- [GITIGNORE_GUIDE.md](GITIGNORE_GUIDE.md) - Detalles del .gitignore
- [JWT_AUTHENTICATION_GUIDE.md](JWT_AUTHENTICATION_GUIDE.md) - Autenticación JWT
- [QUICK_START.md](QUICK_START.md) - Inicio rápido
- [ARCHITECTURE_DIAGRAM.md](ARCHITECTURE_DIAGRAM.md) - Arquitectura

---

## 💡 Tips Finales

### Windows 11
- Usa **PowerShell 7+** (más potente que CMD)
- Instala **Git Bash** para comandos Unix
- Usa **Visual Studio Code** con extensión Maven

### Linux
- Mantén tu sistema actualizado: `sudo apt fast-upgrade`
- Usa **tmux** para múltiples terminales
- Configura aliases útiles:
  ```bash
  alias devstart='cd ~/tienda-generica && mvn clean install'
  alias logs='tail -f logs/*.log'
  ```

### Ambos
- Usa `gitk` o **GitHub Desktop** para ver historial
- Configura SSH keys para no ingresar password cada commit:
  ```bash
  ssh-keygen -t ed25519
  cat ~/.ssh/id_ed25519.pub  # Copiar a GitHub
  ```

---

**Guía creada:** Marzo 4, 2026  
**Versión:** 1.0  
**Compatible con:** Windows 11 + Linux
