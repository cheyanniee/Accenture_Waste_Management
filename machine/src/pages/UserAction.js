{/*
    Purpose:
        Allow user to choose an action after logging in.

    Restriction:
        Only those with ROLES.User will be able to access this page.

    Endpoints:
        NIL

    Author:
        Cheyanne Lim
*/}

import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

const UserAction = () => {
    const { auth } = useAuth();
    const navigate = useNavigate();

    const recycle = () => {
        navigate("/insertRecycling", { replace: true });
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
                                Welcome Back {auth?.firstName}!
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