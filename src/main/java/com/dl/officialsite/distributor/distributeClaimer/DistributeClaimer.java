package com.dl.officialsite.distributor.distributeClaimer;

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

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "distribute_claimer", schema = "dl")
public class DistributeClaimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long distributeId;
    @NotNull
    private Long chainId;
    @NotNull
    private Long claimerId;
    private Double distributeAmount;
    @NotNull
    private Integer status;
    @UpdateTimestamp
    @Column(updatable = true)
    private Long updateTime;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
}
