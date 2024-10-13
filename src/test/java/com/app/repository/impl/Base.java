package com.app.repository.impl;

import org.jdbi.v3.testing.junit5.JdbiExtension;
import org.jdbi.v3.testing.junit5.tc.JdbiTestcontainersExtension;
import org.jdbi.v3.testing.junit5.tc.TestcontainersDatabaseInformation;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.MySQLContainer;

@SuppressWarnings("resource")
abstract class Base{
    static final MySQLContainer<?> mySQLContainer;
    static {
        mySQLContainer = new MySQLContainer<>("mysql:latest")
                .withUsername("user")
                .withPassword("user1234")
                .withDatabaseName("test_db")
                .withInitScript("scripts/init.sql");
        mySQLContainer.start();
    }

    static TestcontainersDatabaseInformation mySql = TestcontainersDatabaseInformation.of(
            mySQLContainer.getUsername(),
            mySQLContainer.getDatabaseName(),
            mySQLContainer.getPassword(),
            (catalogName, schemaName) -> String.format("create database if not exists %s", catalogName)
    );

    @RegisterExtension
    public static JdbiExtension jdbiExtension = JdbiTestcontainersExtension
            .instance(mySql, mySQLContainer);
}
