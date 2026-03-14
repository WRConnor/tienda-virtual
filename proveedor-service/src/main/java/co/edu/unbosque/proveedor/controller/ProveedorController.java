package co.edu.unbosque.proveedor.controller;

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

import co.edu.unbosque.proveedor.model.Proveedor;
import co.edu.unbosque.proveedor.service.ProveedorService;

/**
 * REST controller for managing Proveedor entities.
 * Provides endpoints for creating, updating, deleting,
 * and retrieving suppliers in the system.
 * 
 * This controller acts as the entry point for HTTP requests
 * related to suppliers and delegates business logic to the
 * corresponding service layer.
 * 
 * @author Wilmer Ramos
 */


@RestController
@RequestMapping("/api/proveedores")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ProveedorController {

    @Autowired
    ProveedorService proveedorServ;
    
    /**
     * Creates a new Proveedor.
     *
     * @param c the Proveedor object to create
     * @return ResponseEntity with success message and HTTP CREATED status
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Proveedor c) {
        proveedorServ.crear(c);
        return new ResponseEntity<>("Proveedor creado con exito", HttpStatus.CREATED);
    }
    
    /**
     * Deletes a Proveedor by its ID.
     *
     * @param id the ID of the Proveedor to delete
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = proveedorServ.eliminar(id);

        if (status == 0) {
            return new ResponseEntity<>("Proveedor eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error, proveedor no existente", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Updates an existing Proveedor by ID.
     *
     * @param id the ID of the Proveedor to update
     * @param o the updated Proveedor object
     * @return ResponseEntity with result message and status
     */
    @PutMapping(path = "/actualizar/{id}")
    ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Proveedor o) {
        int status = proveedorServ.actualizar(id, o);

        if (status == 0) {
            return new ResponseEntity<>("Proveedor actualizado con exito", HttpStatus.ACCEPTED);
        } else if (status == 1) {
            // NIT duplicado o proveedor no encontrado
            return new ResponseEntity<>("Proveedor no encontrado", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Retrieves all Proveedor records.
     *
     * @return ResponseEntity containing the list of all Proveedor objects
     *         or NO_CONTENT if none are found
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Proveedor>> mostrarTodo() {
        List<Proveedor> encontrado = proveedorServ.mostrarTodo();
        if (encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED); 
        }
    }
}