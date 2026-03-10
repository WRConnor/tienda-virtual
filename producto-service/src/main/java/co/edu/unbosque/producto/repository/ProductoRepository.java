package co.edu.unbosque.producto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	public Optional<Producto> findByNitProveedor(Long nit);
	public Optional<Producto> findByCodigoProducto(Long codigo);

}
