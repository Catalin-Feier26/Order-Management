package presentation;

import javax.swing.*;
import java.awt.*;

public class OrderGUI extends JFrame {

    public OrderGUI() {
        setTitle("ORDER");
        setSize(800, 800); // Adjust the size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        JLabel titleLabel = new JLabel("ORDER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 22));
        // Create buttons and adjust their size
        JButton createOrderButton = new JButton("CREATE ORDER");
        createOrderButton.setPreferredSize(new Dimension(400, 110));
        JButton seeOrdersButton = new JButton("SEE ORDERS");
        seeOrdersButton.setPreferredSize(new Dimension(400, 110));
        JButton updateOrderButton = new JButton("UPDATE ORDER");
        updateOrderButton.setPreferredSize(new Dimension(400, 110));
        JButton deleteOrderButton = new JButton("DELETE ORDER");
        deleteOrderButton.setPreferredSize(new Dimension(400, 110));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the title and the buttons
        panel.add(createOrderButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(seeOrdersButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(updateOrderButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space between the buttons
        panel.add(deleteOrderButton);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(panel);

        add(gridPanel);

        setVisible(true);
    }

}