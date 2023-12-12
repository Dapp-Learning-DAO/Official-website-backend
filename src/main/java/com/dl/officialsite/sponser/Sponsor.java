package com.dl.officialsite.sponser;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

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

    private String company;

    private String link;

    private String icon;

}
