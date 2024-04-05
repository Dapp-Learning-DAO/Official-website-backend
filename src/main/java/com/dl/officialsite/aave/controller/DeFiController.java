package com.dl.officialsite.aave.controller;

import com.dl.officialsite.aave.service.AaveTokenAPYService;
import com.dl.officialsite.aave.vo.HealthInfoVo;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.config.ChainInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeFiController
 * @Author jackchen
 * @Date 2024/3/6 19:45
 * @Description defi
 **/
@RestController
@RequestMapping("/defi")
@Slf4j
public class DeFiController {

    private final AaveTokenAPYService aaveService;

    public DeFiController(AaveTokenAPYService aaveService) {
        this.aaveService = aaveService;
    }


    /**
     * get all chainName
     */
    @GetMapping("/chainList")
    public BaseResponse chainList() {
        return BaseResponse.successWithData(aaveService.queryChainList());
    }

    /**
     * get all token apy
     */
    @GetMapping("/tokenApy")
    public BaseResponse tokenApy() {
        return BaseResponse.successWithData(aaveService.queryTokenApy());
    }

    /**
     * get healthInfo by wallet address
     */
    @PostMapping("/healthInfo")
    public BaseResponse detail(@RequestParam String address, @RequestBody ChainInfo chainInfo) {
        HealthInfoVo healthInfo = aaveService.getHealthInfo(chainInfo, address);
        return BaseResponse.successWithData(healthInfo);
    }
}
