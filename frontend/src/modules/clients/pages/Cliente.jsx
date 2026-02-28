import { useState } from "react";
import { esAdmin } from "../../auth/context/auth";
import "../styles/Cliente.css";

function Cliente() {
  const [clientes, setClientes] = useState([]);

  const [form, setForm] = useState({
    cedula: "",
    nombre: "",
    direccion: "",
    telefono: "",
    correo: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const crearCliente = () => {
    if (!form.cedula || !form.nombre || !form.direccion || !form.telefono || !form.correo) {
      alert("Todos los campos son obligatorios");
      return;
    }

    const existe = clientes.find(c => c.cedula === form.cedula);
    if (existe) {
      alert("El cliente ya existe");
      return;
    }

    setClientes([...clientes, form]);
    limpiar();
  };

  const consultarCliente = () => {
    const cliente = clientes.find(c => c.cedula === form.cedula);
    if (cliente) {
      setForm(cliente);
    } else {
      alert("Cliente no encontrado");
    }
  };

  const actualizarCliente = () => {
    setClientes(
      clientes.map(c =>
        c.cedula === form.cedula ? form : c
      )
    );
    limpiar();
  };

  const borrarCliente = () => {
    setClientes(
      clientes.filter(c => c.cedula !== form.cedula)
    );
    limpiar();
  };

  const limpiar = () => {
    setForm({
      cedula: "",
      nombre: "",
      direccion: "",
      telefono: "",
      correo: ""
    });
  };

  return (
    <div className="cliente-wrapper">

      <div className="cliente-card">
        <h2>Gestión de Clientes</h2>

        <div className="cliente-form">

          <div className="form-column">
            <label>Cédula</label>
            <input name="cedula" value={form.cedula} onChange={handleChange} />

            <label>Nombre Completo</label>
            <input name="nombre" value={form.nombre} onChange={handleChange} />

            <label>Dirección</label>
            <input name="direccion" value={form.direccion} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Teléfono</label>
            <input name="telefono" value={form.telefono} onChange={handleChange} />

            <label>Correo Electrónico</label>
            <input name="correo" value={form.correo} onChange={handleChange} />
          </div>

        </div>

        <div className="cliente-buttons">
          <button type="button" onClick={consultarCliente}>Consultar</button>
          {esAdmin() && (<button type="button" onClick={crearCliente}>Crear</button>)}
          <button type="button" onClick={actualizarCliente}>Actualizar</button>
          <button type="button" onClick={borrarCliente}>Borrar</button>
        </div>
      </div>

      <div className="cliente-table-container">
        <table className="cliente-table">
          <thead>
            <tr>
              <th>Cédula</th>
              <th>Nombre</th>
              <th>Dirección</th>
              <th>Teléfono</th>
              <th>Correo</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map((c) => (
              <tr key={c.cedula}>
                <td>{c.cedula}</td>
                <td>{c.nombre}</td>
                <td>{c.direccion}</td>
                <td>{c.telefono}</td>
                <td>{c.correo}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default Cliente;