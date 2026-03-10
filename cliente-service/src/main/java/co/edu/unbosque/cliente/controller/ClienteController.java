package co.edu.unbosque.cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
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

import co.edu.unbosque.cliente.model.Cliente;
import co.edu.unbosque.cliente.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ClienteController {

	@Autowired
	ClienteService clienteServ;
	
	@PostMapping(path="/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(@RequestBody Cliente c) {
		clienteServ.crear(c);
		return new ResponseEntity<String>("Cliente creado con exito", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = clienteServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Cliente eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, cliente no existente", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/actualizar/{id}")
	ResponseEntity<String> actualizar(@PathVariable Long id,@RequestBody Cliente o){
	
		int status = clienteServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Cliente actualizado con exito", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<>("Cedula ya registrada", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Cliente>> mostrarTodo() {
		List<Cliente> encontrado = clienteServ.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}
}
