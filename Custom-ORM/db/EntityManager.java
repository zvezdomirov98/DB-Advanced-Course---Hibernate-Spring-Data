package db;

import db.annotations.Column;
import db.annotations.Entity;
import db.annotations.Primary;
import db.base.DbContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
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
    private static final String INSERT_QUERY_TEMPLATE =
            "INSERT INTO {0}({1}) VALUES({2});";
    private static final String UPDATE_QUERY_TEMPLATE =
            "UPDATE {0} {1} WHERE {2}";
    private static final String SET_TEMPLATE =
            "SET {0} = {1}";
    private static final String CREATE_TABLE_TEMPLATE =
            "CREATE TABLE {0}({1});";
    private static final String CHECK_IF_TABLE_EXIST_QUERY = "SHOW TABLES LIKE {0}";
    private Connection dbConnection;
    private Class<T> klass;

    public EntityManager(Connection dbConnection, Class<T> klass) {
        this.dbConnection = dbConnection;
        this.klass = klass;
    }


    @Override
    public boolean persist(T entity) throws IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        if (!doesTableExist()) {
            doCreate();
        } else if (!areColumnsPersistent()) {
            doAlter();
        }
        Field primaryKeyField = getPrimaryKeyField();
        primaryKeyField.setAccessible(true);
        Object currentValue = primaryKeyField.get(entity);
        if (currentValue == null || (long) currentValue <= 0) {
            return this.doInsert(entity);
        } else {
            return this.doUpdate(entity);
        }
    }

    private void doAlter() {

    }

    private boolean areColumnsPersistent() throws SQLException {
        DatabaseMetaData dbmd = dbConnection.getMetaData();
        ResultSet columns =
                dbmd.getColumns(null,null,null,null);

        int i = 1;
        while(columns.next()) {
            columns.getMetaData().getColumnName(i);
            i++;
        }
        return false;
    }

    private boolean doesTableExist() throws SQLException {
        String query = MessageFormat
                .format(CHECK_IF_TABLE_EXIST_QUERY,
                        "'" + getTableName() + "'");
        ResultSet resultSet =
                dbConnection
                        .prepareStatement(query)
                        .executeQuery();
        return resultSet.next();
    }

    @Override
    public void delete(T entity) throws SQLException {

    }

    //TODO: Bug when the element is not in the DB
    @Override
    public T findFirst()
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        return findFirst(null);
    }

    @Override
    public T findFirst(String where) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {

        String template =
                where == null ?
                        SELECT_SINGLE_QUERY_TEMPLATE :
                        SELECT_SINGLE_WHERE_QUERY_TEMPLATE;
        return find(where, template).get(0);
    }

    @Override
    public List<T> find(String where) throws
            SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        String template =
                where == null ?
                        SELECT_QUERY_TEMPLATE :
                        SELECT_WHERE_QUERY_TEMPLATE;
        return find(where, template);
    }

    @Override
    public List<T> find()
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        return find(null);
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

    private void doCreate() throws SQLException {
        String tableName = getTableName();
        String tableParameters = String.join(
                ",\n",
                getTableParameters());
        String createQuery = MessageFormat
                .format(
                        CREATE_TABLE_TEMPLATE,
                        tableName,
                        tableParameters
                );
        dbConnection.prepareStatement(createQuery).executeUpdate();
    }

    private List<String> getTableParameters() {
        List<Field> javaFields = Arrays.asList(klass.getDeclaredFields());
        return parseToSqlTypes(javaFields);
    }

    private List<String> parseToSqlTypes(List<Field> javaFields) {
        final String VARCHAR_DEFAULT = "VARCHAR(250)";
        final String INT_DEFAULT = "INT";
        final String DATE_DEFAULT = "DATE";
        final String FLOAT_DEFAULT = "FLOAT";
        final String PRIMARY_KEY_POSTFIX = " PRIMARY KEY NOT NULL";

        List<String> result = new ArrayList<>();

        for (Field javaField : javaFields) {
            String current = javaField.getName() + " ";
            Class<?> fieldType = javaField.getType();
            if (fieldType == String.class) {

                current += VARCHAR_DEFAULT;
            } else if (fieldType == Integer.class ||
                    fieldType == int.class ||
                    fieldType == Long.class ||
                    fieldType == long.class) {

                current += INT_DEFAULT;
            } else if (fieldType == Double.class ||
                    fieldType == double.class ||
                    fieldType == Float.class ||
                    fieldType == float.class) {

                current += FLOAT_DEFAULT;
            } else if (fieldType == java.sql.Date.class) {

                current += DATE_DEFAULT;
            }
            if (javaField.isAnnotationPresent(Primary.class)) {
                current += PRIMARY_KEY_POSTFIX;
                if (current.contains(" " + INT_DEFAULT + " ")) {
                    current += " AUTO_INCREMENT";
                }
            }
            result.add(current);
        }
        return result;
    }

    private List<T> find(String where, String template)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, SQLException,
            IllegalAccessException {

        String query =
                MessageFormat.format(
                        template,
                        getTableName(),
                        where);
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        return toList(resultSet);
    }

    private boolean doUpdate(T entity)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, SQLException {

        //Check which properties are different from DB
        Field primaryKeyField = getPrimaryKeyField();
        primaryKeyField.setAccessible(true);
        String whereQuery =
                primaryKeyField.getName() +
                        " = " +
                        primaryKeyField.get(entity).toString();
        T dbEntity = findFirst(whereQuery);
        List<Field> differentColumns =
                getColumnFields().stream()
                        .filter(field -> {
                            field.setAccessible(true);
                            try {
                                return !field.get(entity)
                                        .equals(field.get(dbEntity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .collect(Collectors.toList());

        StringBuilder setQueryTemplate = new StringBuilder();

        for (Field column : differentColumns) {
            String columnName = column.getName();
            String newValue = column.get(entity).toString();
            setQueryTemplate
                    .append(MessageFormat
                            .format(
                                    SET_TEMPLATE,
                                    columnName,
                                    newValue
                            ))
                    .append(", ");
        }
        setQueryTemplate.replace(
                setQueryTemplate.length() - 2,
                setQueryTemplate.length() - 1,
                " ");
        String updateQuery = MessageFormat
                .format(
                        UPDATE_QUERY_TEMPLATE,
                        getTableName(),
                        setQueryTemplate,
                        whereQuery
                );
        int rowsAffected = dbConnection.prepareStatement(updateQuery).executeUpdate();
        return rowsAffected != 0;
    }

    private boolean doInsert(T entity) throws SQLException {

        List<Field> columnFields = getColumnFields();

        String columnNames = String.join(", ", getColumnNames(columnFields));

        String columnValues = getColumnValues(columnFields, entity).stream()
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(", "));

        String query = MessageFormat.format(
                INSERT_QUERY_TEMPLATE,
                getTableName(),
                columnNames,
                columnValues
        );
        return dbConnection.prepareStatement(query).execute();
    }

    private List<String> getColumnValues(List<Field> columnFields,
                                         T entity) {
        return columnFields.stream()
                .map(field -> {
                    try {
                        return field.get(entity).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private List<String> getColumnNames(List<Field> columnFields) {
        return columnFields.stream()
                .map(field -> {
                    field.setAccessible(true);
                    return field.getAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
    }

    private List<Field> getColumnFields() {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
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

        T entity = klass.newInstance();

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
        List<Field> columnFields = getColumnFields();
        for (Field columnField : columnFields) {
            String fieldName = columnField
                    .getAnnotation(Column.class)
                    .name();

            columnField.setAccessible(true);
            Class<?> fieldType = columnField.getType();
            if (fieldType == Long.class ||
                    fieldType == long.class ||
                    fieldType == Integer.class ||
                    fieldType == int.class) {
                columnField.set(entity, resultSet.getLong(fieldName));
            } else if (fieldType == String.class) {
                columnField.set(entity, resultSet.getString(fieldName));
            } else if (fieldType == java.sql.Date.class) {
                columnField.set(entity, resultSet.getDate(fieldName));
            } else if (fieldType == Double.class ||
            fieldType == double.class ||
            fieldType == Float.class ||
            fieldType == float.class) {
                columnField.set(entity, resultSet.getDouble(fieldName));
            }
        }
    }

    private String getTableName() {
        return klass.getAnnotation(Entity.class)
                .name();
    }
}
