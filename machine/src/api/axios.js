import axios from "axios";
import { BASE_URL } from "../helper/Constant";

export const config = ({ token }) => {
  return {
    headers: {
      "Content-Type": "application/json",
      token: token,
    },
  };
};
export const headers = ({ token }) => {
  return {
    "Content-Type": "application/json",
    token: token,
  };
};

export default axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});