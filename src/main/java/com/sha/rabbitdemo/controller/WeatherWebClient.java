package com.sha.rabbitdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
public class WeatherWebClient {

    @Value("${weather.api.key}")
    String apiKey;

    private final WebClient client;

    public WeatherWebClient() {
        client = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/weather")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public WebClient getWeatherClient() {
        return client;
    }
}
