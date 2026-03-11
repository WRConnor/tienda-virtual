package co.edu.unbosque.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.cliente.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	public Optional<Cliente> findByCedulaCliente(Long cedulaCliente);

}
