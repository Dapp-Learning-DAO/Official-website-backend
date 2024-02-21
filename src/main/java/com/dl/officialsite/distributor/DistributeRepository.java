package com.dl.officialsite.distributor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DistributeRepository
                extends JpaRepository<DistributeInfo, Long>, JpaSpecificationExecutor<DistributeInfo> {

        @Query(value = "select * from distribute_info where distribute_nonce = :distributeNonce", nativeQuery = true)
        Optional<DistributeInfo> findByChainAndCreatorMessage(@Param("distributeNonce") Long distributeNonce);

        @Query(value = "select * from distribute_info where  chain_id = :chainId  and status IN (:status)  order by create_time desc", nativeQuery = true)
        List<DistributeInfo> findByChainIdAndStatus(@Param("chainId") String chainId,
                        @Param("status") List<Integer> status);

        @Query(value = "select * from distribute_info where  id in :distributeIds  order by create_time desc", nativeQuery = true)
        List<DistributeInfo> findByIds(@Param("distributeIds") Set<Long> distributeIds);
}
