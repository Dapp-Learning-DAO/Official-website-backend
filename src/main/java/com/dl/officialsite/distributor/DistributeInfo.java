package com.dl.officialsite.distributor;

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
@Table(name = "distribute_info", schema = "dl")
public class DistributeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long creatorId;
    @NotNull
    @Column(length = 66)
    private String name;
    @NotNull
    @Column(length = 66)
    private String message;
    @Column(length = 66)
    @NotNull
    private String contractKey;
    private String contractAddress;
    private String merkleRoot;
    private Long expireTime;
    @NotNull
    private String chainId;
    @NotNull
    private Long tokenId;
    @NotNull
    private Double totalAmount;
    @NotNull
    private Integer distributeType;
    private Integer status;
    @UpdateTimestamp
    private Long updateTime;
    @CreatedDate
    private Long createTime;
}
