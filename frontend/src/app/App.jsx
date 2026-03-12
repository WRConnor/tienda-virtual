import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "../../auth/context/authContext";
import Login from "../Login";
import UsuarioAdmin from "../modules/users/pages/Usuario";
import UsuarioCliente from "../modules/users/pages/UsuarioCliente";

const ProtectedRoute = ({ children }) => {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/usuarios" element={
            <ProtectedRoute>
              <UsuariosWrapper />
            </ProtectedRoute>
          }/>
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

function UsuariosWrapper() {
  const { esAdmin } = useAuth();
  return esAdmin() ? <UsuarioAdmin /> : <UsuarioCliente />;
}

export default App;