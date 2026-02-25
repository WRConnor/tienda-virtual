import { useState } from "react";
import "../css/Proveedor.css";

function Proveedor() {
  const [proveedores, setProveedores] = useState([]);

  const [form, setForm] = useState({
    nit: "",
    nombre: "",
    direccion: "",
    telefono: "",
    ciudad: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const crearProveedor = () => {
    if (!form.nit || !form.nombre || !form.direccion || !form.telefono || !form.ciudad) {
      alert("Todos los campos son obligatorios");
      return;
    }

    const existe = proveedores.find(p => p.nit === form.nit);
    if (existe) {
      alert("El proveedor ya existe");
      return;
    }

    setProveedores([...proveedores, form]);
    limpiar();
  };

  const consultarProveedor = () => {
    const proveedor = proveedores.find(p => p.nit === form.nit);
    if (proveedor) {
      setForm(proveedor);
    } else {
      alert("Proveedor no encontrado");
    }
  };

  const actualizarProveedor = () => {
    setProveedores(
      proveedores.map(p =>
        p.nit === form.nit ? form : p
      )
    );
    limpiar();
  };

  const borrarProveedor = () => {
    setProveedores(
      proveedores.filter(p => p.nit !== form.nit)
    );
    limpiar();
  };

  const limpiar = () => {
    setForm({
      nit: "",
      nombre: "",
      direccion: "",
      telefono: "",
      ciudad: ""
    });
  };

  return (
    <div className="proveedor-wrapper">

      <div className="proveedor-card">
        <h2>Gestión de Proveedores</h2>

        <div className="proveedor-form">

          <div className="form-column">
            <label>NIT</label>
            <input name="nit" value={form.nit} onChange={handleChange} />

            <label>Nombre Proveedor</label>
            <input name="nombre" value={form.nombre} onChange={handleChange} />

            <label>Dirección</label>
            <input name="direccion" value={form.direccion} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Teléfono</label>
            <input name="telefono" value={form.telefono} onChange={handleChange} />

            <label>Ciudad</label>
            <input name="ciudad" value={form.ciudad} onChange={handleChange} />
          </div>

        </div>

        <div className="proveedor-buttons">
          <button type="button" onClick={consultarProveedor}>Consultar</button>
          <button type="button" onClick={crearProveedor}>Crear</button>
          <button type="button" onClick={actualizarProveedor}>Actualizar</button>
          <button type="button" onClick={borrarProveedor}>Borrar</button>
        </div>
      </div>

      <div className="proveedor-table-container">
        <table className="proveedor-table">
          <thead>
            <tr>
              <th>NIT</th>
              <th>Nombre</th>
              <th>Dirección</th>
              <th>Teléfono</th>
              <th>Ciudad</th>
            </tr>
          </thead>
          <tbody>
            {proveedores.map((p) => (
              <tr key={p.nit}>
                <td>{p.nit}</td>
                <td>{p.nombre}</td>
                <td>{p.direccion}</td>
                <td>{p.telefono}</td>
                <td>{p.ciudad}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default Proveedor;