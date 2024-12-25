package com.dl.officialsite.wish;

import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.result.WishDetailResult;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName WishService
 * @Author jackchen
 * @Date 2024/12/25 16:51
 * @Description WishService
 **/
@Service
public class WishService {

    @Resource
    private WishRepository wishRepository;


    public WishDetailResult add(AddWishParam addWishParam, String address) {
        Wish wish = addWishParam.buildWish();
        wishRepository.save(wish);
        return buildWishDetailResult(wish);
    }

    private WishDetailResult buildWishDetailResult(Wish wish) {
        WishDetailResult wishDetailResult = new WishDetailResult();
        BeanUtils.copyProperties(wish, wishDetailResult);
        return wishDetailResult;
    }
}
