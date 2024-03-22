package io.github.rajendrasatpute.samplespringbootapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rajendrasatpute.samplespringbootapi.client.OpenMeteoClient;
import io.github.rajendrasatpute.samplespringbootapi.client.SunriseSunsetClient;
import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.model.City;
import io.github.rajendrasatpute.samplespringbootapi.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CityIntegrationTest {

    @MockBean
    private OpenMeteoClient openMeteoClient;

    @MockBean
    private SunriseSunsetClient sunriseSunsetClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CityRepository cityRepository;

    @InjectMocks
    ObjectMapper objectMapper;

    private City city;

    @BeforeEach
    void init() {
        city = City.builder().cityName("BANGALORE").latitude("12.971599").longitude("77.594566").build();
        cityRepository.save(city);
    }

    @Test
    void givenCityName_whenGETAPIIsCalled_thenCityCoordinatesWithWeatherAndSunriseSunsetShouldBeReturned() throws Exception {
        Object weather = objectMapper.readValue(new File("src/test/resources/responses/weatherResponse.json"), Map.class);
        Object sunriseAndSunsetResponse = objectMapper.readValue(new File("src/test/resources/responses/sunriseAndSunsetResponse.json"), Map.class);
        CityInfoResponse expectedResponse = CityInfoResponse.builder()
                .cityName(city.getCityName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .sunriseAndSunset(sunriseAndSunsetResponse)
                .weather(weather)
                .build();

        when(openMeteoClient.getWeather(anyString(), anyString())).thenReturn(weather);
        when(sunriseSunsetClient.getSunriseSunsetTimes(anyString(), anyString())).thenReturn(sunriseAndSunsetResponse);

        mockMvc.perform(get("/city/bangalore"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void givenCityCoordinates_whenCityPOSTCalled_thenCityCoordinatesShouldBeSavedInDB() throws Exception {
        mockMvc.perform(
                post("/city")
                        .content("{\"latitude\":\"18.516726\",\"longitude\":\"73.856255\",\"cityName\":\"Pune\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        List<City> foundCities = cityRepository.findByCityName("PUNE");
        assertFalse(foundCities.isEmpty());
        assertEquals(
                City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build(),
                foundCities.get(0)
        );
    }

    @Test
    void givenCityName_whenUpdateAPIIsCalled_thenCityCoordinatesShouldBeUpdatedInDB() throws Exception {
        mockMvc.perform(
                put("/city/bangalore").content("{\"latitude\":\"12.971590\",\"longitude\":\"77.594560\"}").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        List<City> foundCities = cityRepository.findByCityName("BANGALORE");
        assertFalse(foundCities.isEmpty());
        assertEquals(
                City.builder().cityName("BANGALORE").latitude("12.971590").longitude("77.594560").build(),
                foundCities.get(0)
        );
    }

    @Test
    void givenCityName_whenDeleteAPIIsCalled_thenDeletionTimestampShouldBeAddedForCity() throws Exception {
        mockMvc.perform(delete("/city/bangalore")).andExpect(status().isNoContent());

        List<City> foundCities = cityRepository.findByCityName("BANGALORE");
        assertFalse(foundCities.isEmpty());
        assertTrue(foundCities.get(0).isDeleted());
    }
}
