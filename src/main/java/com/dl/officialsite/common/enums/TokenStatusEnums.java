package com.dl.officialsite.common.enums;

public enum TokenStatusEnums {

    NORMAL(0, "normal"),
    FREEZE(1, "freeze");


    private Integer data;
    private String msg;

    TokenStatusEnums(Integer data, String msg) {
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
