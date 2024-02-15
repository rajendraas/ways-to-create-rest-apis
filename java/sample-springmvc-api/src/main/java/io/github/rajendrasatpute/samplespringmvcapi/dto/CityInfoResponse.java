package io.github.rajendrasatpute.samplespringmvcapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityInfoResponse {
    private String cityName;
    private String latitude;
    private String longitude;
    private Object sunriseAndSunset;
    private Object weather;
}
