package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.value.qual.StringVal;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "red_packet", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = {"id"}),
        @UniqueConstraint(name = "name", columnNames = {"name"})})

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
    @Convert(converter = StringListConverter.class)
    private List<String> addressList;
    @NotNull
    private Long expireTime;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @NotNull
    private String chainId;

    //0 uncompleted  1 completed
    private  Integer status;
    private Long amount;

    //usdc or dai
    private String token;

    @Convert(converter = StringListConverter.class)
    private List<String> claimedAddress;

}
