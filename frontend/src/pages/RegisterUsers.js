import React, { useRef, useEffect, useState } from "react";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";
import axios from "axios";

import useAuth from "../hooks/useAuth";
import { default as myAxios, config } from "../api/axios";
import { PEOPLE_ENDPOINTS, ROLES } from "../helper/Constant";
import { INITIAL_REGISTER_USERS_FORM_VALUES, registerUsersSchema } from "../schemas";

import Header from "../components/Header";
import Footer from "../components/Footer";

/*
    Purpose:
        - Admins can Register an Account for Collectors and Admins

    Restriction:
        - Only those with ROLES.Admin will be able to access this page.

    Endpoints:
        - PEOPLE_ENDPOINTS.RegisterCollector
        - PEOPLE_ENDPOINTS.RegisterAdmin
        - https://developers.onemap.sg/commonapi/search

    Author:
        - Cheyanne Lim
*/

const RegisterUsers = () => {
  const { auth } = useAuth();
  const [errMsg, setErrMsg] = useState("");
  const [addressLabel, setAddressLabel] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const onSubmit = async (values, actions) => {
    setSuccessMsg("");
    const unitNumberTemp = values.floor + "-" + values.unit;
    values = { ...values, dateOfBirth: moment(values.dateOfBirth).format("DD/MM/YYYY"), unitNumber: unitNumberTemp };
    console.log("params: ", values);

    const endpoint = (values.role === ROLES.Collector)
        ? PEOPLE_ENDPOINTS.RegisterCollector
        : PEOPLE_ENDPOINTS.RegisterAdmin;

    try {
      console.log("url: ", endpoint);
      const response = await myAxios.post(
        endpoint,
        values,
        config({ token: auth.token })
      );
      console.log(response.data);
      actions.resetForm();
      setAddressLabel("");
      setSuccessMsg("Registration Successful!");
    } catch (error) {
      console.log(error.response);
      setErrMsg(error.response.data.message);
    }
  };

  const loadAddress = async () => {
    try {
      console.log("postcode: ", values.postcode);
      const url = "https://developers.onemap.sg/commonapi/search?searchVal=" + values.postcode + "&returnGeom=N&getAddrDetails=Y&pageNum=1";
      console.log("url: ", url);
      const response = await axios.get(url);
      console.log("data: ", response.data);
      const add = response.data.results[0].BLK_NO + " " + response.data.results[0].ROAD_NAME;
      values.address = add;
      setAddressLabel(add);
    } catch (error) {
      console.log("error: ", error.response);
      setAddressLabel("No address found!");
    }
  }

  const resetAddress = async () => {
    values.address = "";
  }

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
    initialValues: INITIAL_REGISTER_USERS_FORM_VALUES,
    validationSchema: registerUsersSchema,
    onSubmit,
  });

  useEffect(() => {
    inputRef.current.focus();
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
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
          For Registration of Collectors & Admins
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
                <li className="h5 mb-0">Ministry of Sustainability</li>
                <li className="h5 mb-0">and the Environment (MSE)</li>
                <li className="text-muted">Tel: 6731 9000</li>
              </ul>
            </div>
            <div className="contact row mb-4">
              <div className="contact-icon col-lg-3 col-3">
                <div className="border py-3 mb-2 text-center border rounded text-secondary">
                  <i className="bx bx-laptop display-6" />
                </div>
              </div>
              <ul className="contact-info list-unstyled col-lg-9 col-9 light-300">
                <li className="h5 mb-0">Main Office Address</li>
                <li className="text-muted">40 Scotts Road, Environment Building</li>
                <li className="text-muted">Singapore 228231</li>
              </ul>
            </div>
            <div className="contact row mb-4">
              <div className="contact-icon col-lg-3 col-3">
                <div className="border py-3 mb-2 text-center border rounded text-secondary">
                  <i className="bx bx-money display-6" />
                </div>
              </div>
              <ul className="contact-info list-unstyled col-lg-9 col-9 light-300">
                <li className="h5 mb-0">Operating Hours:</li>
                <li className="text-muted">Mon to Thu: 8.30am to 6.00pm</li>
                <li className="text-muted">Fri: 8.30am to 5.30pm</li>
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
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="date"
                    className={
                      errors.dateOfBirth && touched.dateOfBirth
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="dateOfBirth"
                    placeholder="dateOfBirth"
                    value={values.dateOfBirth}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="dob light-300">Date of Birth</label>
                  {errors.dateOfBirth && touched.dateOfBirth && (
                    <em className="text-error">{errors.dateOfBirth}</em>
                  )}
                </div>
              </div>
              {/* End Input Date of Birth */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="number"
                    className={
                      errors.phoneNumber && touched.phoneNumber
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="phoneNumber"
                    placeholder="Mobile Number"
                    value={values.phoneNumber}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="phone light-300">Mobile Number</label>
                  {errors.phoneNumber && touched.phoneNumber && (
                    <em className="text-error">{errors.phoneNumber}</em>
                  )}
                </div>
              </div>
              {/* End Input phoneNumber */}
              <div className="col-lg-2 mb-4">
                <div className="form-floating">
                  <input
                    type="number"
                    className={
                      errors.floor && touched.floor
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="floor"
                    placeholder="floor"
                    value={values.floor}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="phone light-300">Floor</label>
                  {errors.floor && touched.floor && (
                    <em className="text-error">{errors.floor}</em>
                  )}
                </div>
              </div>
              {/* End Input floor */}
              <div className="col-lg-2 mb-4">
                <div className="form-floating">
                  <input
                    type="number"
                    className={
                      errors.unit && touched.unit
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="unit"
                    placeholder="unit"
                    value={values.unit}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="phone light-300">Unit</label>
                  {errors.unit && touched.unit && (
                    <em className="text-error">{errors.unit}</em>
                  )}
                </div>
              </div>
              {/* End Input unit */}
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
                    onChange={e => {handleChange(e); resetAddress(e);}}
                    onBlur={handleBlur}
                  />
                  <label htmlFor="postcode light-300">Postal Code</label>
                  {errors.postcode && touched.postcode && (
                    <em className="text-error">{errors.postcode}</em>
                  )}
                  <Link className="spanLink" onClick={loadAddress}>
                    Load Address
                  </Link>
                </div>
              </div>
              {/* End Input Postal code */}
              <div className="col-lg-4 mb-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className={
                      errors.address && touched.address
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="address"
                    placeholder="Address"
                    value={addressLabel}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled
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
                  <select
                    className={
                      errors.role && touched.role
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="role"
                    placeholder="Role"
                    value={values.role}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  >
                    <option value="" label="Select a Role" />
                    <option value={ROLES.Collector} label="Collector" />
                    <option value={ROLES.Admin} label="Admin" />
                  </select>
                  <label htmlFor="address light-300">Role</label>
                  {errors.address && touched.address && (
                    <em className="text-error">{errors.address}</em>
                  )}
                </div>
              </div>
              {/* End Input Role */}
              <div className="col-md-12 col-12 m-auto text-end">
                <em className="text-error px-3">{errMsg}</em>
                <em className="text-success">{successMsg}</em>
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

export default RegisterUsers;
