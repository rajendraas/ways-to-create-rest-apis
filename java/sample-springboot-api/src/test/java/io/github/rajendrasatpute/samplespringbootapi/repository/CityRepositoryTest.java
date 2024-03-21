package io.github.rajendrasatpute.samplespringbootapi.repository;

import io.github.rajendrasatpute.samplespringbootapi.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    private City city;

    @BeforeEach
    void setupTestData() {
        city = City.builder().cityName("PUNE").latitude("18.516726").longitude("73.856255").build();
        cityRepository.save(city);
    }

    @Test
    void shouldReturnCityWhenQueriesByCityName() {
        List<City> foundCities = cityRepository.findByCityName("PUNE");

        assertFalse(foundCities.isEmpty());
        assertEquals(city, foundCities.get(0));
    }

    @Test
    void shouldReturnEmptyDataIfCityIsNotFound() {
        List<City> foundCities = cityRepository.findByCityName("MUMBAI");

        assertTrue(foundCities.isEmpty());
    }

}