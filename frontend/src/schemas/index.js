import * as yup from "yup";

const passwordRules = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{5,}$/;
// min 5 characters, 1 upper case letter, 1 lower case letter and 1 number

export const INITIAL_FORM_VALUES = {
  firstName: "",
  lastName: "",
  officialId: "",
  email: "",
  password: "",
  confirm_password: "",
  dateOfBirth: "",
  phoneNumber: "",
  address: "",
  postcode: "",
};

export const registerSchema = yup.object().shape({
  firstName: yup.string().required("Required"),
  lastName: yup.string().required("Required"),
  officialId: yup.string().required("Required"),
  email: yup.string().email("Please enter a valid email").required("Required"),
  password: yup
    .string()
    .min(5)
    .matches(passwordRules, { message: "Please enter stronger password" })
    .required("Required"),
  confirm_password: yup
    .string()
    .oneOf([yup.ref("password"), null], "Password must match")
    .required("Required"),
  dateOfBirth: yup
    .date("Please enter valid date")
    .typeError("Please enter valid date")
    .required("Required"),
  phoneNumber: yup
    .number()
    .positive()
    .integer()
    .min(80000000, "Mobile number must be start with 8 or 9")
    .max(100000000, "Mobile number must be start with 8 or 9")
    .required("Please enter valid phone number"),
  address: yup.string().required("Required"),
  postcode: yup
    .number()
    .positive()
    .integer()
    .min(10000, "Postal code must be exactly 6 digits")
    .max(1000000, "Postal code must be at exactly 6 digits")
    .required("Please enter valid postal code"),
});