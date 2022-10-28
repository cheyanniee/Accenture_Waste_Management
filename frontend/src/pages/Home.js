import React from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Information from "../components/Information";
import Locations from "../components/Locations";

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
