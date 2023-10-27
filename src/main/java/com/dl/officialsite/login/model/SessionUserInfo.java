package com.dl.officialsite.login.model;

import com.dl.officialsite.login.enums.UserRoleEnum;
import lombok.Data;

@Data
public class SessionUserInfo {

    private String address;

    private String nonce;


}
