import { Navigate } from "react-router-dom";
import { useAuth } from "../modules/auth/context/authContext";

const roleHierarchy = {
  ADMIN: 3,
  GERENTE: 2,
  USER: 1,
};

const ProtectedRoute = ({ children, allowedRoles = [] }) => { 
  const { isAuthenticated, user } = useAuth();

  if (!isAuthenticated || !user) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles.length === 0) {
    return children;
  }

  const userLevel = roleHierarchy[user.rol] || 0;

  const hasPermission = allowedRoles.some(
    role => userLevel >= (roleHierarchy[role] || 0)
  );

  if (!hasPermission) {
    return <Navigate to="/no-autorizado" replace />;
  }

  return children;
};

export default ProtectedRoute;