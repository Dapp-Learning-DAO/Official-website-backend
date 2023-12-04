package com.dl.officialsite.sharing.service.impl;

import com.dl.officialsite.sharing.model.pojo.SharingPojo;
import com.dl.officialsite.sharing.model.req.CreateSharingReq;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.AllSharingResp;
import com.dl.officialsite.sharing.model.resp.ClaimSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PreCheckSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;
import com.dl.officialsite.sharing.service.IUserSharingService;
import org.springframework.stereotype.Service;

@Service
public class DefaultSharingServiceImpl implements IUserSharingService {

    @Override
    public long createSharing(CreateSharingReq req) {
        return 0;
    }

    @Override
    public void updateSharing(UpdateSharingReq req) {

    }

    @Override
    public void deleteSharing(long shareId) {

    }


    @Override
    public AllSharingResp loadSharing(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public SharingPojo querySharing(long shareId) {
        return null;
    }

    @Override
    public SharingByUserResp loadSharingByUser(long uid) {
        return null;
    }
}
