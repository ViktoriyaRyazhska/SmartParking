package liquibase;

import java.sql.*;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionWithJDBC {

    /**
     * JDBC Driver and database url
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionWithJDBC.class);
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/smartparking";
    private static final String USER = "root";
    private static final String PASSWORD = "abcde1234567890";


    public static void main(String[] args) {

        LOGGER.info("-------------------Creating a new database and filling it with data---------------------------- ");

        try {
            Class.forName(JDBC_DRIVER);

            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new liquibase.Liquibase("liquibase/db-changelog-master.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());

            connection.close();
        } catch (SQLException exception) {
            LOGGER.info("You have done sql exception");
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            LOGGER.info("You have done classNotFoundException exception");
            exception.printStackTrace();
        } catch (LiquibaseException exception) {
            LOGGER.info("You have done LiquibaseException exception");
            exception.printStackTrace();
        }


    }
}
