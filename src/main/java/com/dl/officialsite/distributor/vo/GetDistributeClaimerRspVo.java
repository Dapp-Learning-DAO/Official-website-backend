package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributeClaimerRspVo extends DistributeClaimer {
    private String claimerAddress;
}
