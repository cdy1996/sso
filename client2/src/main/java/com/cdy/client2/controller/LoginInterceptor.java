package com.cdy.client2.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by 陈东一
 * 2018/1/28 15:46
 */
public class LoginInterceptor  implements HandlerInterceptor {
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri= request.getRequestURI();
        if (uri.contains("login") || uri.contains("logout")) {
            return true;
        }
        String username = CookieUtil.getCookieAttribute("username-client2", request);
        HttpSession session = request.getSession();
        if (username != null && !"".equals(username)) {
            session.setAttribute("username", username);
            return true;
        } else {
            username = CookieUtil.getCookieAttribute("username", request);
            if (username != null && !"".equals(username)) {
                CookieUtil.addCookieAttribute("username-client2", username, 60 * 60, response);
                session.setAttribute("username", username);
                return true;
            }
        }
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+uri;
        response.sendRedirect(UrlContants.server + "/tologin?redirect=" + basePath);
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    
    }
    
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    
    }
}
