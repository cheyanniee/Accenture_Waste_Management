import useLocalStorage from "./useLocalStorage";

/*
    Purpose:
        - Use Toggle

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const useToggle = (key, initValue) => {
    const [value, setValue] = useLocalStorage(key, initValue);

    const toggle = (value) => {
        setValue((prev) => {
            return typeof value === "boolean" ? value : !prev;
        });
    };

    return [value, toggle];
};

export default useToggle;
