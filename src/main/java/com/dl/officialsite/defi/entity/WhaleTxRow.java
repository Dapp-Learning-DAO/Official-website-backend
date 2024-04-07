package com.dl.officialsite.defi.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName WhaleTxRow
 * @Author jackchen
 * @Date 2024/4/4 11:46
 * @Description WhaleTxRow
 **/
@Data
@Entity
@Table(name = "whale_tx_row")
@EntityListeners(AuditingEntityListener.class)
public class WhaleTxRow {

    @Id
    private Long id;

    private String txhash;

    private String debtTokenAddress;

    private String debtTokenSymbol;

    private BigDecimal debtAmount;

    private BigDecimal debtAmountUsd;

    private Long createTime;

    private String chainId;

    private String whaleAddress;

    private String protocol;
}
