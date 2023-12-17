package com.dl.officialsite.hiring.application;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/hiring/application")
@Slf4j
public class ApplicationController {

     private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 创建申请
     */
    @PostMapping("/apply")
    public BaseResponse apply(@RequestBody ApplyVo applyVo, @RequestParam String address) {
        log.info("该地址{},正在投递岗位ID{},简历地址{}", address, applyVo.getHireId(), applyVo.getFile());
        applicationService.apply(applyVo, address);
        return BaseResponse.successWithData(null);
    }


    /**
     * 查询自己的投递的岗位状态
     */
    @GetMapping("/list")
    public BaseResponse applyList(@RequestParam Long memberId,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<HiringVO> page = applicationService.applyList(memberId, pageable);
        return BaseResponse.successWithData(page);
    }



}
