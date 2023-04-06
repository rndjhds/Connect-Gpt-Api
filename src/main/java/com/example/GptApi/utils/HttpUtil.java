package com.example.GptApi.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    public static HttpEntity<String> getStringHttpEntity(String httpBody, String key) {
        HttpHeaders headers = getHttpHeaders(key, MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpBody, headers);

        return httpEntity;
    }

    private static HttpHeaders getHttpHeaders(String key, MediaType type) {
        HttpHeaders headers = new HttpHeaders(); // HttpClient의 header 객체 생성
        headers.setBearerAuth(key); // header의 인증키 set
        headers.setContentType(type);

        return headers;
    }

    public static String getStringResponseEntity(HttpEntity<String> httpEntity, String openAiUrl) {
        RestTemplate restTemplate = new RestTemplate(); // spring 프레임워크의 http call 메서드 생성

        ResponseEntity<String> gptResponseEntity = restTemplate.exchange(openAiUrl, HttpMethod.POST, httpEntity, String.class);

        return gptResponseEntity.getBody();
    }
}
