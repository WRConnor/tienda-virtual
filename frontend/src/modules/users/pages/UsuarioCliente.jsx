import { useAuth } from "../../auth/context/authContext";
import "../styles/Usuario.css";

function UsuarioCliente() {
  const { user } = useAuth();

  const usuarios = [
    { cedula: "1001", nombre: "Administrador", correo: "admin@tienda.com", usuario: "admin", password: "admin123456" },
    { cedula: "2001", nombre: "Cliente Uno", correo: "cliente@correo.com", usuario: "cliente1", password: "cliente123" }
  ];

  const mostrarUsuario = usuarios.find(u => u.usuario === user.username);

  return (
    <div className="usuario-wrapper">
      <div className="usuario-card">
        <h2>Mi Información</h2>
        {mostrarUsuario ? (
          <div className="usuario-form">
            <div className="form-column">
              <label>Cédula</label>
              <input value={mostrarUsuario.cedula} readOnly />
              <label>Nombre</label>
              <input value={mostrarUsuario.nombre} readOnly />
              <label>Correo</label>
              <input value={mostrarUsuario.correo} readOnly />
            </div>

            <div className="form-column">
              <label>Usuario</label>
              <input value={mostrarUsuario.usuario} readOnly />
              <label>Contraseña</label>
              <input value={mostrarUsuario.password} readOnly />
            </div>
          </div>
        ) : (
          <p>Usuario no encontrado</p>
        )}
      </div>
    </div>
  );
}

export default UsuarioCliente;