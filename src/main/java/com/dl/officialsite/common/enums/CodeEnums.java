package com.dl.officialsite.common.enums;


/**
 * @ClassName CodeEnums
 * @Author jackchen
 * @Date 2023/10/16 18:30
 * @Description 错误码集合
 **/
public enum CodeEnums {

    SUCCESSFUL("200", "成功"),
    FAIL("500", "失败"),
    FAIL_DOWNLOAD_FAIL("1001", "文件下载失败"),
    FAIL_UPLOAD_FAIL("1002", "文件上传失败"),
    TELEGRAM_WECHAT_NOT_BIND("1003", "Please bind telegram or wechat"),
    MEMBER_NOT_IN_TEAM("1004", "member not in team"),

    LOGIN_IN("2001", "please login");

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
