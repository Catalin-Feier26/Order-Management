package businessLayer;

import dataAccess.ClientDao;
import dataAccess.OrderDao;
import model.OrderTable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderManager {
    private OrderDao orderDao;
    private ClientDao clientDao;

    public OrderManager() {
        this.orderDao = new OrderDao();
        this.clientDao = new ClientDao();
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
        orderDao.delete(orderId);
    }
    public List<OrderTable> getAllOrders() throws SQLException {
        return orderDao.readAll();
    }
}