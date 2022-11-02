import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import moment from "moment";

import useAuth from "../hooks/useAuth";
import axios, { config } from "../api/axios";
import { BALANCE_ENDPOINTS, TRANSACTION_ENDPOINTS, ROLES, ACTION_TYPES } from "../helper/Constant";

import Header from "../components/Header";
import Footer from "../components/Footer";

/*
    Purpose:
        - View Balance & Transactions History

    Restriction:
        - Only those with ROLES.User will be able to access this page.

    Endpoints:
        - BALANCE_ENDPOINTS.UserBalance
        - TRANSACTION_ENDPOINTS.GetByID

    Author:
        - Cheyanne Lim
*/

const Points = () => {
  const { auth } = useAuth();
  const [data, setData] = useState([]);
  const [balance, setBalance] = useState("");
  const [transactions, setTransactions] = useState([]);
  const [apiSearch, setApiSearch] = useState([]);
  const [message, setMessage] = useState();
  const [errMsg, setErrMsg] = useState();

  const [machine, setMachine] = useState("");

  useEffect(() => {
    const fetchBalance = async () => {
      try {
        const response = await axios.get(
          BALANCE_ENDPOINTS.UserBalance,
          config({ token: auth.token })
        );
        setApiSearch(response?.data);
        console.log("Data: ", response?.data);
        setBalance(response?.data.currentBalance);
        console.log(balance);
      } catch (error) {
        console.log("Error: ", error);
      }
    };

    const fetchTransactions = async () => {
      try {
        const response = await axios.get(
          TRANSACTION_ENDPOINTS.GetByID,
          config({ token: auth.token })
        );

        response?.data.sort((a,b) =>
            (a.dateAndTime < b.dateAndTime)
                ? 1
                : ((b.dateAndTime < a.dateAndTime)
                    ? -1
                    : 0)
        );

        var newData = [];
            response?.data.map((entry) => {
            const date = entry.dateAndTime ? entry.dateAndTime.split("T")[0] : "";
            entry.date = date;

            const time = entry.dateAndTime ? entry.dateAndTime.split("T")[1].split(".")[0] : "";
            entry.time = time;

            newData.push(entry);
        });

        setTransactions(newData);
        setApiSearch(newData);
        setData(newData);
        console.log("Transactions: ", newData);
      } catch (error) {
        console.log("Error: ", error);
      }
    };
    fetchBalance();
    fetchTransactions();
  }, [auth.token]);

  const handleFilter = (e) => {
    setMessage("");
    setErrMsg("");
    const inputText = e.target.value;
    const filterResult = apiSearch.filter((item) => {
      return item.machineModel?.name
        ? (item.machineModel?.name.toLowerCase().includes(inputText.toLowerCase()))
        : false;
    });

    filterResult.length > 0
      ? setData(filterResult)
      : setData([{ id: "No result for " + inputText }]);
  };

  return (
    <>
      <Header />
      <section className="container-lg py-5">
        <div className="row">
          <div className="worksingle-content col-lg-10 m-auto text-left justify-content-center">
            <h2 className="worksingle-heading h3 pb-5 light-300 typo-space-line">
              Check Balance & Transactions
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
                    id="balance"
                    value={balance}
                    disabled
                  />
                  <label htmlFor="officialId light-300">
                    Current Balance
                  </label>
                </div>
              </div>
            </form>

            <br />

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
                    Machine Name
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
              <div className="col-3">Machine</div>
              <div className="col-3">Time Stamp</div>
              <div className="col-2">Type</div>
              <div className="col-2">Transaction</div>
            </div>
            {data.map((entry) => {
              const { id, balanceChange, dateAndTime, machineModel, choose, date, time } = entry;
              return (
                <div key={id} className="row align-items-start mb-2">
                  <div className="col-2">{id}</div>
                  <div className="col-3">{machineModel ? machineModel.name : " "}</div>
                  <div className="col-3">
                      <p>{date ? "Date: " + date : ""}</p>
                      <p>{time ? "Time: " + time : ""}</p>
                  </div>
                  <div className="col-2">{choose}</div>
                  <div className="col-2">
                    {choose === ACTION_TYPES.Recycle
                      ? balanceChange
                      : "-" + balanceChange
                    }
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

export default Points;
