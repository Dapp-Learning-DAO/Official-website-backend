package com.dl.officialsite.hiring.application;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.hiring.HireService;
import com.dl.officialsite.hiring.vo.ApplyVo;
import com.dl.officialsite.hiring.vo.HiringVO;
import com.dl.officialsite.login.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HireController
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description HireController
 **/
@RestController
@RequestMapping("/hiring/application")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    //todo
    /**
     * 创建申请
     */


    /**
     * 查询申请  招聘官查询、 应聘者查询
     */




}
