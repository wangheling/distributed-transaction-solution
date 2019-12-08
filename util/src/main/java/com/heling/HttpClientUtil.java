package com.heling;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    protected static Executor httpExecutor = Executor.newInstance();

    protected static Gson gson = new Gson();

//	protected static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> doPost(String url, Map<String, String> input) throws Exception {

        HttpPost post = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();

        for (Map.Entry<String, String> entry : input.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //转码  封装成请求实体
        HttpEntity reqEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
        post.setEntity(reqEntity);
//		CloseableHttpResponse requestandresponse = HTTP_CLIENT.execute(post);

        try (CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(post)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return gson.fromJson(EntityUtils.toString(entity, Consts.UTF_8), Map.class);
            }
        }
        return null;
    }


    public static Map<String, Object> doPost(String url, BasicNameValuePair... params) throws Exception {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                nvps.add(params[i]);
            }
            //转码  封装成请求实体
            HttpEntity reqEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
            post.setEntity(reqEntity);
            try (CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(post)) {
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    return gson.fromJson(EntityUtils.toString(entity, Consts.UTF_8), Map.class);
                }
            }
        }
        return null;
    }


    public static String doPostReturnStr(String url, Map<String, String> input) throws Exception {

        HttpPost post = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();

        for (Map.Entry<String, String> entry : input.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //转码  封装成请求实体
        HttpEntity reqEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);

        post.setEntity(reqEntity);
//		CloseableHttpResponse requestandresponse = HTTP_CLIENT.execute(post);
        try (CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(post)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, Consts.UTF_8);
            }
        }
        return null;
    }

    public static String sendHttpPost(String url, String body) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        httpPost.setEntity(new StringEntity(body, Consts.UTF_8));
        String responseContent = null;
        try (CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(httpPost)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, Consts.UTF_8);
            }
        }
        return responseContent;
    }

    public static String doJsonPost(String url, Map<String, String> input)
            throws ClientProtocolException, IOException {
        if (input == null) {
            throw new RuntimeException("The input request cannot be empty");
        }
        String response = httpExecutor.execute(Request.Post(url).bodyString(
                new Gson().toJson(input), ContentType.APPLICATION_JSON))
                .returnContent().asString();
        return response;
    }

    public static Map<String, Object> doJsonPost(String url, String json) throws Exception {
        HttpPost post = new HttpPost(url);
        post.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        StringEntity se = new StringEntity(json, Consts.UTF_8);
        post.setEntity(se);
//		try (CloseableHttpResponse requestandresponse = HTTP_CLIENT.execute(post)){
        try (CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(post)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return gson.fromJson(EntityUtils.toString(entity, Consts.UTF_8), Map.class);
            }
        }
        return null;
    }

}
