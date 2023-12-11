package com.dl.officialsite.sharing.model.db;

import com.dl.officialsite.sharing.constant.SharingLockStatus;
import com.dl.officialsite.sharing.constant.SharingMeetingType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_share",
        indexes= {
        @Index(name = "IDX_THEME",unique = true, columnList = "theme"),
        @Index(name = "IDX_MEMBER_ADDRESS", columnList="member_address")
})
public class TbShare {

    /**
     * 分享Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 分享主题
     */
    @Column(name = "theme", nullable = false)
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
    @Column(name = "member_address",length = 40, nullable = false)
    private String memberAddress;

    /**
     * 文档连接
     */
    @Column(name = "sharing_doc", nullable = true)
    private String sharingDoc;

    /**
     * 标签类别
     */
    @Column(name = "label", nullable = true)
    private String label;

    /**
     * 锁定状态
     */
    @Column(name = "lock_status", nullable = false)
    private int lockStatus = SharingLockStatus.UNLOCKED.getCode();

    @Column(name = "meeting_type", nullable = false)
    private int meetingType = SharingMeetingType.TENCENT.getCode();

    @Column(name = "meeting_id", nullable = true)
    private String meetingId;

    @Column(name = "meeting_link", nullable = true)
    private String meetingLink;
}
