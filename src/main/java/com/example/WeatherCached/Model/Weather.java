package com.example.WeatherCached.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Weather implements Serializable {
    private String address;
    private double tempMax;
    private double tempMin;
    private double humidity;
    private double windspeed;
    private double visibility;
    private String description;
    private LocalDate date;



}
