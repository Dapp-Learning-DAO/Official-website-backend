package com.dl.officialsite.nft.service;

import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.nft.bean.MemberNFTMintRecord;
import com.dl.officialsite.nft.bean.MemberNFTMintRecordRepository;
import com.dl.officialsite.nft.config.ContractConfigService;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import com.dl.officialsite.nft.contract.WarCraftContract;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WarCraftContractService {
    private static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";
    private static final ContractGasProvider ZERO_GAS_PROVIDER = new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO);
    public static final long TIMEOUT = 5;

    @Autowired
    private ContractConfigService contractConfigService;
    @Autowired
    private MemberNFTMintRecordRepository memberNFTMintRecordRepository;

    public Pair<Integer, Integer> rank(String address, ContractNameEnum contractName, String chainId) {
        String contractAddress = this.contractConfigService.getContractAddressByName(contractName, chainId);
        String rpcAddress = this.contractConfigService.getRpcAddressByName(contractName, chainId);

        if (StringUtils.isAnyBlank(contractAddress, rpcAddress)) {
            log.error("Fetch blank values of contractAddress:[{}] or rpcAddress:[{}] filtered by contractName:[{}] and chainId:[{}]",
                contractAddress, rpcAddress, contractName.getContractName(), chainId);
            throw new BizException("1204", "Fetch rank failed, please try again later.");
        }

        Optional<MemberNFTMintRecord> memberTaskRecordExists =
            memberNFTMintRecordRepository.findByAddressAndContractNameAndChainId(address, contractName.name(), chainId);

        if(memberTaskRecordExists.isPresent()
            && memberTaskRecordExists.get().getRankValue() >= 0
            && memberTaskRecordExists.get().getTokenId() >= 0){
            return Pair.of(memberTaskRecordExists.get().getRankValue(), memberTaskRecordExists.get().getTokenId());
        }

        Web3j web3j = Web3j.build(new HttpService(rpcAddress));

        ClientTransactionManager transactionManager = new ClientTransactionManager(web3j, ZERO_ADDRESS);
        WarCraftContract contract = WarCraftContract.load(contractAddress, web3j, transactionManager, ZERO_GAS_PROVIDER);

        try {
            BigInteger tokenId = contract.claimedTokenIdBy(address).sendAsync().get(TIMEOUT, TimeUnit.SECONDS);
            if (tokenId == null || tokenId.intValue() == 0) {
                log.error("No claim(tokenId) info found for address:[{}].", address);
                throw new BizException("200", "No claim info found.");
            }

            String rank = contract.tokenURIs(tokenId).sendAsync().get(TIMEOUT, TimeUnit.SECONDS);
            if (StringUtils.isBlank(rank)) {
                log.error("No rank info found for address:[{}] and tokenId:[{}].", address, tokenId.intValue());
                throw new BizException("200", "No rank info found.");
            }

            MemberNFTMintRecord memberNFTMintRecord = memberTaskRecordExists.orElseGet(MemberNFTMintRecord::new);
            memberNFTMintRecord.setAddress(address);
            memberNFTMintRecord.setContractName(contractName);
            memberNFTMintRecord.setChainId(chainId);
            memberNFTMintRecord.setTokenId(tokenId.intValue());
            memberNFTMintRecord.setRankValue(Integer.parseInt(rank));
            this.memberNFTMintRecordRepository.save(memberNFTMintRecord);
            return Pair.of(memberNFTMintRecord.getRankValue(), memberNFTMintRecord.getTokenId());
        } catch (Exception e) {
            log.error("Call contract method:[claimedTokenIdBy] remote error.", e);
            throw new BizException("1205", "Fetch rank failed, please try again later.");
        }

    }

}