package com.dl.officialsite.hiring;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @ClassName HireService
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireService
 **/
@Service
public class HireService {

    @Autowired
    private HireRepository hireRepository;

    @Autowired
    private HiringSkillRepository hiringSkillRepository;

    public void add(HiringVO hiringVO) {
        Hiring hiring = new Hiring();
        BeanUtils.copyProperties(hiringVO, hiring);
        hireRepository.save(hiring);
        hiringVO.getMainSkills().forEach(mainSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            hiringSkill.setHiringId(hiring.getId());
            hiringSkill.setSkill(mainSkill);
            hiringSkill.setType(Constants.HIRING_MAIN_SKILL);
            hiringSkillRepository.save(hiringSkill);
        });

        hiringVO.getOtherSkills().forEach(otherSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            hiringSkill.setHiringId(hiring.getId());
            hiringSkill.setSkill(otherSkill);
            hiringSkill.setType(Constants.HIRING_OTHER_SKILL);
            hiringSkillRepository.save(hiringSkill);
        });
    }

    public Page<HiringVO> all(Pageable pageable) {
        List<HiringVO> hiringVOList = new ArrayList<>();;
        Page<Hiring> hiringPage = hireRepository.findAll(pageable);
        hiringPage.getContent().forEach(hiring -> {
            List<String> skills = hiringSkillRepository.findByHiringId(hiring.getId()).stream()
                .map(HiringSkill::getSkill)
                .collect(Collectors.toList());
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            hiringVO.setMainSkills(skills);
            hiringVOList.add(hiringVO);
        });
        Page<HiringVO> hiringVOPage = new PageImpl<>(hiringVOList, pageable, hiringPage.getTotalElements());
        return hiringVOPage;
    }

    public HiringVO detail(Long id) {
        HiringVO hiringVO = new HiringVO();
        Hiring hiring = hireRepository.findById(id)
            .orElseThrow(() -> new BizException(NOT_FOUND_JD.getCode(), NOT_FOUND_JD.getMsg()));
        BeanUtils.copyProperties(hiring, hiringVO);
        List<HiringSkill> hiringSkills = hiringSkillRepository.findByHiringId(id);
        List<String> mailSkills = hiringSkills.stream()
            .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_MAIN_SKILL)
            .map(HiringSkill::getSkill)
            .collect(Collectors.toList());
        hiringVO.setMainSkills(mailSkills);
        List<String> otherSkills = hiringSkills.stream()
            .filter(hiringSkill -> hiringSkill.getType() == Constants.HIRING_OTHER_SKILL)
            .map(HiringSkill::getSkill)
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
            List<String> mainSkills = hiringSkills.stream()
                .filter(hiringSkill -> hiringSkill.getHiringId().equals(hiring.getId()))
                .map(HiringSkill::getSkill)
                .collect(Collectors.toList());
            hiringVO.setMainSkills(mainSkills);
            hiringVOList.add(hiringVO);
        });
        return hiringVOList;
    }
}
