package com.dl.officialsite.login;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class SignInfo {
    private String  address;
    private int v;
    private BigInteger r;
    private BigInteger s;
    private String message;
}
