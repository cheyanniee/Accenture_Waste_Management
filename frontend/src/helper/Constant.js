export const BASE_URL = "http://localhost:8080";
//export const BASE_URL = "https://localhost";

export const PEOPLE_ENDPOINTS = {
  GetAll: "/dev/v1/people/listall",
  RefreshToken: "/dev/v1/people/getinfo",
  Login: "/dev/v1/people/login",
  Logout: "/dev/v1/people/logout",
  Register: "/dev/v1/people/register",
  RegisterCollector: "/dev/v1/people/register/collector",
  RegisterAdmin: "/dev/v1/people/register/admin",
  UpdateDetails: "/dev/v1/people/update",
}

export const BALANCE_ENDPOINTS = {
  GetAll: "/dev/v1/balance/listall",
  UserBalance: "/dev/v1/balance/find",
}

export const TRANSACTION_ENDPOINTS = {
  GetAll: "/dev/v1/transaction/listall",
  GetByID: "/dev/v1/transaction/find",
}

export const MACHINE_ENDPOINTS = {
  GetAll: "/dev/v1/machine/listall",
  Create: "/dev/v1/machine/add",
  Update: "/dev/v1/machine/update",
  UpdateCurrentLoad: "/dev/v1/machine/update/currentload",
  UpdateStatus: "/dev/v1/machine/update/status",
}

export const BATTERY_ENDPOINTS = {
  GetAll: "/dev/v1/battery/listall",
  Create: "/dev/v1/battery/create",
  Update: "/dev/v1/battery/update",
}

export const TASK_ENDPOINTS = {
  GetAll: "/dev/v1/task/listall",
  Create: "/dev/v1/task/create",
}

export const LOCATION_ENDPOINTS = {
  GetAll: "/dev/v1/location/listall",
  GetDistrict: "/dev/v1/location/getdistrict?postcode=",
}


export const ROLES = {
  User: "user",
  Collector: "collector",
  Admin: "admin",
};

export const FAULTY_MACHINE = "FAULTY";

export const MACHINE_STATUS = [
  "NORMAL",
  FAULTY_MACHINE,
];

export const REGIONS = [
  "Northeast",
  "Central",
  "West",
  "East",
  "North",
];