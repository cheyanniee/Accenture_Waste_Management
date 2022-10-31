/*
    Purpose:
        - Save all endpoints and constants for easy access and change.

    Restriction:
        - NIL

    Endpoints:
        - All

    Author:
        - Cheyanne Lim
*/

//export const BASE_URL = "http://localhost:8080";
//export const BASE_URL = "https://localhost";
export const BASE_URL =
  "https://ec2-103-4-13-163.ap-northeast-1.compute.amazonaws.com:8080";

export const PEOPLE_ENDPOINTS = {
  RefreshToken: "/dev/v1/people/getinfo",
  Login: "/dev/v1/people/login",
  Logout: "/dev/v1/people/logout",
};

export const TRANSACTION_ENDPOINTS = {
  StartR: "/dev/v1/transaction/create/start-r",
  StartE: "/dev/v1/transaction/create/start-e",
  Confirm: "/dev/v1/transaction/create/yes",
  Reject: "/dev/v1/transaction/create/no",
  GetByID: "/dev/v1/transaction/find",
};

export const TRANSACTION_ENTRY_ENDPOINTS = {
  Create: "/dev/v1/transactionentry/create",
  GetByID: "/dev/v1/transactionentry/find",
};

export const MACHINE_ENDPOINTS = {
  GetByID: "/dev/v1/machine/get/",
  UpdateCurrentLoad: "/dev/v1/machine/update/currentload",
};

export const TASK_ENDPOINTS = {
  GetAll: "/dev/v1/task/listall",
  GetByID: "/dev/v1/task/collector",
  Collected: "/dev/v1/task/machine/collected/",
  GetByMachine: "/dev/v1/task/machine/",
};

export const ROLES = {
  User: "user",
  Collector: "collector",
  Admin: "admin",
};

export const ACTION_TYPES = {
  Exchange: "exchange",
  Recycle: "recycle",
};

export const BATTERY_EXCHANGE_TYPES = ["AA", "AAA"];
