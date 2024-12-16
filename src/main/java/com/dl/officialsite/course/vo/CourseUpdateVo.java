package com.dl.officialsite.course.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:44 PM
 **/
@Data
public class CourseUpdateVo {


//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //课程名称
    private String courseName;
    //课程简介
    private String remark;
    //合作社区
    private String cooperateCommunity;
//    //课程状态
//    private Integer status;

    /**
     * 分享id列表
     */
    private List<Long> shareIdList;
}
