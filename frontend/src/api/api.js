// services/api.js
const API = "/api";

export const api = {
  login: async ({ username, password }) => {
    if (!username || !password) throw new Error("Faltan datos del usuario");

    const response = await fetch(
      `${API}/usuarios/login?user=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
    );

    if (!response.ok) throw new Error("Usuario o contraseña incorrectos");

    const text = await response.text();
    if (text !== "Accedido") throw new Error("Usuario o contraseña incorrectos");

    // Como backend no devuelve rol, lo definimos aquí
    if (username === "admin" && password === "admin123456") return { username, rol: "ADMIN" };

    return { username, rol: "USER" };
  },

  getUsuarios: async () => {
    const res = await fetch(`${API}/usuarios/mostrartodo`);
    if (!res.ok) throw new Error("Error al obtener usuarios");
    return await res.json();
  },

  crearUsuario: async (usuario) => {
    // Convertimos cédula a número
    const payload = { ...usuario, cedulaUsuario: Number(usuario.cedulaUsuario) };

    const res = await fetch(`${API}/usuarios/crear`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Error al crear usuario: ${text}`);
    }
  },

  actualizarUsuario: async (id, usuario) => {
    const payload = { ...usuario, cedulaUsuario: Number(usuario.cedulaUsuario) };

    const res = await fetch(`${API}/usuarios/actualizar/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Error al actualizar usuario: ${res.status} - ${text}`);
    }
  },

  borrarUsuario: async (id) => {
    const res = await fetch(`${API}/usuarios/eliminar/${id}`, { method: "DELETE" });
    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Error al borrar usuario: ${res.status} - ${text}`);
    }
  },

  // ================= CLIENTES =================

  getClientes: async () => {
    const res = await fetch(`${API}/clientes/mostrartodo`);
    if (!res.ok) throw new Error("Error al obtener clientes");
    return await res.json();
  },

  crearCliente: async (cliente) => {
    const payload = { ...cliente, cedula: Number(cliente.cedula) };

    const res = await fetch(`${API}/clientes/crear`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Error al crear cliente: ${text}`);
    }
  },

  actualizarCliente: async (id, data) => {
    const response = await fetch(`${API}/clientes/actualizar/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (!response.ok) {
      throw new Error("Error al actualizar cliente");
    }

    return response.text();
  },

  borrarCliente: async (cedula) => {
    const res = await fetch(`${API}/clientes/eliminar/${cedula}`, {
      method: "DELETE",
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Error al borrar cliente: ${res.status} - ${text}`);
    }
  }
};

