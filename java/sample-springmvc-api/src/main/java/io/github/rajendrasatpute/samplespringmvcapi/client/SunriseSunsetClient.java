package io.github.rajendrasatpute.samplespringmvcapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SunriseSunsetClient {

    @Autowired
    private RestClient sunriseSunsetRestClient;

    public Object getSunriseSunsetTimes(String latitude, String longitude) {
        return sunriseSunsetRestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("lat", latitude)
                        .queryParam("lng", longitude)
                        .queryParam("tzid", "Asia/Kolkata")
                        .build())
                .retrieve()
                .body(Object.class);
    }
}
