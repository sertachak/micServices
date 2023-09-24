package com.sha.rabbitdemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("weather/api/v1")
public class WeatherController {

    @Value("${weather.api.key}")
    String apiKey;
    private final WeatherWebClient weatherWebClient;
    private final ObjectMapper objectMapper;

    public WeatherController(WeatherWebClient weatherWebClient, ObjectMapper objectMapper) {
        this.weatherWebClient = weatherWebClient;
        this.objectMapper = objectMapper;
    }

    @GetMapping("info")
    public void getWeather() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("");
        uriBuilder.queryParam("lat", 44.34);
        uriBuilder.queryParam("lon", 10.99);
        uriBuilder.queryParam("appid", apiKey);
        
        weatherWebClient.getWeatherClient().get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(this::useReceivedInfo);
    }

    @SneakyThrows
    public void useReceivedInfo(String jsonString){
        final JsonNode jsonNode = objectMapper.readTree(jsonString);
        String country = jsonNode.get("sys").get("country").textValue();
        System.out.println(country);
    }
}
