package co.edu.unbosque.detalleventa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="detalle_ventas")
public class DetalleVenta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalleventa")
    private Long idDetalleVenta;
    
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "valor_venta")
    private Double valorVenta;

    @Column(name = "valoriva")
    private Double valorIva;

    @Column(name = "codigo_producto")
    private Long codigoProducto;

    @Column(name = "codigo_venta")
    private Long codigoVenta;
    
    public DetalleVenta() {
		// TODO Auto-generated constructor stub
	}

	public DetalleVenta(Integer cantidadProducto, Double valorTotal, Double valorVenta, Double valorIva,
			Long codigoProducto, Long codigoVenta) {
		super();
		this.cantidadProducto = cantidadProducto;
		this.valorTotal = valorTotal;
		this.valorVenta = valorVenta;
		this.valorIva = valorIva;
		this.codigoProducto = codigoProducto;
		this.codigoVenta = codigoVenta;
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

	public Long getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(Long codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public Long getIdDetalleVenta() {
		return idDetalleVenta;
	}

	public void setIdDetalleVenta(Long idDetalleVenta) {
		this.idDetalleVenta = idDetalleVenta;
	}
	
	
	
}
