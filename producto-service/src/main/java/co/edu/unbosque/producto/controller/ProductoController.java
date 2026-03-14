package co.edu.unbosque.producto.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.producto.model.Producto;
import co.edu.unbosque.producto.service.ProductoService;

/**
 * REST controller for managing Producto entities.
 * Provides endpoints for creating, updating, deleting,
 * retrieving, and bulk uploading products.
 * 
 * @author Wilmer Ramos, Santiago Toyo
 */
@RestController
@RequestMapping("/api/productos")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class ProductoController {

    @Autowired
    ProductoService productoServ;

    /**
     * Endpoint to create a new product.
     *
     * @param c Product object to be created
     * @return ResponseEntity with success message and HTTP status
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Producto c) {
        productoServ.crear(c);
        return new ResponseEntity<String>("Producto creado con exito", HttpStatus.CREATED);
    }

    /**
     * Endpoint to upload products from a CSV file.
     *
     * @param archivoCSV CSV file containing product data
     * @return ResponseEntity with success or error message
     * @throws IOException if file reading fails
     */
    @PostMapping("/cargarProductos")
    public ResponseEntity<String> cargarProductos(@RequestParam("Examinar") MultipartFile archivoCSV)
            throws IOException {
        try {
            productoServ.cargarCSVProductos(archivoCSV.getInputStream());
            return new ResponseEntity<String>("Producto creado con exito", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<String>("Error al intentar leer el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete a product by its ID.
     *
     * @param id Product ID
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = productoServ.eliminar(id);

        if (status == 0) {
            return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error, producto no existente", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to update an existing product by its ID.
     *
     * @param id Product ID
     * @param o Updated product object
     * @return ResponseEntity with success or error message
     */
    @PutMapping(path = "/actualizar/{id}")
    ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Producto o) {

        int status = productoServ.actualizar(id, o);

        if (status == 0) {
            return new ResponseEntity<>("Producto actualizado con exito", HttpStatus.ACCEPTED);
        } else if (status == 1) {
            return new ResponseEntity<>("Producto no encontrado ", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to retrieve all products.
     *
     * @return ResponseEntity containing the list of products
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Producto>> mostrarTodo() {
        List<Producto> encontrado = productoServ.mostrarTodo();
        if (encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Endpoint to find a product by its code.
     *
     * @param codigo Product code
     * @return ResponseEntity containing the product if found, otherwise 404
     */
    @GetMapping("/buscarPorCodigo/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable Long codigo) {
        List<Producto> productos = productoServ.mostrarTodo();
        for (Producto p : productos) {
            if (p.getCodigoProducto().equals(codigo)) {
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }
}


