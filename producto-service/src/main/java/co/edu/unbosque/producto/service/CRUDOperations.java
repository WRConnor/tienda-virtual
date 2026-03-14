package co.edu.unbosque.producto.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Generic interface defining basic CRUD operations that
 * service classes implement for their corresponding
 * domain entities. Methods return status codes or
 * optional results depending on the operation.
 *
 * @param <T> the type of entity managed by the service
 * @author Wilmer Ramos
 */
public interface CRUDOperations<T> {

    /**
     * Create a new record for the given entity.
     *
     * @param o entity instance to persist
     * @return status code (0 = success, others indicate error)
     */
    public int crear(T o);

    /**
     * Delete an entity by its identifier.
     *
     * @param in id of the entity to remove
     * @return status code (0 = success, 1 = not found)
     */
    public int eliminar(Long in);

    /**
     * Retrieve all entities of this type.
     *
     * @return list containing zero or more entities
     */
    public List<T> mostrarTodo();

    /**
     * Update an existing entity identified by {@code id}.
     *
     * @param id identifier of the entity to update
     * @param ob object containing new data
     * @return status code (0 = success, others indicate failure)
     */
    public int actualizar(Long id, T ob);

    /**
     * Find an entity by its identifier.
     *
     * @param id identifier to search for
     * @return optional containing entity if found
     */
    public Optional<T> buscarPorId(Long id);

    /**
     * Loads products from a CSV file input stream.
     * This method reads the CSV file, parses its content,
     * and stores the products into the database.
     *
     * @param archivoCSV InputStream representing the CSV file
     * @return String message indicating success or failure
     * @throws IOException if an error occurs while reading the file
     */
    public String cargarCSVProductos(InputStream archivoCSV) throws IOException;



}
