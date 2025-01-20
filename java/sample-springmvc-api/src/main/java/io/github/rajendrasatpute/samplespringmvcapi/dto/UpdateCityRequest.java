package io.github.rajendrasatpute.samplespringmvcapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCityRequest {
    @NotBlank(message = "City latitude is required.")
    @Pattern(regexp = "^-?([0-8]?[0-9]|90)(\\.[0-9]{1,10})$", message = "The latitude is invalid.")
    private String latitude;
    @NotBlank(message = "City longitude is required.")
    @Pattern(regexp = "^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,10})$", message = "The longitude is invalid.")
    private String longitude;
}
