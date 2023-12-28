package com.dl.officialsite.team.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName TeamMemberJoinVO
 * @Author jackchen
 * @Date 2023/10/21 18:01
 * @Description TeamMemberJoinVO
 **/
@Data
public class TeamMemberJoinVO {

    @NotNull(message = "团队ID不能为null")
    private Long teamId;

    @NotNull(message = "成员ID不能为null")
    private Long memberId;
}
