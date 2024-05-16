package businessLayer;

import dataAccess.OrderDetailDao;
import model.OrderDetail;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailManager {
    private OrderDetailDao orderDetailDao;
    private ProductManager productManager;

    public OrderDetailManager() {
        this.orderDetailDao = new OrderDetailDao();
        this.productManager = new ProductManager();
    }

    public OrderDetail createOrderDetail(int orderdetailId, int ordertableId, int productId, int quantity) throws SQLException {
        Product product = productManager.getProductById(productId);
        if(product == null) {
            throw new SQLException("Product with ID: " + productId + " does not exist");
        }
        if(product.getStockQuantity() < quantity) {
            throw new SQLException("Not enough stock for product with ID: " + productId);
        }
        OrderDetail orderDetail = new OrderDetail(orderdetailId, ordertableId, productId, quantity);
        OrderDetail createdOrderDetail = orderDetailDao.create(orderDetail);
        productManager.updateProduct(productId, product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity() - quantity);
        return createdOrderDetail;
    }

    public OrderDetail updateOrderDetail(int orderdetailId, int ordertableId, int productId, int quantity) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(orderdetailId, ordertableId, productId, quantity);
        return orderDetailDao.update(orderDetail);
    }

    public void deleteOrderDetail(int orderdetailId) throws SQLException {
        orderDetailDao.delete(orderdetailId);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }
    public List<OrderDetail> getOrderDetailsByProductId(int productId) {
        return orderDetailDao.getOrderDetailsByProductId(productId);
    }
    public void deleteOrderDetailsWithProductId(int productId) throws SQLException {
        List<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByProductId(productId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDao.delete(orderDetail.getOrderDetailId());
        }
    }
    public void deleteOrderDetailsWithOrderTableId(int ordertableId) throws SQLException{
        List<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByOrderId(ordertableId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDao.delete(orderDetail.getOrderDetailId());
        }
    }
}