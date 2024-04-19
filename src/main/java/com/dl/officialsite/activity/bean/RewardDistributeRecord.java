package com.dl.officialsite.activity.bean;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "reward_distribute_record", schema = "dl", uniqueConstraints = {
    @UniqueConstraint(name = "unique_task_record", columnNames = {
        "address","activityName"
    })
})
public class RewardDistributeRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 42)
    @NotNull
    private String address;

    @Column(length = 32)
    @NotNull
    private String activityName;

    private boolean rewardDistributed = false;

    @Column(length = 128)
    @NotNull
    private String reward;

    @CreatedDate
    @Column(updatable = false)
    private Long distributedTime;
}
