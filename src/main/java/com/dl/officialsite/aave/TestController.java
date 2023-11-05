package com.dl.officialsite.aave;

import com.dl.officialsite.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author jackchen
 * @Date 2023/11/5 11:11
 * @Description TestController
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AaveService aaveService;

    @GetMapping("/test")
    public BaseResponse test() throws Exception {
        String var = aaveService.getHealthInfo(
            "0x3f5ce5fbfe3e9af3971dd833d26ba9b5c936f0be").getHealthFactor().toString();
        return BaseResponse.successWithData(var);
    }
}
