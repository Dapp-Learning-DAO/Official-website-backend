package com.dl.officialsite.sharing;

import com.dl.officialsite.common.base.PagedList;
import com.dl.officialsite.common.base.Pagination;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SharingService  {

    @Autowired
    private SharingRepository sharingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired(required=true)
    private HttpServletRequest request;

    @Autowired
    TeamService teamService;


    public Share createSharing(Share share, String address) {
        /**
         * 登陆用户转member
         */
//        SessionUserInfo userInfo = HttpSessionUtils.getMember(request.getSession());
//        Preconditions.checkState(userInfo != null, "User info not null");
//        Optional<Member> memberOpt = this.memberRepository.findByAddress(userInfo.getAddress());
//        Member member = memberOpt.get();
//        if(member.getId() != req.)

        return this.sharingRepository.save(share);
    }



    public void updateSharing(UpdateSharingReq req, String address) {
        //Verify
        Optional<Share> existed = this.sharingRepository.findById(req.getId());
        if (!existed.isPresent()) {
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }
        Share sharing = existed.get();
        Member member = this.memberRepository.findByAddress(address).get();

        if (Objects.equals(sharing.getMemberAddress(), member.getAddress()) || teamService.checkMemberIsAdmin(address)) {


            if (sharing.getLockStatus() == SharingLockStatus.LOCKED.getCode()) {
                throw new BizException(CodeEnums.SHARING_LOCKED);
            }
            //Update
            sharing.setTheme(req.getTheme());
            sharing.setDate(req.getDate());
            sharing.setTime(req.getTime());
            sharing.setLanguage(req.getLanguage());
            sharing.setPresenter(req.getPresenter());
            sharing.setOrg(req.getOrg());
            sharing.setTwitter(req.getTwitter());
            sharing.setSharingDoc(req.getSharingDoc());
            sharing.setLabel(req.getLabel());

            this.sharingRepository.save(sharing);
        } else {
            throw new BizException(CodeEnums.SHARING_NOT_OWNER_OR_ADMIN);
        }
    }


    public void deleteSharing(long shareId, String address) {
        //Verify
        Optional<Share> existed = this.sharingRepository.findById(shareId);
        if(!existed.isPresent()){
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }
        Share sharing = existed.get();
        //Delete
        this.sharingRepository.deleteById(shareId);
    }



//    public AllSharingResp loadSharing(int pageNo, int pageSize) {
//        int offset  = (pageNo - 1)*pageSize;
//        int totalCount = this.sharingRepository.loadAllCount();
//        int totalPages = (totalCount + pageSize - 1) / pageSize;
//        List<Share> items = this.sharingRepository.findAllSharesPaged(offset, pageSize);
//
//        AllSharingResp resp = new AllSharingResp();
//        resp.setData(items.stream().map(i-> SharingVo.convert(i)).collect(Collectors.toList()));
//        resp.setPagination(new Pagination(totalCount, totalPages, pageNo, items.size(), pageNo < totalPages));
//
//        return resp;
//    }


    public Share querySharing(long shareId) {
        Optional<Share> sharingEntity = this.sharingRepository.findById(shareId);
        if(!sharingEntity.isPresent()){
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }

        return sharingEntity.get();
    }


    public PagedList loadSharingByUser(String memberAddress, int pageNo, int pageSize) {
        int offset  = (pageNo - 1)*pageSize;
        int totalCount = this.sharingRepository.loadCountByUid(memberAddress);
        int totalPages =(totalCount + pageSize - 1) / pageSize;
        List<Share> items = this.sharingRepository.findAllSharesByUidPaged(memberAddress, offset, pageSize);

      //  SharingByUserResp resp = new SharingByUserResp();
        PagedList resp = new PagedList(items ,new Pagination(totalCount, totalPages, pageNo, items.size(), pageNo < totalPages));


        return resp;
    }

    public Page<Share> findAll(Pageable pageable) {
       return  sharingRepository.findAll(pageable);
    }
}
