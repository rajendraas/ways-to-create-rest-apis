package io.github.rajendrasatpute.samplespringmvcapi.repository;

import io.github.rajendrasatpute.samplespringmvcapi.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, String> {
    List<City> findByCityName(String cityName);
}
