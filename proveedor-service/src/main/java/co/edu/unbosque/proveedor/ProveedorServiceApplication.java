package co.edu.unbosque.proveedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Boot application class for ProveedorService.
 * 
 * Annotations:
 * - @SpringBootApplication: Marks this as a Spring Boot application, enabling
 *   component scanning, auto-configuration, and property support.
 * - @EnableDiscoveryClient: Registers this service with a service registry
 *   (e.g., Eureka) to allow discovery by other microservices.
 *   
 *  @author Wilmer Ramos
 *   
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProveedorServiceApplication {

    /**
     * Main method: Entry point of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(ProveedorServiceApplication.class, args);
    }

}