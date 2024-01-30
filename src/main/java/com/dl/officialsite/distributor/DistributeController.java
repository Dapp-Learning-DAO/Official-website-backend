package com.dl.officialsite.distributor;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.utils.MerkleTree;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimer;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerManager;
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
    public BaseResponse createDistributor(@Valid @RequestBody DistributeInfo param) {
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


    @PostMapping("/merkle/root/{id}")
    public BaseResponse buildMerkleTree(@PathVariable("id") Long id) {
        return BaseResponse.successWithData(distributeService.buildAndSaveMerkleTreeRoot(id));
    }

    @GetMapping("/merkle/proof")
    public BaseResponse generateMerkleProof(@RequestParam("distributeId") Long distributeId,@RequestParam("claimerId") Long claimerId) {
        return BaseResponse.successWithData(distributeService.generateMerkleProof(distributeId,claimerId));
    }

}