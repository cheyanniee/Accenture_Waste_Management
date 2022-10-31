import { useRef, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

import axios from "../api/axios";
import { PEOPLE_ENDPOINTS } from "../helper/Constant";

import Header from "./Header";
import Footer from "./Footer";
import Loader from "./Loader";

const ResetPassword = () => {
  const emailRef = useRef();
  const [errMsg, setErrMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setErrMsg("");
    emailRef.current.focus();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const email = emailRef.current.value;
    if (!email) return;

    try {
      setLoading(true);
      const response = await axios.post(PEOPLE_ENDPOINTS.ResetPassword, {
        email,
      });
      console.log(response);
      setSuccessMsg(response?.data.message);
      setLoading(false);
      navigate("/resetpasswordnewpass", {
        state: { message: response?.data.message, email },
      });
    } catch (error) {
      setLoading(false);
      setErrMsg(error?.response.data.message);
    }
    console.log("hello");
  };
  return (
    <>
      <Header />
      {loading ? (
        <Loader />
      ) : (
        <section className="container py-5">
          <div className="row justify-content-center">
            <div className="col-lg-4 border px-3 rounded-3">
              <em className="text-error">{errMsg}</em>
              <em className="text-success">{successMsg}</em>
              <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3 mb-3">
                Reset Password
              </h1>
              <form onSubmit={handleSubmit}>
                <div className="col-lg-12 mb-4">
                  <div className="form-floating">
                    <input
                      type="email"
                      id="email"
                      ref={emailRef}
                      autoComplete="off"
                      required
                      className="form-control form-control-lg light-300"
                    />
                    <label htmlFor="email light-300">Email:</label>
                  </div>
                </div>
                <button className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light mb-4 light-300">
                  Reset Password
                </button>
              </form>
              <p className="text-secondary">
                Get Started?
                <br />
                <span className="text-secondary">
                  <Link to="/login">Login</Link>
                </span>
              </p>
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
      )}
      <Footer />
    </>
  );
};

export default ResetPassword;
