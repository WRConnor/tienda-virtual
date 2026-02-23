package co.edu.unbosque.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.Producto;
import co.edu.unbosque.tienda.repository.ProductoRepository;

@Service
public class ProductoService implements CRUDOperations<Producto> {
	
	@Autowired
	ProductoRepository productoRepo;

	@Override
	public int crear(Producto o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Long in) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Producto> mostrarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Long id, Producto ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Producto> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
