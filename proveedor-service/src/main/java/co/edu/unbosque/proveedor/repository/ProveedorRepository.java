/**
 * Repository interface for managing Proveedor entities.
 * 
 * Provides CRUD operations through JpaRepository and custom queries for Proveedor.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.proveedor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proveedor.model.Proveedor;

/**
 * JpaRepository for Proveedor entity.
 * 
 * Extends JpaRepository to provide basic CRUD operations and query methods.
 * - Entity type: Proveedor
 * - Primary key type: Long
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
    /**
     * Finds a Proveedor by its NIT (tax identification number).
     *
     * @param nitProveedor the NIT of the provider
     * @return Optional containing the Proveedor if found, empty otherwise
     */
    public Optional<Proveedor> findByNitProveedor(Long nitProveedor);

}