package com.example.demo.bip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Log4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        if (log.isDebugEnabled()){
            log.debug("===========================request begin================================================");
            log.debug("URI         : {}" + request.getURI());
            log.debug("Method      : {}" + request.getMethod());
            log.debug("Headers     : {}" + request.getHeaders() );
            log.debug("Request body: {}" + new String(body, StandardCharsets.UTF_8));
            log.debug("==========================request end================================================");
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
            log.info(
                "============================response begin==========================================");
            log.debug("Status code  : {}" + response.getStatusCode());
            log.debug("Status text  : {}" + response.getStatusText());
            log.debug("Headers      : {}" + response.getHeaders());
            log.debug("Response body: {}" + inputStringBuilder);
            log.info(
                "=======================response end=================================================");
        }
    }
}
