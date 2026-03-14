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

/**
 * REST controller exposing endpoints to manage client entities.
 * Delegates business work to {@link ClienteService} and translates
 * service responses into HTTP status codes and bodies.
 *
 * Endpoints are prefixed with {@code /api/clientes}.
 *
 * @author Wilmer Ramos
 */
@RestController
@RequestMapping("/api/clientes")
@Transactional
@CrossOrigin(origins = { "*", "localhost:8081" })
public class ClienteController {

    @Autowired
    ClienteService clienteServ;
    
    /**
     * POST /crear - create a new client record.
     *
     * @param c client object from request body
     * @return HTTP 201 Created on success
     */
    @PostMapping(path="/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crear(@RequestBody Cliente c) {
        clienteServ.crear(c);
        return new ResponseEntity<String>("Cliente creado con exito", HttpStatus.CREATED);
    }
    
    /**
     * DELETE /eliminar/{id} - remove a client by id.
     *
     * @param id identifier of the client to delete
     * @return 202 Accepted if removed, 404 Not Found otherwise
     */
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> eliminar(@PathVariable Long id) {
        int status = clienteServ.eliminar(id);

        if (status == 0) {
            return new ResponseEntity<>("Cliente eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error, cliente no existente", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * PUT /actualizar/{id} - update an existing client.
     *
     * @param id client id to update
     * @param o new client data
     * @return various status codes depending on outcome
     */
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
    
    /**
     * GET /mostrartodo - retrieve all clients.
     *
     * @return list of clients or 204 if none exist
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Cliente>> mostrarTodo() {
        List<Cliente> encontrado = clienteServ.mostrarTodo();
        if(encontrado.isEmpty()) {
            return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
        }
    }
    
    /**
     * POST /buscarPorCedula/{cedula} - lookup client by identification number.
     *
     * @param cedula client cedula
     * @return client data or 204 if not found
     */
    @PostMapping("/buscarPorCedula/{cedula}")
    public ResponseEntity<Cliente> buscar(@PathVariable  Long cedula ) {
        Cliente encontrado = clienteServ.buscarPorCedula(cedula);
        if(encontrado==null) {
            return new ResponseEntity<>(encontrado,HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(encontrado,HttpStatus.ACCEPTED); 
        }
    }
}
