package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.common.base.PageReqBase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
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
