package co.edu.unbosque.venta.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cliente-service") 
public interface ClienteClient {

    @PostMapping("/api/clientes/buscarPorCedula/{cedula}")
    Map<String, Object> obtenerClientePorCedula(@PathVariable Long cedula);
}


