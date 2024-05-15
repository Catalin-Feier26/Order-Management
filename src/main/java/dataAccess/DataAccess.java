package dataAccess;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccess<T> {
    protected Connection connection;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public DataAccess(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.connection = ConnectionFactory.getConnection();
    }

    public T create(T t) throws SQLException {
        String query = createInsert(t);
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setParameters(preparedStatement, t, query);
        int noRows = preparedStatement.executeUpdate();
        if (noRows == 0)
            throw new SQLException("Insert failed");
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                t.getClass().getMethod("set" + type.getSimpleName() + "Id", int.class).invoke(t, generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
    public List<T> readAll() throws SQLException {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            T entity = extractFromResultSet(resultSet);
            list.add(entity);
        }
        return list;
    }
    public T read(int id) throws SQLException {
        String query = createSelect(type);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            return extractFromResultSet(resultSet);
        return null;
    }

    public T update(T t) throws SQLException {
        String query = createUpdate(t);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        setParameters(preparedStatement, t, query);
        int noRows = preparedStatement.executeUpdate();
        if (noRows == 0)
            throw new SQLException("Update failed");
        return t;
    }

    public void delete(int id) throws SQLException {
        String query = createDelete(id);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int noRows = preparedStatement.executeUpdate();
        if (noRows == 0)
            throw new SQLException("Delete failed");
        else
            System.out.println("Delete successful");
    }

    protected T extractFromResultSet(ResultSet rs) throws SQLException {
        T entity = null;
        try {
            Field[] fields = type.getDeclaredFields();

            Class<?>[] parameterTypes = new Class[fields.length];

            List<Object> values = new ArrayList<>();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Object value = rs.getObject(field.getName());
                if (fieldType == double.class && value instanceof BigDecimal) {
                    value = ((BigDecimal) value).doubleValue();
                }
                parameterTypes[i] = fieldType;
                values.add(value);
            }
            Constructor<T> constructor = type.getConstructor(parameterTypes);
            entity = constructor.newInstance(values.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private String createInsert(T t) {
        StringBuilder query= new StringBuilder("INSERT INTO ");
        query.append(t.getClass().getSimpleName().toLowerCase());
        query.append(" (");
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            query.append(field.getName());
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (");
        for (int i=0; i<t.getClass().getDeclaredFields().length; i++) {
            query.append("?");
            if(i<t.getClass().getDeclaredFields().length-1)
                query.append(",");
        }
        query.append(")");
        return query.toString();
    }

    private String createUpdate(T t) {
        StringBuilder query= new StringBuilder("UPDATE ");
        query.append(t.getClass().getSimpleName().toLowerCase());
        query.append(" SET ");
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            query.append(field.getName());
            query.append(" = ?,");
        }
        query.deleteCharAt(query.length()-1);
        query.append(" WHERE ");
        query.append(t.getClass().getSimpleName().toLowerCase());
        query.append("Id = ?");
        return query.toString();
    }

    private String createDelete(int id) {
        StringBuilder query= new StringBuilder("DELETE FROM ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" WHERE ");
        query.append(type.getSimpleName().toLowerCase());
        query.append("Id = ?");
        return query.toString();
    }

    private String createSelect(Class<T> type) {
        StringBuilder query= new StringBuilder("SELECT * FROM ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" WHERE ");
        query.append(type.getSimpleName().toLowerCase());
        query.append("Id = ?");
        return query.toString();
    }

    private void setParameters(PreparedStatement preparedStatement, T t, String query) throws SQLException {
        int i = 1;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            preparedStatement.setObject(i, value);
            i++;
        }
        // Set the additional parameter for the WHERE clause only if the query contains a WHERE clause
        if (query.contains("WHERE")) {
            try {
                Object idValue = t.getClass().getMethod("get" + type.getSimpleName() + "Id").invoke(t);
                preparedStatement.setObject(i, idValue);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}