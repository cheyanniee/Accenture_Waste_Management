import { useRef, useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useFormik } from "formik";

import axios from "../api/axios";
import { PEOPLE_ENDPOINTS } from "../helper/Constant";

import Header from "./Header";
import Footer from "./Footer";
import { resetPasswordSchema } from "../schemas";

const ResetPasswordNewPass = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const passwordRef = useRef();
  const [errMsg, setErrMsg] = useState("");
  const [fromMessage, setFromMessage] = useState(location.state?.message);

  const onSubmit = async (values, actions) => {
    setFromMessage("");
    try {
      const response = await axios.post(
        PEOPLE_ENDPOINTS.ResetPasswordNewPass,
        values
      );
      console.log(response.data);
      actions.resetForm();
      navigate("/login", {
        state: {
          message: "Password reset successful! Please Login to continue",
        },
      });
    } catch (error) {
      setErrMsg(error?.response.data.message);
    }
  };

  const { values, errors, touched, handleChange, handleBlur, handleSubmit } =
    useFormik({
      initialValues: {
        email: location.state?.email,
        password: "",
        confirmPassword: "",
        otp: "",
      },
      validationSchema: resetPasswordSchema,
      onSubmit,
    });

  useEffect(() => {
    setErrMsg("");
    passwordRef.current.focus();
  }, []);
  return (
    <>
      <Header />
      <section className="container py-5">
        <div className="row justify-content-center">
          <div className="col-lg-4 border px-3 rounded-3">
            <em className="text-error">{errMsg}</em>
            <em className="text-success">{fromMessage}</em>
            <h1 className="col-12 col-xl-9 h2 text-left text-primary pt-3 mb-3">
              New Password
            </h1>
            <form onSubmit={handleSubmit}>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    id="email"
                    className={
                      errors.email && touched.email
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="email light-300">Email:</label>
                  {errors.email && touched.email && (
                    <em className="text-error">{errors.email}</em>
                  )}
                </div>
              </div>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="password"
                    id="password"
                    ref={passwordRef}
                    className={
                      errors.password && touched.password
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    value={values.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="password light-300">Password:</label>
                  {errors.password && touched.password && (
                    <em className="text-error">{errors.password}</em>
                  )}
                </div>
              </div>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="password"
                    id="confirmPassword"
                    className={
                      errors.confirmPassword && touched.confirmPassword
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    value={values.confirmPassword}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="confirmPassword light-300">
                    Confirm Password:
                  </label>
                  {errors.confirmPassword && touched.confirmPassword && (
                    <em className="text-error">{errors.confirmPassword}</em>
                  )}
                </div>
              </div>
              <div className="col-lg-12 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    id="otp"
                    className={
                      errors.otp && touched.otp
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    value={values.otp}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="otp light-300">OTP:</label>
                  {errors.otp && touched.otp && (
                    <em className="text-error">{errors.otp}</em>
                  )}
                </div>
              </div>
              <button className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light mb-4 light-300">
                Reset Password
              </button>
            </form>
          </div>
        </div>
      </section>
      <Footer />
    </>
  );
};

export default ResetPasswordNewPass;
