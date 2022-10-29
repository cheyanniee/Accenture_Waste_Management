import React, { useRef, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";

import axios from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import { INITIAL_FORM_VALUES, registerSchema } from "../schemas";
import { ENDPOINTS } from "../helper/Constant";
import jwt_decode from "jwt-decode";

const Register = () => {
  const [errMsg, setErrMsg] = useState("");
  const navigate = useNavigate();
  const onSubmit = async (values, actions) => {
    values = { ...values, dob: moment(values.dob).format("DD/MM/YYYY") };
    console.log("params: ", values);
  };
  const inputRef = useRef();
  const {
    values,
    errors,
    touched,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
  } = useFormik({
    initialValues: INITIAL_FORM_VALUES,
    validationSchema: registerSchema,
    onSubmit,
  });

  const handleCallbackResponse = (response) => {
    console.log("Encoded JWT ID Token:" + response.credential);

    const userObject = jwt_decode(response.credential);
    console.log(userObject);
  };
  useEffect(() => {
    inputRef.current.focus();
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
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
      {/* Start Register Form */}
      <section className="container py-5">
        <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3">
          Registration Form
        </h1>
        <h2 className="col-12 col-xl-8 h4 text-left regular-400">
          For General Public
        </h2>
        <p className="col-12 col-xl-8 text-left text-muted pb-5 light-300">
          Kindly fill in all required fields
        </p>
        <div className="row pb-4">
          <div className="col-lg-4">
            <div className="contact row mb-4">
              <div className="contact-icon col-lg-3 col-3">
                <div className="py-3 mb-2 text-center border rounded text-secondary">
                  <i className="display-6 bx bx-news" />
                </div>
              </div>
              <ul className="contact-info list-unstyled col-lg-9 col-9  light-300">
                <li className="h5 mb-0">MOH COVID-19 HOTLINE</li>
                <li className="text-muted">General Enquiries</li>
                <li className="text-muted">1800 333 9999</li>
              </ul>
              <div id="signInDiv"></div>
            </div>
            <div className="contact row mb-4">
              <div className="contact-icon col-lg-3 col-3">
                <div className="border py-3 mb-2 text-center border rounded text-secondary">
                  <i className="bx bx-laptop display-6" />
                </div>
              </div>
              <ul className="contact-info list-unstyled col-lg-9 col-9 light-300">
                <li className="h5 mb-0">QUARANTINE HOTLINE</li>
                <li className="text-muted">General Line</li>
                <li className="text-muted">6325 9220</li>
              </ul>
            </div>
            <div className="contact row mb-4">
              <div className="contact-icon col-lg-3 col-3">
                <div className="border py-3 mb-2 text-center border rounded text-secondary">
                  <i className="bx bx-money display-6" />
                </div>
              </div>
              <ul className="contact-info list-unstyled col-lg-9 col-9 light-300">
                <li className="h5 mb-0">Hotline Operating Hours:</li>
                <li className="text-muted">Mon to Fri: 8.30am to 6.00pm</li>
                <li className="text-muted">Weekends and PH: Closed </li>
              </ul>
            </div>
          </div>
          {/* Start Contact Form */}
          <div className="col-lg-8 ">
            <form className="contact-form row" onSubmit={handleSubmit}>
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className={
                      errors.firstName && touched.firstName
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="firstName"
                    ref={inputRef}
                    placeholder="First Name"
                    value={values.firstName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="firstName light-300">First Name</label>
                  {errors.firstName && touched.firstName && (
                    <em className="text-error">{errors.firstName}</em>
                  )}
                </div>
              </div>
              {/* End Input First Name */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className={
                      errors.lastName && touched.lastName
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="lastName"
                    placeholder="Last Name"
                    value={values.lastName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="last-name light-300">Last Name</label>
                  {errors.lastName && touched.lastName && (
                    <em className="text-error">{errors.lastName}</em>
                  )}
                </div>
              </div>
              {/* End Input lastName */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className={
                      errors.officialId && touched.officialId
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="officialId"
                    placeholder="NRIC/FIN/Passport"
                    value={values.officialId}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="last-name light-300">NRIC/FIN/Passport</label>
                  {errors.officialId && touched.officialId && (
                    <em className="text-error">{errors.officialId}</em>
                  )}
                </div>
              </div>
              {/* End Input officialId */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="password"
                    className={
                      errors.password && touched.password
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="password"
                    placeholder="password"
                    value={values.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="password light-300">Password</label>
                  {errors.password && touched.password && (
                    <em className="text-error">{errors.password}</em>
                  )}
                </div>
              </div>
              {/* End Input password */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="password"
                    className={
                      errors.confirm_password && touched.confirm_password
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="confirm_password"
                    placeholder="Confirm Password"
                    value={values.confirm_password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="confirm_password light-300">
                    Confirm Password
                  </label>
                  {errors.confirm_password && touched.confirm_password && (
                    <em className="text-error">{errors.confirm_password}</em>
                  )}
                </div>
              </div>
              {/* End Input Confirm password */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className={
                      errors.email && touched.email
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="email"
                    placeholder="Email"
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="email light-300">Email</label>
                  {errors.email && touched.email && (
                    <em className="text-error">{errors.email}</em>
                  )}
                </div>
              </div>
              {/* End Input Email */}
              <div className="col-lg-6 mb-4">
                <div className="form-floating">
                  <input
                    type="date"
                    className={
                      errors.dob && touched.dob
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="dob"
                    placeholder="dob"
                    value={values.dob}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="dob light-300">Date of Birth</label>
                  {errors.dob && touched.dob && (
                    <em className="text-error">{errors.dob}</em>
                  )}
                </div>
              </div>
              {/* End Input Date of Birth */}
              <div className="col-lg-6 mb-4">
                <div className="form-floating">
                  <input
                    type="number"
                    className={
                      errors.phone && touched.phone
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="phone"
                    placeholder="Mobile Number"
                    value={values.phone}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="phone light-300">Mobile Number</label>
                  {errors.phone && touched.phone && (
                    <em className="text-error">{errors.phone}</em>
                  )}
                </div>
              </div>
              {/* End Input phone */}
              <div className="col-8">
                <div className="form-floating mb-4">
                  <input
                    type="text"
                    className={
                      errors.address && touched.address
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="address"
                    placeholder="Address"
                    value={values.address}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="address light-300">Address</label>
                  {errors.address && touched.address && (
                    <em className="text-error">{errors.address}</em>
                  )}
                </div>
              </div>
              {/* End Input Address */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="number"
                    className={
                      errors.postcode && touched.postcode
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="postcode"
                    placeholder="Postal Code"
                    value={values.postcode}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="postcode light-300">Postal Code</label>
                  {errors.postcode && touched.postcode && (
                    <em className="text-error">{errors.postcode}</em>
                  )}
                </div>
              </div>
              {/* End Input Postal code */}
              <div className="col-md-12 col-12 m-auto text-end">
                <em className="text-error px-3">{errMsg}</em>
                <button
                  disabled={isSubmitting}
                  type="submit"
                  className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                >
                  Register!
                </button>
              </div>
            </form>
          </div>
          {/* End Contact Form */}
        </div>
      </section>
      {/* End Register Form */}
      <Footer />
    </>
  );
};

export default Register;
