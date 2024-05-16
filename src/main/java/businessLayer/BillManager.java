package businessLayer;

import model.Bill;
import model.OrderDetail;
import model.OrderTable;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillManager {
    private OrderManager orderManager;
    private OrderDetailManager orderDetailManager;
    private ProductManager productManager;

    public BillManager() {
        this.orderManager = new OrderManager();
        this.orderDetailManager = new OrderDetailManager();
        this.productManager = new ProductManager();
    }

    public List<Bill> generateBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        List<OrderTable> orders = orderManager.getAllOrders();

        for (OrderTable order : orders) {
            List<OrderDetail> orderDetails = orderDetailManager.getOrderDetailsByOrderId(order.getOrderTableId());

            int noProducts = 0;
            double totalPrice = 0.0;

            if (!orderDetails.isEmpty()) {
                for (OrderDetail orderDetail : orderDetails) {
                    Product product = productManager.getProductById(orderDetail.getProductId());
                    noProducts += orderDetail.getQuantity();
                    totalPrice += orderDetail.getQuantity() * product.getPrice();
                }
            }

            Bill bill = new Bill(order.getOrderTableId(), order.getClientId(), noProducts, totalPrice);
            bills.add(bill);
        }

        return bills;
    }
}