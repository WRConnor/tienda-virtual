package co.edu.unbosque.tienda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.unbosque.tienda.model.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
		public boolean deleteByNombre(String nombre);
		public Optional<Cliente> findByNombre(String nombre);
		public List<Cliente> findByDireccion (String direccion);
}
