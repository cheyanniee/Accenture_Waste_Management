import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { TRANSACTION_ENTRY_ENDPOINTS, TRANSACTION_ENDPOINTS, ACTION_TYPES } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Display transaction for user to confirm or reject.
        - Storage is updated if user confirms

    Restriction:
        - Only those with ROLES.User will be able to access this page.

    Endpoints:
        - TRANSACTION_ENTRY_ENDPOINTS.GetByID
        - TRANSACTION_ENDPOINTS.Confirm
        - TRANSACTION_ENDPOINTS.Reject

    Author:
        - Cheyanne Lim
*/

const ConfirmExchange = () => {
    const { auth, transaction, machineID } = useAuth();
    const [data, setData] = useState();
    const navigate = useNavigate();

    const getTransaction = async () => {
        const params = {
            "transactionId" : transaction
        }

        try {
            const response = await axios.post(
                TRANSACTION_ENTRY_ENDPOINTS.GetByID,
                params,
                config({ token: auth.token })
            );
            console.log("Transaction: ", response?.data);
            setData(response?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
        }
    }

    useEffect(() => {
        getTransaction();
    }, [])

    const yes = async () => {
        const params1 = {
            "transactionId" : transaction,
            "machineId": machineID,
            "chooseType": ACTION_TYPES.Exchange
        }

        try {
            console.log("Confirming Transaction: ", params1);

            const res1 = await axios.post(
                TRANSACTION_ENDPOINTS.Confirm,
                params1,
                config({ token: auth.token })
            );

            console.log("Confirmed Transaction: ", res1?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }

        navigate("/collectExchange", { replace: true });
    }

    const no = async () => {
        const params = {
            "transactionId" : transaction
        }

        try {
            const response = await axios.post(
                TRANSACTION_ENDPOINTS.Reject,
                params,
                config({ token: auth.token })
            );
            console.log("Rejected: ", response?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }
        navigate("/logout", { replace: true });
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
                                Please check transaction details:
                            </h3>
                        </div>

                        <br />

                        <div className="row align-items-start ">
                            <div className=" col-lg-10 m-auto text-left justify-content-center">
                                <div className="row align-items-start text-primary fs-4 mb-3">
                                    <div className="col-2">Qty</div>
                                    <div className="col-3">Type</div>
                                    <div className="col-3">Points/Qty</div>
                                    <div className="col-4">Points Gained</div>
                                </div>
                                {data?.map((trans) => {
                                    const { id, quantity, batteryModel } = trans;
                                    return (
                                        <div key={id} className="row align-items-start mb-2">
                                            <div className="col-2">{quantity}</div>
                                            <div className="col-3">{batteryModel?.type}</div>
                                            <div className="col-3">{batteryModel?.recyclePoint}</div>
                                            <div className="col-4">
                                                {((batteryModel?.recyclePoint) && (quantity))
                                                    ? ((batteryModel?.recyclePoint) * (quantity))
                                                    : "Error: Pls Cancel!"
                                                }
                                            </div>
                                        </div>
                                    );
                                })}
                        </div>
                        </div>

                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={yes}
                            >
                                Confirm
                            </button>
                            <br />
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={no}
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default ConfirmExchange;