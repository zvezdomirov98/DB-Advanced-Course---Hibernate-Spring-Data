package com.company.p01_jdbcConnectionDemo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcConnectionDemo {

    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234qwer");

        String connectionString = "jdbc:mysql://localhost:3306/soft_uni";
        Connection connection = DriverManager.getConnection(connectionString, props);

        PreparedStatement preparedStatement = connection.prepareStatement( "" +
                    "SELECT CONCAT(e.first_name, ' ', e.last_name) AS full_name\n" +
                    "FROM employees e\n" +
                    "WHERE e.salary > ?;\n");

        double minSalary = 70000;
        preparedStatement.setDouble(1, minSalary);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();

        List<String> resultList = new ArrayList<>();

        while(resultSet.next()) {
            String fullName = resultSet.getString("full_name");
            resultList.add(fullName);
        }

        resultList.forEach(System.out::println);
    }
}
