package com.dl.officialsite.sharing.reward;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.sharing.model.resp.ClaimSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PreCheckSharingRewardResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("share/v1/reward")
@Slf4j
public class SharingRewardController {

    @Autowired
    private IRewardService rewardService;
    /**
     * 预先查看分享奖励信息供分享人确认
     * @param sharingId
     * @return
     */
    @GetMapping("precheck")
    public BaseResponse<PreCheckSharingRewardResp> preCheckSharingReward(long sharingId){
        return BaseResponse.successWithData(this.rewardService.preCheckSharingReward(sharingId));
    }

    /**
     * 领取奖励
     * @param sharingId
     * @return
     */
    @GetMapping("claim")
    public BaseResponse<ClaimSharingRewardResp> claimSharingReward(long sharingId){
        return BaseResponse.successWithData(this.rewardService.claimSharingReward(sharingId));
    }

}
