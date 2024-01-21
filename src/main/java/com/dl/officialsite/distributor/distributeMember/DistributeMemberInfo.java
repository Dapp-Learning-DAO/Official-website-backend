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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "distribute_member_info", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "uin_distribute_member", columnNames = { "distribute_id", "member_id" }),
        @UniqueConstraint(name = "idx_member", columnNames = { "member_id" }),
        @UniqueConstraint(name = "id", columnNames = { "id" })
})

public class DistributeMemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long distributeId;
    @NotNull
    private Long memberId;
    private BigInteger distributeAmount;
    @NotNull
    private Integer status;
    @NotNull
    private Long expireTime;
    @UpdateTimestamp
    @Column(updatable = true)
    private Long updateTime;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
}
