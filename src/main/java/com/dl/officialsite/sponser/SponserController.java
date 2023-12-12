package com.dl.officialsite.sponser;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.HireService;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HireController
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireController
 **/
@RestController
@RequestMapping("/sponsors")
public class SponserController {

    @Autowired
    private SponserRepository sponserRepository;

    @PostMapping
    public BaseResponse add(@RequestParam String address, @RequestBody Sponsor sponsor) {
        //to check
        Sponsor sponsorNew = sponserRepository.save(sponsor);
        return BaseResponse.successWithData(sponsorNew);
    }
    @GetMapping("/all")
    public BaseResponse all(@RequestParam String address) {
        List<Sponsor> sponsors = sponserRepository.findAll();
        return BaseResponse.successWithData(sponsors);
    }
}
