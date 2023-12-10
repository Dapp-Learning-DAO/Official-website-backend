package com.dl.officialsite.sharing.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class CreateSharingReq {

    /**
     * 分享主题
     */
    @ApiModelProperty("分享主题")
    private String theme;

    /**
     * 分享日期
     */
    @ApiModelProperty("分享日期，如2023-01-01")
    private String date;

    /**
     * 分享时间（UTC+8）
     */
    @ApiModelProperty("分享时间")
    private String time;

    /**
     * 分享语言
     */
    @ApiModelProperty("分享语言,0-中文，1-英文")
    private int language;  // 0 Chinese 1 English

    /**
     * 分享人匿称
     */
    @ApiModelProperty("分享人昵称")
    private String presenter;

    /**
     * 分享所属组织
     */
    @ApiModelProperty("所属组织")
    private String org;

    /**
     * 分享人twitter
     */
    @ApiModelProperty("分享人twitter")
    private String twitter;

    /**
     * 文档连接
     */
    @ApiModelProperty("分享链接")
    private String sharingDoc;


    /**
     * 标签类别
     */
    @ApiModelProperty("标签类别")
    //defi zk underlying
    private String label;
}
