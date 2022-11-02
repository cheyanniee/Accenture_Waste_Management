import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';

import { ROLES } from "./helper/Constant";

import Layout from "./components/Layout";
import Unauthorized from "./components/Unauthorized";
import PersistLogin from "./components/PersistLogin";
import Missing from "./components/Missing";
import RequireAuth from "./components/RequireAuth";

import Home from "./pages/Home";
import Logout from "./pages/Logout";

import UserAction from "./pages/UserAction";
import InsertRecycling from "./pages/InsertRecycling";
import ConfirmRecycling from "./pages/ConfirmRecycling";
import CancelRecycling from "./pages/CancelRecycling";
import ExchangeInput from "./pages/ExchangeInput";
import ConfirmExchange from "./pages/ConfirmExchange";
import CollectExchange from "./pages/CollectExchange";

import CollectorAction from "./pages/CollectorAction";
import ConfirmCollection from "./pages/ConfirmCollection";

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
            <Route path="/login" element={<Home />}></Route>
            <Route path="/logout" element={<Logout />}></Route>
            <Route path="/unauthorized" element={<Unauthorized />}></Route>

            {/* protected routes */}
            <Route element={<PersistLogin />}>
              <Route path="/" element={<Home />}></Route>
            </Route>

            {/* Users Route */}
            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
              <Route path="/userAction" element={<UserAction />}></Route>
              <Route path="/insertRecycling" element={<InsertRecycling />}></Route>
              <Route path="/confirmRecycling" element={<ConfirmRecycling />}></Route>
              <Route path="/cancelRecycling" element={<CancelRecycling />}></Route>
              <Route path="/exchangeInput" element={<ExchangeInput />}></Route>
              <Route path="/confirmExchange" element={<ConfirmExchange />}></Route>
              <Route path="/collectExchange" element={<CollectExchange />}></Route>
            </Route>

            {/* Collectors Route */}
            <Route element={<RequireAuth allowedRoles={[ROLES.Collector]} />}>
              <Route path="/collectorAction" element={<CollectorAction />}></Route>
              <Route path="/confirmCollection" element={<ConfirmCollection />}></Route>
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
