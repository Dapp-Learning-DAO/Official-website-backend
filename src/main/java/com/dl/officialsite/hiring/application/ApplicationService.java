package com.dl.officialsite.hiring.application;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_MEMBER;

import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.HireService;
import com.dl.officialsite.hiring.Hiring;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        try {
            emailService.sendMail(member.getEmail(), "有新人投递简历", "有新人投递简历:\n简历地址：\n "+ "https://dlh-1257682033.cos.ap-hongkong.myqcloud.com/"+ applyVo.getFile() );
            //添加应聘记录
            Application application = new Application();
            application.setHiringId(hiring.getId());
            application.setMemeberId(member.getId());
            applicationRepository.save(application);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查看应聘者应聘了那些岗位
     * @param memberId  memberId
     * @return
     */
    public Page<HiringVO> applyList(Long memberId, Pageable pageable) {
        List<Application> applicationList = applicationRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("memeberId"), memberId));

                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                    .getRestriction();
            });
        List<Long> hiringIds = applicationList.stream().map(Application::getHiringId).collect(
            Collectors.toList());
        return hireService.all(pageable, hiringIds);
    }
}
