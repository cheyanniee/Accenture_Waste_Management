import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import { ROLES } from "./helper/Constant";

import Layout from "./components/Layout";
import Login from "./components/Login";
import Unauthorized from "./components/Unauthorized";
import PersistLogin from "./components/PersistLogin";
import Missing from "./components/Missing";
import RequireAuth from "./components/RequireAuth";

import Register from "./pages/Register";
import LocationsPage from "./pages/LocationsPage";
import Home from "./pages/Home";

import ReportMachine from "./pages/ReportMachine";
import Points from "./pages/Points";
import ViewTask from "./pages/ViewTask";

import AssignTask from "./pages/AssignTask";
import Machines from "./pages/Machines";
import BatteryUpdate from "./pages/BatteryUpdate";
import RegisterUsers from "./pages/RegisterUsers";

import UserDetails from "./pages/UserDetails";
import ResetPassword from "./components/ResetPassword";
import ResetPasswordNewPass from "./components/ResetPasswordNewPass";

/*
    Purpose:
        - Establish Routes

    Restriction:
        - Each route's restrictions are specified below

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            {/* public routes */}
            <Route path="/login" element={<Login />}></Route>
            <Route path="/register" element={<Register />}></Route>
            <Route path="/resetpassword" element={<ResetPassword />}></Route>
            <Route
              path="/resetpasswordnewpass"
              element={<ResetPasswordNewPass />}
            ></Route>
            <Route path="/locations" element={<LocationsPage />}></Route>
            <Route path="/unauthorized" element={<Unauthorized />}></Route>

            {/* protected routes */}
            <Route element={<PersistLogin />}>
              <Route path="/" element={<Home />}></Route>

              {/* Users Route */}
              <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route path="/points" element={<Points />}></Route>
              </Route>

              {/* Collector Route */}
              <Route element={<RequireAuth allowedRoles={[ROLES.Collector]} />}>
                <Route path="/viewTask" element={<ViewTask />}></Route>
              </Route>

              {/* Shared User & Collector Route */}
              <Route
                element={
                  <RequireAuth allowedRoles={[ROLES.Collector, ROLES.User]} />
                }
              >
                <Route
                  path="/reportMachine"
                  element={<ReportMachine />}
                ></Route>
              </Route>

              {/* Admin Route */}
              <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
                <Route path="/assignTask" element={<AssignTask />}></Route>
                <Route path="/machines" element={<Machines />}></Route>
                <Route
                  path="/batteryUpdate"
                  element={<BatteryUpdate />}
                ></Route>
                <Route
                  path="/registerUsers"
                  element={<RegisterUsers />}
                ></Route>
              </Route>

              {/* General Protected Route */}
              <Route
                element={
                  <RequireAuth
                    allowedRoles={[ROLES.User, ROLES.Collector, ROLES.Admin]}
                  />
                }
              >
                <Route path="/userDetails" element={<UserDetails />}></Route>
              </Route>
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
