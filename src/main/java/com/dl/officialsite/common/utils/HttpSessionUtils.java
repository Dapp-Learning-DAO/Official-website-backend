package com.dl.officialsite.common.utils;

import com.dl.officialsite.login.model.SessionUserInfo;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

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



    public static SessionUserInfo getMember(HttpSession session){
        Object sessionObj = session.getAttribute(MEMBER_ATTRIBUTE_KEY);
//        if(sessionObj == null){
//            throw new IllegalArgumentException("User not login");
//        }
        return (SessionUserInfo)sessionObj;
    }

    public static void requireLogin(HttpSession session) {
        SessionUserInfo sessionUserInfo = getMember(session);
        if (sessionUserInfo == null) {
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
}
