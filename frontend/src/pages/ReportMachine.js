import React, { useEffect, useState } from "react";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";

const ReportMachine = () => {

  return (
    <>
      <Header />

      <h1>ReportMachine Page</h1>

      <Footer />
    </>
  );
};

export default ReportMachine;