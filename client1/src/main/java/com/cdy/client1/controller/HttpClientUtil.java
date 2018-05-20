package com.cdy.client1.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * todo
 * Created by 陈东一
 * 2018/5/20 14:38
 */
public class HttpClientUtil {

    public static Object doGet(String url) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity, "utf-8");
            System.out.println(string);
            return string;
        }  finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }
}
