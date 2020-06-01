package com.qtu.portal;

import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 21:19
 */
public class HttpClientTest {
    /**
     * get无参请求
     */
    @Test
    public void TestDoGET(){
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();

        //创建http get请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");

        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = client.execute(httpGet);
            //判断返回状态是否是200
            if (response.getStatusLine().getStatusCode() == 200){
                String content = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println("响应内容："+content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get带参请求
     */
    @Test
    public void TestDoGETParam() throws URISyntaxException {
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();

        URI uri = new URIBuilder("http://www.baidu.com/").setParameter("wd", "java").build();
        System.out.println(uri);

        //创建http get请求
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = client.execute(httpGet);
            //判断返回状态是否是200
            if (response.getStatusLine().getStatusCode() == 200){
                String content = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println("响应内容："+content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void DoPost(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.oschina.net/");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200 ){
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
