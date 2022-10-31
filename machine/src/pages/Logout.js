import React from "react";
import { useNavigate } from "react-router-dom";

import useLogout from "../hooks/useLogout";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";

/*
    Purpose:
        - Logout after all actions are completed

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const Logout = () => {
    const navigate = useNavigate();
    const logout = useLogout();
    const signOut = async () => {
        await logout();
        navigate("/login", {
            state: { message: "You have logged out successfully" },
        });
    };

    return (
        <>
            <Header />
            <Title />

            <section className="container py-5">
                <div className="row justify-content-center">
                    <div className="col-lg-4 border px-3 rounded-3">
                        <div className="row">
                            <h1 className="col-12 col-xl-8 h2 text-left text-primary pt-3 mb-3">
                                Have a good day!
                            </h1>
                        </div>
                        <br />
                        <div className="row">
                            <h3>
                                Please logout:
                            </h3>
                        </div>

                        <div className="h2 py-3">
                            <button
                                className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                                onClick={signOut}
                            >
                                Logout
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <Footer />
        </>
    );
};

export default Logout;