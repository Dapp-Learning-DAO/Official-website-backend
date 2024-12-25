package com.dl.officialsite.bounty;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @ClassName BountyMemberMap
 * @Author jackchen
 * @Date 2024/2/3 20:02
 * @Description BountyMemberMap
 **/
@Data
@Entity
public class BountyMemberMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * bountyId
     */
    private Long bountyId;

    /**
     * memberAddress
     */
    private String memberAddress;

    private Integer status;

    private String contractAddress;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String introduction;

    @CreationTimestamp
    @Column(updatable = false, nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
