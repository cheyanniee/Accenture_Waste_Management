import React from "react";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Title from "../components/Title";
import Login from "../components/Login";

/*
    Purpose:
        - Home page for users.

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const Home = () => {
    return (
        <>
            <Header />
            <Title />
            <Login />
            <Footer />
        </>
    );
};

export default Home;