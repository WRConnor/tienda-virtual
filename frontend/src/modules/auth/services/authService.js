/*import axiosClient from "../../../api/axiosClient  ";

const login = async ({ username, password }) => {
  console.log("Intentando login con:", username, password);

  // 🔒 Validación de datos incompletos
  if (!username?.trim() || !password?.trim()) {
    throw new Error("Faltan datos del usuario");
  }

  try {
    // 🔵 Si quieres usar BACKEND (cuando esté listo)
    const response = await axiosClient.post("/api/usuarios/login", {
      username: username.trim(),
      password: password.trim(),
    });

    return response.data;

  } catch (error) {
    // 🟡 Si el backend aún no está listo, puedes usar MOCK temporal
    if (
      username.trim() === "admin" &&
      password.trim() === "admin123456"
    ) {
      return { token: "fake-jwt-token" };
    }

    throw new Error("usuario o contraseña errados, intente de nuevo");
  }
};

export default { login };*/

import axiosClient from "../../../api/axiosClient";

const login = async ({ username, password }) => {

  if (!username?.trim() || !password?.trim()) {
    throw new Error("Faltan datos del usuario");
  }

  const response = await axiosClient.post("/api/usuarios/login", {
    username: username.trim(),
    password: password.trim(),
  });

  return response.data;
};

export default { login };
