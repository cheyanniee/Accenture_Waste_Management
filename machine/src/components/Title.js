import React from "react";
import { Link, useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";

const Title = () => {
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
      <section className="service-wrapper py-3">
        <Link onClick={signOut}>
          <span className="h2 text-center col-12 py-5 text-dark h4">BATTERY</span>{" "}
          <span className="h2 text-center col-12 py-5 text-primary h4">L!fe</span>
        </Link>
      </section>
    </>
  );
};

export default Title;
