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

@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalleventa")
    private Long idDetalleVenta;

    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "valor_venta")
    private Double valorVenta;

    @Column(name = "valor_iva")
    private Double valorIva;

    @Column(name = "codigo_producto")
    private Long codigoProducto;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    @JsonIgnore
    private Venta venta;

    public DetalleVenta() {}

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
