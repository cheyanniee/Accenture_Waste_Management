import React from "react";
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="bg-secondary pt-4">
      <div className="container">
        <div className="row py-4">
          <div className="col-lg-3 col-12 align-left">
            <Link className="navbar-brand" to="index.html">
              <i className="bx bx-buildings bx-sm text-light" />
              <span className="text-light h5">GECO</span>{" "}
              <span className="text-light h5 semi-bold-600">Buzz</span>
            </Link>
            <p className="text-light my-lg-4 my-2">
              Our company aims to help everyone stay as safe as possible through this tough pandemic.
            </p>
          </div>
          <div className="col-lg-3 col-md-4 my-sm-0 mt-4">
              <h3 className="h4 pb-lg-3 text-light light-300">Our Company</h3>
              <ul className="list-unstyled text-light light-300">
                <li className="pb-2">
                  <i className="bx-fw bx bxs-chevron-right bx-xs" />
                  <Link className="text-decoration-none text-light" to="/">
                    Home
                  </Link>
                </li>
                <li className="pb-2">
                  <i className="bx-fw bx bxs-chevron-right bx-xs" />
                  <Link
                    className="text-decoration-none text-light py-1"
                    to="/clinics"
                  >
                    Clinics
                  </Link>
                </li>

              </ul>
            </div>
          <div className="col-lg-3 col-md-4 my-sm-0 mt-4">
            <h2 className="h4 pb-lg-3 text-light light-300">Frontend Team</h2>
            <ul className="list-unstyled text-light light-300">
              <li className="pb-2">
                <i className="bx-fw bx bx-mail-send bx-xs" />
                <Link
                  className="text-decoration-none text-light py-1"
                  to="mailto:info@company.com"
                >
                  cheyanne.lim@geco.asia
                </Link>
              </li>
              <li className="pb-2">
                <i className="bx-fw bx bx-mail-send bx-xs" />
                <Link
                  className="text-decoration-none text-light py-1"
                  to="mailto:info@company.com"
                >
                  alex.lim@geco.asia
                </Link>
              </li>
            </ul>
          </div>
          <div className="col-lg-3 col-md-4 my-sm-0 mt-4">
              <h2 className="h4 pb-lg-3 text-light light-300">Backend Team</h2>
              <ul className="list-unstyled text-light light-300">
                <li className="pb-2">
                  <i className="bx-fw bx bx-mail-send bx-xs" />
                  <Link
                    className="text-decoration-none text-light py-1"
                    to="mailto:info@company.com"
                  >
                    liu.fang@geco.asia
                  </Link>
                </li>
                <li className="pb-2">
                  <i className="bx-fw bx bx-mail-send bx-xs" />
                  <Link
                    className="text-decoration-none text-light py-1"
                    to="mailto:info@company.com"
                  >
                    xuhong.lew@geco.asia
                  </Link>
                </li>
              </ul>
            </div>
        </div>
      </div>
      <div className="w-100 bg-primary py-3">
        <div className="container">
          <div className="row pt-2">
            <div className="col-lg-6 col-sm-12">
              <p className="text-lg-start text-center text-light light-300">
                © Copyright 2022 GECO Buzz Company. All Rights Reserved.
              </p>
            </div>
            <div className="col-lg-6 col-sm-12">
              <p className="text-lg-end text-center text-light light-300">
                Cheyanne, Alex, Liu Fang, Xu Hong
              </p>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
