package presentation;
import business.*;
import dataAccess.*;
import model.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            if (connection != null) {
                System.out.println("Database connection successful!");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}