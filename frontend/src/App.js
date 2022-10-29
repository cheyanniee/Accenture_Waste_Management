import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import Home from "./pages/Home";
import LocationsPage from "./pages/LocationsPage";
import Register from "./pages/Register";

import Layout from "./components/Layout";
import PersistLogin from "./components/PersistLogin";
import Missing from "./components/Missing";
import Unauthorized from "./components/Unauthorized";
import RequireAuth from "./components/RequireAuth";
import Login from "./components/Login";
import GoogleLogin from "./components/GoogleLogin";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            {/* public routes */}
            <Route path="/login" element={<Login />}></Route>
            <Route path="/register" element={<Register />}></Route>
            <Route path="/locations" element={<LocationsPage />}></Route>
            <Route path="/unauthorized" element={<Unauthorized />}></Route>
            <Route path="/googleLogin" element={<GoogleLogin />}></Route>

            {/* protected routes */}
            <Route element={<PersistLogin />}>
              <Route path="/" element={<Home />}></Route>
            </Route>

            {/* catch all */}
            <Route path="*" element={<Missing />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
