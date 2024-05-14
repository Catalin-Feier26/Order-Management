package businessLayer;

import dataAccess.ProductDao;
import model.Product;

import java.sql.SQLException;

public class ProductManager {
    private ProductDao productDao;

    public ProductManager() {
        this.productDao = new ProductDao();
    }

    public Product createProduct(String name, String description, double price, int stockQuantity) throws SQLException {
        Product product = new Product(0, name, description, price, stockQuantity);
        return productDao.create(product);
    }

    public Product updateProduct(int productId, String name, String description, double price, int stockQuantity) throws SQLException {
        Product product = new Product(productId, name, description, price, stockQuantity);
        return productDao.update(product);
    }

    public void deleteProduct(int productId) throws SQLException {
        productDao.delete(productId);
    }

    public int checkStock(int productId) throws SQLException {
        Product product = productDao.read(productId);
        return product.getStockQuantity();
    }
}