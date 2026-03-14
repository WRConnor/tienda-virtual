package co.edu.unbosque.venta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import co.edu.unbosque.venta.dto.ClienteDTO;
import co.edu.unbosque.venta.dto.FacturaDTO;
import co.edu.unbosque.venta.dto.ProductoDTO;
import co.edu.unbosque.venta.model.Venta;
import co.edu.unbosque.venta.service.VentaService;

/**
 * REST controller for handling sales (Ventas) endpoints.
 * Provides CRUD operations, reporting, and invoice retrieval.
 * 
 * @author Wilmer Ramos
 */
@RestController
@RequestMapping("/api/ventas")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8080" })
public class VentaController {

    @Autowired
    VentaService ventaServ;

    /**
     * Creates a new sale (Venta).
     *
     * @param c the Venta object to create
     * @return ResponseEntity with created Venta or error status
     */
    @PostMapping("/crear")
    public ResponseEntity<Venta> crear(@RequestBody Venta c) {
        int status = ventaServ.crear(c);
        if (status == 0) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.IM_USED);
        }
    }

    /**
     * Deletes a sale by its ID.
     *
     * @param id the ID of the sale to delete
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = ventaServ.eliminar(id);
        if (status == 0) {
            return new ResponseEntity<>("Venta eliminada con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error, venta no existente", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing sale by ID.
     *
     * @param id the ID of the sale to update
     * @param o the new Venta data
     * @return ResponseEntity with status message
     */
    @PutMapping(path = "/actualizar/{id}")
    ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Venta o) {
        int status = ventaServ.actualizar(id, o);
        if (status == 0) {
            return new ResponseEntity<>("Venta actualizada con exito", HttpStatus.ACCEPTED);
        } else if (status == 1) {
            return new ResponseEntity<>("Hay un codigo de venta ya existente ", HttpStatus.IM_USED);
        } else if (status == 2) {
            return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error desconocido", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all sales.
     *
     * @return ResponseEntity with list of ventas
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Venta>> mostrarTodo() {
        List<Venta> encontrado = ventaServ.mostrarTodo();
        if (encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(encontrado, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Retrieves client information by cedula.
     *
     * @param cedula the client's identification number
     * @return ResponseEntity with ClienteDTO or not found
     */
    @GetMapping("/clientes/{cedula}")
    public ResponseEntity<ClienteDTO> obtenerCliente(@PathVariable Long cedula) {
        ClienteDTO clienteDTO = ventaServ.obtenerClientePorCedula(cedula);
        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves product information by product code.
     *
     * @param codigo the product code
     * @return ResponseEntity with ProductoDTO or not found
     */
    @GetMapping("/buscarPorCodigo/{codigo}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorCodigo(@PathVariable Long codigo) {
        ProductoDTO productoDTO = ventaServ.obtenerProductoPorCodigo(codigo);
        if (productoDTO != null) {
            return ResponseEntity.ok(productoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Generates a report of total sales per client.
     *
     * @return ResponseEntity with report data and total sum
     */
    @GetMapping("/reporte-cliente")
    public ResponseEntity<Map<String, Object>> reporteVentasPorCliente() {
        List<Map<String, Object>> reporte = ventaServ.generarReporteVentasPorCliente();
        Double sumaTotal = reporte.stream().mapToDouble(r -> (Double) r.get("totalVentasCliente")).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("reporteClientes", reporte);
        response.put("sumaTotalVentas", sumaTotal);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the invoice (FacturaDTO) for a sale by its code.
     *
     * @param codigoVenta the sale code
     * @return ResponseEntity with FacturaDTO or not found
     */
    @GetMapping("/factura/{codigoVenta}")
    public ResponseEntity<FacturaDTO> obtenerFactura(@PathVariable Long codigoVenta) {
        FacturaDTO factura = ventaServ.obtenerFactura(codigoVenta);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }
}
