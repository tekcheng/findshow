package com.megamusic.findshow.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chengchao on 2018/9/26.
 */
@Slf4j
public class HttpClientUtils {

    public static String doGet(String url) throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个uri对象
        URIBuilder uriBuilder = new URIBuilder(url);
        //uriBuilder.addParameter("query","花千骨");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"utf-8");
        log.info("httpClient get 请求地址：{}，response：{}，statusCode",url,string,statusCode);
        //关闭httpclient
        response.close();
        httpClient.close();
        return string;
    }

    public static String doPost(String url, Map<String,String> map) throws Exception{
        String body = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost post = new HttpPost(url);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置请求的内容
        StringEntity entity = new UrlEncodedFormEntity(nvps,"utf-8");
        post.setEntity(entity);

        log.info("请求地址："+url);
        log.info("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(post);
        //获取结果实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

}
