package com.dl.officialsite.sharing.constant;

import lombok.Getter;

@Getter
public enum SharingLockStatus {

    UNLOCKED(0),

    LOCKED(1);

    private int code;

    SharingLockStatus(int code){
        this.code = code;
    }
}
