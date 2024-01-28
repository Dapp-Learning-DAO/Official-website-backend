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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "tb_token_info", schema = "dl")

public class TokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chainId;
    private String tokenAddress;
    private String tokenName;
    private String tokenSymbol;
    private Integer tokenDecimal;
    private Integer status;
    @UpdateTimestamp
    private Long updateTime;
    @CreatedDate
    private Long createTime;
}
