package com.cdy.client1.controller;

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
//        String username = CookieUtil.getCookieAttribute("username-client1", request);
        
        //在服务端回调时会把token放在uri上
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        
        //将token存在session，方便
        if (token == null) {
            token = (String) session.getAttribute("token");
        } else {
            session.setAttribute("token", token);
        }
        
        if(token != null) {
            String username = JedisUtil.get("client1:token"+token);
           
            if (username != null && !"".equals(username)) {
                JedisUtil.set("client1:username:"+username, username);
                return true;
            } else {
//                username = CookieUtil.getCookieAttribute("username", request);
                username = (String)HttpClientUtil.doGet(UrlContants.server + "user?token=" + token);
                if (username != null && !"".equals(username)) {
                    JedisUtil.set("client1:"+username, username);
//                    CookieUtil.addCookieAttribute("username-client1", username, 60 * 60, response);
                    session.setAttribute("username", username);
                    return true;
                }
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
