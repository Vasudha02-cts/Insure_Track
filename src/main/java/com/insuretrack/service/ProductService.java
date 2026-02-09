package com.insuretrack.service;

import com.insuretrack.entity.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void disableProduct(Long id);
}