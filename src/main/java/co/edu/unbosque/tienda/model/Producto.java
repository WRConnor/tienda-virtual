package co.edu.unbosque.tienda.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="productos")
public class Producto {
	
	    @Id
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

	    @ManyToOne
	    @JoinColumn(name = "nitproveedor")
	    private Proveedor proveedor;

	    @OneToMany(mappedBy = "producto")
	    private List<DetalleVenta> detalles;
	    
	    

}
