import React, { useEffect } from "react";
import Footer from "./Footer";
import Header from "./Header";
import jwt_decode from "jwt-decode";
import { Link } from "react-router-dom";

const GoogleLogin = () => {
  const handleCallbackResponse = (response) => {
    console.log("Encoded JWT ID Token:" + response.credential);

    const userObject = jwt_decode(response.credential);
    console.log(userObject);
  };

  useEffect(() => {
    /* global google */
    google.accounts.id.initialize({
      client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
      callback: handleCallbackResponse,
    });

    google.accounts.id.renderButton(document.getElementById("signInDiv"), {
      theme: "outline",
      size: "large",
    });
  }, []);

  return (
    <>
      <Header />
      <section className="container py-5">
        <div className="row justify-content-center">
          <div className="col-lg-4 border px-3 rounded-3">
            <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3 mb-3">
              Sign In
            </h1>
            <form>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    id="email"
                    autoComplete="off"
                    required
                    className="form-control form-control-lg light-300"
                  />
                  <label htmlFor="email light-300">Email:</label>
                </div>
              </div>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="password"
                    id="password"
                    required
                    className="form-control form-control-lg light-300"
                  />
                  <label htmlFor="password light-300">password:</label>
                </div>
              </div>
              <button className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light mb-4 light-300">
                Sign In
              </button>
              <div id="signInDiv" className=" mb-4"></div>
              <div className="form-check">
                <input
                  type="checkbox"
                  id="persist"
                  className="form-check-input"
                />
                <label className="form-check-label" htmlFor="persist">
                  Trust This Device
                </label>
              </div>
            </form>
            <p className="text-secondary">
              Need an Account?
              <br />
              <span className="text-secondary">
                <Link to="/register">Sign Up</Link>
              </span>
            </p>
          </div>
        </div>
      </section>

      <Footer />
    </>
  );
};

export default GoogleLogin;
