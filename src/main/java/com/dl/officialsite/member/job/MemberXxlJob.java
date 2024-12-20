package com.dl.officialsite.member.job;

import com.dl.officialsite.member.MemberService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/8 12:15 PM
 **/
@Component
@Slf4j
public class MemberXxlJob {


    @Autowired
    private MemberService memberService;
    /**
     * 更新share_count,一小时触发一次
     */
    @XxlJob("updateShareCountJobHandler")
    public void updateShareCount() {
        XxlJobHelper.log("updateShareCount start");
        memberService.updateShareCount();
        XxlJobHelper.log("updateShareCount end");
    }

}
