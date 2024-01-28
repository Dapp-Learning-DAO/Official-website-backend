package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.common.base.PageReqBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributeByPageReqVo extends PageReqBase {
    private Long id;
    private String address;
    private String name;
    private String chainId;
    private String creator;
    private Long expireTime;
    private Integer status;
    private Long createTime;
}
