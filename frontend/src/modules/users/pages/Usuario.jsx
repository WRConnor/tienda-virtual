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
    password: ""
  });

  useEffect(() => {
    cargarUsuarios();
  }, []);

  const cargarUsuarios = async () => {
    try {
      const data = await api.getUsuarios();
      setUsuarios(data);
    } catch (err) {
      alert("Error al cargar usuarios: " + err.message);
    }
  };

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const limpiar = () =>
    setForm({
      idUsuario: null,
      cedula: "",
      nombre: "",
      correo: "",
      usuario: "",
      password: ""
    });

  // Crear usuario
  const crearUsuario = async () => {
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
        password: form.password
      });
      await cargarUsuarios();
      limpiar();
      alert("Usuario creado con éxito");
    } catch (err) {
      alert("Error al crear usuario: " + err.message);
    }
  };

  // Actualizar usuario (buscando primero por cédula)
  const actualizarUsuario = async () => {
  if (!form.idUsuario) {
    alert("Primero debes consultar o seleccionar un usuario");
    return;
  }

  console.log("ID que se envía al backend:", form.idUsuario);
  console.log("Formulario completo:", form);

  try {
    await api.actualizarUsuario(form.idUsuario, {
      cedulaUsuario: Number(form.cedula),
      nombreUsuario: form.nombre,
      emailUsuario: form.correo,
      usuario: form.usuario,
      password: form.password
    });

    await cargarUsuarios();
    limpiar();
    alert("Usuario actualizado con éxito");

  } catch (err) {
    alert("Error al actualizar usuario: " + err.message);
  }
};

  // Borrar usuario
  const borrarUsuario = async () => {
    const usuarioEncontrado = usuarios.find(
      (u) => String(u.cedulaUsuario) === String(form.cedula)
    );

    if (!usuarioEncontrado) {
      alert("Usuario no encontrado por cédula");
      return;
    }

    try {
      await api.borrarUsuario(usuarioEncontrado.idUsuario);
      await cargarUsuarios();
      limpiar();
      alert("Usuario eliminado con éxito");
    } catch (err) {
      alert("Error al borrar usuario: " + err.message);
    }
  };

  // Seleccionar usuario de la tabla
  const seleccionarUsuario = (u) => {
    setForm({
      idUsuario: u.idUsuario,
      cedula: safeString(u.cedulaUsuario),
      nombre: safeString(u.nombreUsuario),
      correo: safeString(u.emailUsuario),
      usuario: safeString(u.usuario),
      password: safeString(u.password)
    });
  };

  // Consultar usuario por cédula
  const consultarUsuario = () => {
    const u = usuarios.find(
      (u) => String(u.cedulaUsuario) === String(form.cedula)
    );
    if (u) {
      seleccionarUsuario(u);
    } else {
      alert("Usuario no encontrado por cédula");
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
          </div>
        </div>

        <div className="usuario-buttons">
          <button onClick={consultarUsuario}>Consultar</button>
          <button onClick={crearUsuario}>Crear</button>
          <button onClick={actualizarUsuario}>Actualizar</button>
          <button onClick={borrarUsuario}>Borrar</button>
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
            {usuarios.map((u) => (
              <tr key={u.idUsuario} onClick={() => seleccionarUsuario(u)}>
                <td>{u.idUsuario}</td>
                <td>{u.cedulaUsuario}</td>
                <td>{u.nombreUsuario}</td>
                <td>{u.emailUsuario}</td>
                <td>{u.usuario}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Usuario;