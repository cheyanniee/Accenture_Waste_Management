import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import { ROLES } from "./helper/Constant";

import Layout from "./components/Layout";
import Login from "./components/Login";
import Unauthorized from "./components/Unauthorized";
import PersistLogin from "./components/PersistLogin";
import Missing from "./components/Missing";
import RequireAuth from "./components/RequireAuth";
<<<<<<< HEAD
import Login from "./components/Login";
import GoogleLogin from "./components/GoogleLogin";
=======

import Register from "./pages/Register";
import LocationsPage from "./pages/LocationsPage";
import Home from "./pages/Home";

import AssignTask from "./pages/AssignTask";
import Machines from "./pages/Machines";
import BatteryUpdate from "./pages/BatteryUpdate";
import RegisterUsers from "./pages/RegisterUsers";

>>>>>>> master

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
<<<<<<< HEAD
=======

              {/* Users Route */}


              {/* Collector Route */}


              {/* Admin Route */}
              <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
                <Route path="/assignTask" element={<AssignTask />}></Route>
                <Route path="/machines" element={<Machines />}></Route>
                <Route path="/batteryUpdate" element={<BatteryUpdate />}></Route>
                <Route path="/registerUsers" element={<RegisterUsers />}></Route>
              </Route>

>>>>>>> master
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
