package businessLayer;

import dataAccess.ClientDao;
import dataAccess.OrderDao;
import model.OrderDetail;
import model.OrderTable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
/**
 * The OrderManager class is responsible for managing orders in the application.
 * It uses OrderDetailManager to perform all operations related to order details.
 * It communicates with the OrderDao and ClientDao to perform its operations.
 */
public class OrderManager {
    private OrderDao orderDao;
    private ClientDao clientDao;
    private OrderDetailManager orderDetailManager;
    /**
     * Constructs a new OrderManager with a new OrderDao, ClientDao, and OrderDetailManager.
     */
    public OrderManager() {
        this.orderDao = new OrderDao();
        this.clientDao = new ClientDao();
        this.orderDetailManager = new OrderDetailManager();
    }
    /**
     * Creates a new order with the given client ID and order date.
     * @param clientId the ID of the client
     * @param orderDate the date of the order
     * @return the created order
     * @throws SQLException if an error occurs while creating the order
     */
    public OrderTable createOrder(int clientId, Timestamp orderDate) throws SQLException {
        // Check if the clientId exists in the client table
        if (clientDao.read(clientId) == null) {
            throw new SQLException("Client with ID: " + clientId + " does not exist");
        }
        OrderTable order = new OrderTable(0, clientId, orderDate);
        return orderDao.create(order);
    }
    /**
     * Updates the order with the given order ID, client ID, and order date.
     * @param orderId the ID of the order
     * @param clientId the ID of the client
     * @param orderDate the date of the order
     * @return the updated order
     * @throws SQLException if an error occurs while updating the order
     */
    public OrderTable updateOrder(int orderId, int clientId, Timestamp orderDate) throws SQLException {
        // Check if the clientId exists in the client table
        if (clientDao.read(clientId) == null) {
            throw new SQLException("Client with ID: " + clientId + " does not exist");
        }
        OrderTable order = new OrderTable(orderId, clientId, orderDate);
        return orderDao.update(order);
    }
    /**
     * Deletes the order with the given order ID.
     * @param orderId the ID of the order
     * @throws SQLException if an error occurs while deleting the order
     */
    public void deleteOrder(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = orderDetailManager.getOrderDetailsByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailManager.deleteOrderDetail(orderDetail.getOrderDetailId());
        }
        orderDao.delete(orderId);
    }
    /**
     * Gets the order with the given order ID.
     * @param orderId the ID of the order
     * @return the order with the given order ID
     * @throws SQLException if an error occurs while getting the order
     */
    public void deleteOrdersWithClientId(int clientId) throws SQLException {
        List<OrderTable> orders = orderDao.getOrdersByClientId(clientId);
        for (OrderTable order : orders) {
            deleteOrder(order.getOrderTableId());
        }
    }
    /**
     * Gets all orders in the application.
     * @return a list of all orders
     * @throws SQLException if an error occurs while getting all orders
     */
    public List<OrderTable> getAllOrders() throws SQLException {
        return orderDao.readAll();
    }
}