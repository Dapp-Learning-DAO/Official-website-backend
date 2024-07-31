package com.dl.officialsite.hiring;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
public class HireController {

    @Autowired
    private HireService hireService;

    /**
     * 添加招聘
     */
    @PostMapping
    //@Auth("admin")
    public BaseResponse add(@RequestParam String address,@RequestBody HiringVO hiringVO) {
        HiringVO hiringVO1 = hireService.add(hiringVO, address);
        return BaseResponse.successWithData(hiringVO1);
    }

    /**
     * 修改招聘
     */
    @PutMapping
    public BaseResponse update(@RequestParam String address,@RequestBody HiringVO hiringVO) {
        hireService.update(hiringVO);
        return BaseResponse.successWithData(null);
    }

    /**
     * 删除招聘
     */

    /**
     * 查询招聘详情
     */
    @GetMapping
    public BaseResponse detail(@RequestParam Long id) {
        HiringVO hiringVO = hireService.detail(id);
        return BaseResponse.successWithData(hiringVO);
    }

    /**
     * 查询所有招聘
     */
    @GetMapping("/all")
    public BaseResponse all(
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<HiringVO> all = hireService.all(pageable);
        return BaseResponse.successWithData(all);
    }

    /**
     * 按照类型查看招聘
     */
    @GetMapping("/type")
    public BaseResponse all(@RequestParam String address,@RequestParam List<String> skills) {
        List<HiringVO> hiringVOList = hireService.selectBySkills(skills);
        return BaseResponse.successWithData(hiringVOList);
    }

    /**
     * 按照创建者查看招聘
     */
    @GetMapping("/address")
    public BaseResponse allByAddress(@RequestParam String address,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<HiringVO> hiringVOList = hireService.selectByAddress(address,pageable);
        return BaseResponse.successWithData(hiringVOList);
    }

}
