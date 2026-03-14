/**
 * This package contains service classes responsible for
 * handling business logic related to user management,
 * including creation, update, deletion of users,
 * login verification, and validation of unique identifiers.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.usuario.model.Usuario;
import co.edu.unbosque.usuario.repository.UsuarioRepository;

/**
 * Service class responsible for managing user operations.
 * It implements CRUD operations for the Usuario entity and
 * handles business logic such as login validation and 
 * checking for duplicate identification numbers (cedula).
 * 
 * Author: Wilmer Ramos
 */
@Service
public class UsuarioService implements CRUDOperations<Usuario> {

    /** Repository used to manage Usuario persistence operations */
    @Autowired
    UsuarioRepository usuarioRepo;

    /**
     * Creates a new user if the identification number (cedula) is not already taken.
     *
     * @param o Usuario object containing user information
     * @return 0 if creation was successful, 1 if the cedula already exists
     */
    @Override
    public int crear(Usuario o) {
        if (findTitleAlreadyTaken(o)) {
            return 1; // cedula ya registrada
        } else {
            usuarioRepo.save(o);
            return 0; // usuario creado exitosamente
        }
    }

    /**
     * Deletes a user by its ID.
     *
     * @param in ID of the user to delete
     * @return 0 if deletion was successful, 1 if the user does not exist
     */
    @Override
    public int eliminar(Long in) {
        if (usuarioRepo.existsById(in)) {
            usuarioRepo.deleteById(in);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Retrieves all users stored in the database.
     *
     * @return list of Usuario objects
     */
    @Override
    public List<Usuario> mostrarTodo() {
        return usuarioRepo.findAll();
    }

    /**
     * Updates an existing user with new data.
     *
     * @param id ID of the user to update
     * @param nuevaData new user information
     * @return 0 if update was successful, 
     *         1 if the cedula is already in use by another user, 
     *         2 if the user does not exist
     */
    @Override
    public int actualizar(Long id, Usuario nuevaData) {

        Optional<Usuario> found = usuarioRepo.findById(id);

        if (!found.isPresent()) {
            return 2; // usuario no encontrado
        }

        Usuario existente = found.get();

        // Verifica si la nueva cedula ya existe en otro usuario
        Optional<Usuario> cedulaExistente = usuarioRepo.findByCedulaUsuario(nuevaData.getCedulaUsuario());

        if (cedulaExistente.isPresent() && !cedulaExistente.get().getIdUsuario().equals(id)) {
            return 1; // cedula ya registrada en otro usuario
        }

        // Actualiza los datos del usuario existente
        existente.setEmailUsuario(nuevaData.getEmailUsuario());
        existente.setCedulaUsuario(nuevaData.getCedulaUsuario());
        existente.setNombreUsuario(nuevaData.getNombreUsuario());
        existente.setPassword(nuevaData.getPassword());
        existente.setUsuario(nuevaData.getUsuario());

        usuarioRepo.save(existente);

        return 0; // actualización exitosa
    }

    /**
     * Finds a user by its ID.
     *
     * @param id ID of the user
     * @return Optional containing the user if found
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepo.findById(id);
    }

    /**
     * Validates user login credentials.
     *
     * @param user username
     * @param password password
     * @return Optional containing the user if credentials are correct, empty otherwise
     */
    public Optional<Usuario> login(String user, String password) {
        for (Usuario u : mostrarTodo()) {
            if (u.getUsuario().equals(user) && u.getPassword().equals(password)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a user's identification number (cedula) already exists.
     *
     * @param newUsuario user to verify
     * @return true if the cedula is already taken, false otherwise
     */
    public boolean findTitleAlreadyTaken(Usuario newUsuario) {
        Optional<Usuario> found = usuarioRepo.findByCedulaUsuario(newUsuario.getCedulaUsuario());
        return found.isPresent();
    }

}
