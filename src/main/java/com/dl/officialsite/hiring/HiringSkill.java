package com.dl.officialsite.hiring;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @ClassName HiringSkill
 * @Author jackchen
 * @Date 2023/12/7 00:33
 * @Description 招聘技能
 **/
@Data
@Entity
@Table(name = "hiring_skill")
public class HiringSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hiringId;

    private String skill;

    //扩展字段
    private int type;

}
