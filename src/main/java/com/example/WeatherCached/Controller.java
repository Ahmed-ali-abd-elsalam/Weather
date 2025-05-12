package com.example.WeatherCached;

import com.example.WeatherCached.Model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class Controller {
    @Value("${app.API_KEY}")
    private String APIKEY;
    private final static String BaseUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    @Cacheable(value = "WeatherList")
    @GetMapping(path = "/All/{Location}")
    public List<Weather> CountryAll(@PathVariable String Location) throws JsonProcessingException {
        List<Weather> report = new ArrayList<Weather>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = BaseUrl+Location;
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url+"?key="+APIKEY,String.class);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode Days = rootNode.get("days");
        for(JsonNode day:Days){
            Weather today = Weather.builder()
                    .address(rootNode.get("address").asText())
                    .description(day.get("description").asText())
                    .date(LocalDate.parse(day.get("datetime").asText()))
                    .tempMax(day.get("tempmax").asDouble())
                    .tempMin(day.get("tempmin").asDouble())
                    .humidity(day.get("humidity").asDouble())
                    .windspeed(day.get("windspeed").asDouble())
                    .visibility(day.get("visibility").asDouble())
                    .build();
            report.add(today);
        }
        return report;
    }
    @Cacheable(value = "Weather")
    @GetMapping(path = "/{Location}")
    public Weather CountryToday(@PathVariable String Location) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = BaseUrl+Location;
        url = url+"/"+LocalDate.now().toString();
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url+"?key="+APIKEY,String.class);
        JsonNode Root = objectMapper.readTree(response.getBody());
        JsonNode Days = Root.get("days");
        Weather today = Weather.builder()
                .address(Root.get("address").asText())
                .description(Days.get(0).get("description").asText())
                .date(LocalDate.parse(Days.get(0).get("datetime").asText()))
                .tempMax(Days.get(0).get("tempmax").asDouble())
                .tempMin(Days.get(0).get("tempmin").asDouble())
                .humidity(Days.get(0).get("humidity").asDouble())
                .windspeed(Days.get(0).get("windspeed").asDouble())
                .visibility(Days.get(0).get("visibility").asDouble())
                .build();
        return today;
    }

    @Cacheable(value = "Weather")
    @GetMapping(path = "/{Location}/{Day}")
    public Weather LocationAndDay(@PathVariable String Location,@PathVariable String Day) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = BaseUrl+Location;
        url = url+"/"+Day;
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url+"?key="+APIKEY,String.class);
        JsonNode Root = objectMapper.readTree(response.getBody());
        JsonNode Days = Root.get("days");
        Weather today = Weather.builder()
                .address(Root.get("address").asText())
                .description(Days.get(0).get("description").asText())
                .date(LocalDate.parse(Days.get(0).get("datetime").asText()))
                .tempMax(Days.get(0).get("tempmax").asDouble())
                .tempMin(Days.get(0).get("tempmin").asDouble())
                .humidity(Days.get(0).get("humidity").asDouble())
                .windspeed(Days.get(0).get("windspeed").asDouble())
                .visibility(Days.get(0).get("visibility").asDouble())
                .build();
        return today;
    }
    @Cacheable(value = "WeatherList")
    @GetMapping(path = "/{Location}/{StartDay}/{EndDay}")
    public List<Weather> LocationWithRange(@PathVariable String Location,@PathVariable String StartDay,@PathVariable String EndDay) throws JsonProcessingException {
        List<Weather> report = new ArrayList<Weather>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = BaseUrl+Location;
        url = url+"/"+StartDay+"/"+EndDay;
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url+"?key="+APIKEY,String.class);
        JsonNode Root = objectMapper.readTree(response.getBody());
        JsonNode Days = Root.get("days");
        for(JsonNode day:Days){
            Weather today = Weather.builder()
                    .address(Root.get("address").asText())
                    .description(day.get("description").asText())
                    .date(LocalDate.parse(day.get("datetime").asText()))
                    .tempMax(day.get("tempmax").asDouble())
                    .tempMin(day.get("tempmin").asDouble())
                    .humidity(day.get("humidity").asDouble())
                    .windspeed(day.get("windspeed").asDouble())
                    .visibility(day.get("visibility").asDouble())
                    .build();
            report.add(today);
        }
        return report;
    }

}
