package co.edu.unbosque.usuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a user in the system.
 * It maps to the "usuarios" table in the database and
 * contains all necessary fields for user management,
 * including credentials and role.
 * 
 * @author Wilmer Ramos
 */
@Entity
@Table(name="usuarios")
public class Usuario {

    /** Primary key for the user entity */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    /** Unique identification number (cedula) of the user */
    @Column(name = "cedula_usuario")
    private Long cedulaUsuario;

    /** Full name of the user */
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    /** Email address of the user */
    @Column(name = "email_usuario")
    private String emailUsuario;

    /** Username used for login */
    @Column(name = "usuario")
    private String usuario;

    /** Password used for login */
    @Column(name = "password")
    private String password;

    /** Role of the user (e.g., USER, ADMIN) */
    @Column(name = "rol")
    private String rol;

    /**
     * Default constructor required by JPA.
     */
    public Usuario() {
        // Default constructor
    }

    /**
     * Constructor to create a user with default role "USER".
     *
     * @param cedulaUsuario identification number of the user
     * @param nombreUsuario full name of the user
     * @param emailUsuario email address of the user
     * @param usuario username for login
     * @param password password for login
     */
    public Usuario(Long cedulaUsuario, String nombreUsuario, String emailUsuario, String usuario, String password) {
        super();
        this.cedulaUsuario = cedulaUsuario;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.usuario = usuario;
        this.password = password;
        this.rol = "USER"; // default role
    }

    /**
     * Constructor to create a user with a specific role.
     *
     * @param cedulaUsuario identification number of the user
     * @param nombreUsuario full name of the user
     * @param emailUsuario email address of the user
     * @param usuario username for login
     * @param password password for login
     * @param rol role of the user
     */
    public Usuario(Long cedulaUsuario, String nombreUsuario, String emailUsuario, String usuario, String password, String rol) {
        this(cedulaUsuario, nombreUsuario, emailUsuario, usuario, password);
        this.rol = rol;
    }

	public Long getCedulaUsuario() {
		return cedulaUsuario;
	}

	public void setCedulaUsuario(Long cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
}
