export const BASE_URL = "http://localhost:8081";

export const PEOPLE_ENDPOINTS = {
  Login: "/dev/v1/people/login",
  Logout: "/dev/v1/people/logout",
  Register: "/dev/v1/people/register",
  RefreshToken: "/dev/v1/people/getinfo",

}

export const ROLES = {
  User: "user",
  Collector: "collector",
  Admin: "admin",
};