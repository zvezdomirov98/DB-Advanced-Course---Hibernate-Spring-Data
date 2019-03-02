package orm;

import entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        String username = "root";
        String password = "1234qwer";
        String dbName = "online_store";

        Connector.createConnection(username, password, dbName);
        EntityManager<User> em = new EntityManager<>(Connector.getConnection());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String dateString = format.format( new Date()   );
        User pesho = new User("Pesho", 20, new java.sql.Date(new Date().getTime()));
        em.persist(pesho);
    }
}
