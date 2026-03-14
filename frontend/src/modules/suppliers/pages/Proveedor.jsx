import { useState, useEffect } from "react";
import { api } from "../../../api/api";
import "../styles/Proveedor.css";

const safeString = (value) => (value == null ? "" : value);

function Proveedor() {
  const [proveedores, setProveedores] = useState([]);
  const [form, setForm] = useState({
    idProveedor: null,
    nit: "",
    nombre: "",
    direccion: "",
    ciudad: "",
    telefono: ""
  });

  useEffect(() => {
    cargarProveedores();
  }, []);

  // Cargar proveedores desde API
  const cargarProveedores = async () => {
    try {
      const data = await api.getProveedores();
      setProveedores(data && data.length ? data : []);
    } catch (err) {
      console.error(err);
      alert("Error al cargar proveedores: " + err.message);
      setProveedores([]);
    }
  };

  // Manejo de formulario
  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const limpiar = () =>
    setForm({
      idProveedor: null,
      nit: "",
      nombre: "",
      direccion: "",
      ciudad: "",
      telefono: ""
    });

  // Crear proveedor
  const crearProveedor = async () => {
    if (!form.nit || !form.nombre || !form.direccion || !form.ciudad || !form.telefono) {
      alert("Faltan datos");
      return;
    }

    try {
      await api.crearProveedor({
        nitProveedor: Number(form.nit),
        nombreProveedor: form.nombre,
        direccionProveedor: form.direccion,
        ciudadProveedor: form.ciudad,
        telefonoProveedor: form.telefono
      });

      await cargarProveedores();
      limpiar();
      alert("Proveedor creado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al crear proveedor: " + err.message);
    }
  };

  // Actualizar proveedor
  const actualizarProveedor = async () => {
    if (!form.idProveedor) {
      alert("Primero debes consultar o seleccionar un proveedor");
      return;
    }

    try {
      await api.actualizarProveedor(form.idProveedor, {
        nitProveedor: Number(form.nit),
        nombreProveedor: form.nombre,
        direccionProveedor: form.direccion,
        ciudadProveedor: form.ciudad,
        telefonoProveedor: form.telefono
      });

      await cargarProveedores();
      limpiar();
      alert("Proveedor actualizado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al actualizar proveedor: " + err.message);
    }
  };

  // Borrar proveedor
  const borrarProveedor = async () => {
    const proveedorEncontrado = proveedores.find(
      (p) => String(p.nitProveedor) === String(form.nit)
    );

    if (!proveedorEncontrado) {
      alert("Proveedor no encontrado por NIT");
      return;
    }

    try {
      await api.borrarProveedor(proveedorEncontrado.idProveedor);
      await cargarProveedores();
      limpiar();
      alert("Proveedor eliminado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al borrar proveedor: " + err.message);
    }
  };

  // Seleccionar proveedor de la tabla
  const seleccionarProveedor = (p) => {
  console.log("Proveedor seleccionado:", p);
  setForm({
    idProveedor: p.idProveedor,
    nit: safeString(p.nitProveedor),
    nombre: safeString(p.nombreProveedor),
    direccion: safeString(p.direccionProveedor),
    ciudad: safeString(p.ciudadProveedor),
    telefono: safeString(p.telefonoProveedor)
  });
};

  // Consultar proveedor por NIT
  const consultarProveedor = () => {
    const p = proveedores.find(
      (p) => String(p.nitProveedor) === String(form.nit)
    );

    if (p) {
      seleccionarProveedor(p);
    } else {
      alert("Proveedor no encontrado por NIT");
    }
  };

  return (
    <div className="usuario-wrapper">
      <div className="usuario-card">
        <h2>Gestión de Proveedores</h2>

        <div className="usuario-form">
          <div className="form-column">
            <label>NIT</label>
            <input name="nit" value={form.nit} onChange={handleChange} />

            <label>Nombre</label>
            <input name="nombre" value={form.nombre} onChange={handleChange} />

            <label>Dirección</label>
            <input name="direccion" value={form.direccion} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Ciudad</label>
            <input name="ciudad" value={form.ciudad} onChange={handleChange} />

            <label>Teléfono</label>
            <input name="telefono" value={form.telefono} onChange={handleChange} />
          </div>
        </div>

        <div className="usuario-buttons">
          <button onClick={consultarProveedor}>Consultar</button>
          <button onClick={crearProveedor}>Crear</button>
          <button onClick={actualizarProveedor}>Actualizar</button>
          <button onClick={borrarProveedor}>Borrar</button>
          <button onClick={limpiar}>Limpiar</button>
        </div>
      </div>

      <div className="usuario-table-container">
        <table className="usuario-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>NIT</th>
              <th>Nombre</th>
              <th>Dirección</th>
              <th>Ciudad</th>
              <th>Teléfono</th>
            </tr>
          </thead>

          <tbody>
            {proveedores.length === 0 ? (
              <tr>
                <td colSpan="6">No hay proveedores registrados</td>
              </tr>
            ) : (
              proveedores.map((p) => (
                <tr key={p.idProveedor} onClick={() => seleccionarProveedor(p)}>
                  <td>{p.idProveedor}</td>
                  <td>{p.nitProveedor}</td>
                  <td>{p.nombreProveedor}</td>
                  <td>{p.direccionProveedor}</td>
                  <td>{p.ciudadProveedor}</td>
                  <td>{p.telefonoProveedor}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Proveedor;