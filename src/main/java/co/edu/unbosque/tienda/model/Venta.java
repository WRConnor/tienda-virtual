package co.edu.unbosque.tienda.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="ventas")
public class Venta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Long codigoVenta;

    @ManyToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cedula_usuario")
    private Usuario usuario;

    @Column(name = "iva_venta")
    private Double ivaVenta;

    @Column(name = "total_venta")
    private Double totalVenta;

    @Column(name = "valor_venta")
    private Double valorVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;
    
    public Venta() {
		// TODO Auto-generated constructor stub
	}

}
