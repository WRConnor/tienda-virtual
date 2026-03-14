/**
 * Package containing repository interfaces for product entities.
 * These repositories provide CRUD operations and custom queries
 * for managing products in the database using Spring Data JPA.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.producto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.producto.model.Producto;

/**
 * Repository interface for the Producto entity.
 * Extends JpaRepository to provide standard CRUD operations
 * and defines custom query methods for product management.
 * 
 * Author: Wilmer Ramos
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Finds all products associated with a specific supplier's NIT.
     *
     * @param nitProveedor Supplier's tax identification number
     * @return List of products linked to the given supplier
     */
    List<Producto> findByNitProveedor(Long nitProveedor);

    /**
     * Retrieves all products that match a given product code.
     *
     * @param codigoProducto Unique product code
     * @return List of products with the specified code
     */
    List<Producto> findAllByCodigoProducto(Long codigoProducto);

    /**
     * Finds a single product by its unique product code.
     *
     * @param codigo Unique product code
     * @return Optional containing the product if found, otherwise empty
     */
    Optional<Producto> findByCodigoProducto(Long codigo);
}

