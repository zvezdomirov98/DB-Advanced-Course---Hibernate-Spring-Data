package p03_villainNames;

import java.sql.*;
import java.util.Properties;

public class VillainNames {
    public static void main(String[] args) throws SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/MinionsDB";
        Properties properties = new Properties();
        properties.setProperty("user", DbAccessConstants.DB_USER);
        properties.setProperty("password", DbAccessConstants.DB_PSWD);
        try (Connection connection = DriverManager.getConnection(connectionString, properties)) {
            String sqlQuery =
                            "SELECT v.name,\n" +
                            "       COUNT(m.id) AS number_of_minions\n" +
                            "FROM villains v\n" +
                            "       LEFT JOIN minions_villains mv\n" +
                            "                 ON v.id = mv.villain_id\n" +
                            "       LEFT JOIN minions m\n" +
                            "                 ON mv.minion_id = m.id\n" +
                            "GROUP BY v.name\n" +
                            "ORDER BY number_of_minions DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            //Don't forget to set parameters if needed!
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()) {
                String villainName = resultSet.getString("name");
                long villainSlaves = resultSet.getInt("number_of_minions");
                System.out.printf("%s %s%n", villainName, villainSlaves);
            }
        }
    }
}
