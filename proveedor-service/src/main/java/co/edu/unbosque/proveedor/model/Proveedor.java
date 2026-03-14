/**
 * Entity class representing a Proveedor (supplier/provider).
 * Maps to the "proveedor" table in the database.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.proveedor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    
    /**
     * Primary key for the Proveedor entity.
     * Auto-generated using IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;
    
    /**
     * Tax identification number (NIT) for the provider.
     * Must be unique across all providers.
     */
    @Column(name = "nit_proveedor")
    private Long nitProveedor;

    /**
     * Name of the provider.
     */
    @Column(name = "nombre_proveedor")
    private String nombreProveedor;

    /**
     * Address of the provider.
     */
    @Column(name = "direccion_proveedor")
    private String direccionProveedor;

    /**
     * City where the provider is located.
     */
    @Column(name = "ciudad_proveedor")
    private String ciudadProveedor;

    /**
     * Contact phone number for the provider.
     */
    @Column(name = "telefono_proveedor")
    private String telefonoProveedor;
    
    /**
     * Default constructor required by JPA.
     */
    public Proveedor() {
        // Default constructor
    }

    /**
     * Parameterized constructor to create a Proveedor instance with all fields except ID.
     *
     * @param nitProveedor Tax identification number
     * @param nombreProveedor Name of the provider
     * @param direccionProveedor Address of the provider
     * @param ciudadProveedor City of the provider
     * @param telefonoProveedor Contact phone number
     */
    public Proveedor(Long nitProveedor, String nombreProveedor, String direccionProveedor, String ciudadProveedor,
                     String telefonoProveedor) {
        super();
        this.nitProveedor = nitProveedor;
        this.nombreProveedor = nombreProveedor;
        this.direccionProveedor = direccionProveedor;
        this.ciudadProveedor = ciudadProveedor;
        this.telefonoProveedor = telefonoProveedor;
    }

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getDireccionProveedor() {
		return direccionProveedor;
	}

	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}

	public String getCiudadProveedor() {
		return ciudadProveedor;
	}

	public void setCiudadProveedor(String ciudadProveedor) {
		this.ciudadProveedor = ciudadProveedor;
	}

	public String getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(String telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	
}
