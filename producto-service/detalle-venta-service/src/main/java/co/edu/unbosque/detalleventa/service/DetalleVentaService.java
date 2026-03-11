package co.edu.unbosque.detalleventa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.detalleventa.model.DetalleVenta;
import co.edu.unbosque.detalleventa.repository.DetalleVentaRepository;

@Service
public class DetalleVentaService implements CRUDOperations<DetalleVenta> {
	
	@Autowired
	DetalleVentaRepository detalleVentaRepo;

	@Override
	public int crear(DetalleVenta o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			detalleVentaRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(detalleVentaRepo.existsById(in)) {
			detalleVentaRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<DetalleVenta> mostrarTodo() {
		return detalleVentaRepo.findAll();
	}

	@Override
	public int actualizar(Long id, DetalleVenta nuevaData) {
		Optional<DetalleVenta> found = detalleVentaRepo.findById(id);
		Optional<DetalleVenta> newFound = detalleVentaRepo.findByCodigoDetalleVenta(nuevaData.getCodigoDetalleVenta());
		
		if (found.isPresent() && !newFound.isPresent()) {
			DetalleVenta temp = found.get();
			temp.setCantidadProducto(nuevaData.getCantidadProducto());
			temp.setValorTotal(nuevaData.getValorTotal());
			temp.setValorVenta(nuevaData.getValorVenta());
			temp.setValorIva(nuevaData.getValorIva());
			temp.setCodigoProducto(nuevaData.getCodigoProducto());
			temp.setCodigoVenta(nuevaData.getCodigoVenta());
			detalleVentaRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1;
		}
		if (!found.isPresent()) {
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public Optional<DetalleVenta> buscarPorId(Long id) {
		return detalleVentaRepo.findById(id);
	}
	
	public boolean findTitleAlreadyTaken(DetalleVenta newDetalleVenta) {
		Optional<DetalleVenta> found = detalleVentaRepo.findByCodigoDetalleVenta(newDetalleVenta.getCodigoDetalleVenta());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
