import db.base.DbContext;
import db.EntityManager;
import entities.Product;
import entities.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Date;
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
        DbContext<Product> dbContext = getDbContext(connection, Product.class);
        Product cottageCheese = new Product("Olympus", 2.79);
        cottageCheese.setBatch("LSAKDJWFA1");
        cottageCheese.setId(3);
        dbContext.persist(cottageCheese);
        dbContext.find().stream()
                .map(Product::toString)
                .forEach(System.out::println);
//        User maria = dbContext.findFirst("username = 'maria'");
//        maria.setAge(21);
//        dbContext.persist(maria);
        connection.close();
    }

    private static <T> DbContext<T> getDbContext(Connection connection,
                                                 Class<T> klass) {
        return new EntityManager<>(connection, klass);
    }
}
