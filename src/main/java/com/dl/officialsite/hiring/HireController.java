package com.dl.officialsite.hiring;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public BaseResponse add(@RequestBody HiringVO hiringVO) {
        hireService.add(hiringVO);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询简历详情
     */
    @GetMapping
    public BaseResponse detail(@RequestParam Long id) {
        HiringVO hiringVO = hireService.detail(id);
        return BaseResponse.successWithData(hiringVO);
    }

    /**
     * 查询所有简历
     */
    @GetMapping("/all")
    public BaseResponse all() {
        List<HiringVO> hiring = hireService.all();
        return BaseResponse.successWithData(hiring);
    }

    /**
     * 按照类型查看简历
     */
    @GetMapping("/type")
    public BaseResponse all(@RequestParam List<String> type) {
        List<HiringVO> hiringVOList = hireService.selectByType(type);
        return BaseResponse.successWithData(hiringVOList);
    }

}
