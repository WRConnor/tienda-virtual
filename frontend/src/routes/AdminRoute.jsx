import { Navigate } from "react-router-dom";
import { esAdmin } from "../../modules/auth/context/auth";

function AdminRoute({ children }) {
  return esAdmin() ? children : <Navigate to="/ventas" />;
}

export default AdminRoute;