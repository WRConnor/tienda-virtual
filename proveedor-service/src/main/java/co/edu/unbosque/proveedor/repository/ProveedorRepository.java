package co.edu.unbosque.proveedor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proveedor.model.Proveedor;



public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
	
	public Optional<Proveedor> findByNitProveedor(Long nitProveedor);

}
