package io.github.rajendrasatpute.samplespringbootapi.handler;

import io.github.rajendrasatpute.samplespringbootapi.constant.ErrorConstants;
import io.github.rajendrasatpute.samplespringbootapi.dto.ErrorResponse;
import io.github.rajendrasatpute.samplespringbootapi.exception.CityAlreadyExistsException;
import io.github.rajendrasatpute.samplespringbootapi.exception.CityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@RestControllerAdvice
public class CityExceptionHandler {
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(CityNotFoundException cityNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .errorMessage(cityNotFoundException.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .errorCode(ErrorConstants.CITY_NOT_FOUND_ERROR_CODE)
                        .build());
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCityAlreadyExistsException(CityAlreadyExistsException cityAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .errorMessage(cityAlreadyExistsException.getMessage())
                        .status(HttpStatus.CONFLICT)
                        .errorCode(ErrorConstants.CITY_ALREADY_EXISTS_ERROR_CODE)
                        .build());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(ConnectException connectException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .errorMessage("Temporary data error")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorCode(ErrorConstants.DB_CONNECTION_ERROR_CODE)
                        .build());
    }
}
