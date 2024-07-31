package com.dl.officialsite.distributor.job;

import com.dl.officialsite.distributor.DistributeService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/7/6 7:40 PM
 **/
@Component
@Slf4j
public class DistributorXxlJob {

    @Autowired
    private DistributeService distributeService;

    /**
     * 更新链上状态
     */
    @XxlJob("updateDistributeStatusJobHandler")
    public void updateDistributeStatus() {
        XxlJobHelper.log("updateDistributeStatus start");
        distributeService.updateDistributeStatus();
        XxlJobHelper.log("updateDistributeStatus end");
    }
}
