package co.edu.unbosque.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.usuario.model.Usuario;

/**
 * Repository interface for managing Usuario entities.
 * Extends JpaRepository to provide CRUD operations and
 * defines custom query methods specific to user requirements.
 * 
 * @author Wilmer Ramos
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Finds a user by their unique identification number (cedula).
     *
     * @param cedulaUsuario unique identification number of the user
     * @return Optional containing the Usuario if found, empty otherwise
     */
    public Optional<Usuario> findByCedulaUsuario(Long cedulaUsuario);

}