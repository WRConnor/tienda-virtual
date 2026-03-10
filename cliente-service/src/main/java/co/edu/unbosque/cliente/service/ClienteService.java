package co.edu.unbosque.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.cliente.model.Cliente;
import co.edu.unbosque.cliente.repository.ClienteRepository;

@Service
public class ClienteService implements CRUDOperations<Cliente> {
	
	@Autowired
	ClienteRepository clienteRepo;

	@Override
	public int crear(Cliente o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			clienteRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(clienteRepo.existsById(in)) {
			clienteRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<Cliente> mostrarTodo() {
		return clienteRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Cliente nuevaData) {

		Optional<Cliente> found = clienteRepo.findById(id);

		if (!found.isPresent()) {
			return 2; 
		}

		Optional<Cliente> cedulaExistente =
				clienteRepo.findByCedulaCliente(nuevaData.getCedulaCliente());


		if (cedulaExistente.isPresent() &&
			!cedulaExistente.get().getIdCliente().equals(id)) {
			return 1; 
		}

		Cliente temp = found.get();
		temp.setCedulaCliente(nuevaData.getCedulaCliente());
		temp.setNombreCliente(nuevaData.getNombreCliente());
		temp.setDireccionCliente(nuevaData.getDireccionCliente());
		temp.setEmailCliente(nuevaData.getEmailCliente());
		temp.setTelefonoCliente(nuevaData.getTelefonoCliente());

		clienteRepo.save(temp);

		return 0;
	}


	@Override
	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepo.findById(id);
	}
	
	public boolean findTitleAlreadyTaken(Cliente newCliente) {
		Optional<Cliente> found = clienteRepo.findByCedulaCliente(newCliente.getCedulaCliente());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
