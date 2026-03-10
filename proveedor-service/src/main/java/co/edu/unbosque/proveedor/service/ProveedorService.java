package co.edu.unbosque.proveedor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proveedor.model.Proveedor;
import co.edu.unbosque.proveedor.repository.ProveedorRepository;

@Service
public class ProveedorService implements CRUDOperations<Proveedor> {
	
	@Autowired
	ProveedorRepository proveedorRepo;

	@Override
	public int crear(Proveedor o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			proveedorRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(proveedorRepo.existsById(in)) {
			proveedorRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<Proveedor> mostrarTodo() {
		return proveedorRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Proveedor nuevaData) {
		Optional<Proveedor> found = proveedorRepo.findById(id);
		Optional<Proveedor> newFound = proveedorRepo.findByNitProveedor(nuevaData.getNitProveedor());
		
		if (found.isPresent() && !newFound.isPresent()) {
			Proveedor temp = found.get();
			temp.setNitProveedor(nuevaData.getNitProveedor());
			temp.setNombreProveedor(nuevaData.getNombreProveedor());
			temp.setDireccionProveedor(nuevaData.getDireccionProveedor());
			temp.setCiudadProveedor(nuevaData.getCiudadProveedor());
			temp.setTelefonoProveedor(nuevaData.getTelefonoProveedor());
			proveedorRepo.save(temp);
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
	public Optional<Proveedor> buscarPorId(Long id) {
		return proveedorRepo.findById(id);
	}
	
	public boolean findTitleAlreadyTaken(Proveedor newProveedor) {
		Optional<Proveedor> found = proveedorRepo.findByNitProveedor(newProveedor.getNitProveedor());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
