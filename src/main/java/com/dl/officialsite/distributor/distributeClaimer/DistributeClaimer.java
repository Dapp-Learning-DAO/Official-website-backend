package com.dl.officialsite.distributor.distributeClaimer;

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
@Table(name = "distribute_claimer", schema = "dl", indexes = {
        @Index(name = "idx_claimer", columnList = "claimer"),
        @Index(name = "idx_status", columnList = "status"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uin_distribute_claimer", columnNames = { "distributeId", "claimer" })
})
public class DistributeClaimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long distributeId;
    @NotNull
    private String chainId;
    @NotNull
    @Column(length = 42)
    private String claimer;
    @Column(precision = 36, scale = 18)
    private BigDecimal distributeAmount;
    @NotNull
    private Integer status;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}