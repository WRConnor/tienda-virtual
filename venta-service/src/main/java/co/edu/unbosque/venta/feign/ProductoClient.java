/**
 * This package contains Feign client interfaces used for
 * communication with external microservices in the sales system.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.venta.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for communicating with the Product microservice.
 * 
 * Provides methods to retrieve product information using product codes.
 * 
 * Author: Wilmer Ramos
 */
@FeignClient(name = "producto-service")  
public interface ProductoClient {

    /**
     * Retrieves product information from the product microservice
     * by product code.
     *
     * @param codigo product code to search for
     * @return Map containing product data with key-value pairs
     */
    @GetMapping("/api/productos/buscarPorCodigo/{codigo}")
    Map<String, Object> obtenerProductoPorCodigo(@PathVariable Long codigo);
}