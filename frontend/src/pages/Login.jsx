import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../services/authService";
import "../css/Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!username.trim() || !password.trim()) {
      setError("Todos los campos son obligatorios");
      return;
    }

    try {
      const data = await authService.login({ username, password });

      localStorage.setItem("token", data.token);

      navigate("/usuarios");
    } catch {
      setError("Credenciales incorrectas");
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