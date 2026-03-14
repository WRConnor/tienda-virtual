package co.edu.unbosque.producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a Producto (product).
 * Maps to the "productos" table in the database.
 * 
 * @author Wilmer Ramos
 */
@Entity
@Table(name = "productos")
public class Producto {

    /**
     * Primary key for the Producto entity.
     * Auto-generated using IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    /**
     * Unique product code used to identify the product.
     */
    @Column(name = "codigo_producto")
    private Long codigoProducto;

    /**
     * Name of the product.
     */
    @Column(name = "nombre_producto")
    private String nombreProducto;

    /**
     * Purchase price of the product.
     */
    @Column(name = "precio_compra")
    private Double precioCompra;

    /**
     * Sale price of the product.
     */
    @Column(name = "precio_venta")
    private Double precioVenta;

    /**
     * Value-added tax (IVA) applied to the purchase.
     */
    @Column(name = "ivacompra")
    private Double ivaCompra;

    /**
     * Tax identification number (NIT) of the supplier associated with the product.
     */
    @Column(name = "nitproveedor")
    private Long nitProveedor;

    /**
     * Default constructor required by JPA.
     */
    public Producto() {
        // Default constructor
    }

    /**
     * Parameterized constructor to create a Producto instance with all fields except ID.
     *
     * @param codigoProducto Unique product code
     * @param nombreProducto Name of the product
     * @param nitProveedor Supplier's tax identification number
     * @param precioCompra Purchase price
     * @param ivaCompra Value-added tax applied to purchase
     * @param precioVenta Sale price
     */
    public Producto(long codigoProducto, String nombreProducto, long nitProveedor,
                    double precioCompra, double ivaCompra, double precioVenta) {

        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.nitProveedor = nitProveedor;
        this.precioCompra = precioCompra;
        this.ivaCompra = ivaCompra;
        this.precioVenta = precioVenta;
    }

    // --- Getters and Setters ---

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

