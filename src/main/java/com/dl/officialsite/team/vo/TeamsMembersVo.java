package com.dl.officialsite.team.vo;

import com.dl.officialsite.member.Member;
import java.util.List;
import lombok.Data;

/**
 * @ClassName TeamsMembersVo
 * @Author jackchen
 * @Date 2023/10/21 17:34
 * @Description TeamsMembersVo
 **/
@Data
public class TeamsMembersVo {

    private Long id;

    private String teamName;

    private String teamProfile;

    private String administrator;

    private String nickName;
    // dao admin , core contributor,  builder
    private String authority;

    private List<Member> members;

}
