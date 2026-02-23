package co.edu.unbosque.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.Proveedor;
import co.edu.unbosque.tienda.repository.ProveedorRepository;

@Service
public class ProveedorService implements CRUDOperations<Proveedor> {
	
	@Autowired
	ProveedorRepository proveedorRepo;

	@Override
	public int crear(Proveedor o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Long in) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Proveedor> mostrarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Long id, Proveedor ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Proveedor> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
