package com.dl.officialsite.admin;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.defi.service.AaveTokenAPYService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TiggerController
 * @Author jackchen
 * @Date 2024/4/5 19:04
 * @Description HookController
 **/
@RestController
@RequestMapping("/hook")
@Slf4j
@ConditionalOnProperty(name = "hook.open", havingValue = "true", matchIfMissing = false)
public class HookController {

    private final AaveTokenAPYService aaveService;

    public HookController(AaveTokenAPYService aaveService) {
        this.aaveService = aaveService;
    }

    @GetMapping("/token/apy")
    public BaseResponse HookTokenApy() {
        aaveService.updateTokenAPYInfo();
        return BaseResponse.success();
    }
}
