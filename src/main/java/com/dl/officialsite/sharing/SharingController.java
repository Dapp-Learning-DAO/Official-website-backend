package com.dl.officialsite.sharing;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.login.Auth;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share")
@Slf4j
public class SharingController {

    @Autowired
    private SharingService sharingService;

    //————————————————————————————————————分享人相关功能————————————————————————————————————
    /**
     * 创建分享
     * @param share
     * @return
     */
    @PostMapping("create")
    public BaseResponse createSharing(@RequestBody Share share, @RequestParam("address") String address){
        return BaseResponse.successWithData(this.sharingService.createSharing(share, address));
    }

    /**
     * 修改分享
     */
    @PostMapping("update")
    public BaseResponse updateSharing(@RequestBody UpdateSharingReq req, @RequestParam("address") String address){
        this.sharingService.updateSharing(req, address);
        return BaseResponse.success();
    }

    /**
     * 删除分享
     */
    @PostMapping("delete")
    @Auth("admin")
    public BaseResponse deleteSharing(@RequestParam("shareId") long shareId,  @RequestParam("address") String address){
        this.sharingService.deleteSharing(shareId, address);
        return BaseResponse.success();
    }

    //————————————————————————————————————网站读者相关功能————————————————————————————————————
    /**
     * 查看全部分享
     * @return
     */
    @GetMapping("all")
    public BaseResponse loadSharing(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize){
        return BaseResponse.successWithData(this.sharingService.findAll(pageNo,pageSize));
    }

    /**
     * 查看分享内容
     * @param shareId
     * @return
     */
    @GetMapping("query")
    public BaseResponse<Share> querySharing(@RequestParam("shareId") long shareId){
        return BaseResponse.successWithData(this.sharingService.querySharing(shareId));
    }

    /**
     * 查看用户的分享
     */
    @GetMapping("user")
    public BaseResponse loadSharingByUser(@RequestParam("memberAddress") String memberAddress,
                                                             @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                             @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {
        return BaseResponse.successWithData(this.sharingService.loadSharingByUser(memberAddress, pageNo, pageSize));
    }
}
