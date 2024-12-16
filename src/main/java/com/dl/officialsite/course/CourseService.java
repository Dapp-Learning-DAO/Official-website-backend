package com.dl.officialsite.course;

import cn.hutool.core.collection.CollUtil;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.course.constant.CourseStatusEnums;
import com.dl.officialsite.course.vo.CourseAddVo;
import com.dl.officialsite.course.vo.CourseQueryVo;
import com.dl.officialsite.course.vo.CourseSearchVo;
import com.dl.officialsite.course.vo.CourseUpdateVo;
import com.dl.officialsite.sharing.Share;
import com.dl.officialsite.sharing.SharingRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_COURSE;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:32 PM
 **/
@Service
@Slf4j
public class CourseService {

    @Resource
    private CourseRepository courseRepository;
    @Resource
    private SharingRepository sharingRepository;
    public Course add(CourseAddVo courseAddVo, String address) {
        Course course = new Course();
        BeanUtils.copyProperties(courseAddVo, course);
        course.setCreator(address);
        course.setUpdater(address);
        course.setStatus(CourseStatusEnums.ING.getCode());
        courseRepository.save(course);
        Long courseId = course.getId();

        List<Long> shareIdList = courseAddVo.getShareIdList();
        shareIdList.forEach(shareId -> {
            Share share = sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
            share.setCourseId(courseId);
            sharingRepository.save(share);
        });
        return course;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(CourseUpdateVo courseUpdateVo, String address) {
        Long courseId = courseUpdateVo.getId();
        Course course = courseRepository.findById(courseUpdateVo.getId()).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        BeanUtils.copyProperties(courseUpdateVo, course);
        course.setUpdater(address);
        courseRepository.save(course);

        List<Long> shareIdList = courseUpdateVo.getShareIdList();
        //先将分享的课程id设置为null
        List<Share> oldShareList = sharingRepository.findByCourseId(courseId);
        List<Long> oldIdList = oldShareList.stream().map(Share::getId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(oldShareList)){
            Collection<Long> subtract = CollUtil.subtract(oldIdList, shareIdList);
            if(CollUtil.isNotEmpty(subtract)){
                sharingRepository.updateCourseIdToNull(Lists.newArrayList(subtract));
            }
        }
        //设置新的分享课程id
        shareIdList.forEach(shareId -> {
            Share share = sharingRepository.findById(shareId).orElseThrow(() -> new BizException(CodeEnums.SHARING_NOT_FOUND.getCode(), CodeEnums.SHARING_NOT_FOUND.getMsg()));
            share.setCourseId(courseId);
            sharingRepository.save(share);
        });
    }

    public Page<CourseQueryVo> search(CourseSearchVo courseSearchVo, Pageable pageable) {
        Page<Course> all = courseRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(courseSearchVo.getCourseName())) {
                predicates.add(criteriaBuilder.like(root.get("courseName"), "%" + courseSearchVo.getCourseName() + "%"));
            }
            if (StringUtils.isNotBlank(courseSearchVo.getRemark())) {
                predicates.add(criteriaBuilder.like(root.get("remark"), "%" + courseSearchVo.getRemark() + "%"));
            }
            if (StringUtils.isNotBlank(courseSearchVo.getCooperateCommunity())) {
                predicates.add(criteriaBuilder.like(root.get("cooperateCommunity"), "%" + courseSearchVo.getCooperateCommunity() + "%"));
            }
            if (courseSearchVo.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), courseSearchVo.getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return all.map(course -> {
            CourseQueryVo courseQueryVo = new CourseQueryVo();
            BeanUtils.copyProperties(course, courseQueryVo);
            return courseQueryVo;
        });
    }

    public CourseQueryVo detail(Long id,  Pageable pageable) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        CourseQueryVo courseQueryVo = new CourseQueryVo();
        BeanUtils.copyProperties(course, courseQueryVo);
        //通过courseId分页查找share
        Page<Share> all = sharingRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("courseId"), id));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        courseQueryVo.setSharePage(all);
        return courseQueryVo;
    }

    public void delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        courseRepository.delete(course);

        //先将分享的课程id设置为null
        List<Share> oldShareList = sharingRepository.findByCourseId(id);
        List<Long> oldIdList = oldShareList.stream().map(Share::getId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(oldShareList)){
            sharingRepository.updateCourseIdToNull(oldIdList);
        }
    }
}
