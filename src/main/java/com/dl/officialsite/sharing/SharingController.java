package com.dl.officialsite.sharing;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.login.Auth;
import com.dl.officialsite.sharing.constant.SharingStatus;
import com.dl.officialsite.sharing.model.bo.RankDto;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        share.setStatus(SharingStatus.PENDING);
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

    /**
     * 审批分享
     */
    @PostMapping("/approve")
    public BaseResponse approveSharing(@RequestParam("shareId") Long shareId, @RequestParam(
        "address") String address){
        sharingService.approveSharing(shareId, address, SharingStatus.SHARING);
        return BaseResponse.success();
    }

    /**
     * 分享完成
     */
    @PostMapping("/finish")
    public BaseResponse finishSharing(@RequestParam("shareId") Long shareId, @RequestParam(
        "address") String address){
        sharingService.approveSharing(shareId, address, SharingStatus.PENDING_REWARD);
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
     * 条件搜索
     */
    @PostMapping("/search")
    public BaseResponse searchSharing(@RequestBody ShareSearchVo searchVo,
        @RequestParam(value = "pageNo",defaultValue = "1") int pageNumber,
        @RequestParam(value = "pageSize",defaultValue = "20") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Share> page = sharingService.searchSharing(searchVo, pageable);
        return BaseResponse.successWithData(page);
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
      */
    @GetMapping("user")
    public BaseResponse loadSharingByUser(@RequestParam("memberAddress") String memberAddress,
                                                             @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                             @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {
        return BaseResponse.successWithData(this.sharingService.loadSharingByUser(memberAddress, pageNo, pageSize));
    }

    /**
     * 分享排行榜
     */
    @GetMapping("/rank")
    public BaseResponse rank(@RequestParam("rankNumber") Integer rankNumber) {
        List<RankDto> rankList = sharingService.rank(rankNumber);
        return BaseResponse.successWithData(rankList);
    }

    /**
     * 查询分享标签
     */
    @PostMapping("/queryShareTag")
    public BaseResponse queryShareTag(){
        return BaseResponse.successWithData(sharingService.queryShareTag());
    }
}
