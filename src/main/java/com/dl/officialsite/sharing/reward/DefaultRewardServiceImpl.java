package com.dl.officialsite.sharing.reward;

import com.dl.officialsite.sharing.model.resp.ClaimSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PreCheckSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PrepareSharingRewardsResp;
import org.springframework.stereotype.Service;

@Service
public class DefaultRewardServiceImpl implements IRewardService {
    @Override
    public PreCheckSharingRewardResp preCheckSharingReward(long sharingId) {
        return null;
    }

    @Override
    public ClaimSharingRewardResp claimSharingReward(long sharingId) {
        return null;
    }

    @Override
    public PrepareSharingRewardsResp prepareRewards(InitSharingRewardsReq req) {
        return null;
    }
}
