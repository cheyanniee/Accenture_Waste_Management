import React, { useEffect, useState } from "react";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";

const ViewTask = () => {

  return (
    <>
      <Header />

      <h1>ViewTask Page</h1>

      <Footer />
    </>
  );
};

export default ViewTask;