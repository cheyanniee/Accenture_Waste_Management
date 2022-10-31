import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import useLogout from "../hooks/useLogout";

import Header from "../components/Header";
import Footer from "../components/Footer";

const Logout = () => {
  const { auth } = useAuth();
  const navigate = useNavigate();
  const logout = useLogout();
  const signOut = async () => {
    await logout();
    navigate("/login", {
      state: { message: "You have logged out successfully" },
    });
  };

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
              Thank you! Please Log Out!
            </h3>
          </div>

          <div className="h2 py-3">
            <button
              className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
              onClick={signOut}
            >
              Logout
            </button>
          </div>
        </div>
      </section>

      <Footer />
    </>
  );
};

export default Logout;