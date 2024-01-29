package com.dl.officialsite.tokenInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "token_info", schema = "dl")

public class TokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String chainId;
    @NotNull
    private String tokenAddress;
    @NotNull
    private String tokenName;
    @NotNull
    private String tokenSymbol;
    @NotNull
    private Integer tokenDecimal;
    private Integer status;
    @UpdateTimestamp
    private Long updateTime;
    @CreatedDate
    private Long createTime;
}
