package com.dl.officialsite.bounty;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "bounty")
@EntityListeners(AuditingEntityListener.class)
public class Bounty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 创建岗位人地址
     */
    @Column(length = 42)
    @NotNull
    private String creator;

    // 标题
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    //薪资范围
    private String salary;

    // 线性释放， 指数释放
    //支付类型
    private Integer paymentType;

    //项目周期
    private String projectPeriod;

    // 所需技能
    private String techTag;

    /**
     * 0:jd 招聘中 1: 已匹配 2:jd 已完成结算 3:jd 已过期 4：jd 已退款 5: 已删除
     */
    private int status;

    // 创建人公司
    private String company;

    //流支付ID
    private String streamId;

    private Long streamStart;

    private Long streamEnd;

    private String streamChainId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deadLine;


    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
