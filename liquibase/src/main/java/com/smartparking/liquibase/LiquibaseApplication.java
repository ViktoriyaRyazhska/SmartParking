package com.smartparking.liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LiquibaseApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseApplication.class);
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "smartparking";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String CHANGE_LOG_FILE = "com/smartparking/liquibase/changelog-master.xml";

    public static void main(String[] args) {
        LOGGER.info("Creating a new database and filling it with data.");
        try {
            registerDriver();
            createDatabase();
            initializeDatabase();
        } catch (SQLException | ClassNotFoundException | LiquibaseException exception) {
            LOGGER.error("You have done exception.", exception);
        }
    }

    private static void createDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        statement.execute("CREATE DATABASE " + DATABASE_NAME + ";");
        statement.close();
        connection.close();
    }

    private static void initializeDatabase() throws SQLException, LiquibaseException {
        Connection databaseConnection = DriverManager.getConnection(CONNECTION_URL + DATABASE_NAME, USER, PASSWORD);
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(databaseConnection));
        Liquibase liquibase = new Liquibase(CHANGE_LOG_FILE, new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
        databaseConnection.close();
    }

    private static void registerDriver() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
    }
}
