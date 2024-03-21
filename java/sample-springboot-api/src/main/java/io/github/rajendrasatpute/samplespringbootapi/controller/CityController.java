package io.github.rajendrasatpute.samplespringbootapi.controller;

import io.github.rajendrasatpute.samplespringbootapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringbootapi.dto.NewCityRequest;
import io.github.rajendrasatpute.samplespringbootapi.dto.UpdateCityRequest;
import io.github.rajendrasatpute.samplespringbootapi.exception.CityAlreadyExistsException;
import io.github.rajendrasatpute.samplespringbootapi.exception.CityNotFoundException;
import io.github.rajendrasatpute.samplespringbootapi.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/city/{cityName}")
    public ResponseEntity<CityInfoResponse> getCityInfo(@PathVariable String cityName) {
        return ResponseEntity.ok().body(cityService.getCityInfo(cityName));
    }

    @PostMapping("/city")
    public ResponseEntity<Object> addCity(@Valid @RequestBody NewCityRequest newCityRequest) throws CityAlreadyExistsException {
        cityService.addCity(newCityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/city/{cityName}")
    public ResponseEntity<Object> updateCityCoordinates(@PathVariable String cityName, @Valid @RequestBody UpdateCityRequest updateCityRequest) throws CityNotFoundException {
        cityService.updateCityCoordinates(cityName, updateCityRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/city/{cityName}")
    public ResponseEntity<Object> deleteCityCoordinates(@PathVariable String cityName) throws CityNotFoundException {
        cityService.deleteCityCoordinates(cityName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
