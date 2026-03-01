import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../auth/context/authContext";
import "../styles/Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");

    if (!username.trim() || !password.trim()) {
      setError("Todos los campos son obligatorios");
      return;
    }

    // 🔹 Login simulado
    let usuarioData = null;

    if (username === "admin" && password === "admin123456") {
      usuarioData = { username: "admin", rol: "ADMIN" };
    } else if (username === "cliente1" && password === "cliente123") {
      usuarioData = { username: "cliente1", rol: "CLIENTE" };
    } else {
      setError("Credenciales incorrectas");
      return;
    }

    // 🔹 Guardar usuario en contexto y localStorage
    login(usuarioData);

    // 🔹 Redirección según rol
    if (usuarioData.rol === "ADMIN") navigate("/usuarios");
    else if (usuarioData.rol === "CLIENTE") navigate("/usuarios");
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