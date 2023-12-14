package com.dl.officialsite.sharing.management;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.sharing.model.req.CreateSessionRoomReq;
import com.dl.officialsite.sharing.model.req.GenerateSharingDataReq;
import com.dl.officialsite.sharing.model.resp.CreateMeetingRoomResp;
import com.dl.officialsite.sharing.model.resp.GenerateSharingDataResp;
import com.dl.officialsite.sharing.model.resp.QueryMeetingResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("share/v1/management")
@Slf4j
public class SharingManagementController {

    @Autowired
    private ISharingManagementService service;

    /**
     * 分享锁定之后，修改人就不能修改。这功能是避免做好海报之后，分享人又改内容，导致海报同分享不一致场景
     */
    @PostMapping("lock")
    public BaseResponse lockSharing(long shareId){
        this.service.lockSharing(shareId);
        return BaseResponse.success();
    }

    /**
     * 解锁。特别情况下，分享人临时改内容。
     * @param shareId
     */
    @PostMapping("unlock")
    public BaseResponse unlockSharing(long shareId){
        this.service.unlockSharing(shareId);
        return BaseResponse.success();
    }


    /**
     * 创建session room
     * @param req
     * @return
     */
    @PostMapping("meeting/creation")
    public BaseResponse<CreateMeetingRoomResp> createSessionRoom(CreateSessionRoomReq req){
        CreateMeetingRoomResp resp = this.service.createSessionRoom(req);
        return BaseResponse.successWithData(resp);
    }

    @PostMapping("meeting/query")
    public BaseResponse<QueryMeetingResp> createSessionRoom(long shareId){
        QueryMeetingResp resp = this.service.queryMeeting(shareId);
        return BaseResponse.successWithData(resp);
    }


    /**
     * 生成分享海报、文案
     * @param req
     * @return
     */
    @PostMapping("view")
    public BaseResponse<GenerateSharingDataResp> viewSharingData(GenerateSharingDataReq req){
        GenerateSharingDataResp resp = this.service.viewSharingData(req);
        return BaseResponse.successWithData(resp);
    }
}
