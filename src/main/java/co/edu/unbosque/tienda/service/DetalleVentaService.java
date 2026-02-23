package co.edu.unbosque.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.DetalleVenta;
import co.edu.unbosque.tienda.repository.DetalleVentaRepository;

@Service
public class DetalleVentaService implements CRUDOperations<DetalleVenta> {
	
	@Autowired
	DetalleVentaRepository DetalleVentaRepo;

	@Override
	public int crear(DetalleVenta o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Long in) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DetalleVenta> mostrarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Long id, DetalleVenta ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<DetalleVenta> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
