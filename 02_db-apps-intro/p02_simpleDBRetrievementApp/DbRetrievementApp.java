package com.company.p02_simpleDBRetrievementApp;

import java.sql.*;
import java.util.*;

public class DbRetrievementApp {
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/diablo";
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);

        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();

        Connection connection = DriverManager.getConnection(
                connectionString, properties);
        String query =
                        "SELECT\n" +
                        "       u.first_name,\n" +
                        "       u.last_name,\n" +
                        "       u.user_name,\n" +
                        "       COUNT(g.id) AS num_of_played_games\n" +
                        "FROM games g\n" +
                        "JOIN users_games ug\n" +
                        "ON g.id = ug.game_id\n" +
                        "JOIN users u\n" +
                        "ON ug.user_id = u.id\n" +
                        "WHERE g.is_finished AND user_name = ?\n" +
                        "GROUP BY u.user_name,\n" +
                        "         u.first_name,\n" +
                        "         u.last_name\n" +
                        "ORDER BY u.user_name;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userName);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (!resultSet.next()) {
            System.out.println("No such user exists");
        } else {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                long gamesPlayed = resultSet.getInt("num_of_played_games");

                System.out.printf("User: %s%n%s %s has played %d games",
                        userName, firstName, lastName, gamesPlayed);
        }
    }
}
