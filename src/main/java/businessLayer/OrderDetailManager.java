package businessLayer;

import dataAccess.OrderDetailDao;
import model.OrderDetail;
import model.Product;

import java.sql.SQLException;
import java.util.List;
/**
 * The OrderDetailManager class is responsible for managing order details in the application.
 * It uses ProductManager to perform its operations.
 */
public class OrderDetailManager {
    private OrderDetailDao orderDetailDao;
    private ProductManager productManager;
    /**
     * Constructs a new OrderDetailManager with a new OrderDetailDao and ProductManager.
     */
    public OrderDetailManager() {
        this.orderDetailDao = new OrderDetailDao();
        this.productManager = new ProductManager();
    }
    /**
     * Creates a new order detail with the given order detail ID, order table ID, product ID, and quantity.
     * @param orderdetailId the ID of the order detail
     * @param ordertableId the ID of the order table
     * @param productId the ID of the product
     * @param quantity the quantity of the product
     * @return the created order detail
     * @throws SQLException if an error occurs while creating the order detail
     */
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
    /**
     * Updates the order detail with the given order detail ID, order table ID, product ID, and quantity.
     * @param orderdetailId the ID of the order detail
     * @param ordertableId the ID of the order table
     * @param productId the ID of the product
     * @param quantity the quantity of the product
     * @return the updated order detail
     * @throws SQLException if an error occurs while updating the order detail
     */
    public OrderDetail updateOrderDetail(int orderdetailId, int ordertableId, int productId, int quantity) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(orderdetailId, ordertableId, productId, quantity);
        return orderDetailDao.update(orderDetail);
    }
    /**
     * Deletes the order detail with the given order detail ID.
     * @param orderdetailId the ID of the order detail
     * @throws SQLException if an error occurs while deleting the order detail
     */
    public void deleteOrderDetail(int orderdetailId) throws SQLException {
        orderDetailDao.delete(orderdetailId);
    }
    /**
     * Gets all order details in the application.
     * @return a list of all order details
     * @throws SQLException if an error occurs while getting the order details
     */
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }
    /**
     * Gets all order details in the application.
     * @return a list of all order details
     * @throws SQLException if an error occurs while getting the order details
     */
    public List<OrderDetail> getOrderDetailsByProductId(int productId) {
        return orderDetailDao.getOrderDetailsByProductId(productId);
    }
    /**
     * Deletes all order details with the given product ID.
     * @param productId the ID of the product
     * @throws SQLException if an error occurs while deleting the order details
     */
    public void deleteOrderDetailsWithProductId(int productId) throws SQLException {
        List<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByProductId(productId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDao.delete(orderDetail.getOrderDetailId());
        }
    }
    /**
     * Deletes all order details with the given order table ID.
     * @param ordertableId the ID of the order table
     * @throws SQLException if an error occurs while deleting the order details
     */
    public void deleteOrderDetailsWithOrderTableId(int ordertableId) throws SQLException{
        List<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByOrderId(ordertableId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDao.delete(orderDetail.getOrderDetailId());
        }
    }
}