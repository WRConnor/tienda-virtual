import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "../pages/Login";
import Layout from "../components/Layout";
import PrivateRoute from "./PrivateRoute";

import Usuario from "../pages/Usuario";
import Cliente from "../pages/Cliente";
import Proveedor from "../pages/Proveedor";
import Producto from "../pages/Producto";
import Ventas from "../pages/ventas";
import Reporte from "../pages/Reporte";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Ruta pública */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />

        {/* Rutas protegidas */}
        <Route
          element={
            <PrivateRoute>
              <Layout />
            </PrivateRoute>
          }
        >
          <Route path="/usuarios" element={<Usuario />} />
          <Route path="/clientes" element={<Cliente />} />
          <Route path="/proveedores" element={<Proveedor />} />
          <Route path="/productos" element={<Producto />} />
          <Route path="/ventas" element={<Ventas />} />
          <Route path="/reportes" element={<Reporte />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;