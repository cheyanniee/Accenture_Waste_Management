import React, { useEffect, useState } from "react";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";

const Machines = () => {

  return (
    <>
      <Header />

      <h1>Machines Page</h1>

      <Footer />
    </>
  );
};

export default Machines;