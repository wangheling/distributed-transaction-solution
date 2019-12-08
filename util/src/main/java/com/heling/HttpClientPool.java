package com.heling;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by admin on 2018/8/10.
 */
public class HttpClientPool {
    private static PoolingHttpClientConnectionManager cm = null;
//    private static HttpConnectionManager manager = null;
    // 读取超时
    private final static int SOCKET_TIMEOUT = 15000;
    // 连接超时
    private final static int CONNECTION_TIMEOUT = 15000;
    // 请求超时
    private final static int CONNECTION_REQUEST_TIMEOUT = 15000;
    // 每个HOST的最大连接数量
    private final static int MAX_CONN_PRE_HOST = 20;
    // 连接池的最大连接数
    private final static int MAX_CONN = 60;
    static{
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(3000);
        cm.setDefaultMaxPerRoute(50);
//        manager = new MultiThreadedHttpConnectionManager();
//        HttpConnectionManagerParams params = manager.getParams();
//        params.setConnectionTimeout(CONNECTION_TIMEOUT);
//        params.setSoTimeout(SOCKET_TIMEOUT);
//        params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
//        params.setMaxTotalConnections(MAX_CONN);
    }
    public static CloseableHttpClient getHttpClient() throws Exception {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).build();
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 默认信任所有证书
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm)
                 .setKeepAliveStrategy(getConnectionKeepAliveStrategy())
                .setSSLSocketFactory(sslsf)
                .setDefaultRequestConfig(globalConfig).build();
        return client;
    }


    public static ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy(){
       return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            }
        };
    }
}
