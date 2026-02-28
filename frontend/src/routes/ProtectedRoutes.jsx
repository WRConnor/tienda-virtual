import { Navigate } from "react-router-dom";
import { useAuth } from "../modules/auth//context/authContext";

const roleHierarchy = {
  ADMIN: 3,
  GERENTE: 2,
  USER: 1,
};

const ProtectedRoute = ({ children, allowedRoles = [] }) => { 
  const { isAuthenticated, roles } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles.length === 0) {
    return children;
  }

  const userMaxLevel = Math.max(
    ...roles.map(role => roleHierarchy[role] || 0)
  );

  const hasPermission = allowedRoles.some(
    role => userMaxLevel >= (roleHierarchy[role] || 0)
  );

  if (!hasPermission) {
    return <Navigate to="/no-autorizado" replace />;
  }

  return children;
};



export default ProtectedRoute;