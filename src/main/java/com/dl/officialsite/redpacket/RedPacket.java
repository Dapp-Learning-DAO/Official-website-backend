package com.dl.officialsite.redpacket;


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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "red_packet", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = {"id"})
        })

public class RedPacket {
    @NotNull
    @Column(length = 66)
    private String  name;

    @NotNull
    @Id
    @Column(length = 66)
    private String id;
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

    //小数
    private Double totalAmount;

    //the number of redpacket
    private Integer number;

    //usdc or dai
    private String token;
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> claimedAddress;

}
