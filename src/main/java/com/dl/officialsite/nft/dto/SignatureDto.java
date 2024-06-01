package com.dl.officialsite.nft.dto;

import lombok.Data;

@Data
public class SignatureDto {

    public SignatureDto(String receiverAddress, Long signedAt, String signature, String seed, String chainId, String nftAddress) {
        this.receiverAddress = receiverAddress;
        this.signedAt = signedAt;
        this.signature = signature;
        this.seed = seed;
        this.chainId = chainId;
        this.nftAddress = nftAddress;
    }

    private String receiverAddress;
    private Long signedAt;
    private String signature;
    private String seed;
    private String chainId;
    private String nftAddress;
}