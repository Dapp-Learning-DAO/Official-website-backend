package com.dl.officialsite.hiring.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ApplyVo
 * @Author jackchen
 * @Date 2023/12/11 21:45
 * @Description TODO
 **/
@Data
public class ApplyVo {

    private Long hireId;

    private MultipartFile file;
}
