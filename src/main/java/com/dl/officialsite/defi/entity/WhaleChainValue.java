package com.dl.officialsite.defi.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName WhaleChainValue
 * @Author jackchen
 * @Date 2024/4/15 22:16
 * @Description WhaleChainValue
 **/
@Data
@Entity
@Table(name = "whale_chain_value")
@EntityListeners(AuditingEntityListener.class)
public class WhaleChainValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String whaleAddress;

    private String chainId;

    private String chainName;

    private String value;

}
