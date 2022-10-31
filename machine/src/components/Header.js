import React from "react";
import { Link, useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";

/*
    Purpose:
        - Allow users to easily access pages.

    Restriction:
        - Only appropriate pages will be displayed to each role.

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

const Header = () => {
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
            <nav
                id="main_nav"
                className="navbar navbar-expand-lg navbar-light bg-white shadow"
            >
                <div className="container d-flex justify-content-between align-items-center">
                    <Link className="navbar-brand h1" onClick={signOut}>
                        <i className="bx bx-buildings bx-sm text-dark"></i>
                        <span className="text-dark h4">BATTERY</span>{" "}
                        <span className="text-primary h4">L!fe</span>
                    </Link>

                    <Link className="nav-link" onClick={signOut}>
                        <i className="bx bx-log-out bx-sm text-primary"></i>
                    </Link>
                </div>
            </nav>
        </>
    );
};

export default Header;
