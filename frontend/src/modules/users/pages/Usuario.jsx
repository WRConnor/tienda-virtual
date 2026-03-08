import { useState, useEffect } from "react";
import { api } from "../../../api/api";
import "../styles/Usuario.css";

const safeString = (value) => (value == null ? "" : value);

function Usuario() {

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

  // 🔐 obtener rol y token del usuario logueado
  const rol = localStorage.getItem("rol");
  const token = localStorage.getItem("token");
  const esAdmin = rol === "ADMIN";

  useEffect(() => {
    cargarUsuarios();
  }, []);

  // 💻 Cargar usuarios desde API
  const cargarUsuarios = async () => {
    try {
      const data = await api.getUsuarios(token); // pasar token al api
      setUsuarios(data && data.length ? data : []);
    } catch (err) {
      console.error(err);
      alert("Error al cargar usuarios");
      setUsuarios([]);
    }
  };

  // 🔄 Manejo de formulario
  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const limpiar = () => {
    setForm({
      idUsuario: null,
      cedula: "",
      nombre: "",
      correo: "",
      usuario: "",
      password: "",
      rol: "USER"
    });
  };

  // ➕ Crear usuario
  const crearUsuario = async () => {
    if (!esAdmin) {
      alert("No tienes permisos para crear usuarios");
      return;
    }
    if (!form.cedula || !form.nombre || !form.correo || !form.usuario || !form.password) {
      alert("Faltan datos");
      return;
    }

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

  // ✏️ Actualizar usuario
  const actualizarUsuario = async () => {
    if (!esAdmin) {
      alert("No tienes permisos para actualizar usuarios");
      return;
    }
    if (!form.idUsuario) {
      alert("Primero selecciona un usuario de la tabla");
      return;
    }

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

  // ❌ Borrar usuario
  const borrarUsuario = async () => {
    if (!esAdmin) {
      alert("No tienes permisos para borrar usuarios");
      return;
    }
    if (!form.cedula) {
      alert("Ingresa la cédula del usuario");
      return;
    }

    const usuarioEncontrado = usuarios.find(
      (u) => String(u.cedulaUsuario) === String(form.cedula)
    );

    if (!usuarioEncontrado) {
      alert("Usuario no encontrado");
      return;
    }

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

  // 🔹 Seleccionar usuario de la tabla
  const seleccionarUsuario = (u) => {
    setForm({
      idUsuario: u.idUsuario,
      cedula: safeString(u.cedulaUsuario),
      nombre: safeString(u.nombreUsuario),
      correo: safeString(u.emailUsuario),
      usuario: safeString(u.usuario),
      password: safeString(u.password),
      rol: u.rol || "USER"
    });
  };

  // 🔍 Consultar usuario por cédula
  const consultarUsuario = () => {
    const u = usuarios.find(
      (u) => String(u.cedulaUsuario) === String(form.cedula)
    );
    if (u) {
      seleccionarUsuario(u);
    } else {
      alert("Usuario no encontrado");
    }
  };

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

            <label>Rol</label>
            <select name="rol" value={form.rol} onChange={handleChange}>
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>

        </div>

        <div className="usuario-buttons">
          <button onClick={consultarUsuario}>Consultar</button>

          {esAdmin && (
            <>
              <button onClick={crearUsuario}>Crear</button>
              <button onClick={actualizarUsuario}>Actualizar</button>
              <button onClick={borrarUsuario}>Borrar</button>
            </>
          )}

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
              <tr>
                <td colSpan="5">No hay usuarios registrados</td>
              </tr>
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