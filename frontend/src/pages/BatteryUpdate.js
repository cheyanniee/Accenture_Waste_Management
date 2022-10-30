import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { BATTERY_ENDPOINTS } from "../helper/Constant";
import { INITIAL_BATTERY_FORM_VALUES, registerBatterySchema } from "../schemas";

const BatteryUpdate = () => {
  const { auth } = useAuth();
  const [formErrMsg, setFormErrMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [data, setData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();

  const [batteryID, setBatteryID] = useState("New");
  const [batteryType, setBatteryType] = useState("Type");
  const [batteryPoints, setBatteryPoints] = useState("Points");

  const fetchData = async () => {
      try {
        const response = await axios.get(
          BATTERY_ENDPOINTS.GetAll,
          config({ token: auth.token })
        );
        response?.data.sort((a,b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
        setData(response?.data);
        setApiSearch(response?.data);
        console.log("Batteries: ", response?.data);
      } catch (error) {
        console.log("Error: ", error);
      }
    };

  useEffect(() => {
    fetchData();
  }, [auth.token]);

  const handleFilter = (e) => {
    setMessage("");
    setErrMsg("");
    const inputText = e.target.value;
    const filterResult = apiSearch.filter((item) => {
      return item.type
        ? (item.type.toLowerCase().includes(inputText.toLowerCase()))
        : false;
    });
    filterResult.length > 0
      ? setData(filterResult)
      : setData([{ type: "No result for " + inputText }]);
  };

  const handleUpdate = (id, type, valuePerWeight) => {
    console.log("Task: ", id, moment().format("DD-MM-YYYY HH:mm:ss"));
    resetMsg();
    resetForm();
    setBatteryID(id);
    setBatteryType(type);
    setBatteryPoints(valuePerWeight);
  };

  const resetMsg = () => {
    setFormErrMsg("");
    setErrMsg("");
    setSuccessMsg("");
  }

  const onReset = () => {
    console.log("Resetting");
    resetForm();
    setBatteryID("New");
    setBatteryType("Type");
    setBatteryPoints("Points");
  }

  const onSubmit = async (values, actions) => {
    const endpoint = (batteryID === "New")
      ? BATTERY_ENDPOINTS.Create
      : BATTERY_ENDPOINTS.Update

    resetMsg();

    const updatedFieldKeys = Object.keys(values).filter(
      (key) => values[key] !== ""
    );

    if (updatedFieldKeys.length === 0) return;

    const params = updatedFieldKeys.reduce((acc, key) => {
      return { ...acc, [key]: values[key] };
    }, {});

    params.id = (batteryID !== "New")
      ? batteryID : "";

    if (!(params.id) && !(params.type && params.valuePerWeight)) {
      setFormErrMsg("Invalid Input");
      return;
    }

    console.log("Params: ", params);

    try {
      console.log("url: ", endpoint);
      const response = await axios.post(
        endpoint,
        params,
        config({ token: auth.token })
      );
      console.log(response.data);
      onReset();
      fetchData();
      setSuccessMsg("Successful!");
    } catch (error) {
      console.log("Error: ", error.response);
      setFormErrMsg(error.response.data.message);
    }
    onReset();
  }

  const {
      values,
      errors,
      touched,
      isSubmitting,
      handleChange,
      handleBlur,
      handleSubmit,
      resetForm,
    } = useFormik({
      initialValues: INITIAL_BATTERY_FORM_VALUES,
      validationSchema: registerBatterySchema,
      onSubmit,
    });

  return (
    <>
      <Header />
      <section className="container-lg py-5">
        <div className="row">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <h2 className="worksingle-heading h3 pb-5 light-300 typo-space-line">
              Batteries
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
                    id="type"
                    onChange={handleFilter}
                  />
                  <label htmlFor="officialId light-300">
                    Battery Type
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
              <div className="col-2">Type</div>
              <div className="col-2">Points</div>
              <div className="col-2">Update</div>
            </div>
            <form className="contact-form row" onSubmit={handleSubmit}>
              <div className="col-2 mb-4">
                <div className="">
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="id"
                    placeholder={batteryID}
                    value={batteryID}
                    disabled
                  />
                </div>
              </div>
              {/* End Input ID */}
              <div className="col-2 mb-4">
                <div className="">
                  <input
                    type="text"
                    className={
                      errors.type && touched.type
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="type"
                    placeholder={batteryType}
                    value={values.type}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.type && touched.type && (
                    <em className="text-error">{errors.type}</em>
                  )}
                </div>
              </div>
              {/* End Input Type */}
              <div className="col-2 mb-4">
                <div className="">
                  <input
                    type="number"
                    className={
                      errors.valuePerWeight && touched.valuePerWeight
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="valuePerWeight"
                    placeholder={batteryPoints}
                    value={values.valuePerWeight}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.valuePerWeight && touched.valuePerWeight && (
                    <em className="text-error">{errors.valuePerWeight}</em>
                  )}
                </div>
              </div>
              {/* End Input Points */}
              <div className="col-2 row align-items-start mb-2">
                <em className="text-error px-3">{formErrMsg}</em>
                <em className="text-success px-3">{successMsg}</em>
                  <div className="col-2">
                    <button
                      disabled={isSubmitting}
                      type="submit"
                      className="btn btn-secondary rounded-pill text-light light-300"
                    >
                      Submit
                    </button>
                    <button
                      type="reset"
                      className="btn btn-secondary rounded-pill text-light light-300"
                      onClick={onReset}
                    >
                      Reset
                    </button>
                  </div>
              </div>
          </form>
            {data.map((battery) => {
              const { id, type, valuePerWeight } = battery;
              return (
                <div key={id} className="row align-items-start mb-2">
                  <div className="col-2">{id}</div>
                  <div className="col-2">{type}</div>
                  <div className="col-2">{valuePerWeight}</div>
                  <div className="col-2">
                    {id && (
                      <Link>
                        <i
                          className="bx bx-pencil bx-sm"
                          onClick={() => handleUpdate(id, type, valuePerWeight)}
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

export default BatteryUpdate;
