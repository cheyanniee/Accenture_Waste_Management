import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useFormik } from "formik";
import moment from "moment";
import axios from "axios";

import { default as myAxios, config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { INITIAL_MACHINE_FORM_VALUES, registerMachineSchema } from "../schemas";
import { PEOPLE_ENDPOINTS, MACHINE_ENDPOINTS, LOCATION_ENDPOINTS, ROLES, MACHINE_STATUS } from "../helper/Constant";

const Machines = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [tempData, setTempData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();
  const [formErrMsg, setFormErrMsg] = useState("");
  const [formSuccessMsg, setFormSuccessMsg] = useState("");

  const defaultId = "New";
  const [idLabel, setIdLabel] = useState(defaultId);

  const defaultName = "Name";
  const [nameLabel, setNameLabel] = useState(defaultName);

  const defaultCurrentLoad = "Load";
  const [currentLoadLabel, setCurrentLoadLabel] = useState(defaultCurrentLoad);

  const defaultCapacity = "Capacity";
  const [capacityLabel, setCapacityLabel] = useState(defaultCapacity);

  const defaultStatusOptions = MACHINE_STATUS;
  const [statusOptions, setStatusOptions] = useState(MACHINE_STATUS);

  const defaultPostcode = "Postal Code";
  const [postcodeLabel, setPostcodeLabel] = useState(defaultPostcode);

  const defaultAddress = "Address";
  const [addressLabel, setAddressLabel] = useState(defaultAddress);

  const defaultRegion = "Region";
  const [regionLabel, setRegionLabel] = useState(defaultRegion);

  const defaultUnitNumber = "Unit Number";
  const [unitNumberLabel, serUnitNumberLabel] = useState(defaultUnitNumber);

  const defaultFloor = "Floor";
  const [floorLabel, setFloorLabel] = useState(defaultFloor);

  const defaultUnit = "Unit";
  const [unitLabel, setUnitLabel] = useState(defaultUnit);


    const fetchMachines = async () => {
      try {
        const response = await myAxios.get(
          MACHINE_ENDPOINTS.GetAll,
          config({ token: auth.token })
        );
        response?.data.sort((a,b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
        setApiSearch(response?.data);
        console.log("Machines: ", response?.data);

        var newData = [];
        response?.data.map((machine) => {
            const percentage = (machine.currentLoad / machine.capacity) * 100;
            machine.percentage = percentage.toFixed().toString() + "%";
            newData.push(machine);
        });
        setData(newData);
        console.log("Add %: ", newData);
      } catch (error) {
        console.log("Error: ", error);
      }
    };

  const loadAddress = async () => {
    try {
      console.log("postcode: ", values.postcode);
      const url = "https://developers.onemap.sg/commonapi/search?searchVal=" + values.postcode + "&returnGeom=N&getAddrDetails=Y&pageNum=1";
      console.log("url: ", url);
      const response = await axios.get(url);
      console.log("data: ", response.data);
      const add = response.data.results[0].BLK_NO + " " + response.data.results[0].ROAD_NAME;
      values.address = add;
      setAddressLabel(add);

      const districtResponse = await myAxios.get(
        LOCATION_ENDPOINTS.GetDistrict + values.postcode,
        config({ token: auth.token })
      );

      console.log("District", districtResponse?.data);
      values.region = districtResponse?.data.region;
      setRegionLabel = districtResponse?.data.region;
    } catch (error) {
      console.log("error: ", error.response);
      setAddressLabel("No address found!");
    }
  }

  useEffect(() => {
    fetchMachines();
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

  const onReset = async (values, actions) => {
    console.log("Resetting");
    resetLabels();
    resetForm();
    resetMsg();
  }

  const onSubmit = async (values, actions) => {
    resetMsg();

    const endpoint = (idLabel === defaultId)
      ? MACHINE_ENDPOINTS.Create
      : MACHINE_ENDPOINTS.Update

    console.log("Endpoint: ", endpoint);

    if (values.floor > 0 && values.unit > 0) {
        values.unitNumber = values.floor + "-" + values.unit;
    } else if (values.floor > 0) {
        values.unitNumber = values.floor + "-" + unitLabel;
    } else if (values.unit > 0) {
        values.unitNumber =  floorLabel + "-" + values.unit;
    }

    const updatedFieldKeys = Object.keys(values).filter(
      (key) => values[key] !== ""
    );

    if (updatedFieldKeys.length === 0) return;

    const params = updatedFieldKeys.reduce((acc, key) => {
      return { ...acc, [key]: values[key] };
    }, {});

    params.machineId = (idLabel !== defaultId)
      ? idLabel : "";

    if (!(params.machineId) && !(
            params.name
            && params.currentLoad
            && params.capacity
            && params.status
            && params.postcode
            && params.address
            && params.unitNumber
       )) {
      setFormErrMsg("Invalid Input");
      return;
    }

    console.log("Params: ", params);

    try {
      console.log("url: ", endpoint);
      const response = await myAxios.post(
        endpoint,
        params,
        config({ token: auth.token })
      );
      console.log(response.data);
      onReset();
      fetchMachines();
      setFormSuccessMsg("Successful!");
    } catch (error) {
      console.log("Error: ", error.response);
      setFormErrMsg(error.response.data.message);
    }
    onReset();
  }

  const handleUpdate = (machine) => {
    console.log("Task: ", machine.id, moment().format("DD-MM-YYYY HH:mm:ss"));

    resetMsg();
    resetForm();

    if (!statusOptions.includes(machine.status)) {
      statusOptions.push(machine.status);
    }

    setIdLabel(machine.id);
    setNameLabel(machine.name);
    setCurrentLoadLabel(machine.currentLoad);
    setCapacityLabel(machine.capacity);
    values.status = machine.status;
    setPostcodeLabel(machine.machinelocation.postcode);
    setAddressLabel(machine.machinelocation.address);
    setRegionLabel(machine.machinelocation.districtModel.region);
    serUnitNumberLabel(machine.unitNumber);
    if (machine.unitNumber) {
      setFloorLabel(machine.unitNumber.split("-")[0]);
      setUnitLabel(machine.unitNumber.split("-")[1]);
    }
  };

  const resetMsg = () => {
    setFormErrMsg("");
    setErrMsg("");
    setFormSuccessMsg("");
  }

  const resetLabels = () => {
    setIdLabel(defaultId);
    setNameLabel(defaultName);
    setCurrentLoadLabel(defaultCurrentLoad);
    setCapacityLabel(defaultCapacity);
    setStatusOptions(defaultStatusOptions);
    setPostcodeLabel(defaultPostcode);
    setAddressLabel(defaultAddress);
    setRegionLabel(defaultRegion);
    serUnitNumberLabel(defaultUnitNumber);
    setFloorLabel(defaultFloor);
    setUnitLabel(defaultUnit);
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
                  <label htmlFor="firstName light-300">ID</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="id"
                    placeholder={idLabel}
                    value={idLabel}
                    onBlur={handleBlur}
                    disabled
                  />
                </div>
              </div>
              {/* End Input ID */}

              <div className="col-4 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Name</label>
                  <input
                    type="text"
                    className={
                      errors.name && touched.name
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="name"
                    placeholder={nameLabel}
                    value={values.name}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.name && touched.name && (
                    <em className="text-error">{errors.name}</em>
                  )}
                </div>
              </div>
              {/* End Input Name */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Current Load</label>
                  <input
                    type="number"
                    className={
                      errors.currentLoad && touched.currentLoad
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="currentLoad"
                    placeholder={currentLoadLabel}
                    value={values.currentLoad}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.currentLoad && touched.currentLoad && (
                    <em className="text-error">{errors.currentLoad}</em>
                  )}
                </div>
              </div>
              {/* End Input Current Load */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Capacity</label>
                  <input
                    type="number"
                    className={
                      errors.capacity && touched.capacity
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="capacity"
                    placeholder={capacityLabel}
                    value={values.capacity}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.capacity && touched.capacity && (
                    <em className="text-error">{errors.capacity}</em>
                  )}
                </div>
              </div>
              {/* End Input Capacity */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Status</label>
                  <select
                    className="form-control form-control-lg light-300"
                    id="status"
                    placeholder="status"
                    value={values.status}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  >
                    {statusOptions.map((stat) => {
                      return(<option value={stat} label={stat} >{stat}</option>)
                    })}
                  </select>
                  {errors.status && touched.status && (
                    <em className="text-error">{errors.status}</em>
                  )}
                </div>
              </div>
              {/* End Input Status */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Postal Code</label>
                  <input
                    type="number"
                    className={
                      errors.postcode && touched.postcode
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="postcode"
                    placeholder={postcodeLabel}
                    value={values.postcode}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.postcode && touched.postcode && (
                    <em className="text-error">{errors.postcode}</em>
                  )}
                  <Link className="spanLink" onClick={loadAddress}>
                    Load Address
                  </Link>
                </div>
              </div>
              {/* End Input Postal Code */}

              <div className="col-4 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Address</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="address"
                    placeholder={addressLabel}
                    value={values.address}
                    onBlur={handleBlur}
                    disabled
                  />
                </div>
              </div>
              {/* End Input Address */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Region</label>
                  <input
                    type="text"
                    className="form-control form-control-lg light-300"
                    id="region"
                    placeholder={regionLabel}
                    value={values.region}
                    onBlur={handleBlur}
                    disabled
                  />
                </div>
              </div>
              {/* End Input Region */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Floor</label>
                  <input
                    type="number"
                    className={
                      errors.floor && touched.floor
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="floor"
                    placeholder={floorLabel}
                    value={values.floor}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.floor && touched.floor && (
                    <em className="text-error">{errors.floor}</em>
                  )}
                </div>
              </div>
              {/* End Input Floor */}

              <div className="col-2 mb-4">
                <div className="">
                  <label htmlFor="firstName light-300">Unit</label>
                  <input
                    type="number"
                    className={
                      errors.unit && touched.unit
                        ? "form-control form-control-lg-error light-300-error"
                        : "form-control form-control-lg light-300"
                    }
                    id="unit"
                    placeholder={unitLabel}
                    value={values.unit}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {errors.unit && touched.unit && (
                    <em className="text-error">{errors.unit}</em>
                  )}
                </div>
              </div>
              {/* End Input Unit */}

              <div className="col-md-12 col-12 m-auto">
                  <button
                    disabled={isSubmitting}
                    type="submit"
                    className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                  >
                    Submit
                  </button>
                  <button
                    disabled={isSubmitting}
                    type="reset"
                    className="btn btn-secondary rounded-pill px-md-5 px-4 py-2 radius-0 text-light light-300"
                    onClick={onReset}
                  >
                    Reset
                  </button>
                  <em className="text-error px-3">{formErrMsg}</em>
                  <em className="text-success">{formSuccessMsg}</em>
                </div>
            </form>
        </div>

        <br />

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
              <div className="col-2">Address</div>
              <div className="col-1">Load</div>
              <div className="col-2">Status</div>
              <div className="col-1">Details</div>
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
                    {(unitNumber) ? "#" + unitNumber : ""}
                    {machinelocation.postcode}
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

export default Machines;
