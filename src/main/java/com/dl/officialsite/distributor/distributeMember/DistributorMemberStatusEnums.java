package com.dl.officialsite.distributor.distributeMember;

public enum DistributorMemberStatusEnums {

    UN_Claim(0, "unClaim"),
    CLAIMED(1, "claimed");

    // 0 uncompleted 1 completed 2 超时 3 refund 4 pending

    private Integer data;
    private String msg;

    DistributorMemberStatusEnums(Integer data, String msg) {
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
