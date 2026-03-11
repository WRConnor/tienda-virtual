package co.edu.unbosque.usuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="usuarios")
public class Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(name = "cedula_usuario")
    private Long cedulaUsuario;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "password")
    private String password;

    @Column(name = "rol")
    private String rol;
    
    public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long cedulaUsuario, String nombreUsuario, String emailUsuario, String usuario, String password) {
		super();
		this.cedulaUsuario = cedulaUsuario;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.usuario = usuario;
		this.password = password;
		this.rol = "USER";
	}

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
