CREATE TABLE IF NOT EXISTS countries
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS persons
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(255)        NOT NULL,
    surname VARCHAR(255)        NOT NULL,
    email   VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tours
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    agency_id        INTEGER        NOT NULL,
    country_id       INTEGER        NOT NULL,
    price_per_person DECIMAL(10, 2) NOT NULL,
    start_date       DATE           NOT NULL,
    end_date         DATE           NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE IF NOT EXISTS reservations
(
    id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
    tour_id            INTEGER NOT NULL,
    agency_id          INTEGER NOT NULL,
    customer_id        INTEGER NOT NULL,
    quantity_of_people INTEGER NOT NULL,
    discount           INTEGER NOT NULL,
    FOREIGN KEY (tour_id) REFERENCES tours (id),
    FOREIGN KEY (customer_id) REFERENCES persons (id)
);

CREATE TABLE IF NOT EXISTS reservation_components
(
    reservation_id INTEGER     NOT NULL,
    component      VARCHAR(50) NOT NULL,
    PRIMARY KEY (reservation_id, component),
    FOREIGN KEY (reservation_id) REFERENCES reservations (id) ON DELETE CASCADE
);