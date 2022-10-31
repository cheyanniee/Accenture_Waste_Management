import { useRef, useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

import axios from "../api/axios";
import { PEOPLE_ENDPOINTS, ROLES } from "../helper/Constant";

import useAuth from "../hooks/useAuth";
import useInput from "../hooks/useInput";
import useToggle from "../hooks/useToggle";

const Login = () => {
  const { setAuth } = useAuth();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";
  console.log("from: ", from);
  const emailRef = useRef();
  const passwordRef = useRef();
  const errRef = useRef();

  const [email, resetEmail, emailAttribs] = useInput("email", "");
  const [password, setpassword] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [check, toggleCheck] = useToggle("persist", false);
  const [fromMessage, setFromMessage] = useState(location.state?.message);

  useEffect(() => {
    passwordRef.current.focus();
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
  }, []);

  useEffect(() => {
    setErrMsg("");
    if (email === "") emailRef.current.focus();
  }, [email, password]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFromMessage("");
    try {
      console.log("params");
      const response = await axios.post(
        PEOPLE_ENDPOINTS.Login,
        JSON.stringify({ email, password }),
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      const token = response?.data?.token;
      localStorage.getItem("persist") === "true"
        ? localStorage.setItem("token", token)
        : localStorage.removeItem("token");
      console.log("User details: ", response?.data);
      setAuth(response.data);
      resetEmail();
      setpassword("");

      const url = (response?.data.role === ROLES.User)
        ? "/userAction"
        : (response?.data.role === ROLES.Collector)
            ? "/collectorAction"
            : "/unauthorized";

      navigate(url, { replace: true });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Wrong Email or password");
      } else if (err.response?.status === 401) {
        setErrMsg("Unauthorized");
      } else {
        setErrMsg("Login Failed");
      }
      errRef.current.focus();
    }
  };
  return (
    <>
      <section className="container py-5">
        <div className="row justify-content-center">
          <div className="col-lg-4 border px-3 rounded-3">
            <em ref={errRef} className="text-error">
              {errMsg}
            </em>
            <em className="text-success">{fromMessage}</em>
            <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3 mb-3">
              Sign In
            </h1>
            <form onSubmit={handleSubmit}>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    id="email"
                    ref={emailRef}
                    autoComplete="off"
                    {...emailAttribs}
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
                    onChange={(e) => setpassword(e.target.value)}
                    value={password}
                    ref={passwordRef}
                    required
                    className="form-control form-control-lg light-300"
                  />
                  <label htmlFor="password light-300">password:</label>
                </div>
              </div>
              <button className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light mb-4 light-300">
                Sign In
              </button>
              <div className="form-check">
                <input
                  type="checkbox"
                  id="persist"
                  onChange={toggleCheck}
                  checked={check}
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
    </>
  );
};

export default Login;
