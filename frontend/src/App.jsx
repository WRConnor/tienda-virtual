import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// Páginas
import Login from "./modules/auth/pages/Login";
import AdminPanel from "./modules/users/pages/Usuario";
import ClientePanel from "./modules/clients/pages/ClientePanel";
import CajeroPanel from "./modules/users/pages/CajeroPanel";
import GerentePanel from "./modules/users/pages/GerentePanel";
import InventarioPanel from "./modules/users/pages/InventarioPanel";

function App() {
  const rol = localStorage.getItem("rol"); // rol del usuario logueado

  return (
    <BrowserRouter>
      <Routes>

        {/* Login */}
        <Route path="/login" element={<Login />} />

        {/* Interfaces separadas por rol */}
        <Route path="/admin/*" element={rol === "ADMIN" ? <AdminPanel /> : <Navigate to="/login" />} />
        <Route path="/cliente/*" element={rol === "CLIENTE" ? <ClientePanel /> : <Navigate to="/login" />} />
        <Route path="/ventas/*" element={rol === "CAJERO" ? <CajeroPanel /> : <Navigate to="/login" />} />
        <Route path="/reportes/*" element={rol === "GERENTE" ? <GerentePanel /> : <Navigate to="/login" />} />
        <Route path="/inventario/*" element={rol === "INVENTARIO" ? <InventarioPanel /> : <Navigate to="/login" />} />

        {/* Ruta default: redirige al panel según rol o login */}
        <Route
          path="*"
          element={
            rol
              ? rol === "ADMIN" ? <Navigate to="/admin" /> :
                rol === "CLIENTE" ? <Navigate to="/cliente" /> :
                rol === "CAJERO" ? <Navigate to="/ventas" /> :
                rol === "GERENTE" ? <Navigate to="/reportes" /> :
                rol === "INVENTARIO" ? <Navigate to="/inventario" /> :
                <Navigate to="/login" />
              : <Navigate to="/login" />
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
