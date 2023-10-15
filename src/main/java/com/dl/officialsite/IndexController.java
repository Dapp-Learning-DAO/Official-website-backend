package com.dl.officialsite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class IndexController {

    @GetMapping("/test")
    public String test() {
        // 退出登录就是将用户信息删除
        return "Hi";
    }

}
