package com.dl.officialsite.sharing.service;

import com.dl.officialsite.sharing.model.vo.SharingVo;
import com.dl.officialsite.sharing.model.req.CreateSharingReq;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.AllSharingResp;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;

/**
 * 分享人的分享管理功能
 */
public interface IUserSharingService {

    /**
     * 创建分享
     * @param req
     * @return
     */
    long createSharing(CreateSharingReq req);

    /**
     * 修改分享
     */
    void updateSharing(UpdateSharingReq req);

    /**
     * 删除分享
     */
    void deleteSharing(long shareId);


    /**
     * 查看全部分享
     * @return
     */
    AllSharingResp loadSharing(int pageNo, int pageSize);

    /**
     * 查看分享内容
     * @param shareId
     * @return
     */
    SharingVo querySharing(long shareId);


    /**
     * 查看用户的分享
     * @param memberId
     * @param pageNo 1开始
     * @param pageSize
     * @return
     */
    SharingByUserResp loadSharingByUser(long memberId, int pageNo, int pageSize);
}
