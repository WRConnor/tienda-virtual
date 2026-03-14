/**
 * Package containing the main entry point for the API Gateway application.
 * This package initializes and configures the Spring Boot application
 * responsible for routing and securing requests across microservices.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for the API Gateway Application.
 * This class bootstraps the Spring Boot application and enables
 * service discovery for integration with Eureka and other microservices.
 * The API Gateway acts as a single entry point for client requests,
 * handling routing, filtering, and security.
 * 
 * Author: Wilmer Ramos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    /**
     * Main method to launch the API Gateway application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

