import React from "react";

/*
    Purpose:
        - Loading Icon

    Restriction:
        - NIL

    Endpoints:
        - Nil

    Author:
        - Alex Lim
*/

const Loader = () => {
    return (
        <>
            <section className="container py-5">
                <div className="row justify-content-center">
                    <div className="col-lg-4 px-3 rounded-3 spinner"></div>
                </div>
            </section>
        </>
    );
};

export default Loader;
