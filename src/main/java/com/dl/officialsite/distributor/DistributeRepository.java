package com.dl.officialsite.distributor;

import com.dl.officialsite.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistributeRepository
                extends JpaRepository<DistributeInfo, String>, JpaSpecificationExecutor<DistributeInfo> {

        @Query(value = "select * from distribute_info where distribute_nonce = :distributeNonce", nativeQuery = true)
        Optional<DistributeInfo> findByDistributeNonce(@Param("distributeNonce") Long distributeNonce);

        @Query(value = "select * from distribute_info where distribute_name = :distributeNonce", nativeQuery = true)
        Optional<DistributeInfo> findByDistributeName(@Param("distributeName") String distributeName);
}
