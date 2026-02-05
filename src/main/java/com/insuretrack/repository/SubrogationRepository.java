package com.insuretrack.repository;
import com.insuretrack.entity.Subrogation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SubrogationRepository extends JpaRepository<Subrogation, Long> {}