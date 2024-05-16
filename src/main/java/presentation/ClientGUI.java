package presentation;

import businessLayer.ClientManager;
import businessLayer.OrderManager;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ClientGUI extends JFrame {

    private ClientManager clientManager;
    private OrderManager orderManager;
    private TableGenerator<Client> tableGenerator;

    public ClientGUI() {
        clientManager = new ClientManager();
        orderManager = new OrderManager();
        tableGenerator = new TableGenerator<>();

        setTitle("CLIENT");
        setSize(800, 800); // Adjust the size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        JLabel titleLabel = new JLabel("CLIENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 22));

        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField contactNumberField = new JTextField(20);

        JComboBox<String> clientComboBox = new JComboBox<>();
        try {
            List<Client> clients = clientManager.getAllClients();
            for (Client client : clients) {
                clientComboBox.addItem(client.getClientId() + " - " + client.getName());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Create buttons and adjust their size
        JButton createClientButton = new JButton("CREATE CLIENT");
        createClientButton.setPreferredSize(new Dimension(400, 110));
        createClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String contactNumber = contactNumberField.getText();
                if(name.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }
                try {
                    Client client = clientManager.createClient(name, address, contactNumber);
                    nameField.setText("");
                    addressField.setText("");
                    contactNumberField.setText("");
                    JOptionPane.showMessageDialog(null, "Client created successfully with ID: " + client.getClientId());
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error creating client: " + sqlException.getMessage());
                }
            }
        });

        JButton seeClientsButton = new JButton("SEE CLIENTS");
        seeClientsButton.setPreferredSize(new Dimension(400, 110));
        seeClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Client> clients = clientManager.getAllClients();
                    JTable table = tableGenerator.generateTableFromList(clients);
                    JOptionPane.showMessageDialog(null, new JScrollPane(table));
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        JTextField nameFieldU = new JTextField(20);
        JTextField addressFieldU = new JTextField(20);
        JTextField contactNumberFieldU = new JTextField(20);

        JButton updateClientButton = new JButton("UPDATE CLIENT");
        updateClientButton.setPreferredSize(new Dimension(400, 110));
        updateClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClient = (String) clientComboBox.getSelectedItem();
                int clientId = Integer.parseInt(selectedClient.split(" - ")[0]);
                String name = nameFieldU.getText();
                String address = addressFieldU.getText();
                String contactNumber = contactNumberFieldU.getText();
                if(name.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }
                try {
                    clientManager.updateClient(clientId, name, address, contactNumber);
                    nameFieldU.setText("");
                    addressFieldU.setText("");
                    contactNumberFieldU.setText("");
                    JOptionPane.showMessageDialog(null, "Client updated successfully");
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error updating client: " + sqlException.getMessage());
                }
            }
        });
        JComboBox<String> deleteClientComboBox = new JComboBox<>();
        try {
            List<Client> clients = clientManager.getAllClients();
            for (Client client : clients) {
                deleteClientComboBox.addItem(client.getClientId() + " - " + client.getName());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JButton deleteClientButton = new JButton("DELETE CLIENT");
        deleteClientButton.setPreferredSize(new Dimension(400, 110));
        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClient = (String) deleteClientComboBox.getSelectedItem();
                int clientId = Integer.parseInt(selectedClient.split(" - ")[0]);
                try {
                    orderManager.deleteOrdersWithClientId(clientId);
                    clientManager.deleteClient(clientId);
                    JOptionPane.showMessageDialog(null, "Client deleted successfully");
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, "Error deleting client: " + sqlException.getMessage());
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the title and the buttons
        panel.add(createClientButton);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Contact Number:"));
        panel.add(contactNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(seeClientsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons

        panel.add(updateClientButton);
        panel.add(new JLabel("Select Client:"));
        panel.add(clientComboBox);
        panel.add(new JLabel("Name:"));
        panel.add(nameFieldU);
        panel.add(new JLabel("Address:"));
        panel.add(addressFieldU);
        panel.add(new JLabel("Contact Number:"));
        panel.add(contactNumberFieldU);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(new JLabel("Select Client to Delete:"));
        panel.add(deleteClientComboBox);
        panel.add(deleteClientButton);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(panel);

        add(gridPanel);

        setVisible(true);
    }
}