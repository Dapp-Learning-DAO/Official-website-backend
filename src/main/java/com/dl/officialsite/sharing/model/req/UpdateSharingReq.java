package com.dl.officialsite.sharing.model.req;

import lombok.Data;

@Data
public class UpdateSharingReq {

    /**
     * 分享id
     */
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
     * 文档连接
     */
    private String sharingDoc;


    /**
     * 标签类别
     */
    private String label;
}
