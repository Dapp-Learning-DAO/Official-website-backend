package com.dl.officialsite.course.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author xiaoming
 * @Date 2024/9/12 7:40 PM
 **/
@Getter
@AllArgsConstructor
public enum CourseStatusEnums {
    /**
     * 状态,0:进行中,1:已结束
     */
    ING(0, "进行中"),
    END(1, "已结束");


    private int code;

    private String message;
}
