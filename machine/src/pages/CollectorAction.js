import React from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";

const CollectorAction = () => {
  return (
    <>
      <Header />

      <section className="service-wrapper py-3">
        <div className="container-fluid pb-3">
          <div className="row">
            <h2 className="h2 text-center col-12 py-5 semi-bold-600">
              Battery Life
            </h2>
          </div>

          <h3>
            User Action
          </h3>
        </div>
      </section>

      <Footer />
    </>
  );
};

export default CollectorAction;