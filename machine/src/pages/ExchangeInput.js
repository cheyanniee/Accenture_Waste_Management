import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { TRANSACTION_ENTRY_ENDPOINTS, BATTERY_EXCHANGE_TYPES } from "../helper/Constant";
import { INITIAL_EXCHANGE_FORM_VALUES, exchangeSchema } from "../schemas";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Users can exchange points for new batteries

    Restriction:
        - Only those with ROLES.User will be able to access this page.

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const ExchangeInput = () => {
    const { auth, transaction } = useAuth();
    const navigate = useNavigate();
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState();

    const onSubmit = async (values, actions)  => {
        try {
            console.log("Making Transaction Entry: ", values);

            const res1 = await axios.post(
                TRANSACTION_ENTRY_ENDPOINTS.Create,
                values,
                config({ token: auth.token })
            );
            console.log("Transaction Entry: ", res1?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }

        resetForm();
        setErrMsg("");
        setSuccessMsg("");

        navigate("/confirmExchange", { replace: true });
    }

    const {
          values,
          errors,
          touched,
          isSubmitting,
          handleChange,
          handleBlur,
          handleSubmit,
          resetForm,
        } = useFormik({
          initialValues: INITIAL_EXCHANGE_FORM_VALUES,
          validationSchema: exchangeSchema,
          onSubmit,
        });

    useEffect(() => {
        values.transactionId = transaction;
    }, [])

    return (
        <>
            <Header />
            <Title />

            <section className="container py-5">
                <div className="row justify-content-center">
                    <div className="col-lg-4 border px-3 rounded-3">
                        <div className="row">
                            <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3 mb-3">
                                Hi {auth?.firstName}!
                            </h1>
                        </div>
                        <br />
                        <div className="row">
                            <h3>
                                Please Input Desired Batteries!
                            </h3>
                        </div>

                        <br /><br /><br />

                        <form className="contact-form row" onSubmit={handleSubmit}>
                            <div className="row justify-content-center">
                                <div className="col-4 mb-4">
                                    <div className="form-floating">
                                        <input
                                            type="number"
                                            className={
                                            errors.quantity && touched.quantity
                                                ? "form-control form-control-lg-error light-300-error"
                                                : "form-control form-control-lg light-300"
                                            }
                                            id="quantity"
                                            placeholder="Qty"
                                            value={values.quantity}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                        <label htmlFor="firstName light-300">Quantity</label>
                                        {errors.quantity && touched.quantity && (
                                            <em className="text-error">{errors.quantity}</em>
                                        )}
                                    </div>
                                </div>

                                <div className="col-4 mb-4">
                                    <div className="form-floating">
                                        <select
                                            className="form-control form-control-lg light-300"
                                            id="batteryType"
                                            placeholder="Type"
                                            value={values.batteryType}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        >
                                            <option value="" label="Select">Select</option>
                                            {BATTERY_EXCHANGE_TYPES.map((type) => {
                                                return (
                                                    <option value={type} label={type}>
                                                        {type}
                                                    </option>
                                                );
                                            })}
                                        </select>
                                        <label htmlFor="firstName light-300">Type</label>
                                        {errors.batteryType && touched.batteryType && (
                                            <em className="text-error">{errors.batteryType}</em>
                                        )}
                                    </div>
                                </div>
                            </div>

                            <div className="row justify-content-center">
                                <div className="col-2 mb-2">
                                    <em className="text-error px-3">{errMsg}</em>
                                    <em className="text-success px-3">{successMsg}</em>
                                    <div className="h2 py-3">
                                        <button
                                            disabled={isSubmitting}
                                            type="submit"
                                            className="btn btn-secondary rounded-pill text-light light-300"
                                        >
                                            Submit
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default ExchangeInput;