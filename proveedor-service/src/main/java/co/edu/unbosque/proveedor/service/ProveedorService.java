package co.edu.unbosque.proveedor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proveedor.model.Proveedor;
import co.edu.unbosque.proveedor.repository.ProveedorRepository;

/**
 * Service class for managing Proveedor entities.
 * Implements the CRUDOperations interface to provide
 * create, read, update, and delete functionality for suppliers.
 * 
 * This class acts as the business logic layer between
 * the ProveedorController and the ProveedorRepository,
 * ensuring proper handling of supplier data.
 * 
 * @author Wilmer Ramos
 */
@Service
public class ProveedorService implements CRUDOperations<Proveedor> {
    
    @Autowired
    ProveedorRepository proveedorRepo;

    /**
     * Creates a new Proveedor.
     *
     * @param o the Proveedor object to create
     * @return 0 if successful, 1 if a Proveedor with the same NIT already exists
     */
    @Override
    public int crear(Proveedor o) {
        if(findTitleAlreadyTaken(o)) {
            // NIT duplicado
            return 1;
        } else {
            proveedorRepo.save(o);
            return 0;
        }
    }

    /**
     * Deletes a Proveedor by its ID.
     *
     * @param in the ID of the Proveedor to delete
     * @return 0 if deleted successfully, 1 if the Proveedor does not exist
     */
    @Override
    public int eliminar(Long in) {
        if(proveedorRepo.existsById(in)) {
            proveedorRepo.deleteById(in);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Retrieves all Proveedor records.
     *
     * @return a List of all Proveedor objects
     */
    @Override
    public List<Proveedor> mostrarTodo() {
        return proveedorRepo.findAll();
    }

    /**
     * Updates an existing Proveedor.
     *
     * @param id the ID of the Proveedor to update
     * @param nuevaData the new data for the Proveedor
     * @return 0 if updated successfully, 1 if NIT is duplicated, 2 if Proveedor not found
     */
    @Override
    public int actualizar(Long id, Proveedor nuevaData) {
        Optional<Proveedor> found = proveedorRepo.findById(id);
        Optional<Proveedor> newFound = proveedorRepo.findByNitProveedor(nuevaData.getNitProveedor());

        if (!found.isPresent()) return 2; // Proveedor no encontrado

        // permite actualizar si el NIT es único o pertenece al mismo proveedor
        if (newFound.isPresent() && !newFound.get().getIdProveedor().equals(id)) return 1; // NIT duplicado

        Proveedor temp = found.get();
        temp.setNitProveedor(nuevaData.getNitProveedor());
        temp.setNombreProveedor(nuevaData.getNombreProveedor());
        temp.setDireccionProveedor(nuevaData.getDireccionProveedor());
        temp.setCiudadProveedor(nuevaData.getCiudadProveedor());
        temp.setTelefonoProveedor(nuevaData.getTelefonoProveedor());
        proveedorRepo.save(temp);

        return 0; // actualización exitosa
    }

    /**
     * Finds a Proveedor by its ID.
     *
     * @param id the ID of the Proveedor
     * @return Optional containing the Proveedor if found, empty otherwise
     */
    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepo.findById(id);
    }
    
    /**
     * Checks if a Proveedor with the same NIT already exists.
     *
     * @param newProveedor the Proveedor to check
     * @return true if NIT already exists, false otherwise
     */
    public boolean findTitleAlreadyTaken(Proveedor newProveedor) {
        Optional<Proveedor> found = proveedorRepo.findByNitProveedor(newProveedor.getNitProveedor());
        return found.isPresent();
    }

}
