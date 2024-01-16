package com.dl.officialsite.hiring.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName ApplySerarchVo
 * @Author jackchen
 * @Date 2024/1/14 18:27
 * @Description 查询岗位投资状态条件对喜
 **/
@Data
public class ApplySearchVo {

    /**
     * 岗位名称
     */
    private String applyName;

    /**
     * 投递人姓名
     */
    private String memberName;

    /**
     * 岗位创建人
     */
    private String creatorName;

    /**
     * 投递时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;
}
