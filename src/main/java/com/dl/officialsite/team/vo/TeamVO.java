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

    private String teamName;

    private String teamProfile;

    private String administrator;

    private String nickName;
    // dao admin , core contributor,  builder
    private String authority;

    private List<Long> members;
}
