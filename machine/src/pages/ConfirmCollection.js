import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { MACHINE_ENDPOINTS, TASK_ENDPOINTS } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - For collectors to open and close this machine for collection
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
    const [ data, setData ] = useState([]);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await axios.get(
                    TASK_ENDPOINTS.GetByID,
                    config({ token: auth.token })
                );

                setData(
                    response?.data?.filter((task) => ((task.machine.id === machineID) && (!task.collectedTime)))
                );

                console.log("Unfiltered Tasks: ", response?.data);
                console.log("Tasks: ", data);
            } catch (error) {
                console.log("Error: ", error);
                navigate("/", { replace: true });
                return;
            }
        }

        fetchTasks();
    }, [auth?.token])

    const done = async () => {
        const params1 = {
            "machineId": machineID,
            "currentLoad": 0
        }

        try {
            console.log("Updating Current Load: ", params1);

            const res1 = await axios.post(
                MACHINE_ENDPOINTS.UpdateCurrentLoad,
                params1,
                config({ token: auth.token })
            );

            console.log("Updated Current Load: ", res1?.data);

            console.log("Updating Collection Time: ", data[0].id);

            const res2 = await axios.get(
                TASK_ENDPOINTS.Collected + data[0].id,
                config({ token: auth.token })
            );

            console.log("Updated Collection Time: ", res2?.data);
        } catch (error) {
            console.log("Error: ", error);
            navigate("/", { replace: true });
            return;
        }

        navigate("/logout", { replace: true });
    }

    const noTask = () => {
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

                        {(data.length > 0) && (
                            <>
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
                            </>
                        )}

                        {!(data.length > 0) && (
                            <>
                                <div className="row">
                                    <h3>
                                        You have no outstanding collections from this vending machine!
                                    </h3>
                                </div>

                                <div className="h2 py-3">
                                    <button
                                        className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                        onClick={noTask}
                                    >
                                        Proceed
                                    </button>
                                </div>
                            </>
                        )}


                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default ConfirmCollection;