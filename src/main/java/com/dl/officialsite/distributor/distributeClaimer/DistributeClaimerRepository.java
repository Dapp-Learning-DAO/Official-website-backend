package com.dl.officialsite.distributor.distributeClaimer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DistributeClaimerRepository
        extends JpaRepository<DistributeClaimer, Long>, JpaSpecificationExecutor<DistributeClaimer> {

    @Query(value = "select * from distribute_claimer where distribute_id = :distributeId and claimer_id=claimerId", nativeQuery = true)
    Optional<DistributeClaimer> findByDistributeAndClaimer(@Param("distributeId") Long distributeId,
            @Param("claimerId") Long claimerId);

}
