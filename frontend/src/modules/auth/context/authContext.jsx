import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("usuario");

    if (!storedUser) return null;

    try {
      return JSON.parse(storedUser);
    } catch (error) {
      // si antes se guardó como string simple ("admin")
      return null;
    }
  });

  const isAuthenticated = !!user;

  const login = (usuarioData) => {
    localStorage.setItem("usuario", JSON.stringify(usuarioData));
    localStorage.setItem("token", usuarioData.token);
    setUser(usuarioData);
  };

  const logout = () => {
    localStorage.removeItem("usuario");
    localStorage.removeItem("token");
    setUser(null);
  };

  const esAdmin = () => user?.rol === "ADMIN";
  const esCliente = () => user?.rol === "CLIENTE";

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated,
        login,
        logout,
        esAdmin,
        esCliente,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
