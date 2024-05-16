package presentation;

import businessLayer.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
    /**
     * The OrderGUI class represents the graphical user interface for managing orders in the application.
     */
public class OrderGUI extends JFrame {

    private OrderManager orderManager;
    private OrderDetailManager orderDetailManager;
    private ProductManager productManager;
    private ClientManager clientManager;
    private BillManager billManager;
    private TableGenerator<OrderTable> orderTableGenerator;
    private TableGenerator<OrderDetail> orderDetailGenerator;
    private TableGenerator<Bill> billTableGenerator;
    /**
     * Constructs a new OrderGUI.
     */
    public OrderGUI() {

        orderManager = new OrderManager();
        orderDetailManager = new OrderDetailManager();
        productManager = new ProductManager();
        clientManager = new ClientManager();
        billManager = new BillManager();
        orderTableGenerator = new TableGenerator<>();
        orderDetailGenerator = new TableGenerator<>();
        billTableGenerator = new TableGenerator<>();

        setTitle("ORDER");
        setSize(800, 800); // Adjust the size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        JLabel titleLabel = new JLabel("ORDER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 22));
        // Create buttons and adjust their size
        JComboBox<Integer> orderIdComboBox = new JComboBox<>();
        JComboBox<Integer> orderIdComboBoxU=new JComboBox<>();
        JComboBox<Client> clientDropDown = new JComboBox<>();
        JComboBox<Product> productDropDownU = new JComboBox<>();
        JComboBox<Product> productDropDown = new JComboBox<>();
        JCheckBox addProductCheckBox = new JCheckBox("Add product");
        JTextField quantityField = new JTextField();
        JTextField quantityFieldU = new JTextField();
        try {
            List<Client> clients = clientManager.getAllClients();
            for (Client client : clients) {
                clientDropDown.addItem(client);
            }

            List<Product> products = productManager.getAllProducts();
            for (Product product : products) {
                productDropDown.addItem(product);
                productDropDownU.addItem(product);
            }
            List<OrderTable> orders = orderManager.getAllOrders();
            for (OrderTable order : orders) {
                orderIdComboBox.addItem(order.getOrderTableId());
                orderIdComboBoxU.addItem(order.getOrderTableId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton createOrderButton = new JButton("CREATE ORDER");
        createOrderButton.setPreferredSize(new Dimension(400, 110));
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client selectedClient = (Client) clientDropDown.getSelectedItem();

                    if (addProductCheckBox.isSelected()) {
                        Product selectedProduct = (Product) productDropDown.getSelectedItem();
                        int quantity = Integer.parseInt(quantityField.getText());
                        if(!productManager.checkStock(selectedProduct.getProductId(), quantity)) {
                            JOptionPane.showMessageDialog(null, "Not enough stock for product with ID: " + selectedProduct.getProductId(), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        OrderTable order = orderManager.createOrder(selectedClient.getClientId(), new Timestamp(System.currentTimeMillis()));
                        orderDetailManager.createOrderDetail(0, order.getOrderTableId(), selectedProduct.getProductId(), quantity);
                    }
                    else {
                        orderManager.createOrder(selectedClient.getClientId(), new Timestamp(System.currentTimeMillis()));
                    }

                    JOptionPane.showMessageDialog(null, "Order created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton seeOrdersButton = new JButton("SEE ORDERS");
        seeOrdersButton.setPreferredSize(new Dimension(400, 110));
        seeOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.util.List<OrderTable> orders = orderManager.getAllOrders();
                    JTable orderTable = orderTableGenerator.generateTableFromList(orders);
                    JOptionPane.showMessageDialog(null, new JScrollPane(orderTable), "Orders", JOptionPane.INFORMATION_MESSAGE);

                    for (OrderTable order : orders) {
                        List<OrderDetail> orderDetails = orderDetailManager.getOrderDetailsByOrderId(order.getOrderTableId());
                        JTable orderDetailTable = orderDetailGenerator.generateTableFromList(orderDetails);
                        JOptionPane.showMessageDialog(null, new JScrollPane(orderDetailTable), "Order Details for Order ID: " + order.getOrderTableId(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        JButton seeBillsButton = new JButton("SEE BILLS");
        seeBillsButton.setPreferredSize(new Dimension(400, 110));
        seeBillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Bill> bills = billManager.generateBills();
                    JTable billTable = billTableGenerator.generateTableFromList(bills);
                    JOptionPane.showMessageDialog(null, new JScrollPane(billTable), "Bills", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        JButton updateOrderButton = new JButton("UPDATE ORDER");
        updateOrderButton.setPreferredSize(new Dimension(400, 110));
        updateOrderButton.addActionListener(new ActionListener()
          {
                @Override
                public void actionPerformed(ActionEvent e) {
                 int selectedOrderId = (int) orderIdComboBoxU.getSelectedItem();
                 Product selectedProduct = (Product) productDropDownU.getSelectedItem();
                 int quantity = Integer.parseInt(quantityFieldU.getText());
                 try {
                      if(!productManager.checkStock(selectedProduct.getProductId(), quantity)) {
                            JOptionPane.showMessageDialog(null, "Not enough stock for product with ID: " + selectedProduct.getProductId(), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                      }
                      orderDetailManager.createOrderDetail(0, selectedOrderId, selectedProduct.getProductId(), quantity);
                      JOptionPane.showMessageDialog(null, "Order updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                 } catch (SQLException sqlException) {
                      JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }
                }
          });
        JButton deleteOrderButton = new JButton("DELETE ORDER");
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected order ID from the JComboBox
                int selectedOrderId = (int) orderIdComboBox.getSelectedItem();
                try {
                    // Call the deleteOrder method from OrderManager
                    orderManager.deleteOrder(selectedOrderId);
                    JOptionPane.showMessageDialog(null, "Order deleted successfully");
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error deleting order: " + sqlException.getMessage());
                }
            }
        });
        deleteOrderButton.setPreferredSize(new Dimension(400, 110));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the title and the buttons
        panel.add(createOrderButton);
        panel.add(clientDropDown);
        panel.add(addProductCheckBox);
        panel.add(productDropDown);
        panel.add(quantityField);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(seeOrdersButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(seeBillsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(updateOrderButton);
        panel.add(new JLabel("Select Order:"));
        panel.add(orderIdComboBoxU);
        panel.add(new JLabel("Select Product:"));
        panel.add(productDropDownU);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityFieldU);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(deleteOrderButton);
        panel.add(new JLabel("Select Order to Delete:"));
        panel.add(orderIdComboBox);
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(panel);

        add(gridPanel);

        setVisible(true);
    }

}