package co.edu.unbosque.usuario.dto;

/**
 * DTO class representing the response returned after a successful login.
 * It contains the JWT token, user role, username, and identification number (cedula).
 * 
 * @author Wilmer Ramos
 */
public class LoginResponse {

    /** JWT token generated for the authenticated user */
    private String token;

    /** Role of the authenticated user (e.g., USER, ADMIN) */
    private String rol;

    /** Username of the authenticated user */
    private String usuario;

    /** Identification number (cedula) of the authenticated user */
    private Long cedula;

    /**
     * Default constructor.
     */
    public LoginResponse() {
        // Default constructor
    }

    /**
     * Constructor to initialize all fields of the login response.
     *
     * @param token JWT token
     * @param rol role of the user
     * @param usuario username of the user
     * @param cedula identification number of the user
     */
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
