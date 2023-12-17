package com.dl.officialsite.hiring.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName HiringVO
 * @Author jackchen
 * @Date 2023/12/7 00:37
 * @Description HiringVO
 **/
@Data
public class HiringVO {

    private Long id;

    @NotBlank(message = "职位不能为空")
    private String position;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotBlank(message = "工作地点不能为空")
    private String  location;

    @NotBlank(message = "email不能为空")
    private String  email;

    @NotNull(message = "工作类型不能为空")
    private List<HiringSkillVO> mainSkills;


    //ignore
    private List<HiringSkillVO> otherSkills;

    @NotBlank(message = "公司不能为空")
    private String company;

    private String invoice;

    @NotNull(message = "年薪不能为空")
    private String yearlySalary;

    private String benefits;

    private String twitter;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String address;

    /**
     * 0:jd 删除
     * 1:jd 招聘中
     * 2:jd 过期
     */
    private int status;

}
