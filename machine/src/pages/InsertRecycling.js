import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { TRANSACTION_ENTRY_ENDPOINTS } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - User to indicate when done inserting batteries for machine to close tray.

    Restriction:
        - Only those with ROLES.User will be able to access this page.

    Endpoints:
        - TRANSACTION_ENTRY_ENDPOINTS.Create (For Demo Purposes ONLY)

    Author:
        - Cheyanne Lim
*/

const InsertRecycling = () => {
    const { auth, transaction } = useAuth();
    const navigate = useNavigate();

    const done = async () => {
        const params1 = {
            "transactionId" : transaction,
            "batteryType": "AA",
            "quantity": 15
        }

        console.log("Inserting Batteries 1: ", params1);

        try {
            const response = await axios.post(
                TRANSACTION_ENTRY_ENDPOINTS.Create,
                params1,
                config({ token: auth.token })
            );
            console.log("Transaction Entry: ", response?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }

        const params2 = {
            "transactionId" : transaction,
            "batteryType": "AAA",
            "quantity": 10
        }

        console.log("Inserting Batteries 2: ", params2);

        try {
            const response = await axios.post(
                TRANSACTION_ENTRY_ENDPOINTS.Create,
                params2,
                config({ token: auth.token })
            );
            console.log("Transaction Entry: ", response?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }

        navigate("/confirmRecycling", { replace: true });
    }

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
                                Please insert batteries for recycling!
                            </h3>
                        </div>

                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={done}
                            >
                                Done Inserting Batteries
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default InsertRecycling;