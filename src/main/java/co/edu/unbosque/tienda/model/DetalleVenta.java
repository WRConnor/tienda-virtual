package co.edu.unbosque.tienda.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name="detalle_ventas")
public class DetalleVenta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "codigo_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "codigo_venta")
    private Venta venta;
    
    public DetalleVenta() {
		// TODO Auto-generated constructor stub
	}
    
    

}
