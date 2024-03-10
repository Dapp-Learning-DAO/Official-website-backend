package com.dl.officialsite.common.utils;

import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.oauth2.config.OAuthSessionKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
public abstract class HttpSessionUtils {

    public static final String MEMBER_ATTRIBUTE_KEY = "member";


    public static boolean hasNonce(HttpSession httpSession){
        SessionUserInfo userInfo = (SessionUserInfo) httpSession.getAttribute(MEMBER_ATTRIBUTE_KEY);
        if (userInfo == null){
            return false;
        }
        return StringUtils.hasText(userInfo.getNonce());
    }

    public static void putUserInfo(HttpSession session, SessionUserInfo userInfo){
        session.setAttribute(MEMBER_ATTRIBUTE_KEY, userInfo);
    }

    public static boolean isUserLogin(HttpSession session){
        log.info("isUserLogin session id {}", session.getId());
        SessionUserInfo userInfo = getMember(session);
        if (userInfo == null || !userInfo.isLogon()){
            return false;
        }
        return true;
    }

    public static SessionUserInfo getMember(HttpSession session){
        Object sessionObj = session.getAttribute(MEMBER_ATTRIBUTE_KEY);
//        if(sessionObj == null){
//            throw new IllegalArgumentException("User not login");
//        }
        return (SessionUserInfo)sessionObj;
    }


    public static void requireLogin(HttpSession session) {
        SessionUserInfo sessionUserInfo = getMember(session);
        if (!isUserLogin(session)) {
            throw new IllegalArgumentException("User not login");
        }
    }

    public static void clearLogin(HttpSession session) {
        Object sessionObj = session.getAttribute(MEMBER_ATTRIBUTE_KEY);
        if(sessionObj == null){
            return;
        }

        session.removeAttribute(MEMBER_ATTRIBUTE_KEY);
    }

    public static void setOAuthUserName(HttpSession session, OAuthSessionKey sessionKey, String githubName) {
        session.setAttribute(sessionKey.toString(), githubName);
    }

    public static String getOAuthUserName(HttpSession session, OAuthSessionKey sessionKey) {
        return Optional.ofNullable(session.getAttribute(sessionKey.toString()))
            .map(Object::toString)
            .orElse(null);
    }
}
