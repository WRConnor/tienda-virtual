package co.edu.unbosque.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the Eureka Server Application.
 * This class bootstraps the Spring Boot application and enables
 * the Eureka Server, which acts as a service registry for 
 * microservices to register and discover each other.
 * 
 * @author Wilmer Ramos
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    /**
     * Main method to launch the Eureka Server application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

