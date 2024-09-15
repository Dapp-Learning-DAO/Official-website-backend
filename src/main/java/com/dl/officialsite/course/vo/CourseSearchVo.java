package com.dl.officialsite.course.vo;

import lombok.Data;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:48 PM
 **/
@Data
public class CourseSearchVo {

    //课程名称
    private String courseName;
    //课程简介
    private String remark;
    //合作社区
    private String cooperateCommunity;
    //课程状态
    private Integer status;
}
