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

    PARAM_ERROR("600", "参数错误"),

    FAIL_DOWNLOAD_FAIL("1001", "文件下载失败"),
    FAIL_UPLOAD_FAIL("1002", "文件上传失败"),
    TELEGRAM_WECHAT_NOT_BIND("1003", "Please bind telegram or wechat"),
    MEMBER_NOT_IN_TEAM("1004", "member not in team"),

    TEAM_NOT_EXIST("1005", "team not exist"),
    LOGIN_IN("2001", "please login"),
    TEAM_ADMIN_NOT_EXIST("1006", "team admin not exist"),

    MEMBER_ALREADY_REQUEST_TEAM("1007", "member already request team"),
    MEMBER_ALREADY_IN_TEAM("1012", "member already in team"),

    NOT_FOUND_JD("1008", "not found jd"),
    NOT_FOUND_MEMBER("1009", "not found user"),
    NOT_THE_ADMIN("1010", "user not the admin"),
    NOT_THE_SUPER_ADMIN("1018", "user not the super admin"),
    NOT_AUTHORITY_FOR_EXIT("1018", "user not the authority for exit"),
    TEAM_JOIN_APPLICATION_NOT_EXIST("1011", "team join application not exist"),
    // Sharing
    SHARING_NOT_FOUND("5001", "Sharing not found"),
    SHARING_NOT_OWNER_OR_ADMIN("5002", "You are not sharing user or admin"),

    SHARING_LOCKED("5003", "Sharing locked, please contact admin to unlock"),

    NOT_DELETE_TEAM("1019", "not delete admin team"),

    INVALID_MEMBER("1023", "member is invalid"),

    APPLY_REPEAT("1024", "apply repeat"),

    NOT_FOUND_BOUNTY("1025", "not found bounty"),
    NOT_FOUND_COURSE("1026", "not found course"),

    // distribute
    ID_NEED_EMPTY("6000", "Id need empty"),
    INVALID_ID("6001", "Id is invalid"),
    INVALID_CHAIN_ID("6002", "Invalid chain"),
    DUPLICATE_MESSAGE("6003", "Iuplicate name"),
    NOT_SUPPORT_MODIFY("6004", "Not support modify"),
    ONLY_CREATOE("6005", "Only creator"),
    EMPTY_CLAIMER("6006", "Empty claimer"),
    INVALID_AMOUNT("6007", "Invalid amount"),
    DUPLICATE_CLAIMER("6008", "Duplicate claimer"),
    SIZE_NOT_MATCH("6009", "Size not match"),
    NOT_FOUND_WISH("7000", "not found wish"),

    // wish
    NOT_REPEAT_LIKE("7001", "not repeat like");

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
