package com.dl.officialsite.bounty.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName BountyVo
 * @Author jackchen
 * @Date 2024/1/28 11:19
 * @Description BountyVo
 **/
@Data
public class BountyVo {

    private Long id;

    /**
     * 创建岗位人地址
     */
    private String creator;

    // 标题
    private String title;

    private String description;

    //薪资范围
    private String salary;

    // 线性释放， 指数释放
    //支付类型
    private Integer paymentType;

    //项目周期
    private String projectPeriod;

    // 所需技能
    private String techTag;

    /**
     * 0:jd 招聘中 1: 已匹配 2:jd 已完成结算 3:jd 已过期 4：jd 已退款 5: 已删除
     */
    private int status;

    // 创建人公司
    private String company;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
