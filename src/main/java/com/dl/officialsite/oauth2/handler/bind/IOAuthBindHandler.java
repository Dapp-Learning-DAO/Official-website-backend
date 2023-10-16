package com.dl.officialsite.oauth2.handler.bind;

public interface IOAuthBindHandler<TUserInfo> {

    void bind(String address, TUserInfo userInfo);

    String getRegistrationId();
}
