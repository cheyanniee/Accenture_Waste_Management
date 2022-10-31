import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { MACHINE_ENDPOINTS } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Collector marks collection complete
        - Machine is closed
        - Current load is updated (Hardcoded for demo purposes ONLY)

    Restriction:
        - Only those with ROLES.Collector will be able to access this page.

    Endpoints:
        - MACHINE_ENDPOINTS.UpdateCurrentLoad

    Author:
        - Cheyanne Lim
*/

const ConfirmCollection = () => {
    const { auth, machineID } = useAuth();
    const navigate = useNavigate();

    const done = async () => {
        const params = {
            "machineId": machineID,
            "currentLoad": 0
        }

        try {
            console.log("Updating Current Load: ", params);

            const res = await axios.post(
                MACHINE_ENDPOINTS.UpdateCurrentLoad,
                params,
                config({ token: auth.token })
            );

            console.log("Updated Current Load: ", res?.data);
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
                                Please Collect All Batteries!
                            </h3>
                        </div>

                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={done}
                            >
                                Collection Complete
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default ConfirmCollection;