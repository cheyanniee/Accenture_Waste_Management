import axios, { config } from "../api/axios";
import { PEOPLE_ENDPOINTS } from "../helper/Constant";
import useAuth from "./useAuth";

/*
    Purpose:
        - Use Refresh Token

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const useRefreshToken = ({ token }) => {
    const { setAuth } = useAuth();

    const refresh = async () => {
        const response = await axios.get(
            PEOPLE_ENDPOINTS.RefreshToken,
            config({ token: token })
        );

        const userDetails = response?.data || {};

        console.log("useRefresh details", userDetails);

        setAuth((prev) => {
            console.log(JSON.stringify(prev));
            console.log("Setting refresh details", userDetails);

            return {
                ...prev,
                ...userDetails,
            };
        });

        return response.data.token;
    };

    return refresh;
};

export default useRefreshToken;
