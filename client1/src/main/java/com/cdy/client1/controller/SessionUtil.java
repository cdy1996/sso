package com.cdy.client1.controller;

import com.cdy.JedisUtil;
import com.cdy.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

/**
 * todo
 * Created by 陈东一
 * 2018/5/26 14:10
 */
public class SessionUtil {
    
    
    public static String saveSession(String key, Map<String, Object> map) throws JsonProcessingException {
        return JedisUtil.set(key, JsonUtil.toJson(map), 60 * 60);
    }
    
    public static Map<String, Object> getSession(String key) {
        String s = JedisUtil.get(key);
        if (s == null) {
            return null;
        }
        return JsonUtil.parseObject(s, Map.class);
    }
    
}
