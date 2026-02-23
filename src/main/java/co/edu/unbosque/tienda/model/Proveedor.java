package co.edu.unbosque.tienda.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="proveedor")
public class Proveedor {
	
    @Id
    @Column(name = "nitproveedor")
    private Long nitProveedor;

    @Column(name = "nombre_proveedor")
    private String nombreProveedor;

    @Column(name = "direccion_proveedor")
    private String direccionProveedor;

    @Column(name = "ciudad_proveedor")
    private String ciudadProveedor;

    @Column(name = "telefono_proveedor")
    private String telefonoProveedor;

    @OneToMany(mappedBy = "proveedor")
    private List<Producto> productos;
    
    public Proveedor() {
		// TODO Auto-generated constructor stub
	}

	public Proveedor(Long nitProveedor, String nombreProveedor, String direccionProveedor, String ciudadProveedor,
			String telefonoProveedor, List<Producto> productos) {
		super();
		this.nitProveedor = nitProveedor;
		this.nombreProveedor = nombreProveedor;
		this.direccionProveedor = direccionProveedor;
		this.ciudadProveedor = ciudadProveedor;
		this.telefonoProveedor = telefonoProveedor;
		this.productos = productos;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	
    
    

}
