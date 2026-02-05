package com.insuretrack.repository;


import com.insuretrack.entity.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {}
