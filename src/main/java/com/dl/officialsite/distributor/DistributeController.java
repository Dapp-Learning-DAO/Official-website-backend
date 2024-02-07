package com.dl.officialsite.distributor;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerService;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/distribute")
public class DistributeController {

    @Autowired
    private DistributeService distributeService;
    @Autowired
    private DistributeClaimerService distributeClaimerService;

    public static final Logger logger = LoggerFactory.getLogger(DistributeController.class);

    @PostMapping("/create")
    public BaseResponse createDistributor(@RequestBody DistributeInfoVo param) {
        return BaseResponse.successWithData(distributeService.createDistribute(param));
    }

    @DeleteMapping("/{id}")
    public BaseResponse createDistribute(@PathVariable("id") Long id) {
        distributeService.deleteDistribute(id);
        return BaseResponse.success();
    }

    @GetMapping(value = "/detail/{id}")
    BaseResponse getDistributeById(@PathVariable("id") Long id) {
        DistributeInfoVo distributeInfoVo = distributeService.queryDistributeDetail(id);
        return BaseResponse.successWithData(distributeInfoVo);
    }

    @GetMapping(value = "/page")
    BaseResponse getDistributeByPage(@ModelAttribute GetDistributeByPageReqVo param) {
        return BaseResponse.successWithData(distributeService.queryDistributeByPage(param));
    }


}