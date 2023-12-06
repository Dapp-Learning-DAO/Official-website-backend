package com.dl.officialsite.hiring.vo;

import java.util.List;
import lombok.Data;

/**
 * @ClassName HiringVO
 * @Author jackchen
 * @Date 2023/12/7 00:37
 * @Description HiringVO
 **/
@Data
public class HiringVO {


    private String position;

    private String description;

    private List<String> skill;
}
