package com.dl.officialsite.distributor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "distribute_info", schema = "dl", indexes = {
        @Index(name = "idx_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uin_chain_user_message", columnNames = { "chainId", "creator", "message" }),
        @UniqueConstraint(name = "uin_chain_key", columnNames = { "chainId", "contractKey" }),
        @UniqueConstraint(name = "uin_chain_address", columnNames = { "chainId", "contractAddress" }),

})
public class DistributeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 42)
    private String creator;
    @NotNull
    @Column(length = 66)
    private String name;
    @NotNull
    @Column(length = 66)
    private String message;
    @Column(length = 66)
    @NotNull
    private String contractKey;
    @Column(length = 42)
    private String contractAddress;
    private String merkleRoot;
    @NotNull
    private String chainId;
    @NotNull
    private Long tokenId;
    @NotNull
    private BigDecimal totalAmount;
    private Integer number;
    @NotNull
    private Integer distributeType;
    private Integer status;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Long expireTime;
}
