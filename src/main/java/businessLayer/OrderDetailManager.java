package businessLayer;

import dataAccess.OrderDetailDao;
import model.OrderDetail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailManager {
    private OrderDetailDao orderDetailDao;

    public OrderDetailManager() {
        this.orderDetailDao = new OrderDetailDao();
    }

    public OrderDetail createOrderDetail(int orderdetailId, int ordertableId, int productId, int quantity, BigDecimal unitPrice) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(orderdetailId, ordertableId, productId, quantity, unitPrice);
        return orderDetailDao.create(orderDetail);
    }

    public OrderDetail updateOrderDetail(int orderdetailId, int ordertableId, int productId, int quantity, BigDecimal unitPrice) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(orderdetailId, ordertableId, productId, quantity, unitPrice);
        return orderDetailDao.update(orderDetail);
    }

    public void deleteOrderDetail(int orderdetailId) throws SQLException {
        orderDetailDao.delete(orderdetailId);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }
}