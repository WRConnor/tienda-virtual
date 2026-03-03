// services/api.js

const API = "http://localhost:8080/api";

export const api = {
  login: async ({ username, password }) => {
    if (!username || !password) {
      throw new Error("Faltan datos del usuario");
    }

    const response = await fetch(
      `${API}/usuarios/login?user=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
    );

    if (!response.ok) {
      throw new Error("Usuario o contraseña incorrectos");
    }

    const text = await response.text();

    if (text !== "Accedido") {
      throw new Error("Usuario o contraseña incorrectos");
    }

    // Como backend no devuelve rol, lo definimos aquí
    if (username === "admin" && password === "admin123456") {
      return { username, rol: "ADMIN" };
    }

    return { username, rol: "USER" };
  }
};