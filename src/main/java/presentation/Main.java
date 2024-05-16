package presentation;

import businessLayer.BillManager;
import model.Bill;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a BillManager instance
            BillManager billManager = new BillManager();

            // Generate all bills
            List<Bill> bills = billManager.generateBills();

            // Print all bills
            System.out.println("All Bills:");
            for (Bill bill : bills) {
                System.out.println(bill);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}