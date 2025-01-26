package com.example.Project2.service;

import com.example.Project2.model.Product;
import com.example.Project2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct( Product updated) {
        Product existing = productRepository.findById(updated.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found: " + updated.getProductID()));
        existing.setProductName(updated.getProductName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        return productRepository.save(existing);
    }
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
