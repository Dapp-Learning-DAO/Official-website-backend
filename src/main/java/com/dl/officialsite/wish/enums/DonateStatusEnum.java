package com.dl.officialsite.wish.enums;

/**
 * @ClassName DonateStatusEnum
 * @Author jackchen
 * @Date 2025/3/2 18:57
 * @Description DonateStatusEnum
 **/
public enum DonateStatusEnum {
    PENDING(0, "pending"),
    SUCCESS(1, "success");

    private final Integer status;
    private final String desc;

    DonateStatusEnum(Integer status, String desc) {
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

