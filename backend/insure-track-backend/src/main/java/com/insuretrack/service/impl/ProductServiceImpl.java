package com.insuretrack.service.impl;

import com.insuretrack.dto.ProductRequestDTO;
import com.insuretrack.dto.ProductResponseDTO;
import com.insuretrack.entity.Product;
import com.insuretrack.mapper.ProductMapper;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    @Transactional
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        // This will now include coverages and rating rules
        // if they are mapped in your ProductMapper
        return productMapper.toDto(product);
    }
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = productMapper.toEntity(request);
        // Default status if not provided in request
        if (product.getStatus() == null) product.setStatus("Active");
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setStatus(request.getStatus());

        return productMapper.toDto(productRepository.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}