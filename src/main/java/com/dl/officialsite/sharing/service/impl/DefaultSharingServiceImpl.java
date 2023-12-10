package com.dl.officialsite.sharing.service.impl;

import com.dl.officialsite.common.base.Pagination;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.dao.ISharingRepository;
import com.dl.officialsite.sharing.model.db.TbShare;
import com.dl.officialsite.sharing.model.vo.SharingVo;
import com.dl.officialsite.sharing.model.req.CreateSharingReq;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.AllSharingResp;
import com.dl.officialsite.sharing.model.resp.SharingByUserResp;
import com.dl.officialsite.sharing.service.IUserSharingService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DefaultSharingServiceImpl implements IUserSharingService {

    @Autowired
    private ISharingRepository sharingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired(required=true)
    private HttpServletRequest request;

    @Override
    public long createSharing(CreateSharingReq req) {
        /**
         * 登陆用户转member
         */
//        SessionUserInfo userInfo = HttpSessionUtils.getMember(request.getSession());
//        Preconditions.checkState(userInfo != null, "User info not null");
//        Optional<Member> memberOpt = this.memberRepository.findByAddress(userInfo.getAddress());
//        Member member = memberOpt.get();
//        if(member.getId() != req.)
        /**
         * 创建资料
         */
        log.info("createSharing start, uid");
        TbShare entity = new TbShare();
        entity.setTheme(req.getTheme());
        entity.setDate(req.getDate());
        entity.setTime(req.getTime());
        entity.setPresenter(req.getPresenter());
        entity.setOrg(req.getOrg());
        entity.setTwitter(req.getTwitter());
        entity.setMemberId(req.getMemberId());
        entity.setSharingDoc(req.getSharingDoc());
        entity.setLabel(req.getLabel());
        entity.setLockStatus(SharingLockStatus.UNLOCKED.getCode());
        this.sharingRepository.save(entity);
        return entity.getId();
    }


    @Override
    public void updateSharing(UpdateSharingReq req) {
        //Verify
        Optional<TbShare> existed = this.sharingRepository.findById(req.getId());
        if(!existed.isPresent()){
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }
        TbShare sharing = existed.get();
        SessionUserInfo userInfo = HttpSessionUtils.getMember(request.getSession());
        Member member = this.memberRepository.findByAddress(userInfo.getAddress()).get();

//        if(!Objects.equals(sharing.getMemberId(), member.getId())){
//            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
//        }

        if(!Objects.equals(sharing.getMemberId(), req.getMemberId())){
            throw new BizException(CodeEnums.SHARING_NOT_OWNER);
        }
        if(sharing.getLockStatus() == SharingLockStatus.LOCKED.getCode()){
            throw new BizException(CodeEnums.SHARING_LOCKED);
        }
        //Update
        sharing.setTheme(req.getTheme());
        sharing.setDate(req.getDate());
        sharing.setTime(req.getTime());
        sharing.setPresenter(req.getPresenter());
        sharing.setOrg(req.getOrg());
        sharing.setTwitter(req.getTwitter());
        sharing.setSharingDoc(req.getSharingDoc());
        sharing.setLabel(req.getLabel());

        this.sharingRepository.save(sharing);
    }

    @Override
    public void deleteSharing(long shareId, long memberId) {
        //Verify
        Optional<TbShare> existed = this.sharingRepository.findById(shareId);
        if(!existed.isPresent()){
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }
        TbShare sharing = existed.get();
//        SessionUserInfo userInfo = HttpSessionUtils.getMember(request.getSession());
//        Member member = this.memberRepository.findByAddress(userInfo.getAddress()).get();
        if(!Objects.equals(sharing.getMemberId(), memberId)){
            throw new BizException(CodeEnums.SHARING_NOT_OWNER);
        }
        //Delete
        this.sharingRepository.deleteById(shareId);
    }


    @Override
    public AllSharingResp loadSharing(int pageNo, int pageSize) {
        int offset  = (pageNo - 1)*pageSize;
        int totalCount = this.sharingRepository.loadAllCount();
        int totalPages = (totalCount + pageSize - 1) / pageSize;
        List<TbShare> items = this.sharingRepository.findAllSharesPaged(offset, pageSize);

        AllSharingResp resp = new AllSharingResp();
        resp.setData(items.stream().map(i-> SharingVo.convert(i)).collect(Collectors.toList()));
        resp.setPagination(new Pagination(totalCount, totalPages, pageNo, items.size(), pageNo < totalPages));

        return resp;
    }

    @Override
    public SharingVo querySharing(long shareId) {
        Optional<TbShare> sharingEntity = this.sharingRepository.findById(shareId);
        if(!sharingEntity.isPresent()){
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }

        return SharingVo.convert(sharingEntity.get());
    }

    @Override
    public SharingByUserResp loadSharingByUser(long memberId, int pageNo, int pageSize) {
        int offset  = (pageNo - 1)*pageSize;
        int totalCount = this.sharingRepository.loadCountByUid(memberId);
        int totalPages =(totalCount + pageSize - 1) / pageSize;
        List<TbShare> items = this.sharingRepository.findAllSharesByUidPaged(memberId, offset, pageSize);

        SharingByUserResp resp = new SharingByUserResp();
        resp.setData(items.stream().map(i-> SharingVo.convert(i)).collect(Collectors.toList()));
        resp.setPagination(new Pagination(totalCount, totalPages, pageNo, items.size(), pageNo < totalPages));

        return resp;
    }
}
