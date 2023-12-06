package com.dl.officialsite.hiring;

import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.ArrayList;
import java.util.List;
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

    public void add(Hiring hiring) {
        hireRepository.save(hiring);
    }

    public List<HiringVO> all() {
        List<HiringVO> hiringVOList = new ArrayList<>();
        List<Hiring> all = hireRepository.findAll();
        all.forEach(hiring -> {
            HiringVO hiringVO = new HiringVO();
            BeanUtils.copyProperties(hiring, hiringVO);
        });
        return hiringVOList;
    }
}
