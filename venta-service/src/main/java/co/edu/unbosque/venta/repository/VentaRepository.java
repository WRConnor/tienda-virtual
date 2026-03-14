package co.edu.unbosque.venta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.venta.model.Venta;

/**
 * Repository interface for managing persistence operations
 * related to the Venta entity.
 * 
 * It extends JpaRepository to provide standard CRUD operations
 * and custom query methods for sales management.
 * 
 * @author Wilmer Ramos
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
	
	/**
	 * Finds a sale by its unique sale code.
	 *
	 * @param codigoVenta unique code of the sale
	 * @return Optional containing the Venta if found
	 */
	public Optional<Venta> findByCodigoVenta(Long codigoVenta);

	/**
	 * Retrieves the highest sale code currently stored in the database.
	 * This is used to generate a new sequential sale code.
	 *
	 * @return the maximum codigoVenta value
	 */
	@Query("SELECT MAX(v.codigoVenta) FROM Venta v")
	Integer obtenerMaxCodigoVenta();

}