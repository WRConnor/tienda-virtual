/**
 * This package contains REST controllers for managing users (usuarios)
 * and related authentication operations in the user management system.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.usuario.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.usuario.dto.LoginResponse;
import co.edu.unbosque.usuario.model.Usuario;
import co.edu.unbosque.usuario.service.JwtService;
import co.edu.unbosque.usuario.service.UsuarioService;

/**
 * REST controller for handling users (Usuarios) endpoints.
 * Provides CRUD operations and login/authentication using JWT.
 * 
 * Author: Wilmer Ramos
 */
@RestController
@RequestMapping("/api/usuarios")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class UsuarioController {

    @Autowired
    UsuarioService usuarioServ;

    @Autowired
    JwtService jwtService;
    
    /**
     * Creates a new user (Usuario).
     *
     * @param c the Usuario object to create
     * @return ResponseEntity with success message and HTTP CREATED status
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Usuario c) {
        usuarioServ.crear(c);
        return new ResponseEntity<>("Usuario creado con exito", HttpStatus.CREATED);
    }
    
    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = usuarioServ.eliminar(id);

        if (status == 0) {
            return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            // User not found
            return new ResponseEntity<>("Error, usuario no existente", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Updates an existing user by ID.
     *
     * @param id the ID of the user to update
     * @param o the updated Usuario object
     * @return ResponseEntity with result of update operation
     */
    @PutMapping(path = "/actualizar/{id}")
    ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Usuario o) {
        int status = usuarioServ.actualizar(id, o);

        if (status == 0) {
            return new ResponseEntity<>("Usuario actualizado con exito", HttpStatus.OK);
        } else if (status == 1) {
            // Duplicate cedula (identification) error
            return new ResponseEntity<>("Cedula ya registrada", HttpStatus.BAD_REQUEST);
        } else {
            // User not found
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing list of all Usuario objects,
     *         or NO_CONTENT if none are found
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Usuario>> mostrarTodo() {
        List<Usuario> encontrado = usuarioServ.mostrarTodo();
        if (encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED); 
        }
    }
    
    /**
     * Performs user login using username and password.
     *
     * @param usuario the username
     * @param password the password
     * @return ResponseEntity with JWT token and user info if login succeeds,
     *         or UNAUTHORIZED if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String password) {
        Optional<Usuario> usuarioEncontrado = usuarioServ.login(usuario, password);
        
        if (usuarioEncontrado.isPresent()) {
            Usuario u = usuarioEncontrado.get();
            String token = jwtService.generateToken(u.getUsuario(), u.getRol());
            // Return token and user info
            return new ResponseEntity<>(new LoginResponse(token, u.getRol(), u.getUsuario(), u.getCedulaUsuario()), HttpStatus.OK);
        } else {
            // Invalid username or password
            return new ResponseEntity<>("Usuario o contraseña no válida", HttpStatus.UNAUTHORIZED);
        }
    }
}
