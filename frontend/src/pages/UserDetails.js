import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";
import axios from "axios";

import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { default as myAxios, config } from "../api/axios";
import { PEOPLE_ENDPOINTS } from "../helper/Constant";
import { INITIAL_REGISTER_FORM_VALUES, userDetailsSchema } from "../schemas";

const UserDetails = () => {
  const { auth, setAuth } = useAuth();
  const [errMsg, setErrMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [addressLabel, setAddressLabel] = useState("");
  const [unitFloor, setUnitFloor] = useState("");
  const [unitUnit, setUnitUnit] = useState("");

  const onSubmit = async (values, actions) => {
    setErrMsg("");
    setSuccessMsg("");

    if (values.floor > 0 && values.unit > 0) {
        values.unitNumber = values.floor + "-" + values.unit;
    } else if (values.floor > 0) {
        values.unitNumber = values.floor + "-" + unitUnit;
    } else if (values.unit > 0) {
        values.unitNumber =  unitFloor + "-" + values.unit;
    }

    const updatedFieldKeys = Object.keys(values).filter(
      (key) => values[key] !== ""
    );

    if (updatedFieldKeys.length === 0) return;

    if (values.postcode.toString().length>0 && values.address.length === 0) {
        setErrMsg("Error: Address Doesn't Match Postal Code");
        return;
    }

    if (values.password.length>0 && values.confirm_password.length === 0) {
        setErrMsg("Error: Password doesn't match");
        return;
    }

    console.log(updatedFieldKeys);
    console.log(values);
    values = {
      ...values,
      dob: values.dob ? moment(values.dob).format("DD/MM/YYYY") : "",
    };
    const params = updatedFieldKeys.reduce((acc, key) => {
      return { ...acc, [key]: values[key] };
    }, {});
    console.log("Params: ", params);
    try {
      const response = await myAxios.post(
        PEOPLE_ENDPOINTS.UpdateDetails,
        params,
        config({ token: auth.token })
      );
      console.log(response.data);
      setAuth((prev) => {
        return { ...prev, ...params };
      });
      setSuccessMsg("Update success!");

      if (values.floor > 0) {
        setUnitFloor(values.floor);
      }

      if (values.unit > 0) {
        setUnitUnit(values.unit);
      }
    } catch (error) {
      setErrMsg("Error: " + error.response.data.message);
      console.log(error);
    }
    actions.resetForm();
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

  const {
    values,
    errors,
    touched,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
  } = useFormik({
    initialValues: INITIAL_REGISTER_FORM_VALUES,
    validationSchema: userDetailsSchema,
    onSubmit,
  });

  useEffect(() => {
    setErrMsg("");
    setSuccessMsg("");
    setUnitFloor(auth?.unitNumber.split("-")[0]);
    setUnitUnit(auth?.unitNumber.split("-")[1]);

  }, []);

  return (
    <>
      <Header />
      <section className="container py-5">
        <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3">
          Your Profile Page
        </h1>
        <h2 className="col-12 col-xl-8 h4 text-left regular-400">
          {/* For General Public */}
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
                <div className="">
                  <label htmlFor="firstName light-300">First Name</label>
                  <input
                    type="text"
                    className={
                      errors.firstName && touched.firstName
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="firstName"
                    placeholder={auth?.firstName}
                    value={values.firstName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />

                  {errors.firstName && touched.firstName && (
                    <em className="text-error">{errors.firstName}</em>
                  )}
                </div>
              </div>
              {/* End Input First Name */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="last-name light-300">Last Name</label>
                  <input
                    type="text"
                    className={
                      errors.lastName && touched.lastName
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="lastName"
                    placeholder={auth?.lastName}
                    value={values.lastName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />

                  {errors.lastName && touched.lastName && (
                    <em className="text-error">{errors.lastName}</em>
                  )}
                </div>
              </div>
              {/* End Input Last_name */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="last-name light-300">NRIC/FIN/Passport</label>
                  <input
                    type="text"
                    className={
                      errors.officialId && touched.officialId
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="officialId"
                    placeholder={auth?.officialId}
                    value={values.officialId}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.officialId && touched.officialId && (
                    <em className="text-error">{errors.officialId}</em>
                  )}
                </div>
              </div>
              {/* End Input officialId */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="password light-300">Password</label>
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
                  {errors.password && touched.password && (
                    <em className="text-error">{errors.password}</em>
                  )}
                </div>
              </div>
              {/* End Input password */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="confirm_password light-300">
                    Confirm Password
                  </label>
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
                  {errors.confirm_password && touched.confirm_password && (
                    <em className="text-error">{errors.confirm_password}</em>
                  )}
                </div>
              </div>
              {/* End Input Confirm password */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="email light-300">Email</label>
                  <input
                    type="text"
                    className={
                      errors.email && touched.email
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="email"
                    placeholder={auth?.email}
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.email && touched.email && (
                    <em className="text-error">{errors.email}</em>
                  )}
                </div>
              </div>
              {/* End Input Email */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="dob light-300">Date of Birth</label>
                  <input
                    type="text"
                    className={
                      errors.dateOfBirth && touched.dateOfBirth
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="dateOfBirth"
                    placeholder={auth?.dateOfBirth}
                    value={values.dateOfBirth}
                    onFocus={(e) => (e.target.type = "date")}
                    onChange={handleChange}
                    onBlur={(e) => (e.target.type = "text")}
                  />
                  {errors.dateOfBirth && touched.dateOfBirth && (
                    <em className="text-error">{errors.dateOfBirth}</em>
                  )}
                </div>
              </div>
              {/* End Input Date of Birth */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="phone light-300">Mobile Number</label>
                  <input
                    type="number"
                    className={
                      errors.phoneNumber && touched.phoneNumber
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="phone"
                    placeholder={auth?.phoneNumber}
                    value={values.phoneNumber}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.phoneNumber && touched.phoneNumber && (
                    <em className="text-error">{errors.phoneNumber}</em>
                  )}
                </div>
              </div>
              {/* End Input Mobile */}
              <div className="col-lg-2 mb-4">
                <div className="">
                  <label htmlFor="phone light-300">Floor</label>
                  <input
                    type="number"
                    className={
                      errors.floor && touched.floor
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="floor"
                    placeholder={unitFloor}
                    value={values.floor}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.floor && touched.floor && (
                    <em className="text-error">{errors.floor}</em>
                  )}
                </div>
              </div>
              {/* End Input floor */}
              <div className="col-lg-2 mb-4">
                <div className="">
                  <label htmlFor="phone light-300">Unit</label>
                  <input
                    type="number"
                    className={
                      errors.unit && touched.unit
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="unit"
                    placeholder={unitUnit}
                    value={values.unit}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.unit && touched.unit && (
                    <em className="text-error">{errors.unit}</em>
                  )}
                </div>
              </div>
              {/* End Input unit */}
              <div className="col-lg-4 mb-4">
                <div className="">
                  <label htmlFor="postcode light-300">Postal Code</label>
                  <input
                    type="number"
                    className={
                      errors.postcode && touched.postcode
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="postcode"
                    placeholder={auth?.locationModel.postcode}
                    value={values.postcode}
                    onChange={e => {handleChange(e); resetAddress(e);}}
                    onBlur={handleBlur}
                  />
                  {errors.postcode && touched.postcode && (
                    <em className="text-error">{errors.postcode}</em>
                  )}
                  <Link className="spanLink" onClick={loadAddress}>
                    Load Address
                  </Link>
                </div>
              </div>
              {/* End Input Postal code */}
              <div className="col-8">
                <div className="">
                  <label htmlFor="address light-300">Address</label>
                  <input
                    type="text"
                    className={
                      errors.address && touched.address
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="address"
                    placeholder={auth?.locationModel.address}
                    value={addressLabel}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled
                  />
                  {errors.address && touched.address && (
                    <em className="text-error">{errors.address}</em>
                  )}
                </div>
              </div>
              {/* End Input Address */}
              <div className="col-md-12 col-12 m-auto text-end">
                <em className="text-error px-3">{errMsg}</em>
                <em className="text-success px-3">{successMsg}</em>
                <button
                  disabled={isSubmitting}
                  type="submit"
                  className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                >
                  Update Details!
                </button>
              </div>
            </form>
          </div>
          {/* End Contact Form */}
        </div>
      </section>
      <Footer />
    </>
  );
};

export default UserDetails;
