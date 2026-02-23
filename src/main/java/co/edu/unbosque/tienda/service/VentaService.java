package co.edu.unbosque.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.Venta;
import co.edu.unbosque.tienda.repository.VentaRepository;

@Service
public class VentaService implements CRUDOperations<Venta> {
	
	@Autowired
	VentaRepository ventaRepo;

	@Override
	public int crear(Venta o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Long in) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Venta> mostrarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Long id, Venta ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Venta> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
