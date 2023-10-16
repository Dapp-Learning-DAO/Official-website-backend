package com.dl.officialsite.login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class SignInfo {
    private String  address;
    private String v;
    private String r;
    private String s;
    private String message;
}
