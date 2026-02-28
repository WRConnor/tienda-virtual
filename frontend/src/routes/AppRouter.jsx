import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "../modules/auth/pages/Login";
import Layout from "../shared/components/Layout";
import PrivateRoute from "./PrivateRoute";

import Usuario from "../modules/users/pages/Usuario";
import Cliente from "../modules/clients/pages/Cliente";
import Proveedor from "../modules/suppliers/pages/Proveedor";
import Producto from "../modules/products/pages/Producto";
import Ventas from "../modules/sales/pages/Ventas";
import Reporte from "../modules/reports/pages/Reporte";

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