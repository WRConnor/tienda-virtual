package co.edu.unbosque.venta.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Feign client for communicating with the Client microservice.
 * 
 * Provides methods to retrieve client information using client identification numbers (cedula).
 * 
 * @author Wilmer Ramos
 */
@FeignClient(name = "cliente-service") 
public interface ClienteClient {

    /**
     * Retrieves client information from the client microservice
     * by client identification number (cedula).
     *
     * @param cedula the client's identification number
     * @return Map containing client data with key-value pairs
     */
    @PostMapping("/api/clientes/buscarPorCedula/{cedula}")
    Map<String, Object> obtenerClientePorCedula(@PathVariable Long cedula);
}