package com.dl.officialsite.distributor.distributeClaimer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DistributeClaimerRepository
        extends JpaRepository<DistributeClaimer, Long>, JpaSpecificationExecutor<DistributeClaimer> {

    @Query(value = "select * from distribute_claimer where distribute_id = :distributeId and claimer_id=:claimerId", nativeQuery = true)
    Optional<DistributeClaimer> findByDistributeAndClaimer(@Param("distributeId") Long distributeId,
            @Param("claimerId") Long claimerId);
    @Query(value = "select * from distribute_claimer where distribute_id = :distributeId order by id asc", nativeQuery = true)
    Optional<List<DistributeClaimer>> findByDistribute(@Param("distributeId") Long distributeId);

    @Query(value = "update distribute_claimer set status=:newStatus where distribute_id = :distributeId and status=:oldStatus", nativeQuery = true)
    @Modifying
    void updateClaimStatus(@Param("distributeId") Long distributeId,@Param("newStatus") Integer newStatus,@Param("oldStatus") Integer oldStatus);

}
