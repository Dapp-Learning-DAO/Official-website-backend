package com.dl.officialsite.login.model;

import com.dl.officialsite.login.enums.UserRoleEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUserInfo implements Serializable {

    private String address;

    private String nonce;

    private boolean isLogon;

}
