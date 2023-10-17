package com.dl.officialsite.common.enums;

import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 * @ClassName CodeEnums
 * @Author jackchen
 * @Date 2023/10/16 18:30
 * @Description 错误码集合
 **/
public enum CodeEnums {

    SUCCESSFUL("200", "成功"),
    FAIL("500", "失败"),
    FAIL_DOWNLOAD_FAIL("1001", "File Downlaod Fail"),
    FAIL_UPLOAD_FAIL("1002", "File Upload Fail"),
    USER_NOT_FOUND("1003", "User Not Found");

    private String code;

    private String msg;

    CodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
