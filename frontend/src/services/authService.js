const login = async ({ username, password }) => {
  console.log("Intentando login con:", username, password);

  if (username.trim() === "admin" && password.trim() === "admin123456") {
    return { token: "fake-jwt-token" };
  } else {
    throw new Error("Credenciales incorrectas");
  }
};

export default { login };

