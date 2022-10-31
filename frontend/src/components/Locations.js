import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "../api/axios";
import { MACHINE_ENDPOINTS, twoDigits } from "../helper/Constant";

const Locations = () => {
  const [machineList, setMachineList] = useState([]);
  const [filterList, setFilterList] = useState([]);
  const [regionList, setRegionList] = useState([]);

  useEffect(() => {
    const getMachines = async () => {
      try {
        const response = await axios.get(MACHINE_ENDPOINTS.GetAll);
        response?.data.sort((a, b) => (a.id > b.id ? 1 : b.id > a.id ? -1 : 0));
        console.log("Machines: ", response?.data);

        let newData = [];
        response?.data.map((machine) => {
          const percentage = (machine.currentLoad / machine.capacity) * 100;
          machine.percentage = percentage.toFixed().toString() + "%";

          const fullAddress =
            machine.machinelocation.address +
            (machine.unitNumber ? " #" + twoDigits(machine.unitNumber) : " ") +
            " Singapore " +
            machine.machinelocation.postcode;
          machine.fullAddress = fullAddress;
          newData.push(machine);
        });
        setMachineList(newData);
        setFilterList(newData);
        setRegionList([
          ...new Set(
            newData.map(
              (machine) => machine.machinelocation.districtModel.region
            )
          ),
        ]);
        console.log("Add %: ", newData);
      } catch (error) {
        console.log("Error: ", error);
      }
    };
    getMachines();
  }, []);

  const handleFilter = (region) => {
    console.log("my region is: ", region);
    setFilterList(
      machineList.filter(
        (machine) => machine.machinelocation.districtModel.region === region
      )
    );
  };

  const clearFilter = () => {
    setFilterList(machineList);
  };

  return (
    <>
      <section className="service-wrapper py-3">
        <div className="container-fluid pb-3">
          <div className="row">
            <h2 className="h2 text-center col-12 py-5 semi-bold-600">
              Battery Recycling Machines
            </h2>
            <div className="service-header col-2 col-lg-3 text-end light-300">
              <i className="bx bx-gift h3 mt-1" />
            </div>
            <div className="service-heading col-10 col-lg-9 text-start float-end light-300">
              <h2 className="h3 pb-4 typo-space-line">Machine Locations</h2>
            </div>
          </div>
          <p className="service-footer col-10 offset-2 col-lg-9 offset-lg-3 text-start pb-3 text-muted px-2">
            All recycling machines available around the island
          </p>
        </div>
        <div className="service-tag py-5 bg-secondary">
          <div className="col-md-12">
            <ul className="nav d-flex justify-content-center">
              <li className="nav-item mx-lg-4">
                <Link
                  className="filter-btn nav-link btn-outline-primary active shadow rounded-pill text-light px-4 light-300"
                  onClick={() => clearFilter()}
                >
                  All
                </Link>
              </li>
              {regionList.map((region) => {
                return (
                  <li
                    key={region}
                    className="nav-item mx-lg-4"
                    onClick={() => handleFilter(region)}
                  >
                    <Link className="filter-btn nav-link btn-outline-primary rounded-pill text-light px-4 light-300">
                      {region}
                    </Link>
                  </li>
                );
              })}
            </ul>
          </div>
        </div>
      </section>
      <section className="container overflow-hidden py-5">
        <div className="row gx-5 gx-sm-3 gx-lg-5 gy-lg-5 gy-3 pb-3 projects">
          {/* Start Recent Work */}
          {filterList.map((machine) => {
            const { id, name, fullAddress, status, percentage } = machine;
            return (
              <div
                key={id}
                className="col-xl-3 col-md-4 col-sm-6 project ui branding"
              >
                <Link
                  to="#"
                  className="service-work card border-0 text-white shadow-sm overflow-hidden mx-5 m-sm-0"
                >
                  <img
                    className="service card-img"
                    src={`./assets/img/services-0${(id % 8) + 1}.jpg`}
                    alt=""
                  />
                  <div className="service-work-vertical card-img-overlay d-flex align-items-end">
                    <div className="service-work-content text-left text-light">
                      <span className="btn btn-outline-light rounded-pill mb-lg-3 px-lg-4 light-300">
                        {name}
                      </span>
                      <p className="card-text">{fullAddress}</p>
                      <p className="card-text">Status: {status}</p>
                      <p className="card-text">Storage: {percentage}</p>
                    </div>
                  </div>
                </Link>
              </div>
            );
          })}

          {/* End Recent Work */}
        </div>
      </section>
    </>
  );
};

export default Locations;
