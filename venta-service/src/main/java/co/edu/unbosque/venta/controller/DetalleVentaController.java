package co.edu.unbosque.venta.controller;

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

import co.edu.unbosque.venta.model.DetalleVenta;
import co.edu.unbosque.venta.service.DetalleVentaService;

/**
 * REST controller for handling sale details (DetalleVenta) endpoints.
 * Provides CRUD operations for sale details.
 * 
 * @author Wilmer Ramos
 */
@RestController
@RequestMapping("/api/detalles-venta")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class DetalleVentaController {

    @Autowired
    DetalleVentaService detalleVentaServ;

    /**
     * Creates a new sale detail.
     *
     * @param c the DetalleVenta object to create
     * @return ResponseEntity with success message and HTTP status CREATED
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody DetalleVenta c) {
        detalleVentaServ.crear(c);
        return new ResponseEntity<>("Detalle de venta creado con exito", HttpStatus.CREATED);
    }

    /**
     * Deletes a sale detail by its ID.
     *
     * @param id the ID of the DetalleVenta to delete
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = detalleVentaServ.eliminar(id);
        if (status == 0) {
            return new ResponseEntity<>("Detalle de venta eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error, detalle de venta no existente", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing sale detail by ID.
     *
     * @param id the ID of the DetalleVenta to update
     * @param o the new DetalleVenta data
     * @return ResponseEntity with status message
     */
    @PutMapping(path = "/actualizar/{id}")
    ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody DetalleVenta o) {
        int status = detalleVentaServ.actualizar(id, o);
        if (status == 0) {
            return new ResponseEntity<>("Detalle de venta actualizado con exito", HttpStatus.ACCEPTED);
        } else if (status == 1) {
            return new ResponseEntity<>("Detalle de venta no encontrado ", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all sale details.
     *
     * @return ResponseEntity with list of DetalleVenta or NO_CONTENT if empty
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<DetalleVenta>> mostrarTodo() {
        List<DetalleVenta> encontrado = detalleVentaServ.mostrarTodo();
        if (encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED);
        }
    }
}
