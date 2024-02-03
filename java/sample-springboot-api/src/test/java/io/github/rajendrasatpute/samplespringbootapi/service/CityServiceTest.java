package io.github.rajendrasatpute.samplespringbootapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rajendrasatpute.samplespringbootapi.client.OpenMeteoClient;
import io.github.rajendrasatpute.samplespringbootapi.client.SunriseSunsetClient;
import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.model.City;
import io.github.rajendrasatpute.samplespringbootapi.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private SunriseSunsetClient sunriseSunsetClient;

    @InjectMocks
    private CityService cityService;

    @InjectMocks
    ObjectMapper objectMapper;

    @Test
    void shouldReturnEmptyCityInfoResponseIfCityIsNotFoundInDB() {
        when(cityRepository.findByCityName("PUNE")).thenReturn(List.of());

        CityInfoResponse cityInfoResponse = cityService.getCityInfo("pune");

        assertEquals(CityInfoResponse.builder().build(), cityInfoResponse);
    }

    @Test
    void shouldReturnEmptyCityInfoResponseIfDBThrowsException() {
        when(cityRepository.findByCityName("PUNE")).thenThrow(RuntimeException.class);

        CityInfoResponse cityInfoResponse = cityService.getCityInfo("pune");

        assertEquals(CityInfoResponse.builder().build(), cityInfoResponse);
    }

    @Test
    void shouldReturnEmptyWeatherIfWeatherAPIFails() throws IOException {
        Object sunriseAndSunsetResponse = objectMapper.readValue(new File("src/test/resources/responses/sunriseAndSunsetResponse.json"), Map.class);
        City city = new City("PUNE", "18.516726", "73.856255");
        CityInfoResponse expectedResponse = CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .sunriseAndSunset(sunriseAndSunsetResponse)
                .build();

        when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(city));
        when(openMeteoClient.getWeather(anyString(), anyString())).thenThrow(RuntimeException.class);
        when(sunriseSunsetClient.getSunriseSunsetTimes(anyString(), anyString())).thenReturn(sunriseAndSunsetResponse);

        CityInfoResponse cityInfoResponse = cityService.getCityInfo("pune");

        assertEquals(expectedResponse, cityInfoResponse);
    }

    @Test
    void shouldReturnEmptySunsetSunriseIfSunsetSunriseAPIFails() throws IOException {
        Object weather = objectMapper.readValue(new File("src/test/resources/responses/weatherResponse.json"), Map.class);
        City city = new City("PUNE", "18.516726", "73.856255");
        CityInfoResponse expectedResponse = CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .weather(weather)
                .build();

        when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(city));
        when(openMeteoClient.getWeather(anyString(), anyString())).thenReturn(weather);
        when(sunriseSunsetClient.getSunriseSunsetTimes(anyString(), anyString())).thenThrow(RuntimeException.class);

        CityInfoResponse cityInfoResponse = cityService.getCityInfo("pune");

        assertEquals(expectedResponse, cityInfoResponse);
    }

    @Test
    void shouldReturnAllData() throws IOException {
        Object weather = objectMapper.readValue(new File("src/test/resources/responses/weatherResponse.json"), Map.class);
        Object sunriseAndSunsetResponse = objectMapper.readValue(new File("src/test/resources/responses/sunriseAndSunsetResponse.json"), Map.class);
        City city = new City("PUNE", "18.516726", "73.856255");
        CityInfoResponse expectedResponse = CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .sunriseAndSunset(sunriseAndSunsetResponse)
                .weather(weather)
                .build();

        when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(city));
        when(openMeteoClient.getWeather(anyString(), anyString())).thenReturn(weather);
        when(sunriseSunsetClient.getSunriseSunsetTimes(anyString(), anyString())).thenReturn(sunriseAndSunsetResponse);

        CityInfoResponse cityInfoResponse = cityService.getCityInfo("pune");

        assertEquals(expectedResponse, cityInfoResponse);
    }

}