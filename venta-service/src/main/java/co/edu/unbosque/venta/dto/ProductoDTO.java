package co.edu.unbosque.venta.dto;



public class ProductoDTO {
	
    private Long idProducto;
    private Long codigoProducto;
    private String nombreProducto;
    private Double precioCompra;
    private Double precioVenta;
    private Double ivaCompra;
    private Long nitProveedor;
    
    public ProductoDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductoDTO(Long idProducto, Long codigoProducto, String nombreProducto, Double precioCompra,
			Double precioVenta, Double ivaCompra, Long nitProveedor) {
		super();
		this.idProducto = idProducto;
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.ivaCompra = ivaCompra;
		this.nitProveedor = nitProveedor;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
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
    
}
