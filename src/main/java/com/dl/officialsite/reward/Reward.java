package com.dl.officialsite.reward;


import com.dl.officialsite.common.converter.StringListConverter;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "reward", schema = "dl", uniqueConstraints = {
        })

public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String chainId;

    @NotNull
    @Column(length = 66)
    private String  name;


    //usdc or dai
    private String token;

    @NotNull
    private String creator;


    @NotNull
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    // {address:value, add1,value1}
    private List<String> nodeList;


    @NotNull
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<Long> sharingIds;


    //the number of reward
    private Integer number;


    //0 uncompleted  1 completed   3 refund
    private  Integer status;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;

    private Long totalAmount;

    private Integer term;





}
