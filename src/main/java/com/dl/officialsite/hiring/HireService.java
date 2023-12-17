package com.dl.officialsite.hiring;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_MEMBER;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.file.cos.FileService;
import com.dl.officialsite.hiring.vo.HiringSkillVO;
import com.dl.officialsite.hiring.vo.HiringVO;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @ClassName HireService
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireService
 **/
@Service
@Slf4j
public class HireService {

    @Autowired
    private HireRepository hireRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private HiringSkillRepository hiringSkillRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    public HiringVO add(HiringVO hiringVO) {
        Hiring hiring = new Hiring();
        BeanUtils.copyProperties(hiringVO, hiring);
        hireRepository.save(hiring);

        ArrayList<HiringSkill> hiringSkillList = new ArrayList<>();
        hiringVO.getMainSkills().forEach(mainSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(mainSkill, hiringSkill);
            hiringSkill.setType(Constants.HIRING_MAIN_SKILL);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillList.add(hiringSkill);
        });


        hiringVO.getOtherSkills().forEach(otherSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(otherSkill, hiringSkill);
            hiringSkill.setType(Constants.HIRING_OTHER_SKILL);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillList.add(hiringSkill);
        });

        hiringSkillRepository.saveAll(hiringSkillList);
        hiringVO.setId(hiring.getId());
        return hiringVO;
    }

    public Page<HiringVO> all(Pageable pageable) {
        List<HiringVO> hiringVOList = new ArrayList<>();;
        Page<Hiring> hiringPage = hireRepository.findAll(pageable);

        //find HiringId in []  query one time !
        hiringPage.getContent().forEach(hiring -> {
            List<HiringSkillVO> mainSkills = hiringSkillRepository.findByHiringId(hiring.getId())
                .stream()
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());

            List<HiringSkillVO> otherSkills = hiringSkillRepository.findByHiringId(hiring.getId())
                .stream()
                .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_OTHER_SKILL)
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            hiringVO.setMainSkills(mainSkills);
            hiringVO.setOtherSkills(otherSkills);
            hiringVOList.add(hiringVO);
        });
        Page<HiringVO> hiringVOPage = new PageImpl<>(hiringVOList, pageable, hiringPage.getTotalElements());
        return hiringVOPage;
    }

    public Page<HiringVO> all(Pageable pageable, List<Long> hiringIds) {
        // 查询hiringIDs下的所有技能标签
        List<HiringSkill> allSkills = hiringSkillRepository.findByHiringId(hiringIds);

        Map<Long, List<HiringSkillVO>> skillsMap = allSkills.stream()
            .map(hiringSkill -> {
                HiringSkillVO hiringSkillVO = new HiringSkillVO();
                BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                return hiringSkillVO;
            })
            .collect(Collectors.groupingBy(HiringSkillVO::getHiringId));

        Page<Hiring> hiringPage = hireRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                In<Object> in = criteriaBuilder.in(root.get("id"));
                for (Long hiringId : hiringIds) {
                    in.value(hiringId);
                }
                predicates.add(in);

                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                    .getRestriction();
            }, pageable);

        List<HiringVO> hiringVOList = hiringPage.getContent().stream()
            .map(hiring -> {
                HiringVO hiringVO = new HiringVO();
                BeanUtils.copyProperties(hiring, hiringVO);

                List<HiringSkillVO> mainSkillList = skillsMap.getOrDefault(hiring.getId(), Collections.emptyList())
                    .stream()
                    .filter(skill -> skill.getType() == Constants.HIRING_MAIN_SKILL)
                    .collect(Collectors.toList());

                List<HiringSkillVO> otherSkillList = skillsMap.getOrDefault(hiring.getId(), Collections.emptyList())
                    .stream()
                    .filter(skill -> skill.getType() == Constants.HIRING_OTHER_SKILL)
                    .collect(Collectors.toList());

                hiringVO.setMainSkills(mainSkillList);
                hiringVO.setOtherSkills(otherSkillList);
                return hiringVO;
            })
            .collect(Collectors.toList());

        return new PageImpl<>(hiringVOList, pageable, hiringPage.getTotalElements());
    }


    public HiringVO detail(Long id) {
        HiringVO hiringVO = new HiringVO();
        Hiring hiring = hireRepository.findById(id)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        BeanUtils.copyProperties(hiring, hiringVO);
        List<HiringSkill> hiringSkills = hiringSkillRepository.findByHiringId(id);
        List<HiringSkillVO> mailSkills = hiringSkills.stream()
            .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_MAIN_SKILL)
            .map(hiringSkill -> {
                HiringSkillVO hiringSkillVO = new HiringSkillVO();
                BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                return hiringSkillVO;
            })
            .collect(Collectors.toList());
        hiringVO.setMainSkills(mailSkills);
        List<HiringSkillVO> otherSkills = hiringSkills.stream()
            .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_OTHER_SKILL)
            .map(hiringSkill -> {
                HiringSkillVO hiringSkillVO = new HiringSkillVO();
                BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                return hiringSkillVO;
            })
            .collect(Collectors.toList());
        hiringVO.setOtherSkills(otherSkills);
        return hiringVO;
    }

    public List<HiringVO> selectBySkills(List<String> skills) {
        //使用in查询
        List<HiringSkill> hiringSkills = hiringSkillRepository.findBySkill(skills);
        //去重通过hiringId
        List<Long> hiringIds = hiringSkills.stream().map(HiringSkill::getHiringId).distinct().collect(Collectors.toList());
        List<HiringVO> hiringVOList = new ArrayList<>();
        hireRepository.findAllById(hiringIds).forEach(hiring -> {
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            List<HiringSkillVO> mainSkills = hiringSkills.stream()
                .filter(hiringSkill -> hiringSkill.getHiringId().equals(hiring.getId()))
                .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_MAIN_SKILL)
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());

            List<HiringSkillVO> otherSkills = hiringSkills.stream()
                .filter(hiringSkill -> hiringSkill.getHiringId().equals(hiring.getId()))
                .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_OTHER_SKILL)
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());
            hiringVO.setMainSkills(mainSkills);
            hiringVO.setOtherSkills(otherSkills);
            hiringVOList.add(hiringVO);
        });
        return hiringVOList;
    }

    /**
     * 修改简历
     * @param hiringVO
     */
    public void update(HiringVO hiringVO) {
        Hiring hiring = hireRepository.findById(hiringVO.getId())
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        BeanUtils.copyProperties(hiringVO, hiring);
        hireRepository.save(hiring);
        //删除原有的技能
        hiringSkillRepository.deleteByHiringId(hiring.getId());
        List<HiringSkill> hiringSkills = new ArrayList<>();
        //添加新的技能
        hiringVO.getMainSkills().forEach(mainSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(mainSkill, hiringSkill);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkills.add(hiringSkill);
        });

        hiringVO.getOtherSkills().forEach(otherSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(otherSkill, hiringSkill);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkills.add(hiringSkill);
        });
        hiringSkillRepository.saveAll(hiringSkills);
    }

    public Page<HiringVO> selectByAddress(String address, Pageable pageable) {
        List<HiringVO> list = new ArrayList<>();
        Specification<Hiring> spec = (root, query, criteriaBuilder) -> {
            Path<Hiring> path = root.get("address");
            Predicate equal = criteriaBuilder.equal(path, address);
            return equal;
        };
        Page<Hiring> page = hireRepository.findAll(spec, pageable);

        //findIDS
        page.getContent().forEach(hiring -> {
            List<HiringSkillVO> mainSkills = hiringSkillRepository.findByHiringId(hiring.getId())
                .stream()
                .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_MAIN_SKILL)
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());
            List<HiringSkillVO> otherSkills = hiringSkillRepository.findByHiringId(hiring.getId())
                .stream()
                .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_OTHER_SKILL)
                .map(hiringSkill -> {
                    HiringSkillVO hiringSkillVO = new HiringSkillVO();
                    BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                    return hiringSkillVO;
                })
                .collect(Collectors.toList());
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            hiringVO.setMainSkills(mainSkills);
            hiringVO.setOtherSkills(otherSkills);
            list.add(hiringVO);
        });
        Page<HiringVO> hiringVOPage = new PageImpl<>(list, pageable, page.getTotalElements());
        return hiringVOPage;
    }


    //application
    public void apply(Long hireId, String fileKey) {
        Hiring hiring = hireRepository.findById(hireId)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        String address = hiring.getAddress();
        Member member = memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
            NOT_FOUND_MEMBER.getCode(), NOT_FOUND_MEMBER.getMsg()));
        try {
            emailService.sendMail(member.getEmail(), "有新人投递简历", "有新人投递简历:\n简历地址：\n "+ "https://dlh-1257682033.cos.ap-hongkong.myqcloud.com/"+ fileKey );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<Hiring> findById(Long hireId) {
        return hireRepository.findById(hireId);
    }
}
