package com.dl.officialsite.tokenInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenInfoRepository
        extends JpaRepository<TokenInfo, Long>, JpaSpecificationExecutor<TokenInfo> {


    @Query(value = "select * from token_info where chain_id = ?1 and  token_address = ?2 ", nativeQuery = true)
    Optional<TokenInfo> findByChainAndAddress(@Param("chain") String chain, @Param("address") String address);

}
