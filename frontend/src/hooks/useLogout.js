import axios, { config } from "../api/axios";
import { ENDPOINTS } from "../helper/Constant";
import useAuth from "./useAuth";

const useLogout = () => {
  const { auth, setAuth } = useAuth();

  const logout = async () => {
    try {
      const response = await axios.get(
        ENDPOINTS.Logout,
        config({ token: auth?.token })
      );
      console.log(response.data);
      setAuth({});
      localStorage.removeItem("token");
    } catch (err) {
      console.error(err);
    }
  };

  return logout;
};

export default useLogout;
