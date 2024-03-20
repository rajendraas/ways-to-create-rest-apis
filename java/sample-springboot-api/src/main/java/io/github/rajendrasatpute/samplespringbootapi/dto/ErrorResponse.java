package io.github.rajendrasatpute.samplespringbootapi.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private HttpStatus status;
}
