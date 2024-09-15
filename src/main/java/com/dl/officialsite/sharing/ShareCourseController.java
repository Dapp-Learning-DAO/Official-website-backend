package com.dl.officialsite.sharing;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.sharing.model.req.CourseShareAddReq;
import com.dl.officialsite.sharing.model.req.CourseShareDeleteReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/13 7:57 AM
 **/
@RestController
@RequestMapping("/shareCourse")
@Slf4j
public class ShareCourseController {

    @Resource
    private ShareCourseService shareCourseService;

    @PostMapping("/addOrModify")
    public BaseResponse addOrModifyShareCourse(@RequestBody CourseShareAddReq courseShareAddReq, @RequestParam String address) {
        shareCourseService.addShareCourse(courseShareAddReq);
        return BaseResponse.successWithData(null);
    }

    @PostMapping("/delete")
    public BaseResponse deleteShareCourse(@RequestBody CourseShareDeleteReq courseShareDeleteReq, @RequestParam String address) {
        shareCourseService.deleteShareCourse(courseShareDeleteReq);
        return BaseResponse.successWithData(null);
    }
}
