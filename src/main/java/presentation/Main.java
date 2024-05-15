package presentation;

import businessLayer.ClientManager;
import businessLayer.OrderManager;
import businessLayer.ProductManager;
import model.Client;
import model.OrderTable;
import model.Product;

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

            // Delete the client
            clientManager.deleteClient(newClient.getClientId());
            System.out.println("Deleted Client: " + newClient.getClientId());

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

            // Delete the product
            productManager.deleteProduct(newProduct.getProductId());
            System.out.println("Deleted Product: " + newProduct.getProductId());

            // Create an OrderManager instance
            OrderManager orderManager = new OrderManager();

            // Create a new order
            OrderTable newOrder = orderManager.createOrder(3, new Timestamp(System.currentTimeMillis()));
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

            // Delete the order
            orderManager.deleteOrder(newOrder.getOrderTableId());
            System.out.println("Deleted Order: " + newOrder.getOrderTableId());

        } catch (SQLException e) {
            System.out.println("An error occurred.");
        }
    }
}