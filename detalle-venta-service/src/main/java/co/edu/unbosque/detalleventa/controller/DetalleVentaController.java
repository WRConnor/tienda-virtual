package co.edu.unbosque.detalleventa.controller;

import java.util.List;

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

import co.edu.unbosque.detalleventa.model.DetalleVenta;
import co.edu.unbosque.detalleventa.service.DetalleVentaService;

@RestController
@RequestMapping("/api/detalles-venta")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class DetalleVentaController {

	@Autowired
	DetalleVentaService detalleVentaServ;
	
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody DetalleVenta c) {
		detalleVentaServ.crear(c);
		return new ResponseEntity<String>("Detalle de venta creado con exito", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = detalleVentaServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Detalle de venta eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, detalle de venta no existente", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/actualizar/{id}")
	ResponseEntity<String> actualizar(@PathVariable Long id,@RequestBody DetalleVenta o){
	
		int status = detalleVentaServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Detalle de venta actualizado con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Detalle de venta no encontrado ", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<DetalleVenta>> mostrarTodo() {
		List<DetalleVenta> encontrado = detalleVentaServ.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}
}
