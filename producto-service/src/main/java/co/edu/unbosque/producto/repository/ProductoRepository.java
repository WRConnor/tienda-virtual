package co.edu.unbosque.producto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNitProveedor(Long nitProveedor);

    // Busca por código de producto y devuelve el primero encontrado
    Optional<Producto> findFirstByCodigoProducto(Long codigoProducto);

    // Si quieres obtener todos con el mismo código
    List<Producto> findAllByCodigoProducto(Long codigoProducto);

	Optional<Producto> findByCodigoProducto(Long codigo);

}