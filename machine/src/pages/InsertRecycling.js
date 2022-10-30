import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Login from "../components/Login";

const InsertRecycling = () => {
  const { auth } = useAuth();
  const navigate = useNavigate();

  const done = () => {
    console.log("Done Inserting Batteries");
    navigate("/confirmRecycling", { replace: true });
  }

  return (
    <>
      <Header />

      <section className="service-wrapper py-3">
        <div className="container-fluid pb-3">
          <div className="row">
            <h2 className="h2 text-center col-12 py-5 semi-bold-600">
              Battery Life
            </h2>
          </div>

          <div className="row">
            <h3>
              Please Insert Old Batteries!
            </h3>
          </div>

          <div className="h2 py-3">
            <button
              className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
              onClick={done}
            >
              Recycle Batteries
            </button>
          </div>
        </div>
      </section>

      <Footer />
    </>
  );
};

export default InsertRecycling;