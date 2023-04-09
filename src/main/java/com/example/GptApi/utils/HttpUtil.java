package com.example.GptApi.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    public static HttpEntity<String> createStringHttpEntity(String httpBody, String apiKey) {
        HttpHeaders headers = createHttpHeaders(apiKey, MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpBody, headers);

        return httpEntity;
    }

    private static HttpHeaders createHttpHeaders(String key, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(key);
        headers.setContentType(mediaType);

        return headers;
    }

    public static String createStringResponseEntity(HttpEntity<String> httpEntity, String URL) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);

        return responseEntity.getBody();
    }
}
