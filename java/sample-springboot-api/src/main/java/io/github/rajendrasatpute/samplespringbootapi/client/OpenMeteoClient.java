package io.github.rajendrasatpute.samplespringbootapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "open-meteo", url = "https://api.open-meteo.com/v1")
public interface OpenMeteoClient {
    @GetMapping("/forecast?current=temperature_2m,wind_speed_10m&timezone=IST")
    Object getWeather(@RequestParam String latitude, @RequestParam String longitude);
}
