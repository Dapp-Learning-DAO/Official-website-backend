package com.dl.officialsite.wish.job;

import com.dl.officialsite.wish.WishService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;


@Component
public class WishXxlJob {

    @Resource
    private WishService wishService;

    /**
     * 更新Wish状态
     */
    @XxlJob("updateWishTokenAmountJobHandler")
    public void updateRedPacketStatus() {
        XxlJobHelper.log("updateWishTokenAmount start");
        wishService.updateWishTokenAmount();
        XxlJobHelper.log("updateWishTokenAmount end");
    }

}
