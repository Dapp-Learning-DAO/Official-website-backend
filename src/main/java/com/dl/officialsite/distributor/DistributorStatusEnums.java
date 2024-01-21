package com.dl.officialsite.distributor;

public enum DistributorStatusEnums {

    UN_COMPLETED(0, "unCompleted"),
    COMPLETED(1, "completed"),
    TIME_OUT(2, "overtime"),
    REFUND(3, "refund");

    // 0 uncompleted 1 completed 2 超时 3 refund 4 pending

    private Integer data;
    private String msg;

    DistributorStatusEnums(Integer data, String msg) {
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
