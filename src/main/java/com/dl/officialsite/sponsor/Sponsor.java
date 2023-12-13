package com.dl.officialsite.sponsor;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @ClassName SponsorVo
 * @Author jackchen
 * @Date 2023/12/11 21:16
 * @Description 广告
 **/
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "sponsor")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String link;

    private String icon;

    private String remark;

}
