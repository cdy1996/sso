package com.cdy.client1.controller;

import com.cdy.CookieUtil;
import com.cdy.HttpClientUtil;
import com.cdy.JedisUtil;
import com.cdy.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 陈东一
 * 2018/1/28 16:07
 */
@Controller
public class UserController {
    
    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(Contants.server_url + "/tologin?redirect=" + Contants.client1_url + "/user");
    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = CookieUtil.getCookie(Contants.client1_token, request);
        JedisUtil.delete(Contants.session_id);
        CookieUtil.delCookie(Contants.session_id, request, response);
        response.sendRedirect(Contants.server_url + "/logout?token=" + token);
    }
    
    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request) throws IOException {
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            token = CookieUtil.getCookie(Contants.client1_token, request);
        }
        String result = (String) HttpClientUtil.doGet(Contants.server_url + "/user?token=" + token);
        HttpSession session = request.getSession();
        Object test = session.getAttribute("test");
        if (test == null) {
            session.setAttribute("test", "test");
        }
        String s = JsonUtil.toJson(request.getSession());
        System.out.println(s);
        HttpSession httpSession = JsonUtil.parseObject(s, HttpSession.class);
        System.out.println(httpSession.getAttribute("test"));

        return result;
    }
}
