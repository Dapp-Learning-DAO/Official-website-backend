package com.dl.officialsite.defi.job;

import com.dl.officialsite.defi.Schedule;
import com.dl.officialsite.defi.TokenInfoList;
import com.dl.officialsite.defi.service.AaveService;
import com.dl.officialsite.defi.service.AaveTokenAPYService;
import com.dl.officialsite.defi.service.WhaleService;
import com.dl.officialsite.defi.vo.HealthInfoVo;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.team.TeamService;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/7/6 7:02 PM
 **/
@Component
@Slf4j
public class DefiXxlJob {

    @Autowired
    private Schedule schedule;

    @Autowired
    private AaveTokenAPYService aaveTokenAPYService;

    @Autowired
    private WhaleService whaleService;


    /**
     * 监控价格一天发送一次
     */
    @XxlJob("monitorPriceJobHandler")
    public void monitorPrice() throws Exception {
        XxlJobHelper.log("monitorPrice start");
        schedule.monitorPrice();
        XxlJobHelper.log("monitorPrice end");
    }

    /**
     * 监控健康系数，如果小于1.2，立即发送邮件
     */
    @XxlJob("monitorHealthJobHandler")
    public void monitorHealth() {
        XxlJobHelper.log("monitorHealth start");
        schedule.monitorHealth();
        XxlJobHelper.log("monitorHealth end");
    }


    /**
     * 定期更新 Token 的 APY（年化收益率）信息
     */
    @XxlJob("updateTokenAPYInfoJobHandler")
    public void updateTokenAPYInfo()  {
        XxlJobHelper.log("update token info task begin --------------------- ");
        aaveTokenAPYService.updateTokenAPYInfo();
        XxlJobHelper.log("update token info task end --------------------- ");
    }

    /**
     * 更新Whale和WhaleTxRow数据
     */
    @XxlJob("aaveListenerJobHandler")
    public void aaveListener() {
        XxlJobHelper.log("aaveListenerJobHandler start");
        whaleService.aaveListener();
        XxlJobHelper.log("aaveListenerJobHandler end");
    }

}
