package co.edu.unbosque.tienda.controller;

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

import co.edu.unbosque.tienda.model.Producto;
import co.edu.unbosque.tienda.service.ProductoService;

@RestController
@RequestMapping("/producto")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ProductoController {

	@Autowired
	ProductoService productoServ;
	
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody Producto c) {
		productoServ.crear(c);
		return new ResponseEntity<String>("Producto creado con exito", 
				HttpStatus.CREATED);
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = productoServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, detalle de venta no existente", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/actualizar")
	ResponseEntity<String> actualizar(Long id,@RequestBody Producto o){
	
		int status = productoServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Producto actualizado con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Producto no encontrado ", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Producto>> mostrarTodo() {
		List<Producto> encontrado = productoServ.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}
	
}
