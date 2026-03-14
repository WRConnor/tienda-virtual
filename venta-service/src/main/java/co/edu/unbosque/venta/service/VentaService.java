/**
 * This package contains service classes responsible for
 * handling business logic related to sales management,
 * including creation of sales, interaction with external
 * services, and generation of sales reports and invoices.
 * 
 * @author Wilmer Ramos
 */
package co.edu.unbosque.venta.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.venta.dto.ClienteDTO;
import co.edu.unbosque.venta.dto.DetalleVentaDTO;
import co.edu.unbosque.venta.dto.FacturaDTO;
import co.edu.unbosque.venta.dto.ProductoDTO;
import co.edu.unbosque.venta.feign.ClienteClient;
import co.edu.unbosque.venta.feign.ProductoClient;
import co.edu.unbosque.venta.model.DetalleVenta;
import co.edu.unbosque.venta.model.Venta;
import co.edu.unbosque.venta.repository.DetalleVentaRepository;
import co.edu.unbosque.venta.repository.VentaRepository;

/**
 * Service class responsible for managing sales operations.
 * It implements CRUD operations for the Venta entity and
 * handles business logic such as calculating totals, retrieving
 * product and client information, generating invoices and reports.
 * 
 * @author Wilmer Ramos
 */
@Service
@Transactional
public class VentaService implements CRUDOperations<Venta> {

    /** Repository used to manage Venta persistence operations */
    @Autowired
    VentaRepository ventaRepo;

    /** Repository used to manage DetalleVenta persistence operations */
    @Autowired
    DetalleVentaRepository detalleRepo;

    /** Feign client used to communicate with the Cliente microservice */
    @Autowired
    ClienteClient clienteCli;

    /** Feign client used to communicate with the Producto microservice */
    @Autowired
    ProductoClient productoCli;

    /**
     * Creates a new sale and calculates all financial values
     * such as subtotal, VAT (IVA), and total.
     *
     * @param o Venta object containing the sale information
     * @return 0 if the sale is successfully created
     */
    @Override
	public int crear(Venta o) {

		double acumuladoValorVenta = 0.0;
		double acumuladoIva = 0.0;
		double acumuladoTotal = 0.0;

		for (DetalleVenta d : o.getDetalles()) {

			ProductoDTO producto = obtenerProductoPorCodigo(d.getCodigoProducto());

			Double precioVenta = producto.getPrecioVenta();
			Integer cantidad = d.getCantidadProducto();

			Double valorVenta = precioVenta * cantidad;
			Double valorIva = valorVenta * 0.19;
			Double valorTotal = valorVenta + valorIva;

			d.setValorVenta(valorVenta);
			d.setValorIva(valorIva);
			d.setValorTotal(valorTotal);

			d.setVenta(o);

			acumuladoValorVenta += valorVenta;
			acumuladoIva += valorIva;
			acumuladoTotal += valorTotal;
		}

		o.setValorVenta(acumuladoValorVenta);
		o.setIvaVenta(acumuladoIva);
		o.setTotalVenta(acumuladoTotal);

		Integer maxCodigo = ventaRepo.obtenerMaxCodigoVenta();

		Long nuevoCodigo = 1L;

		if (maxCodigo != null) {
			nuevoCodigo = maxCodigo.longValue() + 1;
		}

		o.setCodigoVenta(nuevoCodigo);

		ventaRepo.save(o);

		return 0;
	}

    /**
     * Deletes a sale by its ID.
     *
     * @param in ID of the sale to delete
     * @return 0 if deletion was successful, 1 if the sale does not exist
     */
    @Override
    public int eliminar(Long in) {
        if (ventaRepo.existsById(in)) {
            ventaRepo.deleteById(in);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Retrieves all sales stored in the database.
     *
     * @return list of Venta objects
     */
    @Override
    public List<Venta> mostrarTodo() {
        return ventaRepo.findAll();
    }

    /**
     * Updates an existing sale and its detail list.
     *
     * @param id ID of the sale to update
     * @param nuevaData new sale data
     * @return 0 if update was successful, 2 if the sale does not exist
     */
    @Override
    public int actualizar(Long id, Venta nuevaData) {

        Optional<Venta> found = ventaRepo.findById(id);

        if (found.isPresent()) {

            Venta temp = found.get();

            temp.setCedulaCliente(nuevaData.getCedulaCliente());
            temp.setCedulaUsuario(nuevaData.getCedulaUsuario());

            temp.getDetalles().clear();

            for (DetalleVenta d : nuevaData.getDetalles()) {

                d.setVenta(temp);
                temp.getDetalles().add(d);

            }

            ventaRepo.save(temp);

            return 0;
        }

        return 2;
    }

    /**
     * Finds a sale by its ID.
     *
     * @param id ID of the sale
     * @return Optional containing the sale if found
     */
    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepo.findById(id);
    }

    /**
     * Retrieves client information from the Cliente microservice.
     *
     * @param cedula client identification number
     * @return ClienteDTO containing client information
     */
    public ClienteDTO obtenerClientePorCedula(Long cedula) {

        Map<String, Object> response = clienteCli.obtenerClientePorCedula(cedula);

        ClienteDTO clienteDTO = new ClienteDTO(
                Long.valueOf(response.get("idCliente").toString()),
                Long.valueOf(response.get("cedulaCliente").toString()),
                response.get("nombreCliente").toString(),
                response.get("direccionCliente").toString(),
                response.get("emailCliente").toString(),
                response.get("telefonoCliente").toString());

        return clienteDTO;
    }

    /**
     * Retrieves product information from the Producto microservice.
     *
     * @param codigo product code
     * @return ProductoDTO containing product information
     */
    public ProductoDTO obtenerProductoPorCodigo(Long codigo) {
        Map<String, Object> response = productoCli.obtenerProductoPorCodigo(codigo);

        if (response == null || response.isEmpty()) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(Long.valueOf(response.get("idProducto").toString()));
        dto.setCodigoProducto(Long.valueOf(response.get("codigoProducto").toString()));
        dto.setNombreProducto(response.get("nombreProducto").toString());
        dto.setPrecioCompra(Double.valueOf(response.get("precioCompra").toString()));
        dto.setPrecioVenta(Double.valueOf(response.get("precioVenta").toString()));
        dto.setIvaCompra(Double.valueOf(response.get("ivaCompra").toString()));
        dto.setNitProveedor(Long.valueOf(response.get("nitProveedor").toString()));

        return dto;
    }

    /**
     * Generates a sales report grouped by client.
     *
     * @return list containing client sales summary
     */
    public List<Map<String, Object>> generarReporteVentasPorCliente() {

        List<Venta> ventas = ventaRepo.findAll();

        Map<Long, Map<String, Object>> reporteMap = new HashMap<>();

        for (Venta v : ventas) {

            Long cedulaCliente = v.getCedulaCliente();

            Map<String, Object> clienteData = clienteCli.obtenerClientePorCedula(cedulaCliente);

            String nombreCliente = (String) clienteData.get("nombreCliente");

            Double totalVenta = v.getTotalVenta();

            if (reporteMap.containsKey(cedulaCliente)) {

                Map<String, Object> clienteReporte = reporteMap.get(cedulaCliente);

                Double acumulado = (Double) clienteReporte.get("totalVentasCliente");

                clienteReporte.put("totalVentasCliente", acumulado + totalVenta);

            } else {

                Map<String, Object> clienteReporte = new HashMap<>();

                clienteReporte.put("cedulaCliente", cedulaCliente);
                clienteReporte.put("nombreCliente", nombreCliente);
                clienteReporte.put("totalVentasCliente", totalVenta);

                reporteMap.put(cedulaCliente, clienteReporte);
            }
        }

        return new ArrayList<>(reporteMap.values());
    }

    /**
     * Checks if a sale code already exists in the database.
     *
     * @param newVenta sale to verify
     * @return true if the sale code already exists
     */
    public boolean findTitleAlreadyTaken(Venta newVenta) {

        Optional<Venta> found = ventaRepo.findByCodigoVenta(newVenta.getCodigoVenta());

        return found.isPresent();
    }

    /**
     * Generates a complete invoice using the sale information,
     * client data, and product details.
     *
     * @param codigoVenta sale code used to retrieve the invoice
     * @return FacturaDTO containing the invoice information
     */
	public FacturaDTO obtenerFactura(Long codigoVenta){

		Optional<Venta> ventaOpt = ventaRepo.findByCodigoVenta(codigoVenta);

		if(ventaOpt.isEmpty()){
			return null;
		}

		Venta venta = ventaOpt.get();

		ClienteDTO cliente = obtenerClientePorCedula(venta.getCedulaCliente());

		List<DetalleVentaDTO> detallesDTO = new ArrayList<>();

		for(DetalleVenta d : venta.getDetalles()){

			ProductoDTO producto = obtenerProductoPorCodigo(d.getCodigoProducto());

			DetalleVentaDTO detalleDTO = new DetalleVentaDTO(
				d.getCodigoProducto(),
				producto.getNombreProducto(),
				d.getCantidadProducto(),
				producto.getPrecioVenta(),
				d.getValorTotal()
			);

			detallesDTO.add(detalleDTO);
		}

		FacturaDTO factura = new FacturaDTO(
			venta.getCodigoVenta(),
			cliente,
			detallesDTO,
			venta.getValorVenta(),
			venta.getIvaVenta(),
			venta.getTotalVenta()
		);

		return factura;
	}
}