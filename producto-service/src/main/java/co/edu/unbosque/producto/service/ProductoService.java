package co.edu.unbosque.producto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.producto.model.Producto;
import co.edu.unbosque.producto.repository.ProductoRepository;

@Service
public class ProductoService implements CRUDOperations<Producto> {
	
	@Autowired
	ProductoRepository productoRepo;

	@Override
	public int crear(Producto o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			productoRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(productoRepo.existsById(in)) {
			productoRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<Producto> mostrarTodo() {
		return productoRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Producto nuevaData) {
		Optional<Producto> found = productoRepo.findById(id);
		Optional<Producto> newFound = productoRepo.findfindByNitProveedor(nuevaData.getNitProveedor());
		
		if (found.isPresent() && !newFound.isPresent()) {
			Producto temp = found.get();
			temp.setNombreProducto(nuevaData.getNombreProducto());
			temp.setPrecioCompra(nuevaData.getPrecioCompra());
			temp.setPrecioVenta(nuevaData.getPrecioVenta());
			temp.setIvaCompra(nuevaData.getIvaCompra());
			temp.setNitProveedor(nuevaData.getNitProveedor());
			productoRepo.save(temp);
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
	public Optional<Producto> buscarPorId(Long id) {
		return productoRepo.findById(id);
	}
	
	public boolean findTitleAlreadyTaken(Producto newProducto) {
		Optional<Producto> found = productoRepo.findfindByNitProveedor(newProducto.getNitProveedor());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
