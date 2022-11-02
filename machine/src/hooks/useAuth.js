import { useContext } from "react";
import AuthContext from "../context/AuthProvider";

/*
    Purpose:
        - Establish Outlet

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const useAuth = () => {
    return useContext(AuthContext);
};

export default useAuth;
