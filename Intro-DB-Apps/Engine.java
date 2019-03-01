import java.sql.Connection;
import java.sql.*;

public class Engine implements Runnable {
    private Connection connection;

    public Engine(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            addMinion("Mars", 23, "Sofia", "Poppy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Problem 1: Get Villains' Names*/
    private void getVillainsNames() throws SQLException {
        String query =
                "SELECT\n" +
                "       v.name,\n" +
                "       COUNT(m.id) AS number_of_minions\n" +
                "FROM villains v\n" +
                "LEFT JOIN minions_villains mv\n" +
                "ON v.id = mv.villain_id\n" +
                "LEFT JOIN minions m\n" +
                "ON m.id = mv.minion_id\n" +
                "GROUP BY v.name\n" +
                "ORDER BY number_of_minions DESC;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            long numOfMinions = resultSet.getInt("number_of_minions");

            System.out.printf("%s %d%n", villainName, numOfMinions);
        }
    }

    /*Problem 2: Get Minion Names*/
    private void getMinionNames(int villainId) throws SQLException {
        String query =
                "SELECT\n" +
                        "       v.name AS villain_name,\n" +
                        "       m.name AS minion_name,\n" +
                        "       m.age AS minion_age\n" +
                        "FROM minions m\n" +
                        "JOIN minions_villains mv\n" +
                        "ON m.id = mv.minion_id\n" +
                        "RIGHT JOIN villains v\n" +
                        "ON v.id = mv.villain_id\n" +
                        "WHERE v.id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        StringBuilder output = new StringBuilder();
        if (!resultSet.next()) {
            output.append("No villain with ID ")
                    .append(villainId)
                    .append(" exists in the database.");
        } else {

            String villainName = resultSet.getString("villain_name");

            output.append("Villain: ")
                    .append(villainName);

            int minionCount = 0;
            String minionName = null;
            long minionAge = 0;
            while (resultSet.next()) {
                minionCount++;
                minionName = resultSet.getString("minion_name");
                minionAge = resultSet.getInt("minion_age");
                output.append(String.format("%n%d. %s %d",
                        minionCount, minionName, minionAge));
            }
            if (minionCount == 0) {
                output.append("\n <no minions>");
            }
        }
        System.out.println(output);
    }

    /*Problem 3: Add minion*/
    private void addMinion(String minionName,
                           long minionAge,
                           String townName,
                           String villainName) throws SQLException {
        final String DEFAULT_EVIL_FACTOR = "evil";

        if(!isTownInDb(townName)) {
            System.out.println(addTown(townName));
        }
        if (!isVillainInDb(villainName)) {
            System.out.println(addVillain(villainName, DEFAULT_EVIL_FACTOR));
        }

        String addMinionQuery =
                "INSERT INTO minions (name, age)\n" +
                "VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(addMinionQuery);
        preparedStatement.setString(1, minionName);
        preparedStatement.setLong(2, minionAge);
        preparedStatement.execute();
        System.out.println(setVillainServant(minionName, villainName));
    }

    private String setVillainServant(String minionName, String villainName) throws SQLException {
        String response = null;
        String getMinionIdQuery =
                "SELECT id\n" +
                "FROM minions\n" +
                "WHERE name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getMinionIdQuery);
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            long minionId = resultSet.getInt("id");

            String getVillainIdQuery =
                    "SELECT id\n" +
                    "FROM villains\n" +
                    "WHERE name = ?;";
            preparedStatement = connection.prepareStatement(getVillainIdQuery);
            preparedStatement.setString(1, villainName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long villainId = resultSet.getInt("id");

                String setServantQuery =
                        "INSERT IGNORE INTO minions_villains (minion_id, villain_id)\n" +
                        "VALUES (?, ?);";
                preparedStatement = connection.prepareStatement(setServantQuery);
                preparedStatement.setLong(1, minionId);
                preparedStatement.setLong(2, villainId);
                preparedStatement.execute();
                response = String.format("Successfully added %s to be minion of %s.%n",
                        minionName, villainName);
            } else {
                response = "Villain not found.";
            }
        } else {
            response = "Minion not found.";
        }
        return response;
    }

    private String addVillain(String villainName, String evilFactor) throws SQLException {
        String query =
                "INSERT INTO villains (name, evil_factor)\n" +
                "VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, villainName);
        preparedStatement.setString(2, evilFactor);
        preparedStatement.execute();

        return String.format("Villain %s was added to the database", villainName);
    }

    private boolean isVillainInDb(String villainName) throws SQLException {
        String query =
                "SELECT id\n" +
                "FROM villains\n" +
                "WHERE name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private String addTown(String townName) throws SQLException {
        String query =
                "INSERT INTO towns(name)\n" +
                "VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, townName);
        preparedStatement.execute();
        return String.format("Town %s was added to the database.", townName);
    }

    private boolean isTownInDb(String townName) throws SQLException {
        String query =
                "SELECT id\n" +
                "FROM towns\n" +
                "WHERE name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /*Problem 4: Change town names casing*/

}
