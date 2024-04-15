package com.dl.officialsite.defi.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName WhaleProtocol
 * @Author jackchen
 * @Date 2024/4/14 17:32
 * @Description
 **/
@Data
@Entity
@Table(name = "whale_protocol")
@EntityListeners(AuditingEntityListener.class)
public class WhaleProtocol {


    @Id
    private Long id;

    private String whaleAddress;

    private String protocolName;

    private String totalSupply;

    private String totalDebt;

    private Integer chainId;


}
