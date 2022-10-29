import React from "react";
import ReactDOM from "react-dom/client";
<<<<<<< HEAD
import App from "./App";
=======
import "./index.css";
import App from "./App";
import { AuthProvider } from "./context/AuthProvider";
>>>>>>> master

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </React.StrictMode>
<<<<<<< HEAD
);
=======
);
>>>>>>> master
