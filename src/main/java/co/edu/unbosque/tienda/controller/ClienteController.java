package co.edu.unbosque.tienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tienda.model.Cliente;
import co.edu.unbosque.tienda.service.ClienteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/cliente")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ClienteController {
	@Autowired
	ClienteService clienteService;

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody Cliente c) {
		clienteService.crear(c);
		return new ResponseEntity<String>("Cliente creado con exito", 
				HttpStatus.CREATED);
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Cliente>> mostrarTodo() {
		List<Cliente> encontrado = clienteService.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}

}
