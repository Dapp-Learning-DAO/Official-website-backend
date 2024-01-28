package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.common.base.PageReqBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributeClaimerByPageReqVo extends PageReqBase {
    private Long id;
}
