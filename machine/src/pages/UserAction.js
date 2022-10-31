import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { TRANSACTION_ENDPOINTS } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Allow user to choose an action after logging in.

    Restriction:
        - Only those with ROLES.User will be able to access this page.

    Endpoints:
        - TRANSACTION_ENDPOINTS.StartR
        - TRANSACTION_ENDPOINTS.StartE

    Author:
        - Cheyanne Lim
*/

const UserAction = () => {
    const { auth, setTransaction } = useAuth();
    const navigate = useNavigate();

    const recycle = async () => {
        const params = {};

        try {
            const response = await axios.post(
                TRANSACTION_ENDPOINTS.StartR,
                params,
                config({ token: auth.token })
            );
            setTransaction(response?.data?.id);
            console.log("TransactionID: ", response?.data?.id);
            navigate("/insertRecycling", { replace: true });
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
        }
    }

    const exchange = () => {
        navigate("/inputExchange", { replace: true });
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
                                Please select an option:
                            </h3>
                        </div>


                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={recycle}
                            >
                                Recycle Old Batteries
                            </button>
                            <br />
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={exchange}
                            >
                                Exchange Points for New Batteries
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default UserAction;