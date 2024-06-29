package com.dl.officialsite.nft;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.nft.bean.NFTMetadata;
import com.dl.officialsite.nft.config.FileLoadConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * NFT metadata 管理
 */
@RestController
@Slf4j
public class NFTController {
    @Autowired
    private FileLoadConfig fileLoadConfig;

    /**
     * 获取 NFT 的元数据
     */
    @GetMapping("nft/{nftType}")
    public BaseResponse fetchNftMetadata(
        @PathVariable String nftType,
        @NotNull @RequestParam("rank") Integer rank) {
        NFTMetadata nftMetadata = fileLoadConfig.get(nftType, rank);
        if (nftMetadata == null) {
            return BaseResponse.failWithReason("1302", String.format("No NFT found for rank [%s]", rank));
        }
        return BaseResponse.successWithData(nftMetadata);
    }

    /**
     * 获取 NFT 的原始元数据
     */
    @GetMapping("nft/raw/{nftType}")
    public Object fetchRawNftMetadata(
        @PathVariable String nftType,
        @NotNull @RequestParam("rank") Integer rank) {
        NFTMetadata nftMetadata = fileLoadConfig.get(nftType, rank);
        if (nftMetadata == null) {
            return BaseResponse.failWithReason("1302", String.format("No NFT found for rank [%s]", rank));
        }
        return nftMetadata;
    }

}