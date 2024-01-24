package io.github.rajendrasatpute.samplespringbootapi.repository;

import io.github.rajendrasatpute.samplespringbootapi.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, String> {
    List<City> findByCityName(String cityName);
}
