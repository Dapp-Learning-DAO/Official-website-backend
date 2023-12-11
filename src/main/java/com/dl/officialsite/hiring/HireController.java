package com.dl.officialsite.hiring;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HireController
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireController
 **/
@RestController
@RequestMapping("/hire")
public class HireController {

    @Autowired
    private HireService hireService;

    /**
     * 添加简历
     */
    @PostMapping
    public BaseResponse add(@RequestParam String address,@RequestBody HiringVO hiringVO) {
        HiringVO hiringVO1 = hireService.add(hiringVO);
        return BaseResponse.successWithData(hiringVO1);
    }

    /**
     * 修改简历
     */
    @PutMapping
    public BaseResponse update(@RequestParam String address,@RequestBody HiringVO hiringVO) {
        hireService.update(hiringVO);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询简历详情
     */
    @GetMapping
    public BaseResponse detail(@RequestParam String address,@RequestParam Long id) {
        HiringVO hiringVO = hireService.detail(id);
        return BaseResponse.successWithData(hiringVO);
    }

    /**
     * 查询所有简历
     */
    @GetMapping("/all")
    public BaseResponse all(@RequestParam String address,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<HiringVO> all = hireService.all(pageable);
        return BaseResponse.successWithData(all);
    }

    /**
     * 按照类型查看简历
     */
    @GetMapping("/type")
    public BaseResponse all(@RequestParam String address,@RequestParam List<String> skills) {
        List<HiringVO> hiringVOList = hireService.selectBySkills(skills);
        return BaseResponse.successWithData(hiringVOList);
    }

    /**
     * 按照创建者查看简历
     */
    @GetMapping("/address")
    public BaseResponse allByAddress(@RequestParam String address,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<HiringVO> hiringVOList = hireService.selectByAddress(address,pageable);
        return BaseResponse.successWithData(hiringVOList);
    }

    /**
     *
     */
    @GetMapping("/sponsors")
    public BaseResponse sponsor() {
        List<SponsorVo> sponsorVos = new ArrayList<>();
        SponsorVo sponsorVo = new SponsorVo();
        sponsorVo.setCompany("Optimism");
        sponsorVo.setLink("https://www.optimism.io/");
        sponsorVo.setIcon("https://assets-global.website-files.com/611dbb3c82ba72fbc285d4e2/611fd32ef63b79b5f8568d58_OPTIMISM-logo.svg");
        for (int i = 0; i < 4; i++) {
            sponsorVos.add(sponsorVo);
        }
        return BaseResponse.successWithData(sponsorVos);
    }

    /**
     * 投递职位
     */
    @PostMapping("/apply")
    public BaseResponse apply(@ModelAttribute ApplyVo applyVo) {
        hireService.apply(applyVo.getHireId(), applyVo.getFile());
        return BaseResponse.successWithData(null);
    }
}
