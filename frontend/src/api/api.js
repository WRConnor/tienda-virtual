// services/api.js
export const api = {
  login: async ({ username, password }) => {
    if (!username || !password) {
      throw new Error("Faltan datos del usuario");
    }
    if (username === "admininicial" && password === "admin123456") {
      return { username: "admininicial", rol: "ADMIN" };
    } else if (username === "cliente1" && password === "cliente123") {
      return { username: "cliente1", rol: "CLIENTE" };
    } else {
      throw new Error("Credenciales incorrectas");
    }
  },

  crearUsuario: async (usuario) => {
    if (!usuario.username || !usuario.password) {
      throw new Error("Faltan datos del usuario");
    }
    if (usuario.username === "admininicial" && usuario.password === "admin123456") {
      return { message: "Usuario Creado" };
    } else {
      throw new Error("usuario o contraseña errados, intente de nuevo");
    }
  },

  consultarUsuario: async (cedula) => {
    if (cedula === "123456") {
      return {
        nombre: "Juan Pérez",
        correo: "juan@example.com",
        usuario: "juanp",
        password: "pass123",
      };
    } else {
      throw new Error("Usuario Inexistente");
    }
  },

  actualizarUsuario: async (usuario) => {
    if (!usuario.nombre || !usuario.correo) {
      throw new Error("Datos faltantes");
    }
    return { message: "Datos del Usuario Actualizados" };
  },

  borrarUsuario: async (cedula) => {
    if (cedula === "123456") {
      return { message: "Datos del Usuario Borrados" };
    } else {
      throw new Error("Cédula Errada");
    }
  },
};