package com.dl.officialsite.bounty;

import static com.dl.officialsite.common.constants.Constants.BOUNTY_MEMBER_MAP_STATUS_FINISH;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_BOUNTY;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_MEMBER;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.event.EventNotify;
import com.dl.officialsite.bot.event.NotifyMessageFactory;
import com.dl.officialsite.bounty.vo.ApplyBountyVo;
import com.dl.officialsite.bounty.vo.BountyMemberVo;
import com.dl.officialsite.bounty.vo.BountySearchVo;
import com.dl.officialsite.bounty.vo.BountyVo;
import com.dl.officialsite.bounty.vo.MyBountySearchVo;
import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @ClassName BountyService
 * @Author jackchen
 * @Date 2024/1/26 19:15
 * @Description BountyService
 **/
@Service
@Slf4j
public class BountyService {

    private final BountyRepository bountyRepository;

    private final BountyMemberMapRepository bountyMemberMapRepository;

    private final MemberRepository memberRepository;

    private final ApplicationContext applicationContext;

    private final EmailService emailService;

    public BountyService(BountyRepository bountyRepository,
                         BountyMemberMapRepository bountyMemberMapRepository, MemberRepository memberRepository,
                         ApplicationContext applicationContext, EmailService emailService) {
        this.bountyRepository = bountyRepository;
        this.bountyMemberMapRepository = bountyMemberMapRepository;
        this.memberRepository = memberRepository;
        this.applicationContext = applicationContext;
        this.emailService = emailService;
    }



//    @Scheduled(cron = "${jobs.bounty.corn:0 0 * * * *}")
//    @ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
    public void updateBountyData() {
        log.info("updateBountyData_schedule task begin --------------------- ");
        XxlJobHelper.log("updateBountyData_schedule task begin --------------------- ");
        //update status
        long currentSeconds = System.currentTimeMillis() / 1000;
        List<Bounty> bountyList = bountyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.equal(root.get("status"), BountyStatusEnum.IN_RECRUITMENT.getData()));
                    predicates.add(criteriaBuilder.lessThan(root.get("streamEnd"), currentSeconds));
                    return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                            .getRestriction();
                });
        if(bountyList.isEmpty())return;
         bountyList.stream().forEach(row -> {
             row.setStatus(BountyStatusEnum.TIME_OUT.getData());
             bountyRepository.save(row);
         });
    }


    public BountyVo add(BountyVo bountyVo, String address) {
        Bounty bounty = new Bounty();
        BeanUtils.copyProperties(bountyVo, bounty);
        bounty.setCreator(address);
        bountyRepository.save(bounty);
        bountyVo.setId(bounty.getId());
        Member creatorInfo = memberRepository.findByAddress(bounty.getCreator()).orElse(null);
        bountyVo.setCreator(creatorInfo);
        applicationContext.publishEvent(new EventNotify(Member.class, BotEnum.TELEGRAM,
            ChannelEnum.HIRING, NotifyMessageFactory.bountyMessage(creatorInfo.getNickName(), bounty.getTitle())));
        return bountyVo;
    }

    public void update(BountyVo bountyVo, String address) {
        Bounty bounty = bountyRepository.findById(bountyVo.getId())
            .orElseThrow(
                () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        BeanUtils.copyProperties(bountyVo, bounty);
        bountyRepository.save(bounty);
    }

    public Page<BountyVo> search(BountySearchVo bountySearchVo, Pageable pageable) {
        Specification<Bounty> spec = Specification.where(hasCreator(bountySearchVo.getCreator()))
            .and(hasTitle(bountySearchVo.getTitle()))
            .and(hasStatus(bountySearchVo.getStatus()))
            .and(hasDeadLineBefore(bountySearchVo.getDeadLine()))
            .and(isNotStatus(5));
        if (!ObjectUtils.isEmpty(bountySearchVo.getLinkStream())) {
            if (bountySearchVo.getLinkStream().equals(2)) {
                spec = spec.and(notLinkStream());
            }
        }
        Page<Bounty> bountyPage = bountyRepository.findAll(spec, pageable);
        return bountyPage.map(this::mapToBountyVo);
    }

    private Specification<Bounty> hasCreator(String creator) {
        return (root, query, criteriaBuilder) -> StringUtils.hasText(creator) ?
            criteriaBuilder.equal(root.get("creator"), creator) : null;
    }

    private Specification<Bounty> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> StringUtils.hasText(title) ?
            criteriaBuilder.like(root.get("title"), "%" + title + "%") : null;
    }

    private Specification<Bounty> hasStatus(Integer status) {
        return (root, query, criteriaBuilder) -> status != null ?
            criteriaBuilder.equal(root.get("status"), status) : null;
    }

    private Specification<Bounty> hasDeadLineBefore(LocalDateTime deadline) {
        return (root, query, criteriaBuilder) -> deadline != null ?
            criteriaBuilder.lessThan(root.get("deadLine"), deadline) : null;
    }

    private Specification<Bounty> notLinkStream() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.isNull(root.get("streamId"));
    }

    private Specification<Bounty> isNotStatus(Integer status) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.notEqual(root.get("status"), status);
    }

    private BountyVo mapToBountyVo(Bounty bounty) {
        BountyVo bountyVo = new BountyVo();
        BeanUtils.copyProperties(bounty, bountyVo);
        List<BountyMemberMap> bountyMember = findBountyMemberMapByBountyId(bounty.getId());
        bountyVo.setBountyMemberMaps(bountyMember);
        Member creatorInfo = memberRepository.findByAddress(bounty.getCreator()).orElse(null);
        bountyVo.setCreator(creatorInfo);
        return bountyVo;
    }

    public void delete(Long id, String address) {
        Bounty bounty = bountyRepository.findById(id)
            .orElseThrow(
                () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        bounty.setStatus(Constants.BOUNTY_STATUS_DELETE);
        bountyRepository.save(bounty);
    }

    public BountyVo findByIdInternal(Long id) {
        Bounty bounty = bountyRepository.findById(id)
            .orElseThrow(
                () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        return mapToBountyVo(bounty);
    }

    public void apply(Long bountyId, String address) {
        Bounty bounty = bountyRepository.findById(bountyId)
            .orElseThrow(
                () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        if (bounty.getCreator().equals(address)) {
            throw new BizException("2002", "not apply bounty by creator");
        }
        bountyMemberMapRepository.findByBountyIdAndMemberAddress(bountyId, address)
            .ifPresent(bountyMemberMap -> {
                throw new BizException("2004", "bounty has been applied");

            });
        BountyMemberMap bountyMemberMap = new BountyMemberMap();
        bountyMemberMap.setBountyId(bountyId);
        bountyMemberMap.setMemberAddress(address);
        bountyMemberMap.setStatus(Constants.BOUNTY_MEMBER_MAP_STATUS_APPLY);
        bountyMemberMapRepository.save(bountyMemberMap);
        String creatorAddress = bounty.getCreator();
        Member createBountyMember = memberRepository.findByAddress(creatorAddress)
            .orElseThrow(() -> new BizException(NOT_FOUND_MEMBER.getCode(), NOT_FOUND_MEMBER.getMsg()));

        try {
            emailService.sendMail(createBountyMember.getEmail(), "有新人申请bounty", "赶紧去看看吧 https://dapplearning.org/bounty");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BountyMemberMap> findBountyMemberMapByBountyId(Long bounty) {
        return bountyMemberMapRepository.findAll(
            (Specification<BountyMemberMap>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(criteriaBuilder.equal(root.get("bountyId"), bounty));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });
    }

    public Page<BountyVo> myBounty(MyBountySearchVo myBountySearchVo, Pageable pageable) {
        return bountyMemberMapRepository.findAll(
            (Specification<BountyMemberMap>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(criteriaBuilder.equal(root.get("memberAddress"),
                    myBountySearchVo.getMemberAddress()));
                if (myBountySearchVo.getStatus() != null) {
                    predicates.add(
                        criteriaBuilder.equal(root.get("status"), myBountySearchVo.getStatus()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable).map(bountyMemberMap -> {
            Bounty bounty = bountyRepository.findById(bountyMemberMap.getBountyId())
                .orElseThrow(
                    () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
            BountyVo bountyVo = new BountyVo();
            BeanUtils.copyProperties(bounty, bountyVo);
            Member creatorInfo = memberRepository.findByAddress(bounty.getCreator()).orElse(null);
            bountyVo.setCreator(creatorInfo);
            return bountyVo;
        });
    }

    public void approve(ApplyBountyVo applyBountyVo) {
        bountyMemberMapRepository.findByBountyIdAndMemberAddress(applyBountyVo.getBountyId(),
                applyBountyVo.getAddress())
            .ifPresent(bountyMemberMap -> {
                bountyMemberMap.setStatus(BOUNTY_MEMBER_MAP_STATUS_FINISH);
                bountyMemberMapRepository.save(bountyMemberMap);
            });
    }

    public Page<BountyMemberVo> findBountyMemberMapByBountyId(Long id, Pageable pageable) {
        return bountyMemberMapRepository.findAll(
            (Specification<BountyMemberMap>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(criteriaBuilder.equal(root.get("bountyId"), id));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable).map(bountyMemberMap -> {
            BountyMemberVo bountyMemberVo = new BountyMemberVo();
            BeanUtils.copyProperties(bountyMemberMap, bountyMemberVo);
            memberRepository.findByAddress(bountyMemberMap.getMemberAddress())
                .ifPresent(bountyMemberVo::setMember);
            return bountyMemberVo;
        });
    }

    public Integer isApply(Long bountyId, String address) {
        return bountyMemberMapRepository.findByBountyIdAndMemberAddress(
            bountyId, address).map(BountyMemberMap::getStatus).orElse(null);
    }

    public void link(BountyVo bountyVo, String address) {
        Bounty bounty = bountyRepository.findById(bountyVo.getId()).orElseThrow(
            () -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        //String loginUser = UserSecurityUtils.getUserLogin().getAddress();
        if (!bounty.getCreator().equals(address)) {
            throw new BizException("2003", "not link bounty by creator");
        }
        if (!ObjectUtils.isEmpty(bounty.getStreamStart())) {
            throw new BizException("2005", "bounty not apply");
        }
        bounty.setStreamId(bountyVo.getStreamId());
        bounty.setStreamEnd(bountyVo.getStreamStart());
        bounty.setStreamEnd(bountyVo.getStreamEnd());
        bounty.setStreamChainId(bountyVo.getStreamChainId());
        bountyRepository.save(bounty);
    }
}
