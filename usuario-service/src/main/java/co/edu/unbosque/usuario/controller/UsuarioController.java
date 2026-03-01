package co.edu.unbosque.usuario.controller;

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

import co.edu.unbosque.usuario.model.Usuario;
import co.edu.unbosque.usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class UsuarioController {

	@Autowired
	UsuarioService usuarioServ;
	
	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody Usuario c) {
		usuarioServ.crear(c);
		return new ResponseEntity<String>("Usuario creado con exito", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminar(@PathVariable Long id) {
		int status = usuarioServ.eliminar(id);

		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error, usuario no existente", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/actualizar/{id}")
	ResponseEntity<String> actualizar(@PathVariable Long id,@RequestBody Usuario o){
	
		int status = usuarioServ.actualizar(id, o);

		if (status == 0) {
			return new ResponseEntity<>("Usuario actualizado con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Usuario no encontrado ", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<Usuario>> mostrarTodo() {
		List<Usuario> encontrado = usuarioServ.mostrarTodo();
		if(encontrado.isEmpty()) {
			return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
		}
	}
}
