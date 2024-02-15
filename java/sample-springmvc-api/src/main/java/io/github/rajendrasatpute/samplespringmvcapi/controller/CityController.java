package io.github.rajendrasatpute.samplespringmvcapi.controller;

import io.github.rajendrasatpute.samplespringmvcapi.dto.CityInfoResponse;
import io.github.rajendrasatpute.samplespringmvcapi.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/city/{cityName}")
    public ResponseEntity<CityInfoResponse> getCityInfo(@PathVariable(value = "cityName") String cityName) {
        return ResponseEntity.ok().body(cityService.getCityInfo(cityName));
    }
}
