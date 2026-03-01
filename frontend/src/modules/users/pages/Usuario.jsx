import { useState } from "react";
import "../styles/Usuario.css";

function Usuario() {
  const [usuarios, setUsuarios] = useState([
    { cedula: "1001", nombre: "Administrador", correo: "admin@tienda.com", usuario: "admin", password: "admin123456" },
    { cedula: "2001", nombre: "Cliente Uno", correo: "cliente@correo.com", usuario: "cliente1", password: "cliente123" }
  ]);

  const [form, setForm] = useState({ cedula: "", nombre: "", correo: "", usuario: "", password: "" });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const limpiar = () => setForm({ cedula: "", nombre: "", correo: "", usuario: "", password: "" });

  const crearUsuario = () => {
    if (!form.cedula || !form.nombre || !form.correo || !form.usuario || !form.password) { alert("Faltan datos"); return; }
    if (form.usuario === "admin") { alert("No se puede crear otro admin"); return; }
    setUsuarios([...usuarios, form]);
    limpiar();
  };

  const actualizarUsuario = () => {
    setUsuarios(usuarios.map(u => u.cedula === form.cedula ? form : u));
    limpiar();
  };

  const borrarUsuario = () => {
    setUsuarios(usuarios.filter(u => u.cedula !== form.cedula));
    limpiar();
  };

  const consultarUsuario = () => {
    const u = usuarios.find(u => u.cedula === form.cedula);
    if (u) setForm(u); else alert("Usuario no encontrado");
  };

  return (
    <div className="usuario-wrapper">
      <div className="usuario-card">
        <h2>Gestión de Usuarios (Admin)</h2>

        <div className="usuario-form">
          <div className="form-column">
            <label>Cédula</label>
            <input name="cedula" value={form.cedula} onChange={handleChange} />
            <label>Nombre</label>
            <input name="nombre" value={form.nombre} onChange={handleChange} />
            <label>Correo</label>
            <input name="correo" value={form.correo} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Usuario</label>
            <input name="usuario" value={form.usuario} onChange={handleChange} />
            <label>Contraseña</label>
            <input type="password" name="password" value={form.password} onChange={handleChange} />
          </div>
        </div>

        <div className="usuario-buttons">
          <button onClick={consultarUsuario}>Consultar</button>
          <button onClick={crearUsuario}>Crear</button>
          <button onClick={actualizarUsuario}>Actualizar</button>
          <button onClick={borrarUsuario}>Borrar</button>
        </div>
      </div>

      <div className="usuario-table-container">
        <table className="usuario-table">
          <thead>
            <tr>
              <th>Cédula</th><th>Nombre</th><th>Correo</th><th>Usuario</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map(u => (
              <tr key={u.cedula}>
                <td>{u.cedula}</td><td>{u.nombre}</td><td>{u.correo}</td><td>{u.usuario}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Usuario;