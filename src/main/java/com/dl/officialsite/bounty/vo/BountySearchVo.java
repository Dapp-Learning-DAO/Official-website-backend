package com.dl.officialsite.bounty.vo;

import lombok.Data;

/**
 * @ClassName BountySearchVo
 * @Author jackchen
 * @Date 2024/1/28 11:28
 * @Description 查询条件
 **/
@Data
public class BountySearchVo {

    /**
     * 创建岗位人地址
     */
    private String creator;

    // 标题
    private String title;

    //状态
    private Integer status;
}
