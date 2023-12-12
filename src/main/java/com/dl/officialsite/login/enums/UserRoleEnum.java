package com.dl.officialsite.login.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    ADMIN(1),

    NORMAL(0);

    private int power;

    UserRoleEnum(int power){
        this.power = power;
    }


}
