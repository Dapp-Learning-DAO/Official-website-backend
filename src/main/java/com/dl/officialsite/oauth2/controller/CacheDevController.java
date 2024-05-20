package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.oauth2.AccessTokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Profile({"dev", "local"})
public class CacheDevController {

    @GetMapping("cache/list")
    public BaseResponse listAll(){
        return BaseResponse.successWithData(AccessTokenCacheService.listAll());
    }
    @GetMapping("cache/add")
    public BaseResponse add(@RequestParam String key,@RequestParam String value){
        AccessTokenCacheService.addGitHubAccessToken(key,value);
        return BaseResponse.success();
    }

}
