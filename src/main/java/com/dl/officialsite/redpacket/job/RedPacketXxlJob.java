package com.dl.officialsite.redpacket.job;

import com.dl.officialsite.redpacket.RedPacketService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/7/6 7:26 PM
 **/
@Component
public class RedPacketXxlJob {

    @Autowired
    private RedPacketService redPacketService;


    /**
     * 更新红包状态
     */
    @XxlJob("updateRedPacketStatusJobHandler")
    public void updateRedPacketStatus() {
        XxlJobHelper.log("updateRedPacketStatus start");
        redPacketService.updateRedpacketStatus();
        XxlJobHelper.log("updateRedPacketStatus end");
    }
}
