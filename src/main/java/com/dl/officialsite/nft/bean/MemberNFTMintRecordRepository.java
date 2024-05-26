package com.dl.officialsite.nft.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberNFTMintRecordRepository
    extends JpaRepository<MemberNFTMintRecord, Long>, JpaSpecificationExecutor<MemberNFTMintRecord> {

    @Query(value = "select * from member_nft_mint_record where address =:address and contract_name=:contractName and " +
        "chain_id=:chainId", nativeQuery = true)
    Optional<MemberNFTMintRecord> findByAddressAndContractNameAndChainId(@Param("address") String address,
                                                                         @Param("contractName") String contractName,
                                                                         @Param("chainId") String chainId);

    @Modifying
    @Transactional
    @Query(value = "delete from member_nft_mint_record where address =:address and contract_name=:contractName and " +
        "chain_id=:chainId", nativeQuery = true)
    Optional<Integer> deleteByAddressAndContractNameAndChainId(@Param("address") String address,
                                                 @Param("contractName") String contractName,
                                                 @Param("chainId") String chainId);
}
