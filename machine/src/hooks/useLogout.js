import axios, { config } from "../api/axios";
import { PEOPLE_ENDPOINTS } from "../helper/Constant";
import useAuth from "./useAuth";

/*
    Purpose:
        - Use Input

    Restriction:
        - NIL

    Endpoints:
        - PEOPLE_ENDPOINTS.Logout

    Author:
        - Alex Lim
*/

const useLogout = () => {
    const { auth, setAuth } = useAuth();

    const logout = async () => {
        try {
            const response = await axios.get(
                PEOPLE_ENDPOINTS.Logout,
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
