export const getUsuario = () => {
  return JSON.parse(localStorage.getItem("usuario"));
};

export const esAdmin = () => {
  return getUsuario()?.rol === "ADMIN";
};

export const estaAutenticado = () => {
  return !!getUsuario();
};