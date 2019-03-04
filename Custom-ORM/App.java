import db.base.DbContext;
import db.EntityManager;
import entities.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class App {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/online_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234qwer";

    public static void main(String[] args) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        Connection connection = DriverManager
                .getConnection(CONNECTION_STRING, props);
        User user = new User("Mitio", 20);
        DbContext<User> dbContext = getDbContext(connection, User.class);
        List<User> users = dbContext.find();
        users.forEach(System.out::println);
        System.out.println("-----------------------");
        System.out.println(dbContext.findFirst("id = 13"));
        System.out.println(dbContext.findFirst());
        connection.close();
    }

    private static <T> DbContext<T> getDbContext(Connection connection,
                                                 Class<T> klass) {
        return new EntityManager<>(connection, klass);
    }
}
