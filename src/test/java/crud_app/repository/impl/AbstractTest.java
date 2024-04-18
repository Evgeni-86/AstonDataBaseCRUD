package crud_app.repository.impl;


import crud_app.utils.DataBase;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public abstract class AbstractTest {

    private static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:15")
            .withInitScript("init.sql");

    static {
        postgresDBContainer.start();
        Properties properties = new Properties();
        properties.put("database.driver", postgresDBContainer.getDriverClassName());
        properties.put("database.url", postgresDBContainer.getJdbcUrl());
        properties.put("database.username", postgresDBContainer.getUsername());
        properties.put("database.password", postgresDBContainer.getPassword());
        DataBase.setProperties(properties);
    }
}