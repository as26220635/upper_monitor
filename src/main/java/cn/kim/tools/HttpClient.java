package cn.kim.tools;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 余庚鑫 on 2017/8/4.
 */
public class HttpClient {

    private static Logger logger = LogManager.getLogger(HttpClient.class.getName());

    /**
     * 处理get请求.
     *
     * @param url 请求路径
     * @return json
     */
    public Map<String, Object> get(String url, Map<String, String> params) {
        return get(url, null, params);
    }

    public Map<String, Object> get(String url, Map<String, String> headerMap, Map<String, String> params) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(2);
        resultMap.put(MagicValue.STATUS, Attribute.STATUS_ERROR);

        //实例化httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //处理参数
        String str = "";
        if (!ValidateUtil.isEmpty(params)) {
            try {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }
                str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
                str = "?" + str;
            } catch (IOException e) {
            }
        }
        //实例化get方法
        HttpGet httpGet = new HttpGet(url + str);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        //header头
        if (!ValidateUtil.isEmpty(headerMap)) {
            headerMap.keySet().forEach(key -> {
                httpGet.setHeader(key, headerMap.get(key));
            });
        }
        //请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //执行get方法
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                resultMap.put(MagicValue.STATUS, Attribute.STATUS_SUCCESS);
                resultMap.put(MagicValue.DESC, content);
            }
        } catch (ClientProtocolException e) {
            resultMap.put(MagicValue.DESC, "网络连接失败！");
        } catch (HttpHostConnectException e) {
            resultMap.put(MagicValue.DESC, "网络连接失败!");
        } catch (IOException e) {
            resultMap.put(MagicValue.DESC, e.getMessage());
        }

        logger.info("GET请求，URL:" + url + str);
        return resultMap;
    }

    public Map<String, Object> post(String url, Map<String, String> params) {
        return post(url, null, params);
    }

    /**
     * 处理post请求.
     *
     * @param url    请求路径
     * @param params 参数
     * @return json
     */
    public Map<String, Object> post(String url, Map<String, String> headerMap, Map<String, String> params) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(2);
        resultMap.put(MagicValue.STATUS, Attribute.STATUS_ERROR);
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化post方法
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        //header头
        if (!ValidateUtil.isEmpty(headerMap)) {
            headerMap.keySet().forEach(key -> {
                httpPost.setHeader(key, headerMap.get(key));
            });
        }

        //处理参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        //结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                resultMap.put(MagicValue.STATUS, Attribute.STATUS_SUCCESS);
                resultMap.put(MagicValue.DESC, content);
            }
        } catch (ClientProtocolException e) {
            resultMap.put(MagicValue.DESC, "网络连接失败！");
        } catch (HttpHostConnectException e) {
            resultMap.put(MagicValue.DESC, "网络连接失败!");
        } catch (IOException e) {
            resultMap.put(MagicValue.DESC, e.getMessage());
        }
        logger.info("POST请求，URL:" + url + "，参数:" + nvps.toString());
        return resultMap;
    }
}
