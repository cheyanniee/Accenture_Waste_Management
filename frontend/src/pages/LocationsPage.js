import React from "react";

import Header from "../components/Header";
import Footer from "../components/Footer";
import Locations from "../components/Locations";

/*
    Purpose:
        - Display Vending Machines with their Location and Details

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const LocationsPage = () => {
    return (
        <>
            <Header />
            <Locations />
            <Footer />
        </>
    );
};

export default LocationsPage;
