package com.dl.officialsite.common.constants;

/**
 * @ClassName Constants
 * @Author jackchen
 * @Date 2023/10/21 17:57
 * @Description 常量类
 **/
public class Constants {

    /**
     * 0: 已加入 1: 申请中 2: 已退出
     */
    public static final int IN_TEAM = 0;

    /**
     * 0: 同意加入 1: 申请中
     */
    public static final int REQUEST_TEAM = 1;

    public static final int EXIT_TEAM = 2;


    public static final int REJECT_TEAM = 3;


    /**
     * 主要技术类型
     */
    public static final int HIRING_MAIN_SKILL = 1;

    /**
     * 辅助技术类型
     */
    public static final int HIRING_OTHER_SKILL = 2;

    /**
     * 岗位状态 0:jd 删除 1:jd 招聘中 2:jd 过期
     */
    public static final int JD_DELTE = 0;

    public static final int JD_ING = 1;

    public static final int JD_INVAILD = 2;
}
