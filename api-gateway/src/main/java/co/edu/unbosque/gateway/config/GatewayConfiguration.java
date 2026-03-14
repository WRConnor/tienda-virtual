/**
 * Package containing configuration classes for the API Gateway.
 * These classes define custom routes and routing logic to 
 * forward requests to the appropriate microservices.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the API Gateway.
 * Defines route mappings between incoming API requests
 * and the corresponding microservices using Spring Cloud Gateway.
 * 
 * Author: Wilmer Ramos
 */
@Configuration
public class GatewayConfiguration {

    /**
     * Custom route definitions for the API Gateway.
     * Maps specific API paths to their respective microservices
     * registered in the service discovery (Eureka).
     *
     * @param builder RouteLocatorBuilder used to build route mappings
     * @return RouteLocator containing all defined routes
     */
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("usuario-service", r -> r.path("/api/usuarios/**")
                .uri("lb://usuario-service"))
            .route("cliente-service", r -> r.path("/api/clientes/**")
                .uri("lb://cliente-service"))
            .route("producto-service", r -> r.path("/api/productos/**")
                .uri("lb://producto-service"))
            .route("proveedor-service", r -> r.path("/api/proveedores/**")
                .uri("lb://proveedor-service"))
            .route("venta-service", r -> r.path("/api/ventas/**")
                .uri("lb://venta-service"))
            .route("detalle-venta-service", r -> r.path("/api/detalles-venta/**")
                .uri("lb://detalle-venta-service"))
            .build();
    }
}


