package com.dl.officialsite.defi.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName Whale
 * @Author jackchen
 * @Date 2024/4/4 11:42
 * @Description TODO
 **/
@Data
@Entity
@Table(name = "whale")
@EntityListeners(AuditingEntityListener.class)
public class Whale {

    @Id
    private Long id;

    private String address;

    private String status;

    private String tag;

    private BigDecimal amountUsd;

    private Long createTime;

}
