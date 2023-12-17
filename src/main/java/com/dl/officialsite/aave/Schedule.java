package com.dl.officialsite.aave;

import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.team.TeamService;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import java.math.BigInteger;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName Scheaml
 * @Author jackchen
 * @Date 2023/11/5 14:53
 * @Description Schedule
 **/
@Component
@Slf4j
public class Schedule {

    @Autowired
    private AaveService aaveService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TeamService teamService;

    BigInteger e16 = new BigInteger("10000000000000000");

    /**
     * 监控价格一天发送一次
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void monitorPrice() throws Exception {
        log.info("monitorPrice start");
        //查找team0 memeber获取地址
//        TeamQueryVo teamQueryVo = new TeamQueryVo();
//        teamQueryVo.setTeamName("Dapp-Learning DAO core founders");
        List<TeamsWithMembers> teamAndMembers = teamService.getTeamWithMembersByTeamNameAndStatus("Dapp-Learning DAO core founders", 0);
        if (teamAndMembers.size() != 0) {
            for (TeamsWithMembers teamAndMember : teamAndMembers) {
                for (Member member : teamAndMember.getMembers()) {
                    String email = member.getEmail();
                    HealthInfo healthInfo = aaveService.getHealthInfo(member.getAddress());
                    TokenInfoList tokenInfoList = aaveService.getVar();
                    emailService.sendMail(email, "Dapp Defi Monitor", tokenInfoList.toString() + "\n" + healthInfo.toString());
                }
            }
        }
    }

    /**
     * 监控健康系数，如果小于1.2，立即发送邮件
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void monitorHealth() {
        try {
            log.info("monitorHealth start");
            //查找team0 memeber获取地址
            TeamQueryVo teamQueryVo = new TeamQueryVo();
            teamQueryVo.setTeamName("Dapp-Learning DAO core founders");
            List<TeamsWithMembers> teamAndMembers = teamService.getTeamWithMembersByTeamNameAndStatus("Dapp-Learning DAO core founders", 0);
            if (teamAndMembers.size() != 0) {
                for (TeamsWithMembers teamAndMember : teamAndMembers) {
                    for (Member member : teamAndMember.getMembers()) {
                        String email = member.getEmail();
                        HealthInfo healthInfo = aaveService.getHealthInfo(member.getAddress());
                        float health = healthInfo.getHealthFactor().divide(e16).floatValue() / 100;
                        log.info("health is : " + health);
                        if (health < 1.2) {
                            emailService.sendMail(email, "Dapp Defi Monitor", healthInfo.toString());
                        }
                        emailService.sendMail(email, "Dapp Defi Monitor HealthInfo < 1.2",
                            "health is : " + health);
                    }
                }
            }
        } catch (Exception e) {
            log.error("monitorHealth error", e);
        }
    }


}
