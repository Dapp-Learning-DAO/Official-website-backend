package com.dl.officialsite.defi.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName WhaleChainToken
 * @Author jackchen
 * @Date 2024/4/15 22:46
 * @Description WhaleChainToken
 **/
@Data
@Entity
@Table(name = "whale_chain_token")
@EntityListeners(AuditingEntityListener.class)
public class WhaleChainToken {

    @Id
    private Long id;

    private String whaleAddress;

    private String chainName;

    private String tokenAddress;

    private String tokenSymbol;

    private String amount;

    private String price;

    private Integer decimals;
}
