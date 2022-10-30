import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const RequireAuth = ({ allowedRoles }) => {
  const { auth } = useAuth();
  const location = useLocation();
  return allowedRoles.find((role) => role.includes(auth?.role)) ? (
    <Outlet />
  ) : auth?.token ? ( //changed from user to accessToken to persist login after refresh
    <Navigate to="/unauthorized" state={{ from: location }} replace />
  ) : (
    <Navigate
      to="/login"
      state={{ from: location, message: "Please Login to continue." }}
      replace
    />
  );
};

export default RequireAuth;
