package com.dl.officialsite.sponsor;

import com.dl.officialsite.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HireController
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireController
 **/
@RestController
@RequestMapping("/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;
    @Autowired
    private SponsorRepository sponsorRepository;

    @PostMapping
    public BaseResponse create(@RequestParam String address, @RequestBody Sponsor sponsor) {
       return BaseResponse.successWithData( sponsorService.add(address , sponsor));

    }
    @GetMapping("/all")
    public BaseResponse all(@RequestParam String address) {
        List<Sponsor> sponsors = sponsorRepository.findAll();
        return BaseResponse.successWithData(sponsors);
    }
}
