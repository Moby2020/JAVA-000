package org.geekbang.university;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpDemo {
    public static void main(String[] args) {
        HttpGet get = new HttpGet("http://localhost:8803");
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get);) {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
