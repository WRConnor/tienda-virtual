package co.edu.unbosque.producto.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;
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
		productoRepo.save(o);
		return 0;
	}

	@Override
	public String cargarCSVProductos(InputStream archivoCSV) throws IOException {
		String linea;
		Producto p;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivoCSV))) {
			while ((linea = reader.readLine()) != null) {
				String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				p = new Producto(Long.parseLong(datos[0]), datos[1], Long.parseLong(datos[2]),
						Double.parseDouble(datos[3]), Double.parseDouble(datos[4]), Double.parseDouble(datos[5]));
				productoRepo.save(p);
			}
			return "Archivo leido con Exito";
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return "Error: no se seleccionó archivo para cargar";
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			return "Error: datos leídos inválidos";
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return "Error: formato de archivo inválido";
		} catch (NumberFormatException e) {
			return "Error: datos leidos invalidos";
		}
	}

	@Override
	public int eliminar(Long in) {
		if (productoRepo.existsById(in)) {
			productoRepo.deleteById(in);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public List<Producto> mostrarTodo() {
		return productoRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Producto nuevaData) {
		// Buscamos el producto por su ID
		Optional<Producto> found = productoRepo.findById(id);

		if (found.isPresent()) {
			Producto temp = found.get();

			// Actualizamos los datos del producto
			temp.setNombreProducto(nuevaData.getNombreProducto());
			temp.setPrecioCompra(nuevaData.getPrecioCompra());
			temp.setPrecioVenta(nuevaData.getPrecioVenta());
			temp.setIvaCompra(nuevaData.getIvaCompra());
			temp.setNitProveedor(nuevaData.getNitProveedor());
			temp.setCodigoProducto(nuevaData.getCodigoProducto());

			productoRepo.save(temp); // Guardamos cambios
			return 0; // Actualización exitosa
		}

		return 1; // Producto no encontrado
	}

	@Override
	public Optional<Producto> buscarPorId(Long id) {
		return productoRepo.findById(id);
	}

	public boolean findTitleAlreadyTaken(Producto newProducto) {
		List<Producto> encontrados = productoRepo.findByNitProveedor(newProducto.getNitProveedor());
		return !encontrados.isEmpty(); // Devuelve true si hay productos con ese proveedor
	}

}
