import React, { useEffect, useState } from "react";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";

const BatteryUpdate = () => {

  return (
    <>
      <Header />

      <h1>Battery Update Page</h1>

      <Footer />
    </>
  );
};

export default BatteryUpdate;