package com.dl.officialsite.sharing.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_share",
        uniqueConstraints={@UniqueConstraint(columnNames={"theme"})},
        indexes= {
        @Index(columnList="member_id")
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
    private String theme;

    /**
     * 分享日期
     */
    private String date;

    /**
     * 分享时间（UTC+8）
     */
    private String time;

    /**
     * 分享语言
     */
    private int language;  // 0 Chinese 1 English

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
    @Column(name = "member_id")
    private long memberId;

    /**
     * 文档连接
     */
    @Column(name = "sharing_doc")
    private String sharingDoc;

    /**
     * 标签类别
     */
    private String label;

    /**
     * 锁定状态
     */
    @Column(name = "lock_status")
    private int lockStatus;

    private int meetingType;

    private String meetingId;

    private String meetingLink;
}
