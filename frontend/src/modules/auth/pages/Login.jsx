import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../api/api";
import { useAuth } from "../../auth/context/authContext";
import "../styles/Login.css";

function Login() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const loginUsuario = async (usuario, password) => {
  try {
    const res = await api.login(usuario, password);
    localStorage.setItem("token", res.token);
    localStorage.setItem("rol", res.rol);
    localStorage.setItem("usuario", res.usuario);
    localStorage.setItem("cedula", res.cedula);

    // Redirigir a la "interfaz principal" según rol
    switch (res.rol) {
      case "ADMIN":
        navigate("/admin"); // aquí puedes renderizar <AdminPanel />
        break;
      case "CLIENTE":
        navigate("/cliente"); // <ClientePanel /> limitado
        break;
      case "CAJERO":
        navigate("/ventas");
        break;
      case "GERENTE":
        navigate("/reportes");
        break;
      case "INVENTARIO":
        navigate("/inventario");
        break;
      default:
        alert("Rol no reconocido");
        navigate("/login");
    }
  } catch (err) {
    console.error(err);
    alert("Usuario o contraseña incorrectos");
  }
};

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!username.trim() || !password.trim()) {
      setError("Faltan datos del usuario");
      return;
    }

    try {

      // petición al gateway
      const response = await api.login({
        username: username,
        password: password
      });

      console.log(response);

      /*
      backend retorna:

      {
        token: "...",
        rol: "ADMIN",
        usuario: "juanito"
      }
      */

      const { token, rol, usuario, cedula } = response;

      // guardar en contexto
      login({ token, rol, usuario, cedula });

      // redirección según rol
      if (rol === "ADMIN") {
        navigate("/usuarios");
      } else {
        navigate("/clientes");
      }

    } catch (err) {

      console.error(err);

      if (err.response) {
        setError("Usuario o contraseña incorrectos");
      } else {
        setError("Error conectando con el servidor");
      }

    }
  };

  return (
    <div className="login-container">

      <form className="login-card" onSubmit={handleSubmit}>

        <h2 className="login-title">Tienda Genérica</h2>
        <p className="login-subtitle">Sistema Administrativo</p>

        {error && <p className="login-error">{error}</p>}

        <input
          className="login-input"
          type="text"
          placeholder="Usuario"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          className="login-input"
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="login-button" type="submit">
          Ingresar
        </button>

      </form>

    </div>
  );
}

export default Login;