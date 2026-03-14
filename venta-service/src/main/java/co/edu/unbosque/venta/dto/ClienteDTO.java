package co.edu.unbosque.venta.dto;

/**
 * Data Transfer Object representing a client (Cliente).
 * Used for sending client data between microservices and layers.
 * 
 * @author Wilmer Ramos
 */
public class ClienteDTO {

    /** Unique identifier of the client */
    private Long idCliente;

    /** Client identification number (cedula) */
    private Long cedulaCliente;

    /** Full name of the client */
    private String nombreCliente;

    /** Client's address */
    private String direccionCliente;

    /** Client's email */
    private String emailCliente;

    /** Client's phone number */
    private String telefonoCliente;
    
    /** Default constructor */
    public ClienteDTO() {
        // Default constructor
    }

    /**
     * Parameterized constructor to create a ClienteDTO instance.
     *
     * @param idCliente unique identifier of the client
     * @param cedulaCliente client identification number
     * @param nombreCliente full name of the client
     * @param direccionCliente address of the client
     * @param emailCliente email of the client
     * @param telefonoCliente phone number of the client
     */
    public ClienteDTO(Long idCliente, Long cedulaCliente, String nombreCliente, String direccionCliente,
                      String emailCliente, String telefonoCliente) {
        super();
        this.idCliente = idCliente;
        this.cedulaCliente = cedulaCliente;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.emailCliente = emailCliente;
        this.telefonoCliente = telefonoCliente;
    }

    // Getters and setters with JavaDoc can be added for completeness
	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(Long cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}
    
    
}
