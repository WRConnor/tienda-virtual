import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../api/api";
import "../styles/Cliente.css";

function Cliente() {
  const navigate = useNavigate();
  const [clientes, setClientes] = useState([]);

  const [form, setForm] = useState({
    idCliente: null,
    cedulaCliente: "",
    nombreCliente: "",
    direccionCliente: "",
    telefonoCliente: "",
    emailCliente: ""
  });

  const token = localStorage.getItem("token");
  const rol = localStorage.getItem("rol");

  // Solo ADMIN puede acceder
  useEffect(() => {
    if (rol !== "ADMIN") {
      alert("No tienes permiso para acceder a Clientes");
      navigate("/login"); // Redirige al login si no es admin
      return;
    }
    cargarClientes();
  }, []);

  const cargarClientes = async () => {
    try {
      const data = await api.getClientes(token); // asegurarse de pasar token
      setClientes(Array.isArray(data) ? data : []);
    } catch (error) {
      if (error.response?.status === 403) {
        alert("No tienes permiso para ver clientes");
      } else {
        alert("Error cargando clientes: " + error.message);
      }
      setClientes([]);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const limpiar = () => {
    setForm({
      idCliente: null,
      cedulaCliente: "",
      nombreCliente: "",
      direccionCliente: "",
      telefonoCliente: "",
      emailCliente: ""
    });
  };

  const crearCliente = async () => {
    if (rol !== "ADMIN") return;
    try {
      await api.crearCliente(form, token);
      await cargarClientes();
      limpiar();
      alert("Cliente creado con éxito");
    } catch (error) {
      alert("Error al crear cliente: " + error.message);
    }
  };

  const consultarCliente = () => {
    const cliente = clientes.find(
      (c) => c.cedulaCliente.toString() === form.cedulaCliente.toString()
    );
    if (cliente) setForm(cliente);
    else alert("Cliente no encontrado");
  };

  const actualizarCliente = async () => {
    if (!form.idCliente) return alert("Primero consulte el cliente");
    try {
      await api.actualizarCliente(form.idCliente, form, token);
      await cargarClientes();
      limpiar();
      alert("Cliente actualizado con éxito");
    } catch (error) {
      alert("Error al actualizar cliente: " + error.message);
    }
  };

  const borrarCliente = async () => {
    if (!form.idCliente) return alert("Primero consulte el cliente");
    try {
      await api.borrarCliente(form.idCliente, token);
      await cargarClientes();
      limpiar();
      alert("Cliente eliminado con éxito");
    } catch (error) {
      alert("Error al eliminar cliente: " + error.message);
    }
  };

  return (
    <div className="cliente-wrapper">
      <div className="cliente-card">
        <h2>Gestión de Clientes</h2>

        <div className="cliente-form">
          <div className="form-column">
            <label>Cédula</label>
            <input name="cedulaCliente" value={form.cedulaCliente} onChange={handleChange} />
            <label>Nombre Completo</label>
            <input name="nombreCliente" value={form.nombreCliente} onChange={handleChange} />
            <label>Dirección</label>
            <input name="direccionCliente" value={form.direccionCliente} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Teléfono</label>
            <input name="telefonoCliente" value={form.telefonoCliente} onChange={handleChange} />
            <label>Correo Electrónico</label>
            <input name="emailCliente" value={form.emailCliente} onChange={handleChange} />
          </div>
        </div>

        <div className="cliente-buttons">
          <button type="button" onClick={consultarCliente}>Consultar</button>
          {rol === "ADMIN" && <button type="button" onClick={crearCliente}>Crear</button>}
          <button type="button" onClick={actualizarCliente}>Actualizar</button>
          <button type="button" onClick={borrarCliente}>Borrar</button>
        </div>
      </div>

      <div className="cliente-table-container">
        <table className="cliente-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Cédula</th>
              <th>Nombre</th>
              <th>Dirección</th>
              <th>Teléfono</th>
              <th>Correo</th>
            </tr>
          </thead>
          <tbody>
            {clientes.length === 0 ? (
              <tr>
                <td colSpan="6">No hay clientes</td>
              </tr>
            ) : (
              clientes.map((c) => (
                <tr key={c.idCliente} onClick={() => setForm(c)}>
                  <td>{c.idCliente}</td>
                  <td>{c.cedulaCliente}</td>
                  <td>{c.nombreCliente}</td>
                  <td>{c.direccionCliente}</td>
                  <td>{c.telefonoCliente}</td>
                  <td>{c.emailCliente}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Cliente;