import { createContext, useState } from "react";

/*
    Purpose:
        - Store user details when logged in to use across pages

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim & Cheyanne Lim
*/

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({});
    const [transaction, setTransaction] = useState({});
    const [machineID, setMachineID] = useState({});

    machineID = 14;

    /* console.log("Auth Provider:--", auth?.token); */

    return (
        <AuthContext.Provider
            value={{
                auth,
                setAuth,
                transaction,
                setTransaction,
                machineID,
                setMachineID
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;
