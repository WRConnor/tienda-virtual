package co.edu.unbosque.venta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.venta.model.Venta;


public interface VentaRepository extends JpaRepository<Venta, Long> {
	
	public Optional<Venta> findByCodigoVenta(Long codigoVenta);

}
