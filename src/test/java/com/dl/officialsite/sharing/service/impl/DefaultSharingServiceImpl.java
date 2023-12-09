package com.dl.officialsite.sharing.service.impl;

import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.sharing.dao.ISharingRepository;
import com.dl.officialsite.sharing.model.db.SharingEntity;
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
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultSharingServiceImpl implements IUserSharingService {

    @Autowired
    private ISharingRepository repository;

//    @Override
//    public long createSharing(CreateSharingReq req) {
//        Member member = HttpSessionUtils.getMember();
//        log.info("createSharing start, uid");
//        SharingEntity entity = new SharingEntity();
//        entity.setTheme(req.getTheme());
//        entity.setDate(req.getDate());
//        entity.setTime(req.getTime());
//        entity.setPresenter(req.getPresenter());
//        entity.setOrg(req.getOrg());
//        entity.setTwitter(req.getTwitter());
//        entity.setMemberId(req.getMe);
//
////        /**
////         * 分享所属组织
////         */
////        private String Org;
////
////        /**
////         * 分享人twitter
////         */
////        private String twitter;
////
////        /**
////         * 分享人
////         */
////        private String memberId;
////
////        /**
////         * 文档连接
////         */
////        private String sharingDoc;
////
////        /**
////         * 标签类别
////         */
////        //defi zk underlying
////        private String label;
////
////        /**
////         * 奖励金额
////         */
////        private  Integer reward;
//
//        repository.save()
//
//    }

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
