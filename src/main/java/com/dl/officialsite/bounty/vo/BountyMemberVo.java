package com.dl.officialsite.bounty.vo;

import com.dl.officialsite.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @ClassName BountyMemberVo
 * @Author jackchen
 * @Date 2024/2/6 21:36
 * @Description BountyMemberVo
 **/
@Data
public class BountyMemberVo {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Member member;
}
