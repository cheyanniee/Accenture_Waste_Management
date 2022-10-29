import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Locations = () => {


  return (
    <>
      <section className="service-wrapper py-3">
        <div className="container-fluid pb-3">
          <div className="row">
            <h2 className="h2 text-center col-12 py-5 semi-bold-600">
              Locations
            </h2>
            <div className="service-header col-2 col-lg-3 text-end light-300">
              <i className="bx bx-gift h3 mt-1" />
            </div>
            <div className="service-heading col-10 col-lg-9 text-start float-end light-300">
              <h2 className="h3 pb-4 typo-space-line">
                Battery Recycling Machines
              </h2>
            </div>
          </div>
          <p className="service-footer col-10 offset-2 col-lg-9 offset-lg-3 text-start pb-3 text-muted px-2">
            List of all recycling machines available around the island!
          </p>
        </div>
      </section>
    </>
  );
};

export default Locations;
