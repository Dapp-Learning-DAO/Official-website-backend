package com.dl.officialsite.wish;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.wish.params.AddWishParam;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WishController
 * @Author jackchen
 * @Date 2024/12/25 16:50
 * @Description WishController
 **/
@RestController
@RequestMapping("/wish")
@Slf4j
public class WishController {

    @Resource
    private WishService wishService;

    //new wish
    @PostMapping
    public BaseResponse add(@RequestBody AddWishParam addWishParam, @RequestParam String address) {
        return BaseResponse.successWithData(wishService.add(addWishParam, address));
    }


}
