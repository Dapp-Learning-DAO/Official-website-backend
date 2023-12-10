package com.dl.officialsite.sharing.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 经典海报款式数据信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostClassicalGeneratorInput extends PostGenerationInput {

    /**
     * 组织图标，例如SECBIT图标
     */
    private String orgImageBase64;

    /**
     * 项目方，例如Secbits Labs
     */
    private String project;

    /**
     * 分享标题，例如Lookup ArgumentsOverview
     */
    private String theme;

    /**
     * 分享者
     */
    private String presenter;

    /**
     * 日期，例如2023.12.09
     */
    private String date;

    /**
     * 时间，例如20:00
     */
    private String time;

    /**
     * 会议类型，例如TENCENT-MEETING
     */
    private String meetingType;

    /**
     * 会议编号，例如711-901-942
     */
    private String meetingId;

    /**
     * 会议链接
     */
    private String meetingLink;

}
