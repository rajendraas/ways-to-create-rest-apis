package io.github.rajendrasatpute.samplespringmvcapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenMeteoClient {

    @Autowired
    private RestClient openMeteoRestClient;

    public Object getWeather(String latitude, String longitude) {
        return openMeteoRestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("current", "temperature_2m,wind_speed_10m")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("timezone", "IST")
                        .build())
                .retrieve()
                .body(Object.class);
    }

}
