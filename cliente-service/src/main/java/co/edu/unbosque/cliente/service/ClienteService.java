/**
 * Package containing service classes for client management.
 * These classes implement the business logic and operations 
 * related to clients, acting as an intermediary between 
 * controllers and repositories.
 * 
 * Author: Wilmer Ramos
 */


package co.edu.unbosque.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.cliente.model.Cliente;
import co.edu.unbosque.cliente.repository.ClienteRepository;

/**
 * Business service for managing {@link co.edu.unbosque.cliente.model.Cliente}
 * entities. Implements {@link CRUDOperations} to provide standard
 * create/read/update/delete behavior along with a few helpers.
 *
 * Spring will detect this class via the {@link Service} annotation.
 *
 * @author Wilmer Ramos
 */
@Service
public class ClienteService implements CRUDOperations<Cliente> {
    
    @Autowired
    ClienteRepository clienteRepo;

    /**
     * Persist a new client, verifying that the cedula is not already used.
     *
     * @param o client object to persist
     * @return 0 on success, 1 if cedula already exists
     */
    @Override
    public int crear(Cliente o) {
        if(findTitleAlreadyTaken(o)) {
            return 1;
        }else {
            clienteRepo.save(o);
            return 0;
        }
    }

    /**
     * Delete the client with the given database id.
     *
     * @param in id of the client to remove
     * @return 0 if removed, 1 if not found
     */
    @Override
    public int eliminar(Long in) {
        if(clienteRepo.existsById(in)) {
            clienteRepo.deleteById(in);
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * Retrieve every client in the repository.
     *
     * @return list of clients (possibly empty)
     */
    @Override
    public List<Cliente> mostrarTodo() {
        return clienteRepo.findAll();
    }

    /**
     * Update an existing client after performing validation checks.
     *
     * @param id id of the client to update
     * @param nuevaData new values for the client
     * @return 0 on success, 1 if cedula conflict, 2 if id not found
     */
    @Override
    public int actualizar(Long id, Cliente nuevaData) {

        Optional<Cliente> found = clienteRepo.findById(id);

        if (!found.isPresent()) {
            return 2; 
        }

        Optional<Cliente> cedulaExistente =
                clienteRepo.findByCedulaCliente(nuevaData.getCedulaCliente());


        if (cedulaExistente.isPresent() &&
            !cedulaExistente.get().getIdCliente().equals(id)) {
            return 1; 
        }

        Cliente temp = found.get();
        temp.setCedulaCliente(nuevaData.getCedulaCliente());
        temp.setNombreCliente(nuevaData.getNombreCliente());
        temp.setDireccionCliente(nuevaData.getDireccionCliente());
        temp.setEmailCliente(nuevaData.getEmailCliente());
        temp.setTelefonoCliente(nuevaData.getTelefonoCliente());

        clienteRepo.save(temp);

        return 0;
    }

    /**
     * Find a client by its database identifier.
     *
     * @param id id to look up
     * @return optional containing the client if found
     */
    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepo.findById(id);
    }
    
    /**
     * Look up a client using the cedula field.
     *
     * @param cedula identification number
     * @return matching client (assumes existence)
     */
    public Cliente buscarPorCedula(Long cedula) {
        return clienteRepo.findByCedulaCliente(cedula).get();
    }
    
    /**
     * Helper to determine whether the cedula of a new client is already used.
     *
     * @param newCliente candidate client object
     * @return true if cedula already exists, false otherwise
     */
	public boolean findTitleAlreadyTaken(Cliente newCliente) {
		Optional<Cliente> found = clienteRepo.findByCedulaCliente(newCliente.getCedulaCliente());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
