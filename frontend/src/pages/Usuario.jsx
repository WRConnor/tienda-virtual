import { useState } from "react";
import "../css/Usuario.css";

function Usuario() {
  const [usuarios, setUsuarios] = useState([
    {
      cedula: "1001",
      nombre: "Administrador",
      correo: "admin@tienda.com",
      usuario: "admin",
      password: "1234"
    }
  ]);

  const [form, setForm] = useState({
    cedula: "",
    nombre: "",
    correo: "",
    usuario: "",
    password: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const crearUsuario = () => {
    console.log("Crear presionado");
    if (form.usuario === "admin") {
      alert("No se puede crear otro usuario admin");
      return;
    }

    setUsuarios([...usuarios, form]);
    limpiar();
  };

  const actualizarUsuario = () => {
    if (form.usuario === "admin") {
      alert("El usuario admin no puede modificarse");
      return;
    }

    setUsuarios(
      usuarios.map((u) =>
        u.cedula === form.cedula ? form : u
      )
    );
    limpiar();
  };

  const borrarUsuario = () => {
    const usuario = usuarios.find((u) => u.cedula === form.cedula);

    if (usuario?.usuario === "admin") {
      alert("El usuario admin no puede eliminarse");
      return;
    }

    setUsuarios(usuarios.filter((u) => u.cedula !== form.cedula));
    limpiar();
  };

  const consultarUsuario = () => {
    const usuario = usuarios.find((u) => u.cedula === form.cedula);
    if (usuario) {
      setForm(usuario);
    } else {
      alert("Usuario no encontrado");
    }
  };

  const limpiar = () => {
    setForm({
      cedula: "",
      nombre: "",
      correo: "",
      usuario: "",
      password: ""
    });
  };

  return (
    <div className="usuario-wrapper">

    <div className="usuario-card">
        <h2>Gestión de Usuarios</h2>

        <div className="usuario-form">
        <div className="form-column">
            <label>Cédula</label>
            <input name="cedula" value={form.cedula} onChange={handleChange} />

            <label>Nombre Completo</label>
            <input name="nombre" value={form.nombre} onChange={handleChange} />

            <label>Correo Electrónico</label>
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
        <button type="button" onClick={consultarUsuario}>Consultar</button>
        <button type="button" onClick={crearUsuario}>Crear</button>
        <button type="button" onClick={actualizarUsuario}>Actualizar</button>
        <button type="button" onClick={borrarUsuario}>Borrar</button>
        </div>
    </div>

    <div className="usuario-table-container">
        <table className="usuario-table">
        <thead>
            <tr>
            <th>Cédula</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Usuario</th>
            </tr>
        </thead>
        <tbody>
            {usuarios.map((u) => (
            <tr key={u.cedula}>
                <td>{u.cedula}</td>
                <td>{u.nombre}</td>
                <td>{u.correo}</td>
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