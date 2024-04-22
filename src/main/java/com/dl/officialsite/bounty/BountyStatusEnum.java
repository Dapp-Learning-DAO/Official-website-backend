package com.dl.officialsite.bounty;

public enum BountyStatusEnum {
    IN_RECRUITMENT(0, "in recruitment"),
    MATCHED(1, "Already matched"),
    SETTLED(2, "settled"),
    TIME_OUT(3, "overtime"),
    REFUND(4, "refund"),
    DELETED(5, "deleted");

    private Integer data;
    private String msg;

    BountyStatusEnum(Integer data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public Integer getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

}
