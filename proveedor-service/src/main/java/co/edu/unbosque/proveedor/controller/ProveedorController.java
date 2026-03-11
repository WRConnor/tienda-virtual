package co.edu.unbosque.proveedor.controller;

import java.util.List;
import java.util.Optional;

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
import co.edu.unbosque.proveedor.model.Proveedor;
import co.edu.unbosque.proveedor.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ProveedorController {

	@Autowired
	ProveedorService proveedorServ;

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody Proveedor c) {
		proveedorServ.crear(c);
		return new ResponseEntity<String>("Proveedor creado con exito", HttpStatus.CREATED);
	}

	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = proveedorServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Proveedor eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, proveedor no existente", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Proveedor o) {

		int status = proveedorServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Proveedor actualizado con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Proveedor no encontrado ", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Proveedor>> mostrarTodo() {
		List<Proveedor> encontrado = proveedorServ.mostrarTodo();
		if (encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/buscarProveedor/{nit}")
	public ResponseEntity<Optional<Proveedor>> buscarPorNIT(@PathVariable long nit) {
		Optional<Proveedor> proveedorEncontrado = proveedorServ.buscarPorNIT(nit);

		if (proveedorEncontrado.isPresent()) {
			return new ResponseEntity<>(proveedorEncontrado, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(proveedorEncontrado, HttpStatus.NOT_FOUND);
		}
	}
}
