package businessLayer;

import dataAccess.ProductDao;
import model.Product;

import java.sql.SQLException;
import java.util.List;
/**
 * The ProductManager class is responsible for managing products in the application.
 */
public class ProductManager {
    private ProductDao productDao;
    /**
     * Constructs a new ProductManager with a new ProductDao.
     */
    public ProductManager() {
        this.productDao = new ProductDao();
    }
    /**
     * Creates a new product with the given name, description, price, and stock quantity.
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @param stockQuantity the stock quantity of the product
     * @return the created product
     * @throws SQLException if an error occurs while creating the product
     */
    public Product createProduct(String name, String description, double price, int stockQuantity) throws SQLException {
        Product product = new Product(0, name, description, price, stockQuantity);
        return productDao.create(product);
    }
    /**
     * Updates the product with the given product ID, name, description, price, and stock quantity.
     * @param productId the ID of the product
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @param stockQuantity the stock quantity of the product
     * @return the updated product
     * @throws SQLException if an error occurs while updating the product
     */
    public Product updateProduct(int productId, String name, String description, double price, int stockQuantity) throws SQLException {
        Product product = new Product(productId, name, description, price, stockQuantity);
        return productDao.update(product);
    }
    /**
     * Deletes the product with the given product ID.
     * @param productId the ID of the product
     * @throws SQLException if an error occurs while deleting the product
     */
    public void deleteProduct(int productId) throws SQLException {
        productDao.delete(productId);
    }
    /**
     * Gets the stock quantity of the product with the given product ID.
     * @param productId the ID of the product
     * @return the stock quantity of the product
     * @throws SQLException if an error occurs while getting the stock quantity
     */
    public int checkStock(int productId) throws SQLException {
        Product product = productDao.read(productId);
        return product.getStockQuantity();
    }
    /**
     * Gets all products in the application.
     * @return a list of all products
     * @throws SQLException if an error occurs while getting all products
     */
    public List<Product> getAllProducts() throws SQLException {
        return productDao.readAll();
    }
    /**
     * Gets the product with the given product ID.
     * @param productId the ID of the product
     * @return the product with the given product ID
     * @throws SQLException if an error occurs while getting the product
     */
    public Product getProductById(int productId) throws SQLException {
        return productDao.read(productId);
    }
    /**
     * Checks if there is enough stock for the product with the given product ID and quantity.
     * @param productId the ID of the product
     * @param quantity the quantity of the product
     * @return true if there is enough stock, false otherwise
     * @throws SQLException if an error occurs while checking the stock
     */
    public boolean checkStock(int productId, int quantity) throws SQLException {
        Product product = productDao.read(productId);
        return product.getStockQuantity() >= quantity;
    }

}