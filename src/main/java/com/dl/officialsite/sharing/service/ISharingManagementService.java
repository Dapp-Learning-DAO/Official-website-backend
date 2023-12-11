package com.dl.officialsite.sharing.service;

import com.dl.officialsite.sharing.model.req.CreateSessionRoomReq;
import com.dl.officialsite.sharing.model.req.GenerateSharingDataReq;
import com.dl.officialsite.sharing.model.resp.CreateMeetingRoomResp;
import com.dl.officialsite.sharing.model.resp.GenerateSharingDataResp;
import com.dl.officialsite.sharing.model.resp.QueryMeetingResp;

import java.io.InputStream;

/**
 * 管理分享
 */
public interface ISharingManagementService {

    /**
     * 分享锁定之后，修改人就不能修改。这功能是避免做好海报之后，分享人又改内容，导致海报同分享不一致场景
     */
    void lockSharing(long shareId);

    /**
     * 解锁。特别情况下，分享人临时改内容。
     * @param shareId
     */
    void unlockSharing(long shareId);


    byte[] generatePost(String presenter, String roomId, String roomLnk);

    /**
     * 创建meeting room
     * @param req
     * @return
     */
    CreateMeetingRoomResp createSessionRoom(CreateSessionRoomReq req);

    /**
     * 查看room
     * @param shareId
     * @return
     */
    QueryMeetingResp queryMeeting(long shareId);

    /**
     * 生成分享海报、文案
     * @param req
     * @return
     */
    GenerateSharingDataResp viewSharingData(GenerateSharingDataReq req);



}
