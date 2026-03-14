package co.edu.unbosque.venta.dto;

/**
 * Data Transfer Object representing a product (Producto).
 * Used for sending product data between microservices and layers.
 * 
 * @author Wilmer Ramos
 */
public class ProductoDTO {
	
    /** Unique identifier of the product */
    private Long idProducto;

    /** Unique product code */
    private Long codigoProducto;

    /** Name of the product */
    private String nombreProducto;

    /** Purchase price of the product */
    private Double precioCompra;

    /** Sale price of the product */
    private Double precioVenta;

    /** VAT value applied to the purchase price */
    private Double ivaCompra;

    /** Supplier identification number (NIT) */
    private Long nitProveedor;
    
    /** Default constructor */
    public ProductoDTO() {
        // Default constructor
    }

    /**
     * Parameterized constructor to create a ProductoDTO instance.
     *
     * @param idProducto unique identifier of the product
     * @param codigoProducto unique product code
     * @param nombreProducto name of the product
     * @param precioCompra purchase price of the product
     * @param precioVenta sale price of the product
     * @param ivaCompra VAT value for the purchase
     * @param nitProveedor supplier identification number (NIT)
     */
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
