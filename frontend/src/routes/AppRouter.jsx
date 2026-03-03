import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "../modules/auth/pages/Login";
import Layout from "../shared/components/Layout";
import ProtectedRoute from "./ProtectedRoutes";

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

        {/* Layout protegido (solo autenticados) */}
        <Route
          element={
            <ProtectedRoute>
              <Layout />
            </ProtectedRoute>
          }
        >
          {/* SOLO ADMIN */}
          <Route
            path="/usuarios"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <Usuario />
              </ProtectedRoute>
            }
          />

          <Route
            path="/proveedores"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <Proveedor />
              </ProtectedRoute>
            }
          />

          {/* ADMIN y GERENTE */}
          <Route
            path="/reportes"
            element={
              <ProtectedRoute allowedRoles={["ADMIN", "GERENTE"]}>
                <Reporte />
              </ProtectedRoute>
            }
          />

          {/* ADMIN, GERENTE, USER */}
          <Route
            path="/ventas"
            element={
              <ProtectedRoute allowedRoles={["ADMIN", "GERENTE", "USER"]}>
                <Ventas />
              </ProtectedRoute>
            }
          />

          {/* ADMIN y USER */}
          <Route
            path="/clientes"
            element={
              <ProtectedRoute allowedRoles={["ADMIN", "USER", "CLIENTE"]}>
                <Cliente />
              </ProtectedRoute>
            }
          />

          {/* ADMIN */}
          <Route
            path="/productos"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <Producto />
              </ProtectedRoute>
            }
          />

        </Route>

        {/* Ruta fallback */}
        <Route path="*" element={<Navigate to="/login" />} />

      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;