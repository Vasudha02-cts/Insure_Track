package com.insuretrack.repository;
import com.insuretrack.entity.Evidence;
import com.insuretrack.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByClaim_ClaimID(Long claimID);
}