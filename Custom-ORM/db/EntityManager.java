package db;

import db.annotations.Column;
import db.annotations.Entity;
import db.annotations.Primary;
import db.base.DbContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<T> implements DbContext<T> {
    private static final String SELECT_QUERY_TEMPLATE =
            "SELECT * FROM {0}";
    private static final String SELECT_WHERE_QUERY_TEMPLATE =
            "SELECT * FROM {0} WHERE {1}";
    private static final String SELECT_SINGLE_QUERY_TEMPLATE =
            "SELECT * FROM {0} LIMIT 1";
    private static final String SELECT_SINGLE_WHERE_QUERY_TEMPLATE =
            "SELECT * FROM {0} WHERE {1} LIMIT 1";
    private Connection dbConnection;
    private Class<T> klass;

    public EntityManager(Connection dbConnection, Class<T> klass) {
        this.dbConnection = dbConnection;
        this.klass = klass;
    }

    private Field getPrimaryKeyField() {
        return Arrays.stream(
                klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Primary.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Class " + klass.getName() +
                                " has no primary key annotation present."
                ));
    }

    @Override
    public boolean persist(T entity) {
        return false;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public T findFirst()
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        return findFirst(null);
    }

    @Override
    public T findFirst(String where) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {

        String template = where == null ?
                MessageFormat.format(
                        SELECT_SINGLE_QUERY_TEMPLATE,
                        getTableName()) :
                MessageFormat.format(
                        SELECT_SINGLE_WHERE_QUERY_TEMPLATE,
                        getTableName(),
                        where
                );
        return find(where, template).get(0);
    }

    @Override
    public List<T> find(String where) throws
            SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        String template = where == null ?
                MessageFormat.format(
                        SELECT_QUERY_TEMPLATE,
                        getTableName()) :
                MessageFormat.format(
                        SELECT_WHERE_QUERY_TEMPLATE,
                        getTableName(),
                        where
                );
        return find(where, template);
    }

    @Override
    public List<T> find()
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        return find(null);
    }

    private List<T> find(String where, String template)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, SQLException,
            IllegalAccessException {

        PreparedStatement preparedStatement = dbConnection.prepareStatement(template);
        ResultSet resultSet = preparedStatement.executeQuery();
        return toList(resultSet);
    }

    private List<T> toList(ResultSet resultSet) throws
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException,
            SQLException {

        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            T entity = createEntity(resultSet);
            result.add(entity);
        }
        return result;
    }

    private T createEntity(ResultSet resultSet) throws
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, SQLException {

        T entity = klass.getConstructor().newInstance();

        this.setEntityPrimaryKey(resultSet, entity);
        this.setEntityColumns(resultSet, entity);

        return entity;
    }

    private void setEntityPrimaryKey(ResultSet resultSet,
                                     T entity)
            throws SQLException, IllegalAccessException {

        Field primaryKeyField = this.getPrimaryKeyField();
        String primaryFieldName = primaryKeyField
                .getAnnotation(Primary.class)
                .name();
        primaryKeyField.setAccessible(true);
        primaryKeyField.set(entity, resultSet.getLong(primaryFieldName));
    }

    private void setEntityColumns(ResultSet resultSet,
                                  T entity) throws SQLException, IllegalAccessException {
        List<Field> columnFields = Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        for (Field columnField : columnFields) {
            String fieldName = columnField
                    .getAnnotation(Column.class)
                    .name();

            columnField.setAccessible(true);
            if (columnField.getType() == Long.class ||
                    columnField.getType() == long.class ||
                    columnField.getType() == Integer.class ||
                    columnField.getType() == int.class) {
                columnField.set(entity, resultSet.getLong(fieldName));
            } else if (columnField.getType() == String.class) {
                columnField.set(entity, resultSet.getString(fieldName));
            } else if (columnField.getType() == Date.class) {
                columnField.set(entity, resultSet.getDate(fieldName));
            }
        }
    }

    private String getTableName() {
        return klass.getAnnotation(Entity.class)
                .name();
    }
}
