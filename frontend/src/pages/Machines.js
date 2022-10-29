import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { INITIAL_MACHINE_FORM_VALUES, registerMachineSchema } from "../schemas";
import { PEOPLE_ENDPOINTS, MACHINE_ENDPOINTS, ROLES, MACHINE_STATUS } from "../helper/Constant";

const Machines = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();

  const [status, setStatus] = useState("");

  useEffect(() => {
    const fetchMachines = async () => {
      try {
        const response = await axios.get(
          MACHINE_ENDPOINTS.GetAll,
          config({ token: auth.token })
        );
        response?.data.sort((a,b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
        setData(response?.data);
        setApiSearch(response?.data);
        console.log("Machines: ", response?.data);
      } catch (error) {
        console.log("Error: ", error);
      }
    };

    const getCurrentPercentage = () => {
        var newData = [];
        data.map((machine) => {
            const percentage = (machine.currentLoad / machine.capacity) * 100;
            console.log("Calc: ", machine.name, percentage.toFixed());
            machine.percentage = percentage.toFixed();
            console.log("Save: ", machine.name,  machine.percentage);
            newData.push(machine);
        });
        setData(newData);
        console.log("Add %: ", data);
    }

    fetchMachines();
    getCurrentPercentage();
  }, [auth.token]);

  const handleFilter = (e) => {
    setMessage("");
    setErrMsg("");
    const inputText = e.target.value;
    const filterResult = apiSearch.filter((item) => {
      return item.name
        ? (item.name.toLowerCase().includes(inputText.toLowerCase()))
        : false;
    });
    filterResult.length > 0
      ? setData(filterResult)
      : setData([{ officialId: "No result for " + inputText }]);
  };

  const onSubmit = async (values, actions) => {
    console.log("Submitting");
  }

  const handleUpdate = async (id) => {

    console.log("Task: ", id, moment().format("DD-MM-YYYY HH:mm:ss"));

  };

  const {
      values,
      errors,
      touched,
      isSubmitting,
      handleChange,
      handleBlur,
      handleSubmit,
    } = useFormik({
      initialValues: INITIAL_MACHINE_FORM_VALUES,
      validationSchema: registerMachineSchema,
      onSubmit,
    });

  return (
    <>
      <Header />
      <section className="container-lg py-5">
        <div className="row">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <h2 className="worksingle-heading h3 pb-5 light-300 typo-space-line">
              Machines
            </h2>
          </div>
        </div>
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <form className="contact-form row" onSubmit={handleSubmit}>
              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Name</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">capacity</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="capacity"
                    placeholder="capacity"
                    value={values.capacity}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">postcode</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">address</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-1 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">floor</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-1 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">unit</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">status</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="name"
                    placeholder="Name"
                    value={values.name}
                    onChange={handleChange}
                  />
                </div>
              </div>
              {/* End Input Name */}
            </form>
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
              <div className="col-2">ID</div>
              <div className="col-2">Name</div>
              <div className="col-2">Capacity</div>
              <div className="col-2">Region</div>
              <div className="col-2">Status</div>
              <div className="col-1">Details</div>
            </div>
            {data.map((machines) => {
              const { id, name, locationModel, status, percentage } = machines;
              return (
                <div key={id} className="row align-items-start mb-2">
                  <div className="col-2">{id}</div>
                  <div className="col-2">{name}</div>
                  <div className="col-2">{percentage}</div>
                  <div className="col-2">
                    {locationModel.districtModel.region}
                  </div>
                  <div className="col-2">{status}</div>
                  <div className="col-2">
                    {id && (
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

export default Machines;
