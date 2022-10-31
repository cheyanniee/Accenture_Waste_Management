import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

const CollectExchange = () => {
  const { auth } = useAuth();
  const navigate = useNavigate();

  const done = () => {
    console.log("Done Retrieving New Batteries");
    navigate("/logout", { replace: true });
  }

  return (
    <>
      <Header />
      <Title />

      <section className="container-fluid pb-3">

          <div className="row">
            <h3>
              Please Collect Your Batteries!
            </h3>
          </div>

          <div className="h2 py-3">
            <button
              className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
              onClick={done}
            >
              Done Retrieving Batteries
            </button>
          </div>
      </section>

      <Footer />
    </>
  );
};

export default CollectExchange;