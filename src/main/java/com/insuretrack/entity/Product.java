package com.insuretrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    private String name;
    private String description;
    private String status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Coverage> coverages;
}
