package com.insuretrack.service.impl;

import com.insuretrack.entity.Product;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        // Fix: Check for null only, Enums don't use .isEmpty()
        if (product.getName() == null) {
            throw new RuntimeException("Product name cannot be null");
        }
        // Fix: Use the Enum constant instead of a String
        if (product.getStatus() == null) {
            product.setStatus(Product.Status.Active);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStatus(productDetails.getStatus());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void disableProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // This is the "Soft Delete" logic
        product.setStatus(Product.Status.Inactive);
        productRepository.save(product);
    }
}