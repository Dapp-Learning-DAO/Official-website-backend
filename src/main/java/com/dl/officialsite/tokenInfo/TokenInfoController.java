package com.dl.officialsite.tokenInfo;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.distributor.vo.GetTokenByPageReqVo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenInfoController {

    @Autowired
    private TokenInfoService tokenInfoService;
    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @PostMapping("/create")
    public BaseResponse saveToken(@Valid @RequestBody TokenInfo param) {
        return BaseResponse.successWithData(tokenInfoService.save(param));
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteToken(@PathVariable("id") Long id) {
        tokenInfoService.deleteToken(id);
        return BaseResponse.success();
    }

    @GetMapping(value = "/detail/{id}")
    BaseResponse getTokenById(@PathVariable("id") Long id) {
        return BaseResponse.successWithData(tokenInfoRepository.findById(id));
    }

    @GetMapping(value = "/page")
    BaseResponse getTokenByPage(@RequestParam GetTokenByPageReqVo param) {
        return BaseResponse.successWithData(tokenInfoService.queryToekenByPage(param));
    }

}