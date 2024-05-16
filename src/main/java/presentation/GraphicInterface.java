package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The GraphicInterface class represents the graphical user interface for the application.
 */
public class GraphicInterface extends JFrame {
    /**
     * Constructs a new GraphicInterface.
     */
    public GraphicInterface() {
        setTitle("Graphic Interface");
        setSize(800, 800); // Adjust the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        // Create a title label
        JLabel titleLabel = new JLabel("ORDER MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create buttons and adjust their size
        JButton clientButton = new JButton("Client");
        clientButton.setPreferredSize(new Dimension(400, 110));
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientGUI(); // Open the ClientGUI window when the button is clicked
            }
        });

        JButton orderButton = new JButton("Order");
        orderButton.setPreferredSize(new Dimension(400, 110));
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderGUI(); // Open the OrderGUI window when the button is clicked
            }
        });

        JButton productButton = new JButton("Product");
        productButton.setPreferredSize(new Dimension(400, 110));
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductGUI(); // Open the ProductGUI window when the button is clicked
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel); // Add the title label to the panel
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the title and the buttons
        panel.add(clientButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(orderButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(productButton);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(panel);

        add(gridPanel);

        setVisible(true);
    }
    /**
     * The main method is the entry point of the application.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        new GraphicInterface();
    }
}