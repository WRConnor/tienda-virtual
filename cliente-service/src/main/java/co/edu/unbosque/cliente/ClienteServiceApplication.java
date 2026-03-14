/**
 * Package containing the main entry point for the client service application.
 * This package initializes and configures the Spring Boot application
 * responsible for managing client-related operations.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for the Cliente Service Application.
 * This class bootstraps the Spring Boot application and enables
 * service discovery for integration with microservices architecture.
 * 
 * Author: Wilmer Ramos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClienteServiceApplication {

    /**
     * Main method to launch the Spring Boot application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ClienteServiceApplication.class, args);
    }
}

