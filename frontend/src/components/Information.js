import { Link } from "react-router-dom";

/*
    Purpose:
        - Information Carousel for Home Page

    Restriction:
        - NIL

    Endpoints:
        - NIL

    Author:
        - Alex Lim
*/

const Information = () => {
  return (
    <div className="banner-wrapper bg-light">
      <div
        id="index_banner"
        className="banner-vertical-center-index container-fluid pt-1"
      >
        <div
          id="carouselExampleIndicators"
          className="carousel slide"
          data-bs-ride="carousel"
        >
          <ol className="carousel-indicators">
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="0"
              className="active"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="1"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="2"
            ></li>
          </ol>
          <div className="carousel-inner">
            <div className="carousel-item">
              <div className="py-5 row d-flex align-items-center">
                <div className="banner-content col-lg-8 col-8 offset-2 m-lg-auto text-left py-5 pb-5">
                  <h1 className="banner-heading h1 text-secondary display-3 mb-0 pb-3 mx-0 px-0 light-300">
                    E-waste Recycling Locations
                  </h1>
                  <iframe
                    width="450"
                    height="300"
                    title="map"
                    src="https://data.gov.sg/dataset/e-waste-recycling/resource/95b95773-8c29-4a32-a9e1-461e622bf092/view/30a9e8b2-fd0e-4083-ba87-8aa51a4f0200"
                  ></iframe>
                  <p className="banner-body text-muted py-3 mx-0 px-0">
                    <strong>
                      <a
                        href="https://data.gov.sg/dataset/e-waste-recycling?resource_id=95b95773-8c29-4a32-a9e1-461e622bf092"
                        target="_blank"
                        rel="noreferrer"
                        className="text-decoration-none"
                      >
                        National Environment Agency{" "}
                      </a>
                    </strong>
                    - Keep NEA's map handy when you're planning to recycle
                    e-waste.
                  </p>
                </div>
              </div>
            </div>
            <div className="carousel-item">
              <div className="py-5 row d-flex align-items-center">
                <div className="banner-content col-lg-8 col-8 offset-2 m-lg-auto text-left py-5 pb-5">
                  <h1 className="banner-heading h1 text-secondary display-3 mb-0 pb-3 mx-0 px-0 light-300">
                    Inside a Li-ion battery
                  </h1>
                  <p className="banner-body text-muted py-3">
                    All the components of a Li-ion battery have value and can be
                    recovered and reused. Currently, most recyclers recover just
                    the metals. The pie chart describes a cathode material known
                    as NCA, which is made of lithium nickel cobalt aluminum
                    oxide
                  </p>
                  <img
                    src="./../assets/img/Li-ion_battery_component.jpg"
                    alt=""
                    style={{ width: 400 }}
                  />
                </div>
              </div>
            </div>
            <div className="carousel-item active">
              <div className="py-5 row d-flex align-items-center">
                <div className="banner-content col-lg-8 col-8 offset-2 m-lg-auto text-left py-5 pb-5">
                  <h1 className="banner-heading h1 text-secondary display-3 mb-0 pb-3 mx-0 px-0 light-300">
                    Battery L!fe
                  </h1>
                  <p className="banner-body text-muted py-3">
                    Despite it being common knowledge that throwing batteries
                    into the trash is bad for the environment, much of the
                    population still discard them instead of using the battery
                    recycling banks offered by many supermarkets.
                  </p>
                  <p className="banner-body text-muted py-3">
                    Exchange your Old Batteries for New at our vending machines
                    Island-wide
                  </p>
                  <Link
                    className="banner-button btn rounded-pill btn-outline-primary btn-lg px-4"
                    to="/locations"
                    role="button"
                  >
                    See Locations
                  </Link>
                </div>
              </div>
            </div>
          </div>
          <a
            className="carousel-control-prev text-decoration-none"
            href="#carouselExampleIndicators"
            role="button"
            data-bs-slide="prev"
          >
            <i className="bx bx-chevron-left"></i>
            <span className="visually-hidden">Previous</span>
          </a>
          <a
            className="carousel-control-next text-decoration-none"
            href="#carouselExampleIndicators"
            role="button"
            data-bs-slide="next"
          >
            <i className="bx bx-chevron-right"></i>
            <span className="visually-hidden">Next</span>
          </a>
        </div>
      </div>
    </div>
  );
};

export default Information;
