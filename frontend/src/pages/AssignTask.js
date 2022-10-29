import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import moment from "moment";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { PEOPLE_ENDPOINTS, ROLES } from "../helper/Constant";

const AssignTask = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();

  const [machine, setMachine] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          PEOPLE_ENDPOINTS.GetAll,
          config({ token: auth.token })
        );
        setData(
          response?.data?.filter((collector) => collector.role === ROLES.Collector)
        );
        setApiSearch(response?.data);
        console.log("Users: ", response?.data);
      } catch (error) {
        console.log("Error: ", error);
      }
    };
    fetchData();
  }, [auth.token]);

  const handleFilter = (e) => {
    setMessage("");
    setErrMsg("");
    const inputText = e.target.value;
    const filterResult = apiSearch.filter((item) => {
      return item.firstName
        ? (item.firstName.toLowerCase().includes(inputText.toLowerCase()) ||
            item.lastName.toLowerCase().includes(inputText.toLowerCase())) &&
            item.role.includes(ROLES.Collector)
        : false;
    });
    filterResult.length > 0
      ? setData(filterResult)
      : setData([{ officialId: "No result for " + inputText }]);
  };

  const handleUpdate = async (id) => {

    console.log("TimeStamp: ", moment().format("DD-MM-YYYY HH:mm:ss"));


  };

  return (
    <>
      <Header />
      <section className="container-lg py-5">
        <div className="row">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <h2 className="worksingle-heading h3 pb-5 light-300 typo-space-line">
              Assign Task To Collectors
            </h2>
          </div>
        </div>
        <div className="row mb-4">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <form className="contact-form row">
              <div className="col-lg-4">
                <div className="form-floating">
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    onChange={handleFilter}
                  />
                  <label htmlFor="officialId light-300">
                    Collector's Name
                  </label>
                  {message && <em className="text-success px-2">{message}</em>}
                  {errMsg && <em className="text-danger px-2">{errMsg}</em>}
                </div>
              </div>
            </form>
          </div>
        </div>
        <div className="row align-items-start ">
          <div className=" col-lg-10 m-auto text-left justify-content-center">
            <div className="row align-items-start text-primary fs-4 mb-3">
              <div className="col-2">Official ID</div>
              <div className="col-2">Full Name</div>
              <div className="col-2">Region</div>
              <div className="col-2">Machine</div>
              <div className="col-2">Assign</div>
            </div>
            {data.map((patient) => {
              const { id, firstName, lastName, officialId, locationModel } = patient;
              return (
                <div key={id} className="row align-items-start mb-2">
                  <div className="col-2">{officialId}</div>
                  <div className="col-2">
                    {firstName} {lastName}
                  </div>
                  <div className="col-2">
                    {locationModel.districtModel.region}
                  </div>
                  <div className="col-2">
                    {firstName && (
                      <select
                        className="form-select"
                        onChange={(e) => setMachine(e.target.value)}
                      >
                        <option>Select Machine</option>

                      </select>
                    )}
                  </div>
                  <div className="col-2">
                    {firstName && (
                      <Link>
                        <i
                          className="bx bx-pencil bx-sm"
                          onClick={() => handleUpdate(id)}
                        />
                      </Link>
                    )}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </section>
      <Footer />
    </>
  );
};

export default AssignTask;
