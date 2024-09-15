package com.dl.officialsite.course;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.course.vo.CourseAddVo;
import com.dl.officialsite.course.vo.CourseQueryVo;
import com.dl.officialsite.course.vo.CourseSearchVo;
import com.dl.officialsite.course.vo.CourseUpdateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:37 PM
 **/
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;


    @PostMapping("/add")
    public BaseResponse add(@RequestBody CourseAddVo courseAddVo, @RequestParam String address) {
        return BaseResponse.successWithData(courseService.add(courseAddVo, address));
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody CourseUpdateVo courseUpdateVo, @RequestParam String address) {
        courseService.update(courseUpdateVo, address);
        return BaseResponse.successWithData(null);
    }

    @PostMapping("/detail")
    public BaseResponse detail(@RequestParam Long id, @RequestParam(defaultValue = "1") Integer pageNumber,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        return BaseResponse.successWithData(courseService.detail(id, pageable));
    }

    @PostMapping("/list")
    public BaseResponse list(
                             @RequestParam(defaultValue = "1") Integer pageNumber,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        CourseSearchVo courseSearchVo = new CourseSearchVo();
        Page<CourseQueryVo> page = courseService.search(courseSearchVo, pageable);
        return BaseResponse.successWithData(page);
    }

    @PostMapping("/delete")
    public BaseResponse delete(@RequestParam Long id, @RequestParam String address) {
        courseService.delete(id);
        return BaseResponse.successWithData(null);
    }

}
