package com.dl.officialsite.hiring;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.vo.HiringVO;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Hiring hiring = new Hiring();
        BeanUtils.copyProperties(hiringVO, hiring);
        hireService.add(hiring);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询所有简历
     */
    @GetMapping
    public BaseResponse all() {
        List<HiringVO> list = hireService.all();
        return BaseResponse.successWithData(list);
    }

}
