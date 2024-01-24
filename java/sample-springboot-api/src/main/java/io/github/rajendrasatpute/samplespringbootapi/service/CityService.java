package io.github.rajendrasatpute.samplespringbootapi.service;

import io.github.rajendrasatpute.samplespringbootapi.client.OpenMeteoClient;
import io.github.rajendrasatpute.samplespringbootapi.client.SunriseSunsetClient;
import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.model.City;
import io.github.rajendrasatpute.samplespringbootapi.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final SunriseSunsetClient sunriseSunsetClient;
    private final OpenMeteoClient openMeteoClient;

    public CityInfoResponse getCityInfo(String cityName) {
        List<City> cities = cityRepository.findByCityName(cityName.toUpperCase());
        if (cities.isEmpty()) {
            return CityInfoResponse.builder().build();
        }
        City city = cities.get(0);
        Object sunriseAndSunset = sunriseSunsetClient.getSunriseSunsetTimes(city.getLatitude(), city.getLongitude());
        Object weather = openMeteoClient.getWeather(city.getLatitude(), city.getLongitude());
        return CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .sunriseAndSunset(sunriseAndSunset)
                .weather(weather)
                .build();
    }
}
