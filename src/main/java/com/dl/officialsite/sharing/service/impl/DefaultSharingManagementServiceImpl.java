package com.dl.officialsite.sharing.service.impl;

import com.dl.officialsite.sharing.model.req.CreateSessionRoomReq;
import com.dl.officialsite.sharing.model.req.GenerateSharingDataReq;
import com.dl.officialsite.sharing.model.resp.CreateMeetingRoomResp;
import com.dl.officialsite.sharing.model.resp.GenerateSharingDataResp;
import com.dl.officialsite.sharing.model.resp.QueryMeetingResp;
import com.dl.officialsite.sharing.service.ISharingManagementService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class DefaultSharingManagementServiceImpl implements ISharingManagementService {
    @Override
    public void lockSharing(long shareId) {

    }

    @Override
    public void unlockSharing(long shareId) {

    }

    @Override
    public CreateMeetingRoomResp createSessionRoom(CreateSessionRoomReq req) {
        return null;
    }

    @Override
    public QueryMeetingResp queryMeeting(long shareId) {
        return null;
    }

    @Override
    public GenerateSharingDataResp viewSharingData(GenerateSharingDataReq req) {
        return null;
    }
}
