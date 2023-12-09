package com.dl.officialsite.sharing.controller;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.sharing.model.pojo.SharingPojo;
import com.dl.officialsite.sharing.model.req.CreateSharingReq;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.AllSharingResp;
import com.dl.officialsite.sharing.model.resp.ClaimSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.PreCheckSharingRewardResp;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;
import com.dl.officialsite.sharing.service.IUserSharingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("share/v1/usershare")
@Slf4j
public class UserSharingController {

    @Autowired
    private IUserSharingService userSharingService;

    //————————————————————————————————————分享人相关功能————————————————————————————————————
    /**
     * 创建分享
     * @param req
     * @return
     */
    @PostMapping("create")
    public BaseResponse<Long> createSharing(CreateSharingReq req){
        return BaseResponse.successWithData(this.userSharingService.createSharing(req));
    }

    /**
     * 修改分享
     */
    @PostMapping("update")
    public BaseResponse updateSharing(UpdateSharingReq req){
        this.userSharingService.updateSharing(req);
        return BaseResponse.success();
    }

    /**
     * 删除分享
     */
    @PostMapping("delete")
    public BaseResponse deleteSharing(long shareId){
        this.userSharingService.deleteSharing(shareId);
        return BaseResponse.success();
    }

    //————————————————————————————————————网站读者相关功能————————————————————————————————————
    /**
     * 查看全部分享
     * @return
     */
    public BaseResponse<AllSharingResp> loadSharing(int pageNo, int pageSize){
        return BaseResponse.successWithData(this.userSharingService.loadSharing(pageNo, pageSize));
    }

    /**
     * 查看分享内容
     * @param shareId
     * @return
     */
    public BaseResponse<SharingPojo> querySharing(long shareId){
        return BaseResponse.successWithData(this.userSharingService.querySharing(shareId));
    }

    /**
     * 查看用户的分享
     * @param uid
     * @return
     */
    public BaseResponse<SharingByUserResp> loadSharingByUser(long uid) {
        return BaseResponse.successWithData(this.userSharingService.loadSharingByUser(uid));
    }
}
