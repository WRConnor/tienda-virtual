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

@Service
@Transactional
public class VentaService implements CRUDOperations<Venta> {

    @Autowired
    VentaRepository ventaRepo;

    @Autowired
    DetalleVentaRepository detalleRepo;

    @Autowired
    ClienteClient clienteCli;

    @Autowired
    ProductoClient productoCli;

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

		// 🔹 GENERAR CODIGO DE VENTA AUTOMÁTICO
		Integer maxCodigo = ventaRepo.obtenerMaxCodigoVenta();

		Long nuevoCodigo = 1L;

		if (maxCodigo != null) {
			nuevoCodigo = maxCodigo.longValue() + 1;
		}

		o.setCodigoVenta(nuevoCodigo);

		// 🔹 GUARDAR VENTA
		ventaRepo.save(o);

		return 0;
	}

    @Override
    public int eliminar(Long in) {
        if (ventaRepo.existsById(in)) {
            ventaRepo.deleteById(in);
            return 0;
        } else {
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

    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepo.findById(id);
    }

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

    public boolean findTitleAlreadyTaken(Venta newVenta) {

        Optional<Venta> found = ventaRepo.findByCodigoVenta(newVenta.getCodigoVenta());

        return found.isPresent();
    }

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
