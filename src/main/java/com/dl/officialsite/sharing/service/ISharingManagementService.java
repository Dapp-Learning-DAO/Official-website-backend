package com.dl.officialsite.sharing.service;

import com.dl.officialsite.sharing.model.req.CreateSessionRoomReq;
import com.dl.officialsite.sharing.model.req.GenerateSharingDataReq;
import com.dl.officialsite.sharing.model.req.InitSharingRewardsReq;
import com.dl.officialsite.sharing.model.resp.CreateSessionRoomResp;
import com.dl.officialsite.sharing.model.resp.PrepareSharingRewardsResp;
import com.dl.officialsite.sharing.model.resp.GenerateSharingDataResp;

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


    /**
     * 创建session room
     * @param req
     * @return
     */
    CreateSessionRoomResp createSessionRoom(CreateSessionRoomReq req);

    /**
     * 生成分享海报、文案
     * @param req
     * @return
     */
    GenerateSharingDataResp generateSharingData(GenerateSharingDataReq req);


    /**
     * 选择一批已完成的分享发放奖励
     * @param req
     * @return
     */
    PrepareSharingRewardsResp prepareRewards(InitSharingRewardsReq req);



}
