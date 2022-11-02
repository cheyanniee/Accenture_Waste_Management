import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { AuthProvider } from "./context/AuthProvider";

/*
    Purpose:
        - Wrap application to have authentication restriction

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <AuthProvider>
            <App />
        </AuthProvider>
    </React.StrictMode>
);
