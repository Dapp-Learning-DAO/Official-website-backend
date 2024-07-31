package com.dl.officialsite.bounty.job;

import com.dl.officialsite.bounty.BountyService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/7/6 7:45 PM
 **/
@Component
public class BountyXxlJob {

    @Autowired
    private BountyService bountyService;

    /**
     * 更新bounty数据
     */
    @XxlJob("updateBountyDataJobHandler")
    public void updateBountyStatus() {
        XxlJobHelper.log("updateBountyData start");
        bountyService.updateBountyData();
        XxlJobHelper.log("updateBountyData end");
    }
}
