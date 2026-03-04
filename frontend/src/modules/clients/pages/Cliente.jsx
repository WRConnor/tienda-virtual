import { useState, useEffect } from "react";
import { esAdmin } from "../../auth/context/auth";
import { api } from "../../../api/api";
import "../styles/Cliente.css";

function Cliente() {

  const [clientes, setClientes] = useState([]);

  const [form, setForm] = useState({
    idCliente: null,
    cedulaCliente: "",
    nombreCliente: "",
    direccionCliente: "",
    telefonoCliente: "",
    emailCliente: ""
  });

  // =========================
  // CARGAR CLIENTES
  // =========================
  useEffect(() => {
    cargarClientes();
  }, []);

  const cargarClientes = async () => {
    try {
      const data = await api.getClientes();
      console.log(data);
      setClientes(data);
    } catch (error) {
      alert(error.message);
    }
  };

  // =========================
  // HANDLE INPUT
  // =========================
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

  // =========================
  // CREAR
  // =========================
  const crearCliente = async () => {
    try {
      await api.crearCliente(form);
      await cargarClientes();
      limpiar();
    } catch (error) {
      alert(error.message);
    }
  };

  // =========================
  // CONSULTAR (FILTRA LOCALMENTE POR CÉDULA)
  // =========================
  const consultarCliente = () => {
    const cliente = clientes.find(
      c => c.cedulaCliente === Number(form.cedulaCliente)
    );

    if (cliente) {
      setForm(cliente);
    } else {
      alert("Cliente no encontrado");
    }
  };

  // =========================
  // ACTUALIZAR (POR ID)
  // =========================
  const actualizarCliente = async () => {
    if (!form.idCliente) {
      alert("Primero consulte el cliente");
      return;
    }

    try {
      await api.actualizarCliente(form.idCliente, form);
      await cargarClientes();
      limpiar();
    } catch (error) {
      alert(error.message);
    }
  };

  // =========================
  // BORRAR (POR ID)
  // =========================
  const borrarCliente = async () => {
    if (!form.idCliente) {
      alert("Primero consulte el cliente");
      return;
    }

    try {
      await api.borrarCliente(form.idCliente);
      await cargarClientes();
      limpiar();
    } catch (error) {
      alert(error.message);
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
          {esAdmin() && (
            <button type="button" onClick={crearCliente}>Crear</button>
          )}
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
            {clientes.map((c) => (
              <tr key={c.idCliente} onClick={() => setForm(c)}>
                <td>{c.idCliente}</td>
                <td>{c.cedulaCliente}</td>
                <td>{c.nombreCliente}</td>
                <td>{c.direccionCliente}</td>
                <td>{c.telefonoCliente}</td>
                <td>{c.emailCliente}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default Cliente;