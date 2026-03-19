package com.insuretrack.mapper;

import com.insuretrack.dto.ProductRequestDTO;
import com.insuretrack.dto.ProductResponseDTO;
import com.insuretrack.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Used for the GET/Response flow
    @Mapping(source = "productID", target = "productId")
    ProductResponseDTO toDto(Product product);

    // Used for the POST/Create flow
    // We ignore productId because the database generates it
    @Mapping(target = "productID", ignore = true)
    Product toEntity(ProductRequestDTO requestDto);
}