package com.dl.officialsite.hiring.vo;

import lombok.Data;

/**
 * @ClassName HiringVO
 * @Author jackchen
 * @Date 2023/11/7 10:47
 * @Description HiringVO
 **/
@Data
public class HiringVO {


    private Long id;

    private String headline;

    private String employer;

    private String jd;

    private String role;

    private String requirement;

    private String locate;

    private String salary;

    private String memo;
}
