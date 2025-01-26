package com.example.productservice.service;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    public Product updateProduct( Product updated) {
        Product existing = productRepository.findById(updated.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found: " + updated.getProductID()));
        existing.setProductName(updated.getProductName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        return productRepository.save(existing);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void updateStock(int productId, int quantity) {
        Product product = getProductById(productId);
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
    public int getStockOf(int productId) {
        Product product = getProductById(productId);
        return product.getStock();
    }


}
