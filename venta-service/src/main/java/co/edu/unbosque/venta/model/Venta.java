/**
 * This package contains model classes (entities) used for
 * the sales management system. These classes map to database
 * tables using JPA annotations.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.venta.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity representing a Sale (Venta).
 * Maps to the "ventas" table in the database.
 * 
 * Each Venta can have multiple DetalleVenta associated with it.
 * The class includes basic fields like client, user, total values,
 * and a list of sale details.
 * 
 * Author: Wilmer Ramos
 */
@Entity
@Table(name="ventas")
public class Venta {
	
    /** Primary key of the sale */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_venta")
    private Long idVenta;
    
    /** Unique sale code */
    @Column(name = "codigo_venta")
    private Long codigoVenta;

    /** Client identification number */
    @Column(name = "cedula_cliente")
    private Long cedulaCliente;

    /** User identification number who created the sale */
    @Column(name = "cedula_usuario")
    private Long cedulaUsuario;

    /** VAT value for the sale */
    @Column(name = "iva_venta")
    private Double ivaVenta;

    /** Total value including VAT */
    @Column(name = "total_venta")
    private Double totalVenta;

    /** Sale value before VAT */
    @Column(name = "valor_venta")
    private Double valorVenta;

    /** List of sale details associated with this sale */
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;
    
    /** Default constructor */
    public Venta() {
		// Default constructor
	}

    /**
     * Parameterized constructor to create a Venta instance with all fields.
     *
     * @param codigoVenta unique sale code
     * @param cedulaCliente client ID
     * @param cedulaUsuario user ID
     * @param ivaVenta VAT value
     * @param totalVenta total value including VAT
     * @param valorVenta sale value before VAT
     * @param detalles list of sale details
     */
	public Venta(Long codigoVenta, Long cedulaCliente, Long cedulaUsuario, Double ivaVenta, Double totalVenta,
			Double valorVenta, List<DetalleVenta> detalles) {
		super();
		this.codigoVenta = codigoVenta;
		this.cedulaCliente = cedulaCliente;
		this.cedulaUsuario = cedulaUsuario;
		this.ivaVenta = ivaVenta;
		this.totalVenta = totalVenta;
		this.valorVenta = valorVenta;
		this.detalles = detalles;
	}

    // Getters and setters
	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public Long getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(Long codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public Long getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(Long cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public Long getCedulaUsuario() {
		return cedulaUsuario;
	}

	public void setCedulaUsuario(Long cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}

	public Double getIvaVenta() {
		return ivaVenta;
	}

	public void setIvaVenta(Double ivaVenta) {
		this.ivaVenta = ivaVenta;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public Double getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(Double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}
    
    


}
