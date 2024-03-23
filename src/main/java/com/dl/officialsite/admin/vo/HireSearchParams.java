package com.dl.officialsite.admin.vo;

import lombok.Data;

/**
 * @ClassName HireSearchParams
 * @Author jackchen
 * @Date 2024/3/23 15:40
 * @Description HireSearchParams
 **/
@Data
public class HireSearchParams {

    /**
     * hiring creator
     */
    private String creator;

    /**
     * hiring status
     */
    private String status;

    /**
     * hiring title
     */
    private String title;
}
