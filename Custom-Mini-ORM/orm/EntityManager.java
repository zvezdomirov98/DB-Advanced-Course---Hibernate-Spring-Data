package orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        setConnection(connection);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Iterable<E> find(Class<E> table) {
        return null;
    }

    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    public E findFirst(Class<E> table) {
        return null;
    }

    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getPrimary(Class klass) {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                        "This class has no primary key declared."));
    }

    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getPrimary(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        if (value == null || (int) value <= 0) {
            return this.doInsert(entity, primary);
        } else {
            return this.doUpdate(entity, primary);
        }
    }

    private String getTableName(E entity) {
        return entity.getClass()
                .getAnnotation(Entity.class)
                .name();
    }

    private boolean doUpdate(E entity, Field primary) {
        String tableName = getTableName(entity);
        StringBuilder query = new StringBuilder();

        return false;
    }

    private boolean doInsert(E entity, Field primary)
            throws SQLException, IllegalAccessException {
        String tableName = entity.getClass().
                getAnnotation(Entity.class).name();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ")
                .append(tableName)
                .append(" (");

        List<Field> columnFields= Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Column.class) != null)
                .collect(Collectors.toList());

        for (Field field : columnFields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                query.append(field.getAnnotation(Column.class).name())
                        .append(", ");
            }
        }

        query.replace(query.length() - 2, query.length() - 1, ") ");
        query.append("VALUES (");


        for (Field field : columnFields) {
            field.setAccessible(true);
            query.append("'")
                    .append(field.get(entity))
                    .append("', ");
        }
        query.replace(query.length() - 2, query.length() - 1, ")");
        PreparedStatement preparedStatement =
                connection.prepareStatement(query.toString());

        return preparedStatement.execute();
    }


//    private Field getId(Class entity) {
//        return Arrays.stream(entity.getDeclaredFields())
//                .filter(field -> field.isAnnotationPresent(Id.class))
//                .findFirst()
//                .orElseThrow(() -> new UnsupportedOperationException(
//                        "Entity does not have a primary key."));
//    }
//
//    public boolean persist(E entity) throws IllegalAccessException {
//        Field primary = this.getId(entity.getClass());
//        primary.setAccessible(true);
//        Object value = primary.get(entity);
//
//        if (value == null || (int) value <= 0) {
//            return this.doInsert(entity, primary);
//        } else {
//            return this.doUpdate(entity, primary);
//        }
//    }
//
//    private boolean doInsert(E entity, Field primary) {
//        String tableName = this.getTableName(entity.getClass());
//        StringBuilder query = new StringBuilder();
//        query.append("INSERT INTO ")
//                .append(tableName)
//                .append(" (");
//        for (Field field : entity.getClass().getDeclaredFields()) {
//            query.append(field.getName())
//                    .append(", ");
//        }
//        query.replace(query.length() - 2, query.length() - 1, ") ");
//        query.append("VALUES (")
//                .append(entity.getClass().)
//        return false;
//    }
}
