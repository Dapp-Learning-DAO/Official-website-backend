package com.dl.officialsite.distributor.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dl.officialsite.distributor.DistributeInfo;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class DistributeInfoVo extends DistributeInfo {
    private String creatorAddress;
    private String tokenAddress;
}
