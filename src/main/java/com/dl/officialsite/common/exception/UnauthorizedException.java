package com.dl.officialsite.common.exception;

public class UnauthorizedException extends Throwable {

    private String msg;
    public UnauthorizedException(String unauthorizedAccess) {
        this.msg = unauthorizedAccess;
    }
}
