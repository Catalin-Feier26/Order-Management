package presentation;

import businessLayer.OrderDetailManager;
import businessLayer.ProductManager;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
/**
 * The ProductGUI class represents the graphical user interface for managing products in the application.
 */
public class ProductGUI extends JFrame {

    private ProductManager productManager;
    private TableGenerator<Product> tableGenerator;
    private OrderDetailManager orderDetailManager;
    /**
     * Constructs a new ProductGUI.
     */
    public ProductGUI() {
        productManager = new ProductManager();
        orderDetailManager = new OrderDetailManager();
        tableGenerator = new TableGenerator<>();

        setTitle("PRODUCT");
        setSize(800, 800); // Adjust the size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        JLabel titleLabel = new JLabel("PRODUCT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 22));

        // Create text fields for user input
        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockQuantityField = new JTextField(20);

        JTextField nameFieldU = new JTextField(20);
        JTextField descriptionFieldU = new JTextField(20);
        JTextField priceFieldU = new JTextField(20);
        JTextField stockQuantityFieldU = new JTextField(20);

        JComboBox<String> productComboBox = new JComboBox<>();
        try {
            List<Product> products = productManager.getAllProducts();
            for (Product product : products) {
                productComboBox.addItem(product.getProductId() + " - " + product.getName());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Create buttons and adjust their size
        JButton createProductButton = new JButton("CREATE PRODUCT");
        createProductButton.setPreferredSize(new Dimension(400, 110));
        createProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                int stockQuantity= 0;
                double price=0;
                try {
                     price = Double.parseDouble(priceField.getText());
                }catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(null, "Please enter a valid price");
                    return;
                }
                try {
                     stockQuantity = Integer.parseInt(stockQuantityField.getText());
                }catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(null, "Please enter a valid stock quantity");
                    return;
                }
                if(name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }
                try {
                    Product product = productManager.createProduct(name, description, price, stockQuantity);
                    nameField.setText("");
                    descriptionField.setText("");
                    priceField.setText("");
                    stockQuantityField.setText("");
                    JOptionPane.showMessageDialog(null, "Product created successfully with ID: " + product.getProductId());
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error creating product: " + sqlException.getMessage());
                }
            }
        });

        JButton seeProductsButton = new JButton("SEE PRODUCTS");
        seeProductsButton.setPreferredSize(new Dimension(400, 110));
        seeProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Product> products = productManager.getAllProducts();
                    JTable table = tableGenerator.generateTableFromList(products);
                    JOptionPane.showMessageDialog(null, new JScrollPane(table));
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        JButton updateProductButton = new JButton("UPDATE PRODUCT");
        updateProductButton.setPreferredSize(new Dimension(400, 110));
        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                int productId = Integer.parseInt(selectedProduct.split(" - ")[0]);
                String name = nameFieldU.getText();
                String description = descriptionFieldU.getText();
                double price=0;
                int stockQuantity=0;
                try{
                    price = Double.parseDouble(priceFieldU.getText());
                    stockQuantity = Integer.parseInt(stockQuantityFieldU.getText());
                }catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(null, "Please enter a valid price and stock quantity");
                    return;
                }
                try {
                    productManager.updateProduct(productId, name, description, price, stockQuantity);
                    nameFieldU.setText("");
                    descriptionFieldU.setText("");
                    priceFieldU.setText("");
                    stockQuantityFieldU.setText("");
                    JOptionPane.showMessageDialog(null, "Product updated successfully");
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error updating product: " + sqlException.getMessage());
                }
            }
        });

        JComboBox<String> deleteProductComboBox = new JComboBox<>();
        try {
            List<Product> products = productManager.getAllProducts();
            for (Product product : products) {
                deleteProductComboBox.addItem(product.getProductId() + " - " + product.getName());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JButton deleteProductButton = new JButton("DELETE PRODUCT");
        deleteProductButton.setPreferredSize(new Dimension(400, 110));
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) deleteProductComboBox.getSelectedItem();
                int productId = Integer.parseInt(selectedProduct.split(" - ")[0]);
                try {
                    orderDetailManager.deleteOrderDetailsWithProductId(productId);
                    productManager.deleteProduct(productId);
                    JOptionPane.showMessageDialog(null, "Product deleted successfully");
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error deleting product: " + sqlException.getMessage());
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the title and the buttons
        panel.add(createProductButton);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock Quantity:"));
        panel.add(stockQuantityField);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(seeProductsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(updateProductButton);
        panel.add(new JLabel("Select Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Name:"));
        panel.add(nameFieldU);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionFieldU);
        panel.add(new JLabel("Price:"));
        panel.add(priceFieldU);
        panel.add(new JLabel("Stock Quantity:"));
        panel.add(stockQuantityFieldU);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(new JLabel("Select Product to Delete:"));
        panel.add(deleteProductComboBox);
        panel.add(deleteProductButton);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(panel);

        add(gridPanel);

        setVisible(true);
    }
}