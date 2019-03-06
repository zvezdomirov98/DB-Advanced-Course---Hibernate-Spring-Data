package db.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<T> {
    boolean persist(T entity) throws IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException;

    T findFirst() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    T findFirst(String where) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException;

    List<T> find() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    List<T> find(String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void delete(T entity);
}
