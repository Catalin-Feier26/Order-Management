package presentation;

import businessLayer.*;
import model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a ClientManager instance
            ClientManager clientManager = new ClientManager();

            // Create a new client
            Client newClient = clientManager.createClient("John Doe", "123 Main St", "123-456-7890");
            System.out.println("Created Client: " + newClient);

            // Get all clients
            List<Client> clients = clientManager.getAllClients();
            System.out.println("All Clients:");
            for (Client client : clients) {
                System.out.println(client);
            }

            // Update the client
            newClient.setName("Jane Doe");
            Client updatedClient = clientManager.updateClient(newClient.getClientId(), newClient.getName(), newClient.getAddress(), newClient.getContactNumber());
            System.out.println("Updated Client: " + updatedClient);

            // Create a ProductManager instance
            ProductManager productManager = new ProductManager();

            // Create a new product
            Product newProduct = productManager.createProduct("Product A", "Description for Product A", 10.0, 100);
            System.out.println("Created Product: " + newProduct);

            // Get all products
            List<Product> products = productManager.getAllProducts();
            System.out.println("All Products:");
            for (Product product : products) {
                System.out.println(product);
            }

            // Update the product
            newProduct.setName("Product B");
            Product updatedProduct = productManager.updateProduct(newProduct.getProductId(), newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getStockQuantity());
            System.out.println("Updated Product: " + updatedProduct);

            // Create an OrderManager instance
            OrderManager orderManager = new OrderManager();

            // Create a new order
            OrderTable newOrder = orderManager.createOrder(newClient.getClientId(), new Timestamp(System.currentTimeMillis()));
            System.out.println("Created Order: " + newOrder);

            // Get all orders
            List<OrderTable> orders = orderManager.getAllOrders();
            System.out.println("All Orders:");
            for (OrderTable order : orders) {
                System.out.println(order);
            }

            // Update the order
            newOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
            OrderTable updatedOrder = orderManager.updateOrder(newOrder.getOrderTableId(), newOrder.getClientId(), newOrder.getOrderDate());
            System.out.println("Updated Order: " + updatedOrder);

            // Create an OrderDetailManager instance
            OrderDetailManager orderDetailManager = new OrderDetailManager();

            // Create a new order detail
            OrderDetail newOrderDetail = orderDetailManager.createOrderDetail(0, newOrder.getOrderTableId(), newProduct.getProductId(), 5, BigDecimal.valueOf(newProduct.getPrice()));
            System.out.println("Created OrderDetail: " + newOrderDetail);

            // Get all order details for the order
            List<OrderDetail> orderDetails = orderDetailManager.getOrderDetailsByOrderId(newOrder.getOrderTableId());
            System.out.println("All OrderDetails for Order " + newOrder.getOrderTableId() + ":");
            for (OrderDetail orderDetail : orderDetails) {
                System.out.println(orderDetail);
            }

            // Update the order detail
            newOrderDetail.setQuantity(10);
            OrderDetail updatedOrderDetail = orderDetailManager.updateOrderDetail(newOrderDetail.getOrderDetailId(), newOrderDetail.getOrdertableId(), newOrderDetail.getProductId(), newOrderDetail.getQuantity(), newOrderDetail.getUnitPrice());
            System.out.println("Updated OrderDetail: " + updatedOrderDetail);

            // Delete the order detail
            orderDetailManager.deleteOrderDetail(newOrderDetail.getOrderDetailId());
            System.out.println("Deleted OrderDetail: " + newOrderDetail.getOrderDetailId());

            // Delete the order
            orderManager.deleteOrder(newOrder.getOrderTableId());
            System.out.println("Deleted Order: " + newOrder.getOrderTableId());

            // Delete the product
            productManager.deleteProduct(newProduct.getProductId());
            System.out.println("Deleted Product: " + newProduct.getProductId());

            // Delete the client
            clientManager.deleteClient(newClient.getClientId());
            System.out.println("Deleted Client: " + newClient.getClientId());

        } catch (SQLException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}