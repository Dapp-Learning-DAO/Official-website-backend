package com.dl.officialsite.bounty.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @ClassName BountySearchVo
 * @Author jackchen
 * @Date 2024/1/28 11:28
 * @Description 查询条件
 **/
@Data
public class BountySearchVo {

    /**
     * 创建岗位人地址
     */
    private String creator;

    // 标题
    private String title;

    //状态
    private Integer status;

    //0: is linked 2:not linked
    private Integer linkStream;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deadLine;

}
