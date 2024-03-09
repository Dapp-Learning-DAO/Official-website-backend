package com.dl.officialsite.aave;

import com.dl.officialsite.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 查询币种列表
     */

    /**
     * get all chainName
     */
    @GetMapping("/chainList")
    public BaseResponse chainList() {
        return BaseResponse.successWithData(aaveService.queryChainList());
    }

    /**
     * 条件查询所有币种年华，按照最大年华排序
     */
    @GetMapping("/tokenApy")
    public BaseResponse tokenApy() {
        return BaseResponse.successWithData(aaveService.queryTokenApy());
    }

    /**
     * get healthInfo by wallet address
     */
    @GetMapping("/healthInfo")
    public BaseResponse detail(@RequestParam String address) {
        HealthInfo healthInfo = aaveService.getHealthInfo(address);
        return BaseResponse.successWithData(healthInfo);
    }
}
