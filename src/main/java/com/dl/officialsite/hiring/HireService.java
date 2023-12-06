package com.dl.officialsite.hiring;

import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        hiringVO.getSkill().forEach(skill -> {
            HiringSkill hiringSkill = new HiringSkill();
            hiringSkill.setHiringId(hiring.getId());
            hiringSkill.setSkill(skill);
            hiringSkillRepository.save(hiringSkill);
        });
    }

    public List<HiringVO> all() {
        List<HiringVO> hiringVOList = new ArrayList<>();;
        List<Hiring> all = hireRepository.findAll();
        all.forEach(hiring -> {
            List<String> skills = hiringSkillRepository.findByHiringId(hiring.getId()).stream()
                .map(HiringSkill::getSkill)
                .collect(Collectors.toList());
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            hiringVO.setSkill(skills);
            hiringVOList.add(hiringVO);
        });
        return hiringVOList;
    }

    public HiringVO detail(Long id) {
        HiringVO hiringVO = new HiringVO();
        Hiring hiring = hireRepository.findById(id).get();
        BeanUtils.copyProperties(hiring, hiringVO);
        List<HiringSkill> hiringSkills = hiringSkillRepository.findByHiringId(id);
        List<String> skills = hiringSkills.stream().map(HiringSkill::getSkill)
            .collect(Collectors.toList());
        hiringVO.setSkill(skills);
        return hiringVO;
    }

    public List<HiringVO> selectByType(List<String> type) {
        //todo 后面想想怎么做
        return null;
    }
}
