package io.github.rajendrasatpute.samplespringbootapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rajendrasatpute.samplespringbootapi.client.OpenMeteoClient;
import io.github.rajendrasatpute.samplespringbootapi.client.SunriseSunsetClient;
import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringbootapi.dto.UpdateCityRequest;
import io.github.rajendrasatpute.samplespringbootapi.exception.CityNotFoundException;
import io.github.rajendrasatpute.samplespringbootapi.model.City;
import io.github.rajendrasatpute.samplespringbootapi.repository.CityRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Nested
    class GetCityInfo {
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
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
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
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
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
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
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

    @Nested
    class AddCity {
        @Test
        void shouldSaveCityInDB() {
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();

            cityService.addCity(NewCityRequest.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build());

            verify(cityRepository, times(1)).save(city);
        }

        @Test
        void shouldThrowExceptionWhenThrown() {
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
            doThrow(RuntimeException.class).when(cityRepository).save(city);

            assertThrows(RuntimeException.class, () -> {
                cityService.addCity(NewCityRequest.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build());
            });
        }
    }

    @Nested
    class UpdateCityCoordinates {
        @Test
        void shouldUpdateCityCoordinates() {
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
            when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(city));

            assertDoesNotThrow(() -> {
                cityService.updateCityCoordinates("pune", UpdateCityRequest.builder().latitude("18.516728").longitude("73.856250").build());
            });

            verify(cityRepository, times(1)).findByCityName("PUNE");
            verify(cityRepository, times(1)).save(City.builder().cityName("PUNE").latitude("18.516728").longitude("73.856250").build());
        }

        @Test
        void shouldThrowCityNotFoundExceptionWhenCityIsNotFoundInDB() {
            when(cityRepository.findByCityName("PUNE")).thenReturn(List.of());

            assertThrows(CityNotFoundException.class, () -> {
                cityService.updateCityCoordinates("pune", UpdateCityRequest.builder().latitude("18.516728").longitude("73.856250").build());
            });

            verify(cityRepository, times(1)).findByCityName("PUNE");
            verify(cityRepository, times(0)).save(any());
        }
    }

    @Nested
    class DeleteCityCoordinates {
        @Test
        void shouldDeleteCityCoordinates() {
            City city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
            when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(city));

            assertDoesNotThrow(() -> {
                cityService.deleteCityCoordinates("pune");
            });

            verify(cityRepository, times(1)).findByCityName("PUNE");
            verify(cityRepository, times(1)).save(
                    City.builder().cityName("PUNE").latitude("18.516728").longitude("73.856250").deletionTimestamp(any()).build()
            );
        }

        @Test
        void shouldThrowCityNotFoundExceptionWhenCityIsNotFoundInDB() {
            when(cityRepository.findByCityName("PUNE")).thenReturn(List.of());

            assertThrows(CityNotFoundException.class, () -> {
                cityService.deleteCityCoordinates("pune");
            });

            verify(cityRepository, times(1)).findByCityName("PUNE");
            verify(cityRepository, times(0)).save(any());
        }

        @Test
        void shouldThrowCityNotFoundExceptionWhenCityIsAlreadySoftDeleted() {
            when(cityRepository.findByCityName("PUNE")).thenReturn(List.of(
                    City.builder()
                            .cityName("PUNE")
                            .latitude("18.516728")
                            .longitude("73.856250")
                            .deletionTimestamp(Timestamp.from(Instant.now()))
                            .build()
            ));

            assertThrows(CityNotFoundException.class, () -> {
                cityService.deleteCityCoordinates("pune");
            });

            verify(cityRepository, times(1)).findByCityName("PUNE");
            verify(cityRepository, times(0)).save(any());
        }
    }

}