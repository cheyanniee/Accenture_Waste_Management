import * as yup from "yup";

export const INITIAL_EXCHANGE_FORM_VALUES = {
    transactionId: "",
    batteryType: "",
    quantity: "",
};

export const exchangeSchema = yup.object().shape({
    transactionId: yup.number().positive().integer().required("Required"),
    batteryType: yup.string().required("Required"),
    quantity: yup.number().positive().integer().required("Required"),
});
