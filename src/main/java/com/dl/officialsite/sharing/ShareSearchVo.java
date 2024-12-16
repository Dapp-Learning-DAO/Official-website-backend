package com.dl.officialsite.sharing;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName ShareVo
 * @Author jackchen
 * @Date 2024/2/28 19:29
 * @Description 分享查询条件
 **/
@Data
public class ShareSearchVo {
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
    private Date time;

    /**
     * 分享语言
     */
    private Integer language; // 0 Chinese 1 English

    /**
     * 分享人昵称
     */
    private String presenter;

    /**
     * 标签类别
     */
    private String label;

    /**
     * tag
     */
    private String tag;

}
