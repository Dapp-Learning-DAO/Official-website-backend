package com.dl.officialsite.team.vo;

import java.util.List;
import lombok.Data;

/**
 * @ClassName TeamMemberBatchJoinVO
 * @Author jackchen
 * @Date 2023/12/9 15:14
 * @Description 批量加入团队
 **/
@Data
public class TeamMemberBatchJoinVO {

    private Long teamId;

    private List<Long> memberIds;
}
