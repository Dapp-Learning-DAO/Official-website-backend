package com.dl.officialsite.sharing.model.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
public class UpdateSharingReq {

    /**
     * 分享id
     */
    @ApiModelProperty("分享id")
    private long id;
    /**
     * 分享主题
     */
    @ApiModelProperty("分享主题")
    private String theme;

    /**
     * 分享日期
     */
    @ApiModelProperty("分享日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * 分享时间（UTC+8）
     */
    @ApiModelProperty("分享时间")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private String time;

    /**
     * 分享语言
     */
    @ApiModelProperty("分享语言")
    private int language;  // 0 Chinese 1 English

    /**
     * 分享人匿称
     */
    @ApiModelProperty("分享人昵称")
    private String presenter;

    /**
     * 分享所属组织
     */
    @ApiModelProperty("分享所属组织")
    private String org;

    /**
     * 分享人twitter
     */
    @ApiModelProperty("分享人推特")
    private String twitter;

    /**
     * 文档连接
     */
    @ApiModelProperty("分享链接")
    private String sharingDoc;


    /**
     * 标签类别
     */
    @ApiModelProperty("分享标签")
    private String label;

    private String youtubeLink;

    private String bilibiliLink;

    /**
     * tag
     */
    private String tag;
}
