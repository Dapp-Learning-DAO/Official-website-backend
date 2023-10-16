package com.dl.officialsite.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName BizException
 * @Author jackchen
 * @Date 2023/10/16 18:38
 * @Description 自定义异常
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException{

    private String code;

    private String msg;

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
