package com.dl.officialsite.common.enums;

public enum DistributeTypeEnums {

    MERKLE_RE_PACKET(0, "merkle_red_packet"),
    SHARING_REWARD(1, "sharingReward");

    private Integer data;
    private String msg;

    DistributeTypeEnums(Integer data, String msg) {
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
