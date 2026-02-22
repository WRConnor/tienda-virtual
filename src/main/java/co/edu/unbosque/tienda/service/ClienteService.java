package co.edu.unbosque.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.Cliente;
import co.edu.unbosque.tienda.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;

	public void crear(Cliente newCliente) {
		clienteRepository.save(newCliente);
	}

	public List<Cliente> mostrarTodo() {
		return clienteRepository.findAll();
	}
}
