package com.dl.officialsite.oauth2.handler.userinfo;

import com.dl.officialsite.oauth2.model.bo.user.IUserInfo;

public interface IUserInfoRetrieveHandler<TUserInfo extends IUserInfo> {

    TUserInfo retrieve(String userInfoUri, String accessToken);

    String getRegistrationId();

}
