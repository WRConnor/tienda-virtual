import { Navigate } from "react-router-dom";

function PrivateRoute({ children }) {
  if (!isAuthenticated) {
  return <Navigate to="/login" />;
}
  const token = localStorage.getItem("token");
}

export default PrivateRoute;