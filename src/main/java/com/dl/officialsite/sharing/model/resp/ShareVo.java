package com.dl.officialsite.sharing.model.resp;

import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.constant.SharingMeetingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName ShareVo
 * @Author jackchen
 * @Date 2024/6/5 23:38
 * @Description ShareVo
 **/
@Data
public class ShareVo {

    /**
     * 分享Id
     */
    private long id;

    /**
     * 分享主题
     */
    private String theme;

    /**
     * 分享日期,2020-12-02
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * 分享时间 20:00（UTC+8）
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private String time;

    /**
     * 分享语言
     */
    private int language; // 0 Chinese 1 English

    /**
     * 分享人昵称
     */
    private String presenter;

    /**
     * 分享所属组织
     */
    private String org;

    /**
     * 分享人twitter
     */
    private String twitter;

    /**
     * 分享人
     */
    private String memberAddress;

    /**
     * 文档连接
     */
    private String sharingDoc;

    /**
     * sharing status
     */
    private Integer status;

    /**
     * 标签类别
     */
    private String label;

    private Integer term;

    /**
     * 锁定状态
     */
    private int lockStatus = SharingLockStatus.UNLOCKED.getCode();

    private int meetingType = SharingMeetingType.TENCENT.getCode();

    // wating, claimable, claimed, timeout
    private Integer rewardStatus;

    private Integer rewardAmount;

    private Integer distributeId;

    private String meetingId;

    private String meetingLink;

    
    private String youtubeLink;

    
    private String bilibiliLink;
    
    private String otherMediumLink;
    
    private Long createTime;

    private Long updateTime;

    private String shareCount;

    /**
     * 分享人头像
     */
    private String avatar;
}
