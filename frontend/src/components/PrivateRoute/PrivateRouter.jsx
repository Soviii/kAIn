import { Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext.jsx";


const PrivateRoute = ({ children }) => {
  const { isAuthenticated } = useAuth();

  if (isAuthenticated === null) return null; // TODO: have page have page effect when switching/loading

  return isAuthenticated ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
