package com.dl.officialsite.distributor.distributeClaimer;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.distributor.vo.AddDistributeClaimerReqVo;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerByPageReqVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/distribute-claimer")
public class DistributeClaimerController {

    @Autowired
    private DistributeClaimerService distributeClaimerService;
    @Autowired
    private DistributeClaimerManager distributeClaimerManager;


    @PostMapping("/add")
    public BaseResponse addDistributeClaimer( @RequestBody AddDistributeClaimerReqVo param) {
        distributeClaimerService.saveClaimer(param);
        return BaseResponse.success();
    }

    @GetMapping(value = "/detail/{id}")
    BaseResponse getDistributeClaimerById(@PathVariable("id") Long id) {
        GetDistributeClaimerRspVo distributeClaimerInfoVo = distributeClaimerManager.queryDistributeClaimerDetail(id);
        return BaseResponse.successWithData(distributeClaimerInfoVo);
    }

    @DeleteMapping("/{id}")
    public BaseResponse deletDistributeClaimer(@PathVariable("id") Long id) {
        distributeClaimerService.deleteDistributeClaimer(id);
        return BaseResponse.success();
    }

    @GetMapping(value = "/page")
    BaseResponse getDistributeByPage(@ModelAttribute GetDistributeClaimerByPageReqVo param) {
        return BaseResponse.successWithData(distributeClaimerService.queryDistributeClaimerByPage(param));
    }

}