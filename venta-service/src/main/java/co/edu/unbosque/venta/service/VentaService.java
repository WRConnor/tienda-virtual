package co.edu.unbosque.venta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.venta.model.Venta;
import co.edu.unbosque.venta.repository.VentaRepository;

@Service
public class VentaService implements CRUDOperations<Venta> {
	
	@Autowired
	VentaRepository ventaRepo;

	@Override
	public int crear(Venta o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			ventaRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(ventaRepo.existsById(in)) {
			ventaRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<Venta> mostrarTodo() {
		return ventaRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Venta nuevaData) {
		Optional<Venta> found = ventaRepo.findById(id);
		Optional<Venta> newFound = ventaRepo.findByCodigoVenta(nuevaData.getCodigoVenta());
		
		if (found.isPresent() && !newFound.isPresent()) {
			Venta temp = found.get();
			temp.setCodigoVenta(nuevaData.getCodigoVenta());;
			temp.setCedulaCliente(nuevaData.getCedulaCliente());
			temp.setCedulaUsuario(nuevaData.getCedulaUsuario());
			temp.setIvaVenta(nuevaData.getIvaVenta());
			temp.setTotalVenta(nuevaData.getTotalVenta());
			temp.setValorVenta(nuevaData.getValorVenta());
			ventaRepo.save(temp);
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
	public Optional<Venta> buscarPorId(Long id) {
		return ventaRepo.findById(id);
	}
	
	public boolean findTitleAlreadyTaken(Venta newVenta) {
		Optional<Venta> found = ventaRepo.findByCodigoVenta(newVenta.getCodigoVenta());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
