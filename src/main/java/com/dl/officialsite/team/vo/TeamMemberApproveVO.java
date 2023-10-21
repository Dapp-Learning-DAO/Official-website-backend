package com.dl.officialsite.team.vo;

import java.util.List;
import lombok.Data;

/**
 * @ClassName TeamMemberApproveVO
 * @Author jackchen
 * @Date 2023/10/21 18:05
 * @Description TeamMemberApproveVO
 **/
@Data
public class TeamMemberApproveVO {

    private Long teamId;

    private List<Long> memberIds;

}
