package io.github.rajendrasatpute.samplespringmvcapi.controller;

import io.github.rajendrasatpute.samplespringmvcapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringmvcapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.dto.UpdateCityRequest;
import io.github.rajendrasatpute.samplespringmvcapi.exception.CityAlreadyExistsException;
import io.github.rajendrasatpute.samplespringmvcapi.exception.CityNotFoundException;
import io.github.rajendrasatpute.samplespringmvcapi.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/city/{cityName}")
    public ResponseEntity<CityInfoResponse> getCityInfo(@PathVariable(value = "cityName") String cityName) {
        return ResponseEntity.ok().body(cityService.getCityInfo(cityName));
    }
    
    @PostMapping("/city")
    public ResponseEntity<Object> addCity(@Valid @RequestBody NewCityRequest newCityRequest) throws CityAlreadyExistsException {
        cityService.addCity(newCityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/city/{cityName}")
    public ResponseEntity<Object> updateCityCoordinates(@PathVariable(value = "cityName") String cityName, @Valid @RequestBody UpdateCityRequest updateCityRequest) throws CityNotFoundException {
        cityService.updateCityCoordinates(cityName, updateCityRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/city/{cityName}")
    public ResponseEntity<Object> deleteCityCoordinates(@PathVariable(value = "cityName") String cityName) throws CityNotFoundException {
        cityService.deleteCityCoordinates(cityName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
