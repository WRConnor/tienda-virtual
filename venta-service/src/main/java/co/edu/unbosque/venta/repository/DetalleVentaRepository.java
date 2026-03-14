package co.edu.unbosque.venta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.venta.model.DetalleVenta;

/**
 * Repository interface responsible for handling persistence
 * operations for the DetalleVenta entity.
 * 
 * It extends JpaRepository to provide built-in CRUD methods
 * and defines additional query methods when needed.
 * 
 * @author Wilmer Ramos
 */
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
	
	/**
	 * Finds a sale detail using its unique detail code.
	 *
	 * @param codigoDetalleVenta unique code of the sale detail
	 * @return Optional containing the DetalleVenta if found
	 */
	public Optional<DetalleVenta> findByCodigoDetalleVenta(Long codigoDetalleVenta);

}