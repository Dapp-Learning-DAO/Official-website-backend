package com.dl.officialsite.defi.controller;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.config.ChainInfo;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleTxRow;
import com.dl.officialsite.defi.service.AaveTokenAPYService;
import com.dl.officialsite.defi.service.WhaleProtocolService;
import com.dl.officialsite.defi.service.WhaleService;
import com.dl.officialsite.defi.vo.HealthInfoVo;
import com.dl.officialsite.defi.vo.params.QueryWhaleParams;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final WhaleService whaleService;

    private final WhaleProtocolService whaleProtocolService;

    private final MemberService memberService;

    private final TeamService teamService;

    public DeFiController(AaveTokenAPYService aaveService, WhaleService whaleService,
        WhaleProtocolService whaleProtocolService,
        MemberService memberService, TeamService teamService) {
        this.aaveService = aaveService;
        this.whaleService = whaleService;
        this.whaleProtocolService = whaleProtocolService;
        this.memberService = memberService;
        this.teamService = teamService;
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

    @GetMapping("/init")
    public BaseResponse init(@RequestParam String address) {
        //teamService.checkMemberIsSuperAdmin(address);
        whaleService.init();
        return BaseResponse.success();
    }

    @GetMapping("/init/whale/protocol")
    public BaseResponse initWhaleProtocol() {
        whaleProtocolService.initWhaleProtocol();
        return BaseResponse.success();
    }

    @GetMapping("/Listener")
    public BaseResponse Listener() {
        whaleService.aaveListener();
        return BaseResponse.success();
    }

    @PostMapping("/query/whale")
    public BaseResponse queryWhale(
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestBody QueryWhaleParams query) {
        Pageable pageable = null;
        if (query.getOrder() == 1) {
            pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "amountUsd"));
        } else {
            pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.ASC, "amountUsd"));
        }
        Page<Whale> whaleDataVos = whaleService.queryWhale(pageable, query);
        return BaseResponse.successWithData(whaleDataVos);
    }

    @GetMapping("/query/whale/tx")
    public BaseResponse queryWhaleTx(
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam String address) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<WhaleTxRow> whaleDataVos = whaleService.queryWhaleTx(pageable, address);
        return BaseResponse.successWithData(whaleDataVos);
    }


}
