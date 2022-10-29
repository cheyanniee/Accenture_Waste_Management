import { React, useEffect, useState } from "react";

const Information = () => {
    return (
        <div className="banner-wrapper bg-light">
            <div
            id="index_banner"
            className="banner-vertical-center-index container-fluid pt-5"
            >
                <div
                id="carouselExampleIndicators"
                className="carousel slide"
                data-bs-ride="carousel"
                >
                    <div className="carousel-inner">
                        <div className="carousel-item active">
                            <div className="py-5 row d-flex align-items-center">
                                <div className="banner-content col-lg-8 col-8 offset-2 m-lg-auto text-left py-5 pb-5">
                                    <h1 className="banner-heading h1 text-secondary display-3 mb-0 pb-5 mx-0 px-0 light-300 typo-space-line">
                                        List of Affected Areas
                                    </h1>
                                </div>
                            </div>
                        </div>
                        <div className="carousel-item">
                            <div className="py-5 row d-flex align-items-center">
                                <div className="banner-content col-lg-8 col-8 offset-2 m-lg-auto text-left py-5 pb-5">
                                    <h1 className="banner-heading h1 text-secondary display-3 mb-0 pb-5 mx-0 px-0 light-300 typo-space-line">
                                        Map of Affected Areas
                                    </h1>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a
                        className="carousel-control-prev text-decoration-none"
                        href="#carouselExampleIndicators"
                        role="button"
                        data-bs-slide="prev"
                    >
                        <i className="bx bx-chevron-left"></i>
                        <span className="visually-hidden">Previous</span>
                    </a>
                    <a
                        className="carousel-control-next text-decoration-none"
                        href="#carouselExampleIndicators"
                        role="button"
                        data-bs-slide="next"
                    >
                        <i className="bx bx-chevron-right"></i>
                        <span className="visually-hidden">Next</span>
                    </a>
                </div>
            </div>
        </div>
    );
};

export default Information;