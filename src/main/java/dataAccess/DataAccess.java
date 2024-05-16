package dataAccess;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * The DataAccess class is responsible for managing the data access operations in the application.
 * It uses the ConnectionFactory class to establish a connection to the database.
 * It uses reflection to extract data from the ResultSet and set parameters in the PreparedStatement.
 * @param <T> the type of the entity
 */
public class DataAccess<T> {
    protected Connection connection;
    private final Class<T> type;
    /**
     * Constructs a new DataAccess with the type of the entity.
     */
    @SuppressWarnings("unchecked")
    public DataAccess(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.connection = ConnectionFactory.getConnection();
    }
    /**
     * Creates a new entity in the database.
     * @param t the entity to create
     * @return the created entity
     * @throws SQLException if an error occurs while creating the entity
     */
    public T create(T t) throws SQLException {
        String query = createInsert(t);
        PreparedStatement preparedStatement=null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
            } finally {
                ConnectionFactory.closeStatement(preparedStatement);
                ConnectionFactory.close(connection);
            }
        }finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    /**
     * Reads all entities from the database.
     * @return a list of all entities
     * @throws SQLException if an error occurs while reading the entities
     */
    public List<T> readAll() throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = "SELECT * FROM " + type.getSimpleName().toLowerCase();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T entity = extractFromResultSet(resultSet);
                list.add(entity);
            }
        }finally {
            ConnectionFactory.closeResultSet(resultSet);
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return list;
    }
    /**
     * Reads the entity with the given ID from the database.
     * @param id the ID of the entity
     * @return the entity with the given ID
     * @throws SQLException if an error occurs while reading the entity
     */
    public T read(int id) throws SQLException {
        T result=null;
        String query = createSelect(type);
        PreparedStatement preparedStatement= null;
        try {
            connection= ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                result= extractFromResultSet(resultSet);
        } finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return result;
    }
    /**
     * Updates the entity in the database.
     * @param t the entity to update
     * @return the updated entity
     * @throws SQLException if an error occurs while updating the entity
     */
    public T update(T t) throws SQLException {
        String query = createUpdate(t);
        PreparedStatement preparedStatement=null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, t, query);
            int noRows = preparedStatement.executeUpdate();
            if (noRows == 0)
                throw new SQLException("Update failed");
        } finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    /**
     * Deletes the entity with the given ID from the database.
     * @param id the ID of the entity
     * @throws SQLException if an error occurs while deleting the entity
     */
    public void delete(int id) throws SQLException {
        String query = createDelete(id);
        PreparedStatement preparedStatement= null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int noRows = preparedStatement.executeUpdate();
            if (noRows == 0)
                throw new SQLException("Delete failed");
            else
                System.out.println("Delete successful");
        }finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * Extracts the entity from the ResultSet.
     * @param rs the ResultSet
     * @return the entity
     * @throws SQLException if an error occurs while extracting the entity
     */
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
    /**
     * Creates an INSERT query for the entity.
     * @param t the entity
     * @return the INSERT query
     */
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
    /**
     * Creates an UPDATE query for the entity.
     * @param t the entity
     * @return the UPDATE query
     */
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
    /**
     * Creates a DELETE query for the entity.
     * @param id the ID of the entity
     * @return the DELETE query
     */
    private String createDelete(int id) {
        StringBuilder query= new StringBuilder("DELETE FROM ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" WHERE ");
        query.append(type.getSimpleName().toLowerCase());
        query.append("Id = ?");
        return query.toString();
    }
    /**
     * Creates a SELECT query for the entity.
     * @param type the type of the entity
     * @return the SELECT query
     */
    private String createSelect(Class<T> type) {
        StringBuilder query= new StringBuilder("SELECT * FROM ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" WHERE ");
        query.append(type.getSimpleName().toLowerCase());
        query.append("Id = ?");
        return query.toString();
    }
    /**
     * Sets the parameters in the PreparedStatement.
     * @param preparedStatement the PreparedStatement
     * @param t the entity
     * @param query the query
     * @throws SQLException if an error occurs while setting the parameters
     */
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