import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import moment from "moment";

import axios, { config } from "../api/axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import useAuth from "../hooks/useAuth";
import { PEOPLE_ENDPOINTS, MACHINE_ENDPOINTS, TASK_ENDPOINTS, ROLES } from "../helper/Constant";

const ViewTask = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [successMsg, setSuccessMsg] = useState();
  const [errMsg, setErrMsg] = useState();

  const [machine, setMachine] = useState("");
  const [machineList, setMachineList] = useState("");

  const collected = "collected";
  const delivered = "delivered";

    const fetchData = async () => {
      try {
        const response = await axios.get(
          TASK_ENDPOINTS.GetByID,
          config({ token: auth.token })
        );

        response?.data.sort((a,b) =>
            (a.assignedTime < b.assignedTime)
                ? 1
                : ((b.assignedTime < a.assignedTime)
                    ? -1
                    : 0)
        );

        console.log("Data: ", response?.data);

        var newData = [];
        response?.data.map((task) => {
            const fullAddress = task.machine.machinelocation.address
                + ((task.machine.unitNumber) ? " #" + task.machine.unitNumber : " ")
                + " Singapore " + task.machine.machinelocation.postcode;
            task.fullAddress = fullAddress;

            task.timeStamps = {};

            task.timeStamps.assignDate = task.assignedTime ? task.assignedTime.split("T")[0] : "";
            task.timeStamps.assignTime = task.assignedTime ? task.assignedTime.split("T")[1].split(".")[0] : "";

            task.timeStamps.collectDate = task.collectedTime ? task.collectedTime.split("T")[0] : "";
            task.timeStamps.collectTime = task.collectedTime ? task.collectedTime.split("T")[1].split(".")[0] : "";

            task.timeStamps.deliverDate = task.deliveredTime ? task.deliveredTime.split("T")[0] : "";
            task.timeStamps.deliverTime = task.deliveredTime ? task.deliveredTime.split("T")[1].split(".")[0] : "";

            newData.push(task);
        });

        setData(newData);
        setApiSearch(newData);
        console.log("Tasks: ", newData);
      } catch (error) {
        console.log("Error: ", error);
        setErrMsg(error);
      }
    };

  useEffect(() => {
    fetchData();
  }, [auth.token]);

  const markDone = async (action, task) => {
    setErrMsg("");
    setSuccessMsg("");

    console.log(action);

    if ((action === delivered)
      && (!task.timeStamps.collectDate)
      && (!task.timeStamps.collectTime)) {
        setErrMsg("Please Collect Before Marking Delivered!")
        return;
    }

    const endpoint =
      (action === collected)
        ? TASK_ENDPOINTS.Collected
        : (action === delivered)
        ? TASK_ENDPOINTS.Delivered
        : "";

    if (endpoint === "") {
      setErrMsg("Invalid Endpoint");
      return;
    }

    try {
      const response = await axios.get(
        endpoint + task.id,
        config({ token: auth.token })
      );
      setSuccessMsg("Updated!");
    } catch (error) {
      console.log("Error: ", error);
      setErrMsg(error);
    }

    fetchData();
  }

  return (
    <>
      <Header />
      <section className="container-lg py-5">
        <div className="row">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <h2 className="worksingle-heading h3 pb-5 light-300 typo-space-line">
              View Assigned Task
            </h2>
          </div>
        </div>
        <div className="row align-items-start ">
          <div className=" col-lg-10 m-auto text-left justify-content-center">
            <div className="row align-items-start text-primary fs-4 mb-3">
              {successMsg && <em className="text-success px-2">{successMsg}</em>}
              {errMsg && <em className="text-danger px-2">{errMsg}</em>}
            </div>
          </div>
        </div>
        <div className="row align-items-start ">
          <div className=" col-lg-10 m-auto text-left justify-content-center">
            <div className="row align-items-start text-primary fs-4 mb-3">
              <div className="col-2">Machine</div>
              <div className="col-3">Machine Address</div>
              <div className="col-2">Assigned</div>
              <div className="col-2">Collected</div>
              <div className="col-2">Delivered</div>
            </div>
            {data.map((task) => {
              const { id, machine, fullAddress, timeStamps } = task;
              return (
                <div key={id} className="row align-items-start mb-2">
                  <div className="col-2">
                    {machine
                        ? machine.id
                        : " "
                    }
                  </div>
                  <div className="col-3">
                    {fullAddress}
                  </div>
                  <div className="col-2">
                    <p>{timeStamps.assignDate}</p>
                    <p>{timeStamps.assignTime}</p>
                  </div>
                  <div className="col-2">
                    <p>{timeStamps.collectDate}</p>
                    <p>{timeStamps.collectTime}</p>
                    {!timeStamps.collectDate && !timeStamps.collectTime && (
                      <button
                        onClick={() => markDone(collected, task)}
                        className="btn btn-secondary rounded-pill px-4 py-2 radius-0 text-light light-300"
                      >
                        Collected
                      </button>
                    )}
                  </div>
                  <div className="col-2">
                    <p>{timeStamps.deliverDate}</p>
                    <p>{timeStamps.deliverTime}</p>
                    {!timeStamps.deliverDate && !timeStamps.deliverTime && (
                      <button
                        onClick={() => markDone(delivered, task)}
                        className="btn btn-secondary rounded-pill px-4 py-2 radius-0 text-light light-300"
                      >
                        Delivered
                      </button>
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

export default ViewTask;
