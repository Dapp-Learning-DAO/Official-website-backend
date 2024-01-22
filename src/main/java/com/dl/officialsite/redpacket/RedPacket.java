package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.converter.BigIntegerListConverter;
import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
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
@Table(name = "red_packet", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = {"id"} )
        })

public class RedPacket {
    @NotNull
    @Column(length = 66)
    private String  name;

   // @Column(length = 66)   // string to Long
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long redPacketId;

    @NotNull
    private String creator;
    @NotNull
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> addressList;
    @NotNull
    private Long expireTime;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @NotNull
    private String chainId;

    //0 uncompleted  1 completed  2 超时  3 refund
    private  Integer status;

    private Boolean ifRandom;

    //小数
    private Double totalAmount;

    //the number of redpacket
    private Integer number;

    //usdc or dai
    private String token;

    private Integer tokenDecimal;

    private String tokenSymbol;

    private String tokenName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> claimedAddress;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> claimedValues;

}
