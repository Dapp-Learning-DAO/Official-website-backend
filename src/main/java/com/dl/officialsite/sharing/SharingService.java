package com.dl.officialsite.sharing;

import cn.hutool.core.lang.Assert;
import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.event.EventNotify;
import com.dl.officialsite.bot.event.NotifyMessageFactory;
import com.dl.officialsite.common.base.PagedList;
import com.dl.officialsite.common.base.Pagination;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.config.bean.ServerConfig;
import com.dl.officialsite.config.bean.ServerConfigRepository;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.constant.SharingStatus;
import com.dl.officialsite.sharing.model.bo.RankDto;
import com.dl.officialsite.sharing.model.req.UpdateSharingReq;
import com.dl.officialsite.sharing.model.resp.ShareTagResp;
import com.dl.officialsite.team.TeamService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class SharingService {

    @Autowired
    private SharingRepository sharingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    TeamService teamService;

    private final EmailService emailService;

    private final ApplicationContext applicationContext;

    public SharingService(EmailService emailService, ApplicationContext applicationContext) {
        this.emailService = emailService;
        this.applicationContext = applicationContext;
    }


    @Transactional(rollbackFor = Exception.class)
    public Share createSharing(Share share, String address) {
        /**
         * 登陆用户转member
         */
//        SessionUserInfo userInfo = HttpSessionUtils.getMember(request.getSession());
//        Preconditions.checkState(userInfo != null, "User info not null");
//        Optional<Member> memberOpt = this.memberRepository.findByAddress(userInfo.getAddress());
//        Member member = memberOpt.get();
//        if(member.getId() != req.)
        share = sharingRepository.save(share);
        Member creatorInfo = memberRepository.findByAddress(address).orElse(null);
        Assert.isNull(creatorInfo, "not found member by address");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(share.getDate());
        applicationContext.publishEvent(new EventNotify(Member.class, BotEnum.TELEGRAM,
            ChannelEnum.SHARING,
            NotifyMessageFactory.sharingMessage("👏Create New Share👏", creatorInfo.getNickName(), share.getTheme(),
                formatDate)));
        return share;
    }


    public void updateSharing(UpdateSharingReq req, String address) {
        //Verify
        Optional<Share> existed = this.sharingRepository.findById(req.getId());
        if (!existed.isPresent()) {
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }
        Share sharing = existed.get();
        Member member = this.memberRepository.findByAddress(address).get();

        if (Objects.equals(sharing.getMemberAddress(), member.getAddress())
            || teamService.checkMemberIsAdmin(address)) {

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
            sharing.setBilibiliLink(req.getBilibiliLink());
            sharing.setYoutubeLink(req.getYoutubeLink());
            sharing.setTag(req.getTag());

            this.sharingRepository.save(sharing);
/*            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String formatDate = format.format(req.getDate());
            applicationContext.publishEvent(new EventNotify(Member.class, BotEnum.TELEGRAM,
                ChannelEnum.SHARING,
                NotifyMessageFactory.sharingMessage("‼️‼️Edit Share Info‼️‼️", member.getNickName(), req.getTheme(),
                    formatDate)));*/
        } else {
            throw new BizException(CodeEnums.SHARING_NOT_OWNER_OR_ADMIN);
        }
    }


    public void deleteSharing(long shareId, String address) {
        //Verify
        Optional<Share> existed = this.sharingRepository.findById(shareId);
        if (!existed.isPresent()) {
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
        if (!sharingEntity.isPresent()) {
            throw new BizException(CodeEnums.SHARING_NOT_FOUND);
        }

        return sharingEntity.get();
    }


    public PagedList loadSharingByUser(String memberAddress, int pageNo, int pageSize) {
        int offset = (pageNo - 1) * pageSize;
        int totalCount = this.sharingRepository.loadCountByUid(memberAddress);
        int totalPages = (totalCount + pageSize - 1) / pageSize;
        List<Share> items = this.sharingRepository.findAllSharesByUidPaged(memberAddress, offset,
            pageSize);

        //  SharingByUserResp resp = new SharingByUserResp();
        PagedList resp = new PagedList(items,
            new Pagination(totalCount, totalPages, pageNo, items.size(), pageNo < totalPages));

        return resp;
    }

    public Page<Share> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
            Sort.by("date").descending().and(Sort.by("time").descending())
                .and(Sort.by("id").descending()));
        return this.sharingRepository.findAllByPage(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void approveSharing(Long shareId, String address, Integer status) {
        Share share = this.sharingRepository.findById(shareId)
            .orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND));
        if (teamService.checkMemberIsAdmin(address)) {
            share.setStatus(status);
            Member member = memberRepository.findByAddress(share.getMemberAddress())
                .orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_MEMBER));
            sendMailBySharingStatus(share, member);
            sharingRepository.save(share);

        } else {
            throw new BizException(CodeEnums.NOT_THE_ADMIN);
        }
    }

    private void sendMailBySharingStatus(Share share, Member member) {
        if (share.getStatus().equals(SharingStatus.SHARING)) {
            emailService.sendMail(member.getEmail(), "DappLearning Sharing has been Approved",
                "Congratulations🎉！ Your sharing👉👉👉" + share.getTheme()
                    + "👈👈👈has been approved, "
                    + "please check it in the sharing list\n https://dapplearning.org/");
        } else if (share.getStatus().equals(SharingStatus.PENDING_REWARD)) {
            emailService.sendMail(member.getEmail(), "DappLearning Sharing has been Finish",
                "Congratulations🎉! Please claim your reward\n https://dapplearning.org/");
        }
    }

    public Page<Share> searchSharing(ShareSearchVo searchVo, Pageable pageable) {
        Page<Share> page = sharingRepository.findAll(
            (Specification<Share>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new LinkedList<>();
                if (StringUtils.hasText(searchVo.getTheme())) {
                    predicates.add(
                        criteriaBuilder.like(root.get("theme"), "%" + searchVo.getTheme() + "%"));
                }
                if (searchVo.getLanguage() != null) {
                    predicates.add(
                        criteriaBuilder.equal(root.get("language"), searchVo.getLanguage()));
                }
                if (searchVo.getPresenter() != null) {
                    predicates.add(
                        criteriaBuilder.like(root.get("presenter"),
                            "%" + searchVo.getPresenter() + "%"));
                }
                if (searchVo.getLabel() != null) {
                    predicates.add(
                        criteriaBuilder.like(root.get("label"), "%" + searchVo.getLabel() + "%"));
                }
                if (searchVo.getDate() != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("date"), searchVo.getDate()));
                }
                if (searchVo.getTag() != null) {
                    predicates.add(
                            criteriaBuilder.like(root.get("tag"),
                                    "%" + searchVo.getTag() + "%"));
                }
                query.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable);
        return page;
    }

    public List<RankDto> rank(Integer rankNumber) {
        List<Object[]> resultList = sharingRepository.findTopGroups(rankNumber);
        List<RankDto> rankDtoList = new ArrayList<>();
        for (Object[] row : resultList) {
            RankDto rankDto = new RankDto();
            rankDto.setPresenter((String) row[0]);
            rankDto.setShareCount((BigInteger) row[1]);
            rankDtoList.add(rankDto);
        }
        return rankDtoList;
    }

    public ShareTagResp queryShareTag() {
        ShareTagResp shareTagResp = new ShareTagResp();
        Optional<ServerConfig> oneByConfigName = serverConfigRepository.findOneByConfigName(ConfigEnum.SHARE_TAG_KEY.getConfigName());
        if (oneByConfigName.isPresent()) {
            shareTagResp.setTagList(Lists.newArrayList(oneByConfigName.get().getConfigValue().split(",")));
        }
        return shareTagResp;
    }


}
