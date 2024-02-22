package com.dl.officialsite.common.enums;

public enum DistributeStatusEnums {

    UN_COMPLETED(null, "unCompleted"),//default
    COMPLETED(0, "completed"),
    ALL_CLAIMED(1, "allClaimed"),
    TIME_OUT(2, "overtime"),
    REFUND(3, "refund");

    private Integer data;
    private String msg;

    DistributeStatusEnums(Integer data, String msg) {
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
