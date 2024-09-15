package com.dl.officialsite.course;

import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.course.constant.CourseStatusEnums;
import com.dl.officialsite.course.vo.CourseAddVo;
import com.dl.officialsite.course.vo.CourseQueryVo;
import com.dl.officialsite.course.vo.CourseSearchVo;
import com.dl.officialsite.course.vo.CourseUpdateVo;
import com.dl.officialsite.team.Team;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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
    public Course add(CourseAddVo courseAddVo, String address) {
        Course course = new Course();
        BeanUtils.copyProperties(courseAddVo, course);
        course.setCreator(address);
        course.setUpdater(address);
        course.setStatus(CourseStatusEnums.ING.getCode());
        courseRepository.save(course);
        return course;
    }

    public void update(CourseUpdateVo courseUpdateVo, String address) {
        Course course = courseRepository.findById(courseUpdateVo.getId()).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        BeanUtils.copyProperties(courseUpdateVo, course);
        course.setUpdater(address);
        courseRepository.save(course);
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

    public CourseQueryVo detail(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        CourseQueryVo courseQueryVo = new CourseQueryVo();
        BeanUtils.copyProperties(course, courseQueryVo);
        return courseQueryVo;
    }

    public void delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BizException(NOT_FOUND_COURSE.getCode(), NOT_FOUND_COURSE.getMsg()));
        courseRepository.delete(course);
    }
}
