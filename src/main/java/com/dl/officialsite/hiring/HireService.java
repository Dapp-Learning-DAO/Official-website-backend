package com.dl.officialsite.hiring;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;
import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_MEMBER;

import com.dl.officialsite.admin.vo.HireSearchParams;
import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.file.cos.FileService;
import com.dl.officialsite.hiring.vo.HiringSkillVO;
import com.dl.officialsite.hiring.vo.HiringVO;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.member.MemberWithTeam;
import com.dl.officialsite.team.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private MemberService memberService;

    /**
     * 新增岗位
     */
    public HiringVO add(HiringVO hiringVO, String address) {

        //check in hiring team or in sharing team

        MemberWithTeam memberWithTeam = memberService.getMemberWithTeamInfoByAddress(address);
        ArrayList<Team> teams = memberWithTeam.getTeams();
        List teamNames = teams.stream().map(Team::getTeamName).collect(Collectors.toList());
        if (!teamNames.contains("Dapp-Learning DAO co-founders") && !teamNames.contains(
            "Dapp-Learning DAO sharing group") && !teamNames.contains("Hiring Team")) {
            throw new BizException("1001", "no permission");
        }
        //do nothing

        Hiring hiring = new Hiring();
        BeanUtils.copyProperties(hiringVO, hiring);
        hireRepository.save(hiring);

        List<HiringSkill> hiringSkillList = new ArrayList<>();
        hiringVO.getMainSkills().forEach(
            skill -> createHiringSkill(Constants.HIRING_MAIN_SKILL, skill, hiring,
                hiringSkillList));
        hiringVO.getOtherSkills().forEach(
            skill -> createHiringSkill(Constants.HIRING_OTHER_SKILL, skill, hiring,
                hiringSkillList));

        hiringSkillRepository.saveAll(hiringSkillList);
        hiringVO.setId(hiring.getId());

        // formate hiringVO and send telegram and discord message
        return hiringVO;
    }

    private void createHiringSkill(int skillType, HiringSkillVO skillVO, Hiring hiring,
        List<HiringSkill> hiringSkillList) {
        HiringSkill hiringSkill = new HiringSkill();
        BeanUtils.copyProperties(skillVO, hiringSkill);
        hiringSkill.setType(skillType);
        hiringSkill.setHiringId(hiring.getId());
        hiringSkillList.add(hiringSkill);
    }

    /**
     * 查询所有岗位
     */
    public Page<HiringVO> all(Pageable pageable) {
        Page<Hiring> hiringPage = hireRepository.findAll(pageable);
        List<Long> hiringIds = hiringPage.getContent().stream()
            .map(Hiring::getId)
            .collect(Collectors.toList());

        Map<Long, List<HiringSkillVO>> skillsMap = fetchSkillsMapByHiringIds(hiringIds);

        List<HiringVO> hiringVOList = hiringPage.getContent().stream()
            .map(hiring -> mapHiringToHiringVO(hiring, skillsMap))
            .collect(Collectors.toList());

        return new PageImpl<>(hiringVOList, pageable, hiringPage.getTotalElements());
    }

    /**
     * 根据岗位ID,查询岗位详情
     */
    public Page<HiringVO> all(Pageable pageable, List<Long> hiringIds) {
        Map<Long, List<HiringSkillVO>> skillsMap = fetchSkillsMapByHiringIds(hiringIds);

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
            .map(hiring -> mapHiringToHiringVO(hiring, skillsMap))
            .collect(Collectors.toList());

        return new PageImpl<>(hiringVOList, pageable, hiringPage.getTotalElements());
    }

    /**
     * 根据岗位ID集合查询岗位标签
     */
    private Map<Long, List<HiringSkillVO>> fetchSkillsMapByHiringIds(List<Long> hiringIds) {
        List<HiringSkillVO> allSkills = hiringSkillRepository.findByHiringId(hiringIds).stream()
            .map(hiringSkill -> {
                HiringSkillVO hiringSkillVO = new HiringSkillVO();
                BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                return hiringSkillVO;
            })
            .collect(Collectors.toList());

        return allSkills.stream()
            .collect(Collectors.groupingBy(HiringSkillVO::getHiringId));
    }

    /**
     * 将Hiring转换为HiringVO
     */
    private HiringVO mapHiringToHiringVO(Hiring hiring, Map<Long, List<HiringSkillVO>> skillsMap) {
        HiringVO hiringVO = new HiringVO();
        BeanUtils.copyProperties(hiring, hiringVO);

        List<HiringSkillVO> mainSkillList = skillsMap.getOrDefault(hiring.getId(),
                Collections.emptyList())
            .stream()
            .filter(skill -> skill.getType() == Constants.HIRING_MAIN_SKILL)
            .collect(Collectors.toList());

        List<HiringSkillVO> otherSkillList = skillsMap.getOrDefault(hiring.getId(),
                Collections.emptyList())
            .stream()
            .filter(skill -> skill.getType() == Constants.HIRING_OTHER_SKILL)
            .collect(Collectors.toList());

        hiringVO.setMainSkills(mainSkillList);
        hiringVO.setOtherSkills(otherSkillList);
        return hiringVO;
    }


    public HiringVO detail(Long id) {
        Hiring hiring = hireRepository.findById(id)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        Map<Long, List<HiringSkillVO>> skillMap = this.fetchSkillsMapByHiringIds(
            Collections.singletonList(id));
        HiringVO hiringVO = this.mapHiringToHiringVO(hiring, skillMap);
        return hiringVO;
    }

    /**
     * 根据技能筛选岗位
     */
    public List<HiringVO> selectBySkills(List<String> skills) {
        List<HiringSkill> hiringSkills = hiringSkillRepository.findBySkill(skills);
        Map<Long, List<HiringSkillVO>> skillsMap = hiringSkills.stream()
            .map(hiringSkill -> {
                HiringSkillVO hiringSkillVO = new HiringSkillVO();
                BeanUtils.copyProperties(hiringSkill, hiringSkillVO);
                return hiringSkillVO;
            })
            .collect(Collectors.groupingBy(HiringSkillVO::getHiringId));

        List<Long> hiringIds = hiringSkills.stream().map(HiringSkill::getHiringId).distinct()
            .collect(Collectors.toList());

        return hireRepository.findAllById(hiringIds).stream()
            .map(hiring -> this.mapHiringToHiringVO(hiring, skillsMap))
            .collect(Collectors.toList());
    }

    /**
     * 修改简历
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
        Specification<Hiring> spec = (root, query, criteriaBuilder) -> criteriaBuilder.equal(
            root.get("address"), address);
        Page<Hiring> page = hireRepository.findAll(spec, pageable);

        List<Long> hiringIds = page.getContent().stream().map(Hiring::getId)
            .collect(Collectors.toList());
        Map<Long, List<HiringSkillVO>> skillsMap = this.fetchSkillsMapByHiringIds(hiringIds);

        List<HiringVO> hiringVOList = page.getContent().stream()
            .map(hiring -> mapHiringToHiringVO(hiring, skillsMap))
            .collect(Collectors.toList());

        return new PageImpl<>(hiringVOList, pageable, page.getTotalElements());
    }


    //application
    public void apply(Long hireId, String fileKey) {
        Hiring hiring = hireRepository.findById(hireId)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        String address = hiring.getAddress();
        Member member = memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
            NOT_FOUND_MEMBER.getCode(), NOT_FOUND_MEMBER.getMsg()));
        try {
            emailService.sendMail(member.getEmail(), "有新人投递简历", "有新人投递简历:\n简历地址：\n "
                + "https://dlh-1257682033.cos.ap-hongkong.myqcloud.com/" + fileKey);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<Hiring> findById(Long hireId) {
        return hireRepository.findById(hireId);
    }

    public Page<HiringVO> getAllHire(HireSearchParams hireSearchParams, Pageable pageable) {
        Page<Hiring> hiringPage = hireRepository.findAll(((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(hireSearchParams.getCreator())) {
                predicates.add(
                    criteriaBuilder.equal(root.get("address"), hireSearchParams.getCreator()));
            }
            if (StringUtils.hasText(hireSearchParams.getStatus())) {
                predicates.add(
                    criteriaBuilder.equal(root.get("status"), hireSearchParams.getStatus()));
            }
            if (StringUtils.hasText(hireSearchParams.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("position"),
                    "%" + hireSearchParams.getTitle() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }), pageable);
        return hiringPage.map(hiring -> {
            Map<Long, List<HiringSkillVO>> skillsMap = fetchSkillsMapByHiringIds(
                Collections.singletonList(hiring.getId()));
            return mapHiringToHiringVO(hiring, skillsMap);
        });
    };

    public void deleteHire(Long hireId) {
        Hiring hiring = hireRepository.findById(hireId)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        hiring.setStatus(1);
        hireRepository.save(hiring);
    }
}
