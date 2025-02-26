package com.dl.officialsite.wish;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.wish.domain.Wish;
import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.params.ApplyWishParam;
import com.dl.officialsite.wish.params.DonationWishParam;
import com.dl.officialsite.wish.params.EditWishParam;
import com.dl.officialsite.wish.params.QueryWishParam;
import com.dl.officialsite.wish.result.WishDetailResult;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    //edit wish
    @PutMapping
    public BaseResponse edit(@RequestBody EditWishParam editWishParam, @RequestParam String address) {
        wishService.edit(editWishParam, address);
        return BaseResponse.success();
    }

    //delete wish
    @DeleteMapping
    public BaseResponse delete(@RequestParam Long id, @RequestParam String address) {
        wishService.delete(id, address);
        return BaseResponse.success();
    }

    //list wish
    @PostMapping("/list")
    public BaseResponse list(@RequestBody QueryWishParam queryWishParam,
        @RequestParam(value = "pageNo",defaultValue = "1") int pageNumber,
        @RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
        @RequestParam String address) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Wish> page = wishService.list(queryWishParam, pageable);
        return BaseResponse.successWithData(page);
    }

    //get wish
    @GetMapping
    public BaseResponse<WishDetailResult> get(@RequestParam Long id, @RequestParam String address) {
        return BaseResponse.successWithData(wishService.get(id, address));
    }

    //like wish
    @PostMapping("/like")
    public BaseResponse like(@RequestParam Long wishId, @RequestParam String address) {
        wishService.like(wishId, address);
        return BaseResponse.success();
    }

    //donation wish
    @PostMapping("/donation")
    public BaseResponse apply(@RequestParam String address,
        @RequestBody DonationWishParam donationWishParam) {
        wishService.donation(address, donationWishParam);
        return BaseResponse.success();
    }


    @GetMapping("test")
    public void test() {
        wishService.updateWishTokenAmount();
    }



}
