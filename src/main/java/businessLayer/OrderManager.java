package businessLayer;

import dataAccess.ClientDao;
import dataAccess.OrderDao;
import model.OrderDetail;
import model.OrderTable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderManager {
    private OrderDao orderDao;
    private ClientDao clientDao;
    private OrderDetailManager orderDetailManager;

    public OrderManager() {
        this.orderDao = new OrderDao();
        this.clientDao = new ClientDao();
        this.orderDetailManager = new OrderDetailManager();
    }

    public OrderTable createOrder(int clientId, Timestamp orderDate) throws SQLException {
        // Check if the clientId exists in the client table
        if (clientDao.read(clientId) == null) {
            throw new SQLException("Client with ID: " + clientId + " does not exist");
        }
        OrderTable order = new OrderTable(0, clientId, orderDate);
        return orderDao.create(order);
    }

    public OrderTable updateOrder(int orderId, int clientId, Timestamp orderDate) throws SQLException {
        // Check if the clientId exists in the client table
        if (clientDao.read(clientId) == null) {
            throw new SQLException("Client with ID: " + clientId + " does not exist");
        }
        OrderTable order = new OrderTable(orderId, clientId, orderDate);
        return orderDao.update(order);
    }

    public void deleteOrder(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = orderDetailManager.getOrderDetailsByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailManager.deleteOrderDetail(orderDetail.getOrderDetailId());
        }
        orderDao.delete(orderId);
    }
    public void deleteOrdersWithClientId(int clientId) throws SQLException {
        List<OrderTable> orders = orderDao.getOrdersByClientId(clientId);
        for (OrderTable order : orders) {
            deleteOrder(order.getOrderTableId());
        }
    }
    public List<OrderTable> getAllOrders() throws SQLException {
        return orderDao.readAll();
    }
}