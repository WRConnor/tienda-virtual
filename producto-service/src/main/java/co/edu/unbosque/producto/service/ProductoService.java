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

/**
 * Service class responsible for managing product (Producto) operations.
 * It implements CRUD operations for the Producto entity and handles
 * business logic such as loading products from CSV files, validating
 * products by provider, and updating product information.
 * 
 * @author Wilmer Ramos
 */
@Service
public class ProductoService implements CRUDOperations<Producto> {

    @Autowired
    ProductoRepository productoRepo;

    /**
     * Creates a new Producto.
     *
     * @param o the Producto object to create
     * @return 0 on success
     */
    @Override
    public int crear(Producto o) {
        productoRepo.save(o);
        return 0;
    }

    /**
     * Loads Productos from a CSV input stream.
     * Expects each line in the CSV to contain: codigo,nombre,nitProveedor,precioCompra,precioVenta,ivaCompra
     *
     * @param archivoCSV InputStream of the CSV file
     * @return Status message indicating success or type of error
     * @throws IOException if reading the input stream fails
     */
    @Override
    public String cargarCSVProductos(InputStream archivoCSV) throws IOException {
        String linea;
        Producto p;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivoCSV))) {
            while ((linea = reader.readLine()) != null) {
                // Split CSV line considering quoted fields
                String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                // Parse values and create Producto object
                p = new Producto(
                    Long.parseLong(datos[0]),    // codigoProducto
                    datos[1],                    // nombreProducto
                    Long.parseLong(datos[2]),    // nitProveedor
                    Double.parseDouble(datos[3]),// precioCompra
                    Double.parseDouble(datos[4]),// precioVenta
                    Double.parseDouble(datos[5]) // ivaCompra
                );
                
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

    /**
     * Deletes a Producto by its ID.
     *
     * @param in ID of the Producto to delete
     * @return 0 if deleted successfully, 1 if not found
     */
    @Override
    public int eliminar(Long in) {
        if (productoRepo.existsById(in)) {
            productoRepo.deleteById(in);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Retrieves all Producto records.
     *
     * @return List of all Productos
     */
    @Override
    public List<Producto> mostrarTodo() {
        return productoRepo.findAll();
    }

    /**
     * Updates an existing Producto by ID.
     *
     * @param id the ID of the Producto to update
     * @param nuevaData the new data for the Producto
     * @return 0 if updated successfully, 1 if not found
     */
    @Override
    public int actualizar(Long id, Producto nuevaData) {
        Optional<Producto> found = productoRepo.findById(id);

        if (found.isPresent()) {
            Producto temp = found.get();

            temp.setNombreProducto(nuevaData.getNombreProducto());
            temp.setPrecioCompra(nuevaData.getPrecioCompra());
            temp.setPrecioVenta(nuevaData.getPrecioVenta());
            temp.setIvaCompra(nuevaData.getIvaCompra());
            temp.setNitProveedor(nuevaData.getNitProveedor());
            temp.setCodigoProducto(nuevaData.getCodigoProducto());

            productoRepo.save(temp); 
            return 0; 
        }

        return 1; // Producto no encontrado
    }

    /**
     * Finds a Producto by its ID.
     *
     * @param id the ID of the Producto
     * @return Optional containing the Producto if found
     */
    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepo.findById(id);
    }

    /**
     * Checks if there are Productos associated with a given nitProveedor.
     *
     * @param newProducto Producto to check
     * @return true if one or more Productos exist for the nitProveedor
     */
    public boolean findTitleAlreadyTaken(Producto newProducto) {
        List<Producto> encontrados = productoRepo.findByNitProveedor(newProducto.getNitProveedor());
        return !encontrados.isEmpty(); 
    }

}
