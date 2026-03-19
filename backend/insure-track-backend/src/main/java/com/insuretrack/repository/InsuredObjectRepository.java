package com.insuretrack.repository;

import com.insuretrack.entity.InsuredObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuredObjectRepository extends JpaRepository<InsuredObject, Long> {
    List<InsuredObject> findByCustomer_CustomerID(Long customerID);
}
