package dataAccess;

import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * The OrderDetailDao class is responsible for managing order details in the application.
 * It uses DataAccess to perform all operations related to order details.
 */
public class OrderDetailDao extends DataAccess<OrderDetail> {
    /**
     * Constructs a new OrderDetailDao.
     */
    public OrderDetailDao() {
        super();
    }
    /**
     * Creates a new order detail with the given order detail.
     * @param orderDetail the order detail to create
     * @return the created order detail
     */
    @Override
    protected OrderDetail extractFromResultSet(ResultSet rs) throws SQLException {
        int orderdetailId = rs.getInt("orderdetailId");
        int ordertableId = rs.getInt("ordertableId");
        int productId = rs.getInt("productId");
        int quantity = rs.getInt("quantity");

        return new OrderDetail(orderdetailId, ordertableId, productId, quantity);
    }
    /**
     * Creates a new order detail with the given order detail ID, order table ID, product ID, and quantity.
     * @param orderDetail the order detail to create
     * @return the created order detail
     */
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM orderdetail WHERE ordertableId = ?";
        PreparedStatement preparedStatement=null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = extractFromResultSet(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return orderDetails;
    }
    /**
     * Creates a new order detail with the given order detail ID, order table ID, product ID, and quantity.
     * @param orderDetail the order detail to create
     * @return the created order detail
     */
    public List<OrderDetail> getOrderDetailsByProductId(int productId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM orderdetail WHERE productId = ?";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = extractFromResultSet(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeStatement(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return orderDetails;
    }
}