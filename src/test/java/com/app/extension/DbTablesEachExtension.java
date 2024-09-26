package com.app.extension;

import lombok.Setter;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DbTablesEachExtension implements BeforeEachCallback, AfterEachCallback {
    @Setter
    private static Jdbi jdbi;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        createTables(jdbi);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        dropTables(jdbi);
    }

    private void createTables(Jdbi jdbi) {
        var countrySql = """
                CREATE TABLE IF NOT EXISTS countries (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) UNIQUE NOT NULL
                );
                """;

        var personSql = """
                CREATE TABLE IF NOT EXISTS persons (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    surname VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL
                );
                """;

        var toursSql = """
                CREATE TABLE IF NOT EXISTS tours (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    agency_id INTEGER NOT NULL,
                    country_id INTEGER NOT NULL,
                    price_per_person DECIMAL(10, 2) NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    FOREIGN KEY (country_id) REFERENCES countries(id)
                );
                """;

        var reservationSql = """
                CREATE TABLE IF NOT EXISTS reservations (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    tour_id INTEGER NOT NULL,
                    agency_id INTEGER NOT NULL,
                    customer_id INTEGER NOT NULL,
                    quantity_of_people INTEGER NOT NULL,
                    discount INTEGER NOT NULL,
                    FOREIGN KEY (tour_id) REFERENCES tours(id),
                    FOREIGN KEY (customer_id) REFERENCES persons(id)
                );
                """;

        var componentsSql = """
                CREATE TABLE IF NOT EXISTS reservation_components (
                    reservation_id INTEGER NOT NULL,
                    component VARCHAR(50) NOT NULL,
                    PRIMARY KEY (reservation_id, component),
                    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
                );
                """;

        jdbi.useHandle(handle -> {
            handle.inTransaction(transactionHandle -> {
                transactionHandle.execute(countrySql);
                transactionHandle.execute(personSql);
                transactionHandle.execute(toursSql);
                transactionHandle.execute(reservationSql);
                transactionHandle.execute(componentsSql);
                return null;
            });
        });

    }

    private void dropTables(Jdbi jdbi) {
        jdbi.useHandle(handle -> {
            handle.execute("DROP TABLE IF EXISTS reservation_components;");
            handle.execute("DROP TABLE IF EXISTS reservations;");
            handle.execute("DROP TABLE if exists tours;");
            handle.execute("DROP TABLE IF EXISTS persons");
            handle.execute("DROP TABLE IF EXISTS countries");
        });
    }
}
