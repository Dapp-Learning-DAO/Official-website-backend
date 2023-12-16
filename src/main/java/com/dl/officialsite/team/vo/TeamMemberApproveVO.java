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

    /**
     * 0: 已加入
     * 1: 申请中
     * 2： 已退出 , 未加入
     * 3： 拒绝
     */
    private int status;

}
