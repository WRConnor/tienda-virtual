package co.edu.unbosque.detalleventa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.detalleventa.model.DetalleVenta;


public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
	
	public Optional<DetalleVenta> findByCodigoDetalleVenta(Long codigoDetalleVenta);

}
