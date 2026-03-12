package co.edu.unbosque.venta.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service")  
public interface ProductoClient {

    @GetMapping("/api/productos/buscarPorCodigo/{codigo}")
    Map<String, Object> obtenerProductoPorCodigo(@PathVariable Long codigo);
}

