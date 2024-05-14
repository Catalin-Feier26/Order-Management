package presentation;

import businessLayer.ClientManager;
import businessLayer.OrderManager;
import businessLayer.ProductManager;
import model.Client;
import model.OrderTable;
import model.Product;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a ClientManager instance
            ClientManager clientManager = new ClientManager();

            // Create a new client
            Client client = clientManager.createClient("Test Client", "123 Test Street", "1234567890");
            System.out.println("Client created with ID: " + client.getClientId());

            // Update the client
            client = clientManager.updateClient(client.getClientId(), "Updated Client", "456 Test Avenue", "0987654321");
            System.out.println("Client updated: " + client);

            // Create a ProductManager instance
            ProductManager productManager = new ProductManager();

            // Create a new product
            Product product = productManager.createProduct("Test Product", "This is a test product", 10.0, 100);

            // Check the stock of the product
            int stock = productManager.checkStock(product.getProductId());
            System.out.println("Stock of product: " + stock);

            // Create an OrderManager instance
            OrderManager orderManager = new OrderManager();

            // Create a new order
            OrderTable order = orderManager.createOrder(client.getClientId(), new Timestamp(System.currentTimeMillis()));

            // Print order details
            System.out.println("Order created with ID: " + order.getOrderTableId());

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}