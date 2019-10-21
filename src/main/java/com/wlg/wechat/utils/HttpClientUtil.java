package com.wlg.wechat.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtil {
    /**
     * 发送get 网络请求
     * @param uri
     * @return
     */
    public static String get(String uri) {

        CloseableHttpClient httpClient = null;
        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
