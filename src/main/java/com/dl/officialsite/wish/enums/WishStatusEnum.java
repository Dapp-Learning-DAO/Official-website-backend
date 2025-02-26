package com.dl.officialsite.wish.enums;

/**
 * @ClassName WishStatusEnum
 * @Author jackchen
 * @Date 2025/2/26 21:54
 * @Description WishStatusEnum
 **/
public enum WishStatusEnum {
    DONATION(0, "donation"),
    FINISH(1, "finish"),
    SETTLE(2, "settle");

    private final Integer status;
    private final String desc;

    WishStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}

