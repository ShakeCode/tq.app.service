package com.org.dream.config.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * The type Rest template log interceptor.
 */
@Slf4j
@Component
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        this.traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        this.traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        log.info("============================== request URI         : {}", request.getURI().getPath());
        log.info("============================== request Method      : {}", request.getMethod());
        log.info("============================== request Headers     : {}", request.getHeaders());
        log.info("============================== request Body: {}", new String(body, StandardCharsets.UTF_8));
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }
        log.info("****************************** response Status code  : {}", response.getStatusCode().value());
        log.info("****************************** response Headers      : {}", response.getHeaders());
        log.info("****************************** response Body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        log.info("****************************** final response : {}", inputStringBuilder);
    }
}