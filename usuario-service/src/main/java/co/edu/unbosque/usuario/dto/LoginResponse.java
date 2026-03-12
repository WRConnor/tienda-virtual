package co.edu.unbosque.usuario.dto;

public class LoginResponse {
	
	private String token;
	private String rol;
	private String usuario;
	private Long cedula;
	
	
	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public LoginResponse(String token, String rol, String usuario, Long cedula) {
		super();
		this.token = token;
		this.rol = rol;
		this.usuario = usuario;
		this.cedula = cedula;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getCedula() {
		return cedula;
	}

	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}
	
	
	
}
