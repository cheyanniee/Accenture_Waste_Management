import useLocalStorage from "./useLocalStorage";

/*
    Purpose:
        - Use Input

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const useInput = (key, initValue) => {
    const [value, setValue] = useLocalStorage(key, initValue);
    const reset = () => setValue(initValue);
    const attributeObj = {
        value,
        onChange: (e) => setValue(e.target.value),
    };
    return [value, reset, attributeObj];
};

export default useInput;
