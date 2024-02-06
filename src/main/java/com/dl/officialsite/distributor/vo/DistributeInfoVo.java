package com.dl.officialsite.distributor.vo;

import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dl.officialsite.distributor.DistributeInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
//@Entity
public class DistributeInfoVo extends DistributeInfo {
    private String creator;

    //usdc or dai
    @NotNull
    private String token;
    @NotNull
    private Integer tokenDecimal;
    @NotNull
    private String tokenSymbol;
    @NotNull
    private String tokenName;

    @Convert(converter = StringListConverter.class)
    private List<String> claimedAddress;

    @Convert(converter = StringListConverter.class)
    private List<BigDecimal> claimedValues;

    @Convert(converter = StringListConverter.class)
    private List<Integer> claimedStatus;
}
