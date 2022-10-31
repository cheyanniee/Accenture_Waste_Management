import * as yup from "yup";

/*
    Purpose:
        - Schemas for formik templates

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Cheyanne Lim
*/

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
