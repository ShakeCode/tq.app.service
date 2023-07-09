package com.org.dream.config.resttemplate;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rest template config.
 */
@Configuration
public class RestTemplateConfig {
    @Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;

    @Value("${http.validateAfterInactivity}")
    private Integer validateAfterInactivity;

    /*@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    @Resource
    private RestTemplateLogInterceptor restTemplateLogInterceptor;

    /**
     * Rest template rest template.
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 解决401报错时，报java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode
        requestFactory.setOutputStreaming(false);
        RestTemplate restTemplate = new RestTemplate();
        // 添加拦截器
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(restTemplateLogInterceptor);
        restTemplate.setInterceptors(interceptors);

        ClientHttpRequestFactory clientHttpRequestFactory = httpRequestFactory();

        // 提供对传出/传入流的缓冲,可以让响应body多次读取(如果不配置,拦截器读取了Response流,再响应数据时会返回body=null)
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));

        // 采用RestTemplate内部的MessageConverter
        // 重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        modifyDefaultCharset(restTemplate);
        // 请求失败异常处理，如果不重写hasError方法，抛出异常，无法执行后续代码
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }
        });
        return restTemplate;
    }

    private void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        Charset defaultCharset = StandardCharsets.UTF_8;
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }

    /**
     * Http request factory client http request factory.
     * @return the client http request factory
     */
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    /**
     * Http client http client.
     * @return the http client
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        connectionManager.setMaxTotal(maxTotal);
        // 单个路由最大连接数
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        // 官方推荐使用检查永久链接的可用性,而不推荐每次请求的时候才去检查 (milliseconds 毫秒)
        connectionManager.setValidateAfterInactivity(validateAfterInactivity);

        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据(response)的时间，超过抛出read timeout
                .setSocketTimeout(socketTimeout)
                // 连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setConnectTimeout(connectTimeout)
                // 提交前检测是否可用
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled)
                //从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        // headers
        List<Header> headers = new ArrayList<>();
       /* headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        headers.add(new BasicHeader("Content-type", "application/json;charset=UTF-8"));*/
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setDefaultHeaders(headers)
                // 保持长连接配置，需要在头添加Keep-Alive
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                // 重试次数，默认是3次，没有开启
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
                .build();
    }
}
