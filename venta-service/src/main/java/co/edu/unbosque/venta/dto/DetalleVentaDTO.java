/**
 * This package contains Data Transfer Objects (DTOs) used
 * for transferring data between layers and microservices
 * in the sales management system.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.venta.dto;

/**
 * Data Transfer Object representing a sale detail (DetalleVenta).
 * Used for transferring sale detail information between layers or microservices.
 * 
 * Author: Santiado Rada
 */
public class DetalleVentaDTO {

    /** Product code associated with the sale detail */
    private Long codigoProducto;

    /** Name of the product */
    private String nombreProducto;

    /** Quantity of the product in this detail */
    private Integer cantidad;

    /** Unit price of the product */
    private Double precioUnitario;

    /** Total value for this sale detail */
    private Double total;

    /** Default constructor */
    public DetalleVentaDTO() {}

    /**
     * Parameterized constructor to create a DetalleVentaDTO instance.
     *
     * @param codigoProducto code of the product
     * @param nombreProducto name of the product
     * @param cantidad quantity of the product
     * @param precioUnitario unit price of the product
     * @param total total value for this detail
     */
    public DetalleVentaDTO(Long codigoProducto, String nombreProducto, Integer cantidad, Double precioUnitario, Double total) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
