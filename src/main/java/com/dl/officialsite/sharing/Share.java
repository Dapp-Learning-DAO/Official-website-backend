package com.dl.officialsite.sharing;

import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.constant.SharingMeetingType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "share",
        indexes= {
        @Index(name = "IDX_THEME",unique = true, columnList = "theme"),
        @Index(name = "IDX_MEMBER_ADDRESS", columnList="member_address")
})
public class Share {

    /**
     * 分享Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 分享主题
     */
    @Column(name = "theme", nullable = false,length = 128)
    private String theme;

    /**
     * 分享日期,2020-12-02
     */
    @Column(name = "date", nullable = false)
    private String date;

    /**
     * 分享时间 20:00（UTC+8）
     */
    @Column(name = "time", nullable = false)
    private String time;

    /**
     * 分享语言
     */
    @Column(name = "language", nullable = false)
    private int language = 0;  // 0 Chinese 1 English

    /**
     * 分享人昵称
     */
    @Column(name = "presenter",nullable = false)
    private String presenter;

    /**
     * 分享所属组织
     */
    @Column(name = "org",nullable = true)
    private String org;

    /**
     * 分享人twitter
     */
    @Column(name = "twitter",nullable = true)
    private String twitter;

    /**
     * 分享人
     */
    @Column(name = "member_address",length = 42)
    private String memberAddress;

    /**
     * 文档连接
     */
    @Column(name = "sharing_doc")
    private String sharingDoc;

    /**
     * 标签类别
     */
    @Column(name = "label")
    private String label;

    @NotNull
    private Integer term;

    /**
     * 锁定状态
     */
    @Column(name = "lock_status", nullable = false)
    private int lockStatus = SharingLockStatus.UNLOCKED.getCode();

    @Column(name = "meeting_type", nullable = false)
    private int meetingType = SharingMeetingType.TENCENT.getCode();

    //unclaim, claimed, timeout
    @Column(name = "reward_status")
    private Integer rewardStatus;

    @Column(name = "meeting_id")
    private String meetingId;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column
    private String youtubeLink;

    @Column
    private String bilibiliLink;

    @Column
    private String otherMediumLink;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;
}
