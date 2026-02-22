package co.edu.unbosque.tienda.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Cliente {
	@Id
	private String id;
	private Long cedula;
	private String direccion;
	private String email;
	private String nombre;
	private Long telefono;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente( Long cedula, String direccion, String email, String nombre, Long telefono) {
		super();
		this.cedula = cedula;
		this.direccion = direccion;
		this.email = email;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCedula() {
		return cedula;
	}

	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
}
