import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/MinionsDB";
        Properties properties = new Properties();
        properties.setProperty("user", DbAccessConstants.DB_USER);
        properties.setProperty("password", DbAccessConstants.DB_PSWD);
        try (Connection connection = DriverManager.getConnection(connectionString, properties)) {
            Engine engine = new Engine(connection);
            engine.run();
        }
    }
}
