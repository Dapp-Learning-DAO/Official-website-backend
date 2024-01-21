package com.dl.officialsite.distributor;

import com.dl.officialsite.common.converter.BigIntegerListConverter;
import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "distributor_info", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "uin_nonce", columnNames = { "distributor_nonce" }),
        @UniqueConstraint(name = "uin_name", columnNames = { "distributor_name" }),
        @UniqueConstraint(name = "id", columnNames = { "id" })
})

public class DistributeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long creatorId;
    @NotNull
    private Long chainId;
    @NotNull
    private String tokenAddress;
    @NotNull
    @Column(length = 66)
    private String distributeName;
    @NotNull
    private Long distributeNonce;
    @NotNull
    private BigInteger totalAmount;
    @NotNull
    private Integer status;
    @NotNull
    private Integer decimals;
    @NotNull
    private Long expireTime;
    @UpdateTimestamp
    @Column(updatable = true)
    private Long updateTime;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
}
