package businessLayer;

import dataAccess.OrderDao;
import model.Bill;
import model.OrderTable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderManager {
    private OrderDao orderDao;

    public OrderManager() {
        this.orderDao = new OrderDao();
    }

    public OrderTable createOrder(int clientId, Timestamp orderDate) throws SQLException {
        OrderTable order = new OrderTable(0, clientId, orderDate);
        return orderDao.create(order);
    }

    public OrderTable updateOrder(int orderId, int clientId, Timestamp orderDate, List<Bill> bills) throws SQLException {
        OrderTable order = new OrderTable(orderId, clientId, orderDate);
        return orderDao.update(order);
    }

    public void deleteOrder(int orderId) throws SQLException {
        orderDao.delete(orderId);
    }

}