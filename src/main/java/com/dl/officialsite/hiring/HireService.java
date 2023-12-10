package com.dl.officialsite.hiring;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_JD;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.vo.HiringSkillVO;
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

    public HiringVO add(HiringVO hiringVO) {
        Hiring hiring = new Hiring();
        BeanUtils.copyProperties(hiringVO, hiring);
        hireRepository.save(hiring);
        hiringVO.getMainSkills().forEach(mainSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(mainSkill, hiringSkill);
            hiringSkill.setType(Constants.HIRING_MAIN_SKILL);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillRepository.save(hiringSkill);
        });

        hiringVO.getOtherSkills().forEach(otherSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(otherSkill, hiringSkill);
            hiringSkill.setType(Constants.HIRING_OTHER_SKILL);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillRepository.save(hiringSkill);
        });
        hiringVO.setId(hiring.getId());
        return hiringVO;
    }

    public Page<HiringVO> all(Pageable pageable) {
        List<HiringVO> hiringVOList = new ArrayList<>();;
        Page<Hiring> hiringPage = hireRepository.findAll(pageable);
        hiringPage.getContent().forEach(hiring -> {
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
        //添加新的技能
        hiringVO.getMainSkills().forEach(mainSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(mainSkill, hiringSkill);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillRepository.save(hiringSkill);
        });

        hiringVO.getOtherSkills().forEach(otherSkill -> {
            HiringSkill hiringSkill = new HiringSkill();
            BeanUtils.copyProperties(otherSkill, hiringSkill);
            hiringSkill.setHiringId(hiring.getId());
            hiringSkillRepository.save(hiringSkill);
        });
    }

    public List<HiringVO> selectByAddress(String address) {
        List<HiringVO> list = new ArrayList<>();
        hireRepository.findAllByAddress(address).forEach(hiring -> {
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
            List<HiringSkill> hiringSkills = hiringSkillRepository.findByHiringId(hiring.getId());
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
            list.add(hiringVO);
        });
        return list;
    }
}
