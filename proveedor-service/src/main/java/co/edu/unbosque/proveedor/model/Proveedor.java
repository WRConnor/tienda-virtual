package co.edu.unbosque.proveedor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="proveedor")
public class Proveedor {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;
    
    @Column(name = "nit_proveedor")
    private Long nitProveedor;

    @Column(name = "nombre_proveedor")
    private String nombreProveedor;

    @Column(name = "direccion_proveedor")
    private String direccionProveedor;

    @Column(name = "ciudad_proveedor")
    private String ciudadProveedor;

    @Column(name = "telefono_proveedor")
    private String telefonoProveedor;
    
    public Proveedor() {
		// TODO Auto-generated constructor stub
	}

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
