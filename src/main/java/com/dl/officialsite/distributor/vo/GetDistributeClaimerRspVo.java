package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDistributeClaimerRspVo extends DistributeClaimer {
    private String claimerAddress;
}
