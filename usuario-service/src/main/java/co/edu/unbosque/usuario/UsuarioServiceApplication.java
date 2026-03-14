package co.edu.unbosque.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Boot application class for UsuarioService.
 * 
 * Annotations:
 * - @SpringBootApplication: Marks this as a Spring Boot application, enabling
 *   component scanning, auto-configuration, and property support.
 * - @EnableDiscoveryClient: Enables this service to register with a service
 *   registry (e.g., Eureka) for service discovery in a microservices architecture.
 *   @author Wilmer Ramos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UsuarioServiceApplication {

    /**
     * Main method: Entry point of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(UsuarioServiceApplication.class, args);
    }

}