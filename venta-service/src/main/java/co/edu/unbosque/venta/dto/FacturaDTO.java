package co.edu.unbosque.venta.dto;

import java.util.List;

public class FacturaDTO {

    private Long codigoVenta;
    private ClienteDTO cliente;
    private List<DetalleVentaDTO> detalles;

    private Double valorVenta;
    private Double ivaVenta;
    private Double totalVenta;

    public FacturaDTO() {}

    public FacturaDTO(Long codigoVenta, ClienteDTO cliente, List<DetalleVentaDTO> detalles,
                      Double valorVenta, Double ivaVenta, Double totalVenta) {
        this.codigoVenta = codigoVenta;
        this.cliente = cliente;
        this.detalles = detalles;
        this.valorVenta = valorVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
    }

    public Long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
    }

    public Double getIvaVenta() {
        return ivaVenta;
    }

    public void setIvaVenta(Double ivaVenta) {
        this.ivaVenta = ivaVenta;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }
}
