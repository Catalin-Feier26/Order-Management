package dataAccess;

import model.OrderDetail;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao extends DataAccess<OrderDetail> {

    public OrderDetailDao() {
        super();
    }

    @Override
    protected OrderDetail extractFromResultSet(ResultSet rs) throws SQLException {
        int orderdetailId = rs.getInt("orderdetailId");
        int ordertableId = rs.getInt("ordertableId");
        int productId = rs.getInt("productId");
        int quantity = rs.getInt("quantity");
        BigDecimal unitPrice = rs.getBigDecimal("unitPrice");

        return new OrderDetail(orderdetailId, ordertableId, productId, quantity, unitPrice);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM orderdetail WHERE ordertableId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            OrderDetail orderDetail = extractFromResultSet(resultSet);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}