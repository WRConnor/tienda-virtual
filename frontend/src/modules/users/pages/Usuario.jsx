import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../api/api";
import "../styles/Usuario.css";

const safeString = (value) => (value == null ? "" : value);

function Usuario() {
  const navigate = useNavigate();
  const [usuarios, setUsuarios] = useState([]);
  const [form, setForm] = useState({
    idUsuario: null,
    cedula: "",
    nombre: "",
    correo: "",
    usuario: "",
    password: "",
    rol: "USER"
  });
  const [accesoPermitido, setAccesoPermitido] = useState(false);

  const rol = localStorage.getItem("rol");
  const token = localStorage.getItem("token");

  const tieneRol = (rolesPermitidos) => rolesPermitidos.includes(rol);

  // Controlar acceso y cargar usuarios solo si es ADMIN o GERENTE
  useEffect(() => {
    if (!tieneRol(["ADMIN", "GERENTE"])) {
      setAccesoPermitido(false); // bloquear acceso
      return;
    }
    setAccesoPermitido(true);
    cargarUsuarios();
  }, []);

  const cargarUsuarios = async () => {
    try {
      const data = await api.getUsuarios(token);
      setUsuarios(Array.isArray(data) ? data : []);
    } catch (err) {
      alert(err.response?.status === 403 ? "No tienes permiso para ver estos datos" : "Error al cargar usuarios");
      setUsuarios([]);
    }
  };

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const limpiar = () => setForm({ idUsuario: null, cedula: "", nombre: "", correo: "", usuario: "", password: "", rol: "USER" });

  const crearUsuario = async () => {
    if (!tieneRol(["ADMIN"])) return alert("No tienes permisos para crear usuarios");
    if (!form.cedula || !form.nombre || !form.correo || !form.usuario || !form.password) return alert("Faltan datos");

    try {
      await api.crearUsuario({
        cedulaUsuario: Number(form.cedula),
        nombreUsuario: form.nombre,
        emailUsuario: form.correo,
        usuario: form.usuario,
        password: form.password,
        rol: form.rol
      }, token);
      await cargarUsuarios();
      limpiar();
      alert("Usuario creado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al crear usuario");
    }
  };

  const actualizarUsuario = async () => {
    if (!tieneRol(["ADMIN", "GERENTE"])) return alert("No tienes permisos para actualizar usuarios");
    if (!form.idUsuario) return alert("Primero selecciona un usuario de la tabla");

    try {
      await api.actualizarUsuario(form.idUsuario, {
        cedulaUsuario: Number(form.cedula),
        nombreUsuario: form.nombre,
        emailUsuario: form.correo,
        usuario: form.usuario,
        password: form.password,
        rol: form.rol
      }, token);
      await cargarUsuarios();
      limpiar();
      alert("Usuario actualizado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al actualizar usuario");
    }
  };

  const borrarUsuario = async () => {
    if (!tieneRol(["ADMIN"])) return alert("No tienes permisos para borrar usuarios");
    if (!form.cedula) return alert("Ingresa la cédula del usuario");

    const usuarioEncontrado = usuarios.find(u => String(u.cedulaUsuario) === String(form.cedula));
    if (!usuarioEncontrado) return alert("Usuario no encontrado");

    try {
      await api.borrarUsuario(usuarioEncontrado.idUsuario, token);
      await cargarUsuarios();
      limpiar();
      alert("Usuario eliminado con éxito");
    } catch (err) {
      console.error(err);
      alert("Error al borrar usuario");
    }
  };

  const seleccionarUsuario = (u) => setForm({
    idUsuario: u.idUsuario,
    cedula: safeString(u.cedulaUsuario),
    nombre: safeString(u.nombreUsuario),
    correo: safeString(u.emailUsuario),
    usuario: safeString(u.usuario),
    password: safeString(u.password),
    rol: u.rol || "USER"
  });

  const consultarUsuario = () => {
    const u = usuarios.find(u => String(u.cedulaUsuario) === String(form.cedula));
    if (u) seleccionarUsuario(u);
    else alert("Usuario no encontrado");
  };

  // 🔹 Renderizado condicional según acceso
  if (!accesoPermitido) {
    return <div className="usuario-wrapper"><h2>No tienes permiso para ver esta sección</h2></div>;
  }

  return (
    <div className="usuario-wrapper">
      <div className="usuario-card">
        <h2>Gestión de Usuarios</h2>

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

            {tieneRol(["ADMIN"]) && (
              <>
                <label>Rol</label>
                <select name="rol" value={form.rol} onChange={handleChange}>
                  <option value="ADMIN">ADMIN</option>
                  <option value="CLIENTE">CLIENTE</option>
                  <option value="CAJERO">CAJERO</option>
                  <option value="GERENTE">GERENTE</option>
                  <option value="INVENTARIO">INVENTARIO</option>
                </select>
              </>
            )}
          </div>
        </div>

        <div className="usuario-buttons">
          <button onClick={consultarUsuario}>Consultar</button>
          {tieneRol(["ADMIN"]) && <>
            <button onClick={crearUsuario}>Crear</button>
            <button onClick={borrarUsuario}>Borrar</button>
          </>}
          {tieneRol(["ADMIN", "GERENTE"]) && <button onClick={actualizarUsuario}>Actualizar</button>}
          <button onClick={limpiar}>Limpiar</button>
        </div>
      </div>

      <div className="usuario-table-container">
        <table className="usuario-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Cédula</th>
              <th>Nombre</th>
              <th>Correo</th>
              <th>Usuario</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.length === 0 ? (
              <tr><td colSpan="5">No hay usuarios registrados</td></tr>
            ) : (
              usuarios.map((u) => (
                <tr key={u.idUsuario} onClick={() => seleccionarUsuario(u)}>
                  <td>{u.idUsuario}</td>
                  <td>{u.cedulaUsuario}</td>
                  <td>{u.nombreUsuario}</td>
                  <td>{u.emailUsuario}</td>
                  <td>{u.usuario}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Usuario;