package com.dl.officialsite.common.enums;

public enum DistributeClaimerStatusEnums {

    CREATING(0, "creating"),
    UN_CLAIM(1, "unClaim"),
    CLAIMED(2, "claimed");

    private Integer data;
    private String msg;

    DistributeClaimerStatusEnums(Integer data, String msg) {
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
