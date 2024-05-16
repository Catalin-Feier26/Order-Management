package dataAccess;

import model.OrderTable;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends DataAccess<OrderTable> {
    public OrderDao() {
        super();
    }

    public List<OrderTable> getOrdersByClientId(int clientId) {
        List<OrderTable> orders = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = "SELECT * FROM ordertable WHERE clientId = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clientId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderTable order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeResultSet(resultSet);
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return orders;
    }

}
