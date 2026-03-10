package co.edu.unbosque.venta.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.venta.dto.ClienteDTO;
import co.edu.unbosque.venta.dto.ProductoDTO;
import co.edu.unbosque.venta.feign.ClienteClient;
import co.edu.unbosque.venta.feign.ProductoClient;
import co.edu.unbosque.venta.model.DetalleVenta;
import co.edu.unbosque.venta.model.Venta;
import co.edu.unbosque.venta.repository.DetalleVentaRepository;
import co.edu.unbosque.venta.repository.VentaRepository;

@Service
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
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
	        double acumuladoValorVenta = 0.0;
	        double acumuladoIva = 0.0;
	        double acumuladoTotal = 0.0;

	        // Procesar cada detalle de la venta
	        for (DetalleVenta d : o.getDetalles()) {
	            // Obtener información del producto por código
	            ProductoDTO producto = obtenerProductoPorCodigo(d.getCodigoProducto());

	            Double precioVenta = producto.getPrecioVenta();
	            Integer cantidad = d.getCantidadProducto();

	            // Calcular valores del detalle
	            Double valorVenta = precioVenta * cantidad;
	            Double valorIva = valorVenta * 0.19; // IVA fijo del 19%
	            Double valorTotal = valorVenta + valorIva;

	            // Asignar valores calculados al detalle
	            d.setValorVenta(valorVenta);
	            d.setValorIva(valorIva);
	            d.setValorTotal(valorTotal);

	            // Asociar el detalle con la venta
	            d.setVenta(o);

	            // Acumular valores para la venta
	            acumuladoValorVenta += valorVenta;
	            acumuladoIva += valorIva;
	            acumuladoTotal += valorTotal;
	        }

	        // Asignar valores acumulados a la venta
	        o.setValorVenta(acumuladoValorVenta);
	        o.setIvaVenta(acumuladoIva);
	        o.setTotalVenta(acumuladoTotal);

	        // Guardar la venta junto con los detalles 

			for (DetalleVenta d : o.getDetalles()) {
			    d.setVenta(o);
			}
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
			temp.getDetalles().clear();
	        for (DetalleVenta d : nuevaData.getDetalles()) {
	            d.setVenta(temp); 
	            temp.getDetalles().add(d);
	        }
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
	
	public ClienteDTO obtenerClientePorCedula(Long cedula) {
        Map<String, Object> response = clienteCli.obtenerClientePorCedula(cedula);

        ClienteDTO clienteDTO = new ClienteDTO(
            Long.valueOf(response.get("idCliente").toString()),
            Long.valueOf(response.get("cedulaCliente").toString()),
            response.get("nombreCliente").toString(),
            response.get("direccionCliente").toString(),
            response.get("emailCliente").toString(),
            response.get("telefonoCliente").toString()
        );

        return clienteDTO;
    }
	
	 public ProductoDTO obtenerProductoPorCodigo(Long codigo) {
	        Map<String, Object> response = productoCli.obtenerProductoPorCodigo(codigo);

	        ProductoDTO productoDTO = new ProductoDTO();
	        productoDTO.setIdProducto((Long) response.get("idProducto"));
	        productoDTO.setCodigoProducto((Long) response.get("codigoProducto"));
	        productoDTO.setNombreProducto((String) response.get("nombreProducto"));
	        productoDTO.setPrecioCompra((Double) response.get("precioCompra"));
	        productoDTO.setPrecioVenta((Double) response.get("precioVenta"));
	        productoDTO.setIvaCompra((Double) response.get("ivaCompra"));
	        productoDTO.setNitProveedor((Long) response.get("nitProveedor"));

	        return productoDTO;
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
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
