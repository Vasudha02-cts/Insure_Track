package com.insuretrack.service;

import com.insuretrack.dto.ProductRequestDTO;
import com.insuretrack.dto.ProductResponseDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO createProduct(ProductRequestDTO request);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);
    void deleteProduct(Long id);
    ProductResponseDTO getProductById(Long id);

}