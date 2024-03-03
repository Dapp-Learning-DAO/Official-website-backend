package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.dl.officialsite.distributor.DistributeInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @EntityListeners(AuditingEntityListener.class)
// @Entity
public class DistributeInfoVo extends DistributeInfo {
    // usdc or dai
    @NotNull
    private String token;
    @NotNull
    private Integer tokenDecimal;
    @NotNull
    private String tokenSymbol;
    @NotNull
    private String tokenName;

    @Convert(converter = StringListConverter.class)
    private List<ClaimerInfo> claimerList;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ClaimerInfo {
        private String address;
        private BigDecimal value;
        private Integer status;
    }
}
