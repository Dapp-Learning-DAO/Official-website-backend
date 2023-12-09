package com.dl.officialsite.sharing.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_share")
public class SharingEntity {

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
     * 分享人匿称
     */
    private String presenter;

    /**
     * 分享所属组织
     */
    private String Org;

    /**
     * 分享人twitter
     */
    private String twitter;

    /**
     * 分享人
     */
    private String memberId;

    /**
     * 文档连接
     */
    private String sharingDoc;

    /**
     * 标签类别
     */
    //defi zk underlying
    private String label;

    /**
     * 奖励金额
     */
    private  Integer reward;
}
