package com.dl.officialsite.sharing.controller;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.sharing.model.vo.SharingVo;
import com.dl.officialsite.sharing.model.req.CreateSharingReq;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.AllSharingResp;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;
import com.dl.officialsite.sharing.service.IUserSharingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("share/usershare")
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
    public BaseResponse deleteSharing(@RequestParam("shareId") long shareId, @RequestParam("memberId") long memberId){
        this.userSharingService.deleteSharing(shareId, memberId);
        return BaseResponse.success();
    }

    //————————————————————————————————————网站读者相关功能————————————————————————————————————
    /**
     * 查看全部分享
     * @return
     */
    @GetMapping("all")
    public BaseResponse<AllSharingResp> loadSharing(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize){
        return BaseResponse.successWithData(this.userSharingService.loadSharing(pageNo, pageSize));
    }

    /**
     * 查看分享内容
     * @param shareId
     * @return
     */
    @GetMapping("queryByShareId")
    public BaseResponse<SharingVo> querySharing(@RequestParam("shareId") long shareId){
        return BaseResponse.successWithData(this.userSharingService.querySharing(shareId));
    }

    /**
     * 查看用户的分享
     */
    @GetMapping("byUser")
    public BaseResponse<SharingByUserResp> loadSharingByUser(@RequestParam("memberId") long memberId,
                                                             @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                             @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {
        return BaseResponse.successWithData(this.userSharingService.loadSharingByUser(memberId, pageNo, pageSize));
    }
}
