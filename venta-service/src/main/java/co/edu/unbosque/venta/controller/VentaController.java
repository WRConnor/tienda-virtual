package co.edu.unbosque.venta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.venta.dto.ClienteDTO;
import co.edu.unbosque.venta.dto.ProductoDTO;
import co.edu.unbosque.venta.model.Venta;
import co.edu.unbosque.venta.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class VentaController {

	@Autowired
	VentaService ventaServ;
	
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody Venta c) {
		int status = ventaServ.crear(c);
		if (status==0) {
			return new ResponseEntity<>("Venta creada con exito", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Ya existe un codigo venta asociado", HttpStatus.IM_USED);
		}
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = ventaServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Venta eliminada con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, venta no existente", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/actualizar/{id}")
	ResponseEntity<String> actualizar(@PathVariable Long id,@RequestBody Venta o){
	
		int status = ventaServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Venta actualizada con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Hay un codigo de venta ya existente ", HttpStatus.IM_USED);
		} else if(status == 2) {
			return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>("Error desconocido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Venta>> mostrarTodo() {
		List<Venta> encontrado = ventaServ.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}
	
	@PostMapping("/clientes/{cedula}")
    public ResponseEntity<ClienteDTO> obtenerCliente(@PathVariable Long cedula) {
        ClienteDTO clienteDTO = ventaServ.obtenerClientePorCedula(cedula);

        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
    @GetMapping("/buscarPorCodigo/{codigo}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorCodigo(@PathVariable Long codigo) {
        ProductoDTO productoDTO = ventaServ.obtenerProductoPorCodigo(codigo);
        if (productoDTO != null) {
            return ResponseEntity.ok(productoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/reporte-cliente")
    public ResponseEntity<Map<String, Object>> reporteVentasPorCliente() {
        List<Map<String, Object>> reporte = ventaServ.generarReporteVentasPorCliente();

        Double sumaTotal = reporte.stream()
                                  .mapToDouble(r -> (Double) r.get("totalVentasCliente"))
                                  .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("reporteClientes", reporte);
        response.put("sumaTotalVentas", sumaTotal);

        return ResponseEntity.ok(response);
    }

}
