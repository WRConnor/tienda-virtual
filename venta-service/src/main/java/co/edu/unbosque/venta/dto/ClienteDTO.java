package co.edu.unbosque.venta.dto;


public class ClienteDTO {

    private Long idCliente;
    private Long cedulaCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String emailCliente;
    private String telefonoCliente;
    
    public ClienteDTO() {
		// TODO Auto-generated constructor stub
	}

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
