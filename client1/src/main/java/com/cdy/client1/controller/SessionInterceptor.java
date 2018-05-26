package com.cdy.client1.controller;

import com.cdy.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 陈东一
 * 2018/1/28 15:46
 */
public class SessionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String sessionId = CookieUtil.getCookie(Contants.session_id, request);
        if(sessionId == null){
            HttpSession session = request.getSession();
            sessionId = session.getId();
            CookieUtil.addCookie(Contants.session_id, sessionId, 24 * 60 * 60, response);
        }
        HttpSession session = request.getSession();
        Map<String, Object> map = SessionUtil.getSession(sessionId);
        if(map == null){
            return true;
        }
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        String sessionId = CookieUtil.getCookie(Contants.session_id, request);
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();
        if (attributeNames == null) {
            SessionUtil.saveSession(sessionId, map);
            return;
        }
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            map.put(element, session.getAttribute(element));
        }
        SessionUtil.saveSession(sessionId, map);
    }
    
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    
    }
}
