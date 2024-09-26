package com.app.repository.impl;

import org.jdbi.v3.testing.junit5.JdbiExtension;
import org.jdbi.v3.testing.junit5.tc.JdbiTestcontainersExtension;
import org.jdbi.v3.testing.junit5.tc.TestcontainersDatabaseInformation;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class Base {
    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:latest")
            .withUsername("user")
            .withPassword("user1234")
            .withDatabaseName("test_db")
            .withInitScript("scripts/init.sql");

    private static final TestcontainersDatabaseInformation MYSQL = TestcontainersDatabaseInformation.of(
            MYSQL_CONTAINER.getUsername(),
            MYSQL_CONTAINER.getDatabaseName(),
            MYSQL_CONTAINER.getPassword(),
            (catalogName, schemaName) -> String.format("create database if not exists %s", catalogName)
    );

    @RegisterExtension
    static JdbiExtension jdbiExtension = JdbiTestcontainersExtension
            .instance(MYSQL, MYSQL_CONTAINER);
}
