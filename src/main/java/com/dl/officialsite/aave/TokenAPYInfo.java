package com.dl.officialsite.aave;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @ClassName TokenAPYInfo
 * @Author jackchen
 * @Date 2024/3/6 20:37
 * @Description TokenAPYInfo
 **/
@Data
@Entity
@Table(name = "token_apy_info")
@EntityListeners(AuditingEntityListener.class)
public class TokenAPYInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String tokenName;

    String tokenAddress;

    String chainName;

    String tokenApy;

    String current;

    String protocol;
}
