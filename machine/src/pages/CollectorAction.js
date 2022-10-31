import React from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../hooks/useAuth";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Collector to open machine by pressing start.

    Restriction:
        - Only those with ROLES.Collector will be able to access this page.

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const CollectorAction = () => {
    const { auth } = useAuth();
    const navigate = useNavigate();

    const done = () => {
        console.log("Done Collecting Batteries");
        navigate("/confirmCollection", { replace: true });
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
                                Please Click to Begin Collection!
                            </h3>
                        </div>

                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={done}
                            >
                                Start Collection
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default CollectorAction;