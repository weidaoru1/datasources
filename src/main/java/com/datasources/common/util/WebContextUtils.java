package com.datasources.common.util;
import com.datasources.master.entity.UserInfo;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class WebContextUtils {
    public static HttpServletRequest getRequest(){
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            return request;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static HttpSession getSession(){
        HttpServletRequest req = getRequest();
        if (req != null) {
            return req.getSession();
        }
        return null;
    }
    public static UserInfo getLoginUser(){
        HttpSession session = getSession();
        if (session != null){
            return (UserInfo)session.getAttribute(UserConstants.LOGIN_USER.name());
        }
        return null;
    }
    public static String getLoginUserName(){
        HttpSession session = getSession();
        if (session != null) {
            return (String)session.getAttribute(UserConstants.LOGIN_USER_NAME.name());
        }
        return null;
    }
    public static String getLoginRealName(){
        HttpSession session = getSession();
        if (session != null) {
            return (String)session.getAttribute(UserConstants.LOGIN_REAL_NAME.name());
        }
        return null;
    }

}
