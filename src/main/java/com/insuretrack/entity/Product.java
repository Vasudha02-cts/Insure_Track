package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    private String name; // e.g., "AUTO"
    private String description;
    private String status; // Active/Inactive

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Coverage> coverages;
}