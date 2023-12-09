package com.dl.officialsite.sharing.service;

import com.dl.officialsite.sharing.model.req.InitSharingRewardsReq;
import com.dl.officialsite.sharing.model.resp.ClaimSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PreCheckSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PrepareSharingRewardsResp;

public interface IRewardService {

    /**
     * 预先查看分享奖励信息供分享人确认
     * @param sharingId
     * @return
     */
    PreCheckSharingRewardResp preCheckSharingReward(long sharingId);

    /**
     * 领取奖励
     * @param sharingId
     * @return
     */
    ClaimSharingRewardResp claimSharingReward(long sharingId);


    /**
     * 选择一批已完成的分享发放奖励
     * @param req
     * @return
     */
    PrepareSharingRewardsResp prepareRewards(InitSharingRewardsReq req);


}
