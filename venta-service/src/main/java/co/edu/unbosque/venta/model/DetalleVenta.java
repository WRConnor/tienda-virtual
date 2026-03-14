/**
 * This package contains model classes (entities) used for
 * the sales management system. These classes map to database
 * tables using JPA annotations.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.venta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity representing a sale detail (DetalleVenta).
 * Maps to the "detalle_ventas" table in the database.
 * 
 * Each DetalleVenta is associated with a Venta and contains
 * product, quantity, and pricing information.
 * 
 * Author: Wilmer Ramos
 */
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    /** Primary key of the sale detail */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalleventa")
    private Long idDetalleVenta;

    /** Unique code of the sale detail */
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    /** Quantity of the product in this detail */
    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    /** Total value including VAT for this detail */
    @Column(name = "valor_total")
    private Double valorTotal;

    /** Sale value before VAT */
    @Column(name = "valor_venta")
    private Double valorVenta;

    /** VAT value for this detail */
    @Column(name = "valor_iva")
    private Double valorIva;

    /** Product code associated with this detail */
    @Column(name = "codigo_producto")
    private Long codigoProducto;

    /** Reference to the associated sale */
    @ManyToOne
    @JoinColumn(name = "id_venta")
    @JsonIgnore
    private Venta venta;

    /** Default constructor */
    public DetalleVenta() {}

    /**
     * Parameterized constructor to create a DetalleVenta instance.
     *
     * @param codigoDetalleVenta unique code for this detail
     * @param cantidadProducto quantity of the product
     * @param valorTotal total value including VAT
     * @param valorVenta value before VAT
     * @param valorIva VAT value for this detail
     * @param codigoProducto code of the associated product
     * @param venta associated Venta object
     */
    public DetalleVenta(Long codigoDetalleVenta, Integer cantidadProducto, Double valorTotal,
                        Double valorVenta, Double valorIva, Long codigoProducto, Venta venta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidadProducto = cantidadProducto;
        this.valorTotal = valorTotal;
        this.valorVenta = valorVenta;
        this.valorIva = valorIva;
        this.codigoProducto = codigoProducto;
        this.venta = venta;
    }

    public Long getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Long idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Long getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
