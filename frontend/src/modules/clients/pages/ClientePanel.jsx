import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../api/api";
import "../styles/ClientePanel.css";

function ClientePanel() {
  const navigate = useNavigate();
  const [cliente, setCliente] = useState(null);
  const token = localStorage.getItem("token");
  const rol = localStorage.getItem("rol");

  useEffect(() => {
    if (rol !== "CLIENTE") {
      alert("No tienes permiso para acceder a esta sección");
      navigate("/login");
      return;
    }

    cargarDatosCliente();
  }, []);

  const cargarDatosCliente = async () => {
    try {
      // Suponiendo que el backend tiene endpoint GET /clientes/mi-perfil
      const data = await api.getMiCliente(token);
      setCliente(data);
    } catch (err) {
      console.error(err);
      alert("Error cargando tus datos");
    }
  };

  if (!cliente) {
    return <div className="cliente-wrapper"><p>Cargando tus datos...</p></div>;
  }

  return (
    <div className="cliente-wrapper">
      <div className="cliente-card">
        <h2>Bienvenido, {cliente.nombreCliente}</h2>

        <div className="cliente-info">
          <p><strong>Cédula:</strong> {cliente.cedulaCliente}</p>
          <p><strong>Nombre:</strong> {cliente.nombreCliente}</p>
          <p><strong>Dirección:</strong> {cliente.direccionCliente}</p>
          <p><strong>Teléfono:</strong> {cliente.telefonoCliente}</p>
          <p><strong>Email:</strong> {cliente.emailCliente}</p>
        </div>

        <p className="nota">Esta es tu interfaz de cliente. No tienes acceso a gestión de usuarios ni otros módulos administrativos.</p>
      </div>
    </div>
  );
}

export default ClientePanel;