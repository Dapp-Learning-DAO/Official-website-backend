package com.dl.officialsite.team.vo;

import java.util.List;
import lombok.Data;

/**
 * @ClassName TeamVO
 * @Author jackchen
 * @Date 2023/10/21 17:25
 * @Description TeamVO
 **/
@Data
public class TeamVO {

    private Long id;

    private String teamName;

    private String teamProfile;

    private String administrator;

    // dao admin , core contributor,  builder
    private int authority;

    private int status;

    private List<Long> members;
}
