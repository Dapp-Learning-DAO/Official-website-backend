package com.dl.officialsite.sharing;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.course.Course;
import com.dl.officialsite.sharing.model.req.CourseShareAddReq;
import com.dl.officialsite.sharing.model.req.CourseShareDeleteReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_COURSE;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/13 8:02 AM
 **/
@Service
@Slf4j
public class ShareCourseService {

    @Resource
    private SharingRepository sharingRepository;

    public void addShareCourse(CourseShareAddReq courseShareAddReq){
        Long shareId = courseShareAddReq.getShareId();
        Long courseId = courseShareAddReq.getCourseId();
        Share share = sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
        share.setCourseId(courseId);
        sharingRepository.save(share);
    }

    public void deleteShareCourse(CourseShareDeleteReq courseShareDeleteReq){
        Long shareId = courseShareDeleteReq.getShareId();
//        sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
        sharingRepository.updateCourseIdToNull(shareId);
    }
}
