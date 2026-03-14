package co.edu.unbosque.venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main Spring Boot application class for the Venta microservice.
 * 
 * Responsibilities:
 *  - Launches the Spring Boot application.
 *  - Enables service discovery with @EnableDiscoveryClient.
 *  - Enables Feign clients in the specified package for inter-service calls.
 * 
 * @author Wilmer Ramos
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "co.edu.unbosque.venta.feign")
public class VentaServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VentaServiceApplication.class, args);
    }
}
