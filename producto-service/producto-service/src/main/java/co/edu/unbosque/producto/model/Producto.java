package co.edu.unbosque.producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long idProducto;

	@Column(name = "codigo_producto")
	private Long codigoProducto;

	@Column(name = "nombre_producto")
	private String nombreProducto;

	@Column(name = "precio_compra")
	private Double precioCompra;

	@Column(name = "precio_venta")
	private Double precioVenta;

	@Column(name = "ivacompra")
	private Double ivaCompra;

	@Column(name = "nitproveedor")
	private Long nitProveedor;

	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(long codigoProducto, String nombreProducto, long nitProveedor,
			double precioCompra, double ivaCompra, double precioVenta) {

		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.nitProveedor = nitProveedor;
		this.precioCompra = precioCompra;
		this.ivaCompra = ivaCompra;
		this.precioVenta = precioVenta;

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

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Double getIvaCompra() {
		return ivaCompra;
	}

	public void setIvaCompra(Double ivaCompra) {
		this.ivaCompra = ivaCompra;
	}

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

}