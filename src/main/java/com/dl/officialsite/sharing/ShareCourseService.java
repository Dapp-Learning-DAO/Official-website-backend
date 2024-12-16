package com.dl.officialsite.sharing;

import cn.hutool.core.collection.CollUtil;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.sharing.model.req.CourseShareAddReq;
import com.dl.officialsite.sharing.model.req.CourseShareDeleteReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Long> shareIdList = courseShareAddReq.getShareIdList();
        Long courseId = courseShareAddReq.getCourseId();
        //先将分享的课程id设置为null
        List<Share> oldShareList = sharingRepository.findByCourseId(courseId);
        List<Long> oldIdList = oldShareList.stream().map(Share::getId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(oldShareList)){
            sharingRepository.updateCourseIdToNull(oldIdList);
        }
        shareIdList.forEach(shareId -> {
            Share share = sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
            share.setCourseId(courseId);
            sharingRepository.save(share);
        });
    }

    public void deleteShareCourse(CourseShareDeleteReq courseShareDeleteReq){
        Long shareId = courseShareDeleteReq.getShareId();
        sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
        sharingRepository.updateCourseIdToNull(shareId);
    }
}
