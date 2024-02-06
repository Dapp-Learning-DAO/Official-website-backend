package com.dl.officialsite.bounty.vo;

import lombok.Data;

/**
 * @ClassName MyBountySearchVo
 * @Author jackchen
 * @Date 2024/2/3 20:34
 * @Description MyBountySearchVo
 **/
@Data
public class MyBountySearchVo {

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 申请人地址
     */
    private String memberAddress;
}
