/**
 * This package contains service classes responsible for
 * handling business logic related to sales details management,
 * including CRUD operations for DetalleVenta entities.
 * 
 * @author Wilmer Ramos
 */
package co.edu.unbosque.venta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.venta.model.DetalleVenta;
import co.edu.unbosque.venta.repository.DetalleVentaRepository;

/**
 * Service class responsible for managing operations related to
 * DetalleVenta entities. It implements CRUD operations and
 * communicates with the DetalleVentaRepository for database access.
 * 
 * @author Wilmer Ramos
 */
@Service
public class DetalleVentaService implements CRUDOperations<DetalleVenta> {

    /** Repository used to perform persistence operations for DetalleVenta */
    @Autowired
    DetalleVentaRepository detalleVentaRepo;

    /**
     * Creates a new sale detail record.
     *
     * @param o DetalleVenta object containing the detail information
     * @return 0 if the detail was successfully created,
     *         1 if the detail code already exists
     */
    @Override
    public int crear(DetalleVenta o) {

        if (findTitleAlreadyTaken(o)) {
            return 1;
        }

        detalleVentaRepo.save(o);

        return 0;
    }

    /**
     * Deletes a sale detail by its ID.
     *
     * @param id ID of the DetalleVenta to delete
     * @return 0 if the deletion was successful,
     *         1 if the detail does not exist
     */
    @Override
    public int eliminar(Long id) {

        if (detalleVentaRepo.existsById(id)) {

            detalleVentaRepo.deleteById(id);

            return 0;
        }

        return 1;
    }

    /**
     * Retrieves all sale details stored in the database.
     *
     * @return list of DetalleVenta objects
     */
    @Override
    public List<DetalleVenta> mostrarTodo() {

        return detalleVentaRepo.findAll();
    }

    /**
     * Updates an existing sale detail.
     *
     * @param id ID of the detail to update
     * @param nuevaData new data to update the detail
     * @return 0 if the update was successful,
     *         1 if the detail does not exist
     */
    @Override
    public int actualizar(Long id, DetalleVenta nuevaData) {

        Optional<DetalleVenta> found = detalleVentaRepo.findById(id);

        if (found.isPresent()) {

            DetalleVenta temp = found.get();

            temp.setCantidadProducto(nuevaData.getCantidadProducto());
            temp.setValorTotal(nuevaData.getValorTotal());
            temp.setValorVenta(nuevaData.getValorVenta());
            temp.setValorIva(nuevaData.getValorIva());
            temp.setCodigoProducto(nuevaData.getCodigoProducto());
            temp.setVenta(nuevaData.getVenta());

            detalleVentaRepo.save(temp);

            return 0;
        }

        return 1;
    }

    /**
     * Finds a sale detail by its ID.
     *
     * @param id ID of the DetalleVenta
     * @return Optional containing the detail if found
     */
    @Override
    public Optional<DetalleVenta> buscarPorId(Long id) {

        return detalleVentaRepo.findById(id);
    }

    /**
     * Checks if a sale detail code already exists in the database.
     *
     * @param newDetalleVenta DetalleVenta object to verify
     * @return true if the detail code already exists, false otherwise
     */
    public boolean findTitleAlreadyTaken(DetalleVenta newDetalleVenta) {

        Optional<DetalleVenta> found =
                detalleVentaRepo.findByCodigoDetalleVenta(newDetalleVenta.getCodigoDetalleVenta());

        return found.isPresent();
    }
}