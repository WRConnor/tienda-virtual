# Validación de Roles en Microservicios (Opcional)

Aunque el API Gateway maneja la autenticación principal, cada microservicio puede validar roles opcionalmente.

## 📊 Información Disponible

Después de pasar el filtro JWT del API Gateway, cada solicitud incluye headers:

```
X-Usuario: [nombre_usuario]
X-Rol: [USER|ADMIN]
```

## 🔍 Cómo Validar Roles en un Microservicio

### Opción 1: Validación Simple en Controlador

```java
@PostMapping("/crear")
public ResponseEntity<String> crear(
    @RequestBody Producto producto,
    @RequestHeader("X-Rol") String rol) {
    
    if (!"ADMIN".equals(rol)) {
        return new ResponseEntity<>(
            "Solo ADMIN puede crear productos", 
            HttpStatus.FORBIDDEN);
    }
    
    // Crear producto
    return new ResponseEntity<>("Producto creado", HttpStatus.CREATED);
}
```

### Opción 2: Anotación Personalizada (Más Limpia)

```java
import java.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    String value(); // "ADMIN" o "USER"
}

@Aspect
@Component
public class RoleValidationAspect {
    
    @Around("@annotation(requireRole)")
    public Object validateRole(ProceedingJoinPoint pjp, RequireRole requireRole) throws Throwable {
        ServletRequestAttributes attrs = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String rol = attrs.getRequest().getHeader("X-Rol");
        
        if (!requireRole.value().equals(rol)) {
            throw new RuntimeException("Permiso denegado: requiere " + requireRole.value());
        }
        
        return pjp.proceed();
    }
}

// Uso:
@PostMapping("/crear")
@RequireRole("ADMIN")
public ResponseEntity<String> crear(@RequestBody Producto p) {
    // Solo ADMIN puede acceder aquí
}
```

### Opción 3: Inyectar Usuario Actual

```java
@Component
public class CurrentUserProvider {
    
    public String getUsuario() {
        return RequestContextHolder.getRequestAttributes()
            .getRequest().getHeader("X-Usuario");
    }
    
    public String getRol() {
        return RequestContextHolder.getRequestAttributes()
            .getRequest().getHeader("X-Rol");
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(getRol());
    }
}

// Uso:
@Autowired
private CurrentUserProvider userProvider;

@PostMapping("/crear")
public ResponseEntity<?> crear(@RequestBody Producto p) {
    if (!userProvider.isAdmin()) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Acceso denegado");
    }
    // ...
}
```

## 📋 Matriz de Permisos (Referencia)

| Operación | USER | ADMIN |
|-----------|------|-------|
| GET (Lectura) | ✅ | ✅ |
| POST (Crear) | ❌ | ✅ |
| PUT (Actualizar) | ❌ | ✅ |
| DELETE (Eliminar) | ❌ | ✅ |

## ⚠️ IMPORTANTE

### Recomendación: Mantener Simple por Ahora

La implementación actual es suficiente porque:
- ✅ API Gateway valida autenticación (token JWT)
- ✅ Todos los headers incluyen usuario y rol
- ✅ Cada microservicio PUEDE validar si lo necesita

**Usar Opción 1 (validación simple) o Opción 3 (inyección)** para no sobrecomplicar.

### Validación en Producción

Para futuro, considerer agregar:
- Spring Security con roles
- @PreAuthorize("hasRole('ADMIN')")
- Tabla de permisos granulares por endpoint

## 🧪 Prueba Rápida

```bash
# Con rol ADMIN
TOKEN=$(curl -s -X POST "http://localhost:8080/api/usuarios/login?usuario=admin&password=admin123" | jq -r '.token')

curl -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Producto Test"}' \
  http://localhost:8080/api/productos/crear

# Ver headers recibidos (los microservicios recibirán):
# X-Usuario: admin
# X-Rol: ADMIN
```

---

**Nota:** La implementación actual no aplica validación de roles obligatoriamente en los microservicios (solo en API Gateway). Esta guía muestra CÓMO HACERLO si se necesita mayor granularidad.
