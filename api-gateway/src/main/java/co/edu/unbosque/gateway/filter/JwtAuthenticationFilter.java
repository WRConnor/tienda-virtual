package co.edu.unbosque.gateway.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import co.edu.unbosque.gateway.util.JwtUtil;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class JwtAuthenticationFilter implements GlobalFilter {

	@Autowired
	private JwtUtil jwtUtil;

	private static final List<String> PUBLIC_ROUTES = Arrays.asList(
		"/api/usuarios/login",
		"/api/usuarios/crear",
		"/api/usuarios/v3/api-docs",
		"/api/ventas/v3/api-docs",
		"/api/clientes/v3/api-docs"
	);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		// Check if the route is public
		if (isPublicRoute(path)) {
			return chain.filter(exchange);
		}

		// Extract token from header
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		String token = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		// Validate token
		if (token == null || !jwtUtil.validateToken(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		// Add user info to request headers
		String usuario = jwtUtil.extractUsuario(token);
        String rol = jwtUtil.extractRol(token);

        // 🔒 Validar permisos por rol
        if (!tienePermiso(rol, path)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header("X-Usuario", usuario)
                        .header("X-Rol", rol)
                        .build())
                .build());
    }

    private boolean isPublicRoute(String path) {
        return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
    }

    private boolean tienePermiso(String rol, String path) {

        switch (rol) {

            case "ADMIN":
                return true;

            case "CAJERO":
                return path.startsWith("/api/ventas") ||
                       path.startsWith("/api/detalles-venta");

            case "INVENTARIO":
                return path.startsWith("/api/productos");

            case "GERENTE":
                return path.startsWith("/api/proveedores") || path.startsWith("/api/ventas");
            case "USER":
                return false; // no tiene permisos para APIs protegidas

            default:
                return false;
        }
    }
}
