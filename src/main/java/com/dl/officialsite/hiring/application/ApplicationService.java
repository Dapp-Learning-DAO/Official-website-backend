package com.dl.officialsite.hiring.application;

import static com.dl.officialsite.common.enums.CodeEnums.APPLY_REPEAT;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_MEMBER;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.HireService;
import com.dl.officialsite.hiring.Hiring;
import com.dl.officialsite.hiring.vo.ApplySearchVo;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @ClassName ApplicationService
 * @Author jackchen
 * @Date 2023/12/17 16:50
 * @Description 投资service
 **/
@Service
public class ApplicationService {

    private final HireService hireService;

    private final MemberRepository memberRepository;

    private final EmailService emailService;

    private final ApplicationRepository applicationRepository;

    public ApplicationService(HireService hireService, MemberRepository memberRepository,
        EmailService emailService, ApplicationRepository applicationRepository) {
        this.hireService = hireService;
        this.memberRepository = memberRepository;
        this.emailService = emailService;
        this.applicationRepository = applicationRepository;
    }

    public void apply(ApplyVo applyVo, String address) {
        Hiring hiring = hireService.findById(applyVo.getHireId())
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        Member member = memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
            NOT_FOUND_MEMBER.getCode(), NOT_FOUND_MEMBER.getMsg()));

        Member createJDMember =
            memberRepository.findByAddress(hiring.getAddress()).orElseThrow(() -> new BizException(
                NOT_FOUND_MEMBER.getCode(), NOT_FOUND_MEMBER.getMsg()));
        applicationRepository.findByMemberIdAndHiringId(member.getId(), hiring.getId())
            .ifPresent(application -> {
                throw new BizException(APPLY_REPEAT.getCode(), APPLY_REPEAT.getMsg());
            });
        try {
            emailService.sendMail(member.getEmail(), "有新人投递简历", "有新人投递简历:\n简历地址：\n "
                + "https://dlh-1257682033.cos.ap-hongkong.myqcloud.com/" + applyVo.getFile());
            //添加应聘记录
            Application application = new Application();
            application.setHiringId(hiring.getId());
            application.setMemberId(member.getId());
            application.setCreatorName(createJDMember.getNickName());
            application.setMemberName(member.getNickName());
            application.setStatus(Constants.APPLYING);
            applicationRepository.save(application);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查看应聘者应聘了那些岗位
     *
     * @param memberId memberId
     */
    public Page<HiringVO> applyList(Long memberId, Pageable pageable) {
        List<Application> applicationList = applicationRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("memberId"), memberId));

                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                    .getRestriction();
            });
        List<Long> hiringIds = applicationList.stream().map(Application::getHiringId).collect(
            Collectors.toList());
        return hireService.all(pageable, hiringIds);
    }

    public Application findByMemberIdAndHireId(Long memberId, Long hireId) {
        return applicationRepository.findByMemberIdAndHiringId(memberId, hireId).orElse(null);
    }

    public Page<Application> applySearch(ApplySearchVo applySearchVo, Pageable pageable) {
        Specification<Application> specification =
            this.hasDescriptionAndNickName(applySearchVo);
        return applicationRepository.findAll(specification, pageable);
    }

    public static Specification<Application> hasDescriptionAndNickName(
        ApplySearchVo applySearchVo) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new LinkedList<>();
            // Adding conditions for the query
            if (!ObjectUtils.isEmpty(applySearchVo.getApplyName())) {
                predicates.add(
                    builder.like(root.get("applyName"), "%" + applySearchVo.getApplyName() + "%"));
            }
            if (!ObjectUtils.isEmpty(applySearchVo.getCreatorName())) {
                predicates.add(builder.like(root.get("creatorName"),
                    "%" + applySearchVo.getCreatorName() + "%"));
            }
            if (!ObjectUtils.isEmpty(applySearchVo.getMemberName())) {
                predicates.add(builder.like(root.get("memberName"),
                    "%" + applySearchVo.getMemberName() + "%"));
            }
            if (!ObjectUtils.isEmpty(applySearchVo.getApplyTime())) {
                predicates.add(builder.greaterThan(root.get("createTime"),
                    "%" + applySearchVo.getApplyTime() + "%"));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    public ApplicationHiringDetailVo getInfo(Long hireId) {
        ApplicationHiringDetailVo info = new ApplicationHiringDetailVo();
        long count = applicationRepository.count(
            (root, query, builder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(builder.equal(root.get("hiringId"), hireId));
                return query.where(predicates.toArray(new Predicate[0])).getRestriction();
            });
        info.setApplyPersonNum(count);

        List<ApplyPersonInfo> applyPersonInfoList  = new ArrayList<>();
        List<Long> memberIds = applicationRepository.findAll(
            (root, query, builder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(builder.equal(root.get("hiringId"), hireId));
                return query.where(predicates.toArray(new Predicate[0])).getRestriction();
            }).stream().map(row ->{
            ApplyPersonInfo applyPersonInfo = new ApplyPersonInfo(row.getMemberId,getMemberId.getCreateTime);
            applyPersonInfoList.add(applyPersonInfo);
            return Application::getMemberId
        }).collect(Collectors.toList());
        List<Member> member = memberRepository.findByIdIn(memberIds);
        info.setApplyPersonAddress(member);
        info.setApplyPersonInfoList(applyPersonInfoList);
        return info;
    }
}
