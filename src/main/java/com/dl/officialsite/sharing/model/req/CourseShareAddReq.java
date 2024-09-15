package com.dl.officialsite.sharing.model.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/13 7:59 AM
 **/
@Data
public class CourseShareAddReq {

    /**
     * 课程id
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    /**
     * 分享id列表
     */
    private Long shareId;
}
