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

    TEAM_NOT_EXIST("1005", "team not exist"),
    LOGIN_IN("2001", "please login"),
    TEAM_ADMIN_NOT_EXIST("1006", "team admin not exist"),
    MEMBER_ALREADY_REQUEST_TEAM("1007", "member already request team"),

    NOT_FOUND_JD("1008", "not found jd"),
    NOT_FOUND_MEMBER("1009", "not found user"),
    NOT_THE_ADMIN("1010", "user not the admin"),
    //Sharing
    SHARING_NOT_FOUND("5001", "Sharing not found"),
    SHARING_NOT_OWNER("5002", "You are no sharing user"),

    SHARING_LOCKED("5003", "Sharing locked, please contact admin to unlock");



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
