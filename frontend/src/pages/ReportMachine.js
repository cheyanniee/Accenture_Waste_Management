import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { MACHINE_ENDPOINTS, FAULTY_MACHINE } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";

const ReportMachine = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();

  const fetchMachines = async () => {
      try {
        const response = await axios.get(
          MACHINE_ENDPOINTS.GetAll,
          config({ token: auth.token })
        );
        response?.data.sort((a,b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
        setApiSearch(response?.data);
        setData(response?.data);
        console.log("Machines: ", response?.data);
      } catch (error) {
        console.log("Error: ", error);
      }
    };

  useEffect(() => {
    fetchMachines();
  }, [auth.token]);

  const handleFilter = (e) => {
    setMessage("");
    setErrMsg("");
    const inputText = e.target.value;
    const filterResult = apiSearch.filter((machine) => {
      return machine.name
        ? (machine.name.toLowerCase().includes(inputText.toLowerCase()))
        : false;
    });
    filterResult.length > 0
      ? setData(filterResult)
      : setData([{ id: "No result for " + inputText }]);
  };

  const handleUpdate = async (machine) => {
    const params = {
      machineId: machine.id,
      status: FAULTY_MACHINE,
    };

    console.log("Params: ", params);

    try {
      const response = await axios.post(
        MACHINE_ENDPOINTS.Update,
        params,
        config({ token: auth.token })
      );
      console.log(response.data);
      setMessage("Successful!");
    } catch (error) {
      console.log("Error: ", error.response);
      setErrMsg(error.response.data.message);
    }

    fetchMachines();
  }

  return (
    <>
      <Header />
      <section className="container-lg py-5">
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
                <div className="col-2">ID</div>
                <div className="col-2">Name</div>
                <div className="col-2">Region</div>
                <div className="col-3">Address</div>
                <div className="col-2">Status</div>
                <div className="col-1">Faulty</div>
              </div>
              {data.map((machine) => {
                const { id, name, machinelocation, status, percentage, unitNumber } = machine;
                return (
                  <div key={id} className="row align-items-start mb-2">
                    <div className="col-2">{id}</div>
                    <div className="col-2">{name}</div>
                    <div className="col-2">{machinelocation.districtModel.region}</div>
                    <div className="col-2">
                      {machinelocation.address}
                      {(unitNumber) ? " #" + unitNumber : " "}
                      {" Singapore " + machinelocation.postcode}
                   </div>
                    <div className="col-1">{percentage}</div>
                    <div className="col-2">{status}</div>
                    <div className="col-1">
                      {id && (
                        <Link>
                          <i
                            className="bx bx-pencil bx-sm"
                            onClick={() => handleUpdate(machine)}
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

export default ReportMachine;