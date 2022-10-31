import React from "react";

import Header from "../components/Header";
import Footer from "../components/Footer";

import Information from "../components/Information";
import Locations from "../components/Locations";

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
            <Information />
            <Locations />
            <Footer />
        </>
    );
};

export default Home;
