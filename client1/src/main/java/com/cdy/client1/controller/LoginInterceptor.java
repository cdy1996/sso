package com.cdy.client1.controller;

import com.cdy.web.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 陈东一
 * 2018/1/28 15:46
 */
public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("请求链接:" + uri);
        if (uri.contains("login") || uri.contains("logout")) {
            return true;
        }

        //在登录系统登录后会返回username
        String token = request.getParameter("token");
        if (token != null && !token.isEmpty()) {
            //在子系统也加入到cookie
            CookieUtil.addCookie(Contants.client1_token, token, 60 * 60, response);
            return true;
        }

        //已经登陆过了
        token = CookieUtil.getCookie(Contants.client1_token, request);
        if (token != null && !token.isEmpty()) {
            return true;
        }

        //没登陆则去登录
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + uri;
        response.sendRedirect(Contants.server_url + "/tologin?redirect=" + basePath);
        return false;
    }
    
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
