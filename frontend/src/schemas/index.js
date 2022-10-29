import * as yup from "yup";

const passwordRules = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{5,}$/;
// min 5 characters, 1 upper case letter, 1 lower case letter and 1 number

export const INITIAL_REGISTER_FORM_VALUES = {
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
  floor: "",
  unit: "",
  unitNumber: "",
};

export const INITIAL_REGISTER_USERS_FORM_VALUES = {
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
  role: "",
  floor: "",
  unit: "",
  unitNumber: "",
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
    .min(80000000, "Mobile must be an 8 digit number starting with 8/9")
    .max(100000000, "Mobile must be an 8 digit number starting with 8/9")
    .required("Required"),
  address: yup.string().required("Required"),
  postcode: yup
    .number()
    .positive()
    .integer()
    .min(10000, "Postal code must be exactly 6 digits")
    .max(1000000, "Postal code must be at exactly 6 digits")
    .required("Required"),
  floor: yup
    .number()
    .positive()
    .integer(),
  unit: yup
    .number()
    .positive()
    .integer(),
  unitNumber: yup.string(),
});

export const registerUsersSchema = yup.object().shape({
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
    .min(80000000, "Mobile must be an 8 digit number starting with 8/9")
    .max(100000000, "Mobile must be an 8 digit number starting with 8/9")
    .required("Required"),
  address: yup.string().required("Required"),
  postcode: yup
    .number()
    .positive()
    .integer()
    .min(10000, "Postal code must be exactly 6 digits")
    .max(1000000, "Postal code must be at exactly 6 digits")
    .required("Required"),
  role: yup.string().required("Required"),
  floor: yup
    .number()
    .positive()
    .integer(),
  unit: yup
    .number()
    .positive()
    .integer(),
  unitNumber: yup.string(),
});

export const userDetailsSchema = yup.object().shape({
  firstName: yup.string(),
  lastName: yup.string(),
  officialId: yup.string(),
  email: yup.string().email("Please enter a valid email"),
  password: yup
    .string()
    .min(5)
    .matches(passwordRules, { message: "Please enter stronger password" }),
  confirm_password: yup
    .string()
    .oneOf([yup.ref("password"), null], "Password must match"),
  dateOfBirth: yup
    .date("Please enter valid date")
    .typeError("Please enter valid date"),
  phoneNumber: yup
    .number()
    .positive()
    .integer()
    .min(80000000, "Mobile must be an 8 digit number starting with 8/9")
    .max(100000000, "Mobile must be an 8 digit number starting with 8/9"),
  address: yup.string(),
  postcode: yup
    .number()
    .positive()
    .integer()
    .min(10000, "Postal code must be exactly 6 digits")
    .max(1000000, "Postal code must be at exactly 6 digits"),
  floor: yup
    .number()
    .positive()
    .integer(),
  unit: yup
    .number()
    .positive()
    .integer(),
  unitNumber: yup.string(),
});
