package com.dl.officialsite.common.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpUtil {
    // 配置连接超时和socket超时时间，单位为毫秒
    private static final int connectionTimeout = 5000; // 连接超时时间为5秒
    private static final int socketTimeout = 10000; // Socket超时时间为10秒

    private static final CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(5);
        connManager.setDefaultMaxPerRoute(2);

        // 创建RequestConfig.Builder来设置超时参数
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(connectionTimeout)
            .setSocketTimeout(socketTimeout)
            .build();

        httpClient = HttpClientBuilder.create()
            .setConnectionManager(connManager)
            .setDefaultRequestConfig(requestConfig)
            .build();
    }

    public static HttpClient client() {
        return httpClient;
    }
}