package com.dl.officialsite.login.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ANONYMOUS(-1),

    ADMIN(Integer.MAX_VALUE),

    NORMAL(0);

    private int power;

    UserRoleEnum(int power){
        this.power = power;
    }


}
