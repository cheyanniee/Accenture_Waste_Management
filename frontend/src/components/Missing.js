import { Link } from "react-router-dom";

/*
    Purpose:
        - Missing page

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const Missing = () => {
    return (
        <section style={{ padding: "100px" }}>
            <h1>Oops!</h1>
            <p>Page Not Found</p>
            <div className="">
                <Link to="/">Visit Our Homepage</Link>
            </div>
        </section>
    );
};

export default Missing;
