package co.edu.unbosque.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tienda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
