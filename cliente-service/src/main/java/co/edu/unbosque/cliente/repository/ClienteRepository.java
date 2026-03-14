package co.edu.unbosque.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.cliente.model.Cliente;

/**
 * Repository interface for the Cliente entity.
 * Extends JpaRepository to provide standard CRUD operations
 * and defines custom query methods for client management.
 * 
 * @author Wilmer Ramos
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Finds a client by their unique identification number (cedula).
     *
     * @param cedulaCliente Client's identification number
     * @return Optional containing the client if found, otherwise empty
     */
    public Optional<Cliente> findByCedulaCliente(Long cedulaCliente);

}

