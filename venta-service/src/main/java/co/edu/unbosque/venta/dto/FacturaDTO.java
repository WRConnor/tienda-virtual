/**
 * This package contains Data Transfer Objects (DTOs) used
 * for transferring data between layers and microservices
 * in the sales management system.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.venta.dto;

import java.util.List;

/**
 * Data Transfer Object representing an invoice (Factura).
 * Combines client information, sale details, and totals for a sale.
 * 
 * Author: Santiago Rada
 */
public class FacturaDTO {

    /** Unique sale code */
    private Long codigoVenta;

    /** Client information associated with the sale */
    private ClienteDTO cliente;

    /** List of sale detail DTOs associated with this sale */
    private List<DetalleVentaDTO> detalles;

    /** Total value of the sale before VAT */
    private Double valorVenta;

    /** VAT value of the sale */
    private Double ivaVenta;

    /** Total value of the sale including VAT */
    private Double totalVenta;

    /** Default constructor */
    public FacturaDTO() {}

    /**
     * Parameterized constructor to create a FacturaDTO instance.
     *
     * @param codigoVenta unique sale code
     * @param cliente client information
     * @param detalles list of sale details
     * @param valorVenta total value before VAT
     * @param ivaVenta VAT value
     * @param totalVenta total value including VAT
     */
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
