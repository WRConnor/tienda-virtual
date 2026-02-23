package co.edu.unbosque.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tienda.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
