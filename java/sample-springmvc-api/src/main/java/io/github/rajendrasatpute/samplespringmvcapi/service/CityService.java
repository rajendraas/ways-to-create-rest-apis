package io.github.rajendrasatpute.samplespringmvcapi.service;

import io.github.rajendrasatpute.samplespringmvcapi.client.OpenMeteoClient;
import io.github.rajendrasatpute.samplespringmvcapi.client.SunriseSunsetClient;
import io.github.rajendrasatpute.samplespringmvcapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringmvcapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.dto.UpdateCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.exception.CityAlreadyExistsException;
import io.github.rajendrasatpute.samplespringmvcapi.exception.CityNotFoundException;
import io.github.rajendrasatpute.samplespringmvcapi.model.City;
import io.github.rajendrasatpute.samplespringmvcapi.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;
    private final SunriseSunsetClient sunriseSunsetClient;
    private final OpenMeteoClient openMeteoClient;

    public CityInfoResponse getCityInfo(String cityName) {
        City city = getCityCoordinates(cityName);
        if (null == city) {
            return CityInfoResponse.builder().build();
        }
        Object sunriseAndSunset = getSunriseSunsetTimesForCity(city);
        Object weather = getWeatherForCity(city);
        return CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .sunriseAndSunset(sunriseAndSunset)
                .weather(weather)
                .build();
    }
    
    public void addCity(NewCityRequest newCityRequest) throws CityAlreadyExistsException {
        if (null == getCityCoordinates(newCityRequest.getCityName())) {
            City city = City.builder()
                    .cityName(newCityRequest.getCityName().toUpperCase())
                    .latitude(newCityRequest.getLatitude())
                    .longitude(newCityRequest.getLongitude())
                    .build();
            cityRepository.save(city);
        } else {
            throw new CityAlreadyExistsException(newCityRequest.getCityName());
        }
    }

    public void updateCityCoordinates(String cityName, UpdateCityRequest updateCityRequest) throws CityNotFoundException {
        if (null != getCityCoordinates(cityName)) {
            City city = City.builder()
                    .cityName(cityName.toUpperCase())
                    .latitude(updateCityRequest.getLatitude())
                    .longitude(updateCityRequest.getLongitude())
                    .build();

            cityRepository.save(city);
        } else {
            throw new CityNotFoundException(cityName);
        }
    }

    public void deleteCityCoordinates(String cityName) throws CityNotFoundException {
        City cityToDelete = getCityCoordinates(cityName);
        if (null != cityToDelete) {
            cityToDelete.setDeletionTimestamp(Timestamp.from(Instant.now()));
            cityRepository.save(cityToDelete);
        } else {
            throw new CityNotFoundException(cityName);
        }
    }

    private City getCityCoordinates(String cityName) {
        City city = null;
        try {
            List<City> cities = cityRepository.findByCityName(cityName.toUpperCase());
            if (!cities.isEmpty() && !cities.get(0).isDeleted()) {
                city = cities.get(0);
            }
        } catch (Exception exception) {
            log.error("Error while fetching city coordinates", exception);
            throw exception;
        }
        return city;
    }

    private Object getSunriseSunsetTimesForCity(City city) {
        Object sunriseAndSunset = null;
        try {
            sunriseAndSunset = sunriseSunsetClient.getSunriseSunsetTimes(city.getLatitude(), city.getLongitude());
        } catch (Exception exception) {
            log.error("Error while fetching sunset sunrise details -  {}", exception);
        }
        return sunriseAndSunset;
    }

    private Object getWeatherForCity(City city) {
        Object weather = null;
        try {
            weather = openMeteoClient.getWeather(city.getLatitude(), city.getLongitude());
        } catch (Exception exception) {
            log.error("Error while fetching weather details -  {}", exception);
        }
        return weather;
    }
}
