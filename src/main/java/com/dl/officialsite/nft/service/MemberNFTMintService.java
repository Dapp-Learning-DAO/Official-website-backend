package com.dl.officialsite.nft.service;

import com.dl.officialsite.nft.bean.MemberNFTMintRecordRepository;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberNFTMintService {
    @Autowired
    private MemberNFTMintRecordRepository memberNFTMintRecordRepository;

    public void clean(String address, ContractNameEnum contractName, String chainId) {
        int removedCount = this.memberNFTMintRecordRepository.deleteByAddressAndContractNameAndChainId(address,
            contractName.name(),
            chainId).orElse(0);
        log.info("Remove {} NFT mint record(s) for address {} and contract {}", removedCount, address, contractName.name());
    }

}