package com.dl.officialsite.course.vo;

import com.dl.officialsite.sharing.Share;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:50 PM
 **/
@Data
public class CourseQueryVo {

//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //课程名称
    private String courseName;
    //课程简介
    private String remark;
    //合作社区
    private String cooperateCommunity;
    //创建者
    private String creator;
    //更新者
    private String updater;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Long createTime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Long updateTime;
    //状态,0:进行中,1:已结束
    private Integer status;

    private Page<Share> sharePage;
}
